package info.octera.droidstorybox.presentation.read_pack

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import info.octera.droidstorybox.domain.model.pack.Pack
import info.octera.droidstorybox.presentation.PreviewFakeData

@Preview(showBackground = true)
@Composable
fun PreviewReadPackScreen() {
    ReadPackScreen(pack = PreviewFakeData.pack)
}


@Composable
fun ReadPackScreen(
    pack: Pack
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        IconButton(onClick = { /*TODO*/ }) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Back"
            )
        }
    }
}