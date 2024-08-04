package info.octera.droidstorybox.presentation.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import info.octera.droidstorybox.domain.model.PackSource
import info.octera.droidstorybox.presentation.Dimens.ExtraSmallPadding2

@Composable
@Preview
fun PreviewPackSourcesList() {
    PackSourcesList(
        packSources = listOf(
            PackSource("http://foo.bar/dslfkjslfjsfs", "List 1"),
            PackSource("http://foo.bar/dslfkjslfjsfs", "List 2")
        ),
        onClick = {})
}

@Composable
fun PackSourcesList(
    modifier: Modifier = Modifier,
    packSources: List<PackSource>,
    onClick: (PackSource) -> Unit
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(ExtraSmallPadding2),
        contentPadding = PaddingValues(all = ExtraSmallPadding2)
    ) {
        items(
            count = packSources.size,
        ) {
            packSources[it].let { packSource ->
                PackSourceListItem(packSource = packSource, onClick = { onClick(packSource) })
            }
        }
    }
}

@Composable
fun PackSourceListItem(packSource: PackSource, onClick: (PackSource) -> Unit) {
    ListItem(
        trailingContent = {
            IconButton(onClick = { onClick(packSource) }) {
                Icon(Icons.Filled.Delete, "Delete Item", tint = Color.Red)
            }
        },
        headlineContent = {
            Text(text = packSource.name)
        },
        supportingContent = {
            Text(
                text = packSource.url,
                style = MaterialTheme.typography.labelSmall.copy()
            )
        }
    )
}
