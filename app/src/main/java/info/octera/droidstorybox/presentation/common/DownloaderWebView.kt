package info.octera.droidstorybox.presentation.common

import android.app.DownloadManager
import android.net.Uri
import android.os.Environment
import android.view.ViewGroup
import android.webkit.URLUtil
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat.getSystemService

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DownloaderWebView(
    onDismissRequest: () -> Unit,
    onDownloadUrlResolved: (String, String) -> Unit,
    url: String
) {
    ModalBottomSheet(onDismissRequest = { onDismissRequest() }) {
        // Adding a WebView inside AndroidView
        // with layout as full screen
        AndroidView(factory = {
            WebView(it).apply {
                this.layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
                this.setDownloadListener { url, _, contentDisposition, mimeType, _ ->
                    val filename = URLUtil.guessFileName(url, contentDisposition, mimeType)
                    onDownloadUrlResolved(url, filename)
                }

                this.settings.javaScriptEnabled=true
                this.webViewClient = WebViewClient()
            }
        }, update = {
            it.loadUrl(url)
        })
    }
}