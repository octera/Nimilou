package info.octera.droidstorybox.presentation.read_pack

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import info.octera.droidstorybox.domain.model.pack.Pack
import info.octera.droidstorybox.domain.model.pack.Stage
import info.octera.droidstorybox.presentation.PreviewFakeData
import kotlinx.coroutines.launch

@Preview(showBackground = true)
@Composable
fun PreviewReadPackScreen() {
    ReadPackScreen(
        pack = PreviewFakeData.pack,
        stage = PreviewFakeData.stage,
        onBackPressed = {}
        )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReadPackScreen(
    pack: Pack?,
    stage: Stage?,
    onBackPressed: () -> Unit
) {
    Scaffold (
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = { onBackPressed() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
                title = {
                    Text(pack?.metadata?.title ?: "")
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            if (stage != null) {
                Image(
                    modifier = Modifier
                        .height(205.dp),
                    painter = rememberAsyncImagePainter(model = stage.image),
                    contentDescription = stage.name,
                    contentScale = ContentScale.Crop
                )
                Row(modifier = Modifier.padding(0.dp, 20.dp)) {
                    IconButton(
                        modifier = Modifier.fillMaxWidth(0.33F),
                        onClick = {},
                    )
                    {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            "",
                            modifier = Modifier.size(128.dp)
                        )
                    }
                    IconButton(
                        modifier = Modifier.fillMaxWidth(0.5F),
                        onClick = {},
                    ) {
                        Icon(
                            Icons.Filled.PlayArrow,
                            "",
                            modifier = Modifier.size(128.dp)
                        )
                    }
                    IconButton(
                        modifier = Modifier.fillMaxWidth(1.0F),
                        onClick = {},
                    )
                    {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowForward,
                            "",
                            modifier = Modifier.size(128.dp)
                        )
                    }
                }
            }

        }
    }
}