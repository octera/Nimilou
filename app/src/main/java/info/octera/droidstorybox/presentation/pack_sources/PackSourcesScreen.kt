package info.octera.droidstorybox.presentation.pack_sources

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.LargeFloatingActionButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import info.octera.droidstorybox.domain.model.PackSource
import info.octera.droidstorybox.presentation.common.PackSourcesList

@Composable
@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
fun PreviewPackSourcesScreen() {
    PackSourcesScreen(
        packSourcesState = PackSourcesState(
            packSources = listOf(
                PackSource("http://foo.bar/dslfkjslfjsfs", "List 1"),
                PackSource("http://foo.bar/dslfkjslfjsfs", "List 2")
            )
        ),
        )
}

@Composable
fun PackSourcesScreen(
    modifier: Modifier = Modifier,
    packSourcesState: PackSourcesState
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        PackSourcesList(
            packSources = packSourcesState.packSources,
            onClick = {}
        )
        FloatingActionButton(
            onClick = {  },
            shape = CircleShape,
        ) {
            Icon(Icons.Filled.Add, "Large floating action button")
        }
    }

}