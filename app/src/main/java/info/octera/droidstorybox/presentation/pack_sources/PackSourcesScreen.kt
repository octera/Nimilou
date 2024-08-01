package info.octera.droidstorybox.presentation.pack_sources

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.LargeFloatingActionButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import info.octera.droidstorybox.domain.model.PackSource
import info.octera.droidstorybox.presentation.PreviewFakeData
import info.octera.droidstorybox.presentation.common.PackSourcesList

@Composable
@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
fun PreviewPackSourcesScreen() {
    PackSourcesScreen(
        state = PackSourcesState(
            packSources = PreviewFakeData.packSources
        ),
        addPackSource = {},
        deletePackSource = {},
        )
}

@Composable
fun PackSourcesScreen(
    modifier: Modifier = Modifier,
    state: PackSourcesState,
    addPackSource: (PackSource) -> Unit,
    deletePackSource: (PackSource) -> Unit
) {
    var addDialogOpen by remember { mutableStateOf(false) }

    Scaffold (
        modifier = modifier,
        floatingActionButton = {
            FloatingActionButton(
                onClick = { addDialogOpen = true },
                shape = CircleShape,
            ) {
                Icon(Icons.Filled.Add, "Large floating action button")
            }
        }
    )
    { paddingValues ->
            PackSourcesList(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                packSources = state.packSources,
                onClick = {deletePackSource(it)}
            )
            if (addDialogOpen) {
                PackSourceDialog(
                    onDismissRequest = { addDialogOpen = false },
                    onConfirmation =  { addPackSource(it)})
            }
    }
}

@Preview
@Composable
fun PreviewPackSourceDialog() {
    PackSourceDialog(
        onDismissRequest = {},
        onConfirmation = {})
}
@Composable
fun PackSourceDialog(
    onDismissRequest: () -> Unit,
    onConfirmation: (PackSource) -> Unit,
) {
    var name by remember { mutableStateOf("") }
    var url by remember { mutableStateOf("") }
    fun validName(): Boolean = name.isNotEmpty()
    fun validUrl(): Boolean = url.startsWith("https://")
    fun validForm(): Boolean = validName() && validUrl()

    Dialog(onDismissRequest = { onDismissRequest() }) {
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
                OutlinedTextField(
                    value = name,
                    onValueChange = {name = it},
                    label = { Text("Name") },
                    isError = !validName(),
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = url,
                    onValueChange = {url = it},
                    label = { Text("URL") },
                    isError = !validUrl(),
                    modifier = Modifier.fillMaxWidth()
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
            ) {
                TextButton(
                    onClick = { onDismissRequest() },
                    modifier = Modifier.padding(16.dp),
                ) {
                    Text("Dismiss")
                }

                TextButton(
                    onClick = {
                        onConfirmation(PackSource(url = url, name= name))
                        onDismissRequest()
                    },
                    modifier = Modifier.padding(16.dp),
                    enabled = validForm()
                ) {
                    Text("Confirm")
                }
            }
        }
    }
}