package info.octera.droidstorybox.presentation.local_packs

import android.content.Intent
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import info.octera.droidstorybox.domain.model.pack.PackMetadata
import info.octera.droidstorybox.presentation.PreviewFakeData
import info.octera.droidstorybox.presentation.common.LocalPackList

@Preview
@Composable
fun PreviewLocalPacksScreen() {
    LocalPacksScreen(
        state = LocalPacksState(
            packs = PreviewFakeData.localPacks
        ),
        deletePack = {},
        addPack = {}
    )
}

@Composable
fun LocalPacksScreen(
    modifier: Modifier = Modifier,
    state: LocalPacksState,
    deletePack: (PackMetadata) -> Unit,
    addPack: (Uri) -> Unit
) {
    val launcher =
        rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            println("selected file URI ${it.data?.data}")
            it.data?.data?.let { it1 -> addPack(it1) }
        }

    Scaffold(
        modifier = modifier,
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
                        .apply {
                            type = "*/*"
                            addCategory(Intent.CATEGORY_OPENABLE)
                        }
                    launcher.launch(intent)
                },
                shape = CircleShape,
            ) {
                Icon(Icons.Filled.Add, "Large floating action button")
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
        ) {
            if (state.packs.isEmpty()) {
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
                        text = "Please add pack",
                        style = MaterialTheme.typography.headlineLarge.copy(),
                        textAlign = TextAlign.Center

                    )
                }
            } else {
                LocalPackList(
                    packsMetadata = state.packs,
                    onClick = { deletePack(it) }
                )
            }
        }
    }
}