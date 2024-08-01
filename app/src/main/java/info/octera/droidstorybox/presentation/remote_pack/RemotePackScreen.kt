package info.octera.droidstorybox.presentation.remote_pack

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
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
            remotePack = PreviewFakeData.remotePacks
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
    val options = state.packSources.map { MyDropDownMenuItem(
        label = it.name,
        entity = it
    )}

    Scaffold (
        modifier = modifier,
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
        ) {
            if (state.packSources.isEmpty()) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        Icons.Filled.Warning,
                        "Warn",
                        modifier = Modifier.size(128.dp)
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
                    onClick = { fetchPack(it) })
            }
        }
    }
}