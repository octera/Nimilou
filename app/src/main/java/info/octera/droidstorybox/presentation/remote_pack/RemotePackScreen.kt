package info.octera.droidstorybox.presentation.remote_pack

import android.app.DownloadManager
import android.net.Uri
import android.os.Environment
import android.view.ViewGroup
import android.webkit.URLUtil
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.window.Dialog
import androidx.core.content.ContextCompat.getSystemService
import info.octera.droidstorybox.domain.model.PackSource
import info.octera.droidstorybox.domain.model.RemotePack
import info.octera.droidstorybox.presentation.PreviewFakeData
import info.octera.droidstorybox.presentation.common.MyDropDownMenuItem
import info.octera.droidstorybox.presentation.common.MyExposedDropdownMenu
import info.octera.droidstorybox.presentation.common.RemotePackList


@Preview
@Composable
fun PreviewRemotePackScreen() {
    RemotePackScreen(
        state = RemotePackState(
            packSources = PreviewFakeData.packSources,
            remotePack = PreviewFakeData.remotePacks,
            downloadProgress = 50
        ),
        fetchPacksFromPackSource = {},
        fetchPack = {}
    )
}

@Preview
@Composable
fun PreviewRemotePackScreenEmptySource() {
    RemotePackScreen(
        state = RemotePackState(
            packSources = listOf(),
            remotePack = listOf()
        ),
        fetchPacksFromPackSource = {},
        fetchPack = {}
    )
}

@Composable
fun RemotePackScreen(
    modifier: Modifier = Modifier,
    state: RemotePackState,
    fetchPacksFromPackSource: (PackSource) -> Unit,
    fetchPack: (RemotePack) -> Unit
) {
    var showBottomSheet by remember { mutableStateOf(false) }
    var urlBottomSheet by remember { mutableStateOf(null as String?) }


    val options = state.packSources.map {
        MyDropDownMenuItem(
            label = it.name,
            entity = it
        )
    }

    Scaffold(
        modifier = modifier,
        contentWindowInsets = WindowInsets(0.dp)
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
        ) {
            if (state.downloading) {
                Dialog(onDismissRequest = {}) {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()

                            .padding(16.dp),
                        shape = RoundedCornerShape(16.dp),
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp)
                        ) {
                            Text(
                                modifier = Modifier.fillMaxWidth(),
                                text = "Downloading",
                                style = MaterialTheme.typography.headlineMedium.copy(),
                                textAlign = TextAlign.Center
                            )
                            LinearProgressIndicator(
                                progress = { state.downloadProgress / 100.0f },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(8.dp, 12.dp),
                            )

                        }
                    }
                }
            }

            if (state.packSources.isEmpty()) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        Icons.Filled.Warning,
                        "Warn",
                        modifier = Modifier
                            .size(128.dp)
                            .padding(20.dp)
                    )
                    Text(
                        text = "Please add remote pack sources",
                        style = MaterialTheme.typography.headlineLarge.copy(),
                        textAlign = TextAlign.Center

                    )
                }
            } else {
                MyExposedDropdownMenu(
                    modifier = Modifier.fillMaxWidth(),
                    label = "Source",
                    options = options,
                    onSelectedOptionChange = { fetchPacksFromPackSource(it) }
                )
                RemotePackList(
                    remotePacks = state.remotePack,
                    onClick = {
                        // fetchPack(it)
                        showBottomSheet = true
                        urlBottomSheet = it.download
                    })
            }
        }
    }
    if (showBottomSheet && urlBottomSheet != null) {
        ModalWebView(
            url = urlBottomSheet!!,
            onDismissRequest = { showBottomSheet = false },
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ModalWebView(
    onDismissRequest: () -> Unit,
    url: String
) {
    val context = LocalContext.current

    ModalBottomSheet(onDismissRequest = { onDismissRequest() }) {
        // Adding a WebView inside AndroidView
        // with layout as full screen
        AndroidView(factory = {
            WebView(it).apply {
                this.layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
                this.setDownloadListener { url, userAgent, contentDisposition, mimeType, lenght ->
                    val filename = URLUtil.guessFileName(url, contentDisposition, mimeType)
                    val request = DownloadManager.Request(Uri.parse(url))
                    request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED) //Notify client once download is completed!
                    request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, filename);
                    val dm = getSystemService(context, DownloadManager::class.java) as DownloadManager
                    dm.enqueue(request)
                    onDismissRequest()
                }

                this.settings.javaScriptEnabled=true
                this.webViewClient = WebViewClient()
            }
        }, update = {
            it.loadUrl(url)
        })
    }
}