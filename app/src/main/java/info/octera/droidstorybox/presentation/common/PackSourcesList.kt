package info.octera.droidstorybox.presentation.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.tooling.preview.Preview
import info.octera.droidstorybox.domain.model.PackSource
import info.octera.droidstorybox.presentation.Dimens.ExtraSmallPadding2
import info.octera.droidstorybox.presentation.PreviewFakeData

@Composable
@Preview
fun PreviewPackSourcesList() {
    PackSourcesList(
        packSources = PreviewFakeData.packSources,
        onClick = {})
}

@Composable
fun PackSourcesList(
    modifier: Modifier = Modifier,
    packSources: List<PackSource>,
    onClick: (PackSource) -> Unit
) {
    LazyColumn(
        modifier = modifier.fillMaxWidth(),
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
    val localUriHandler = LocalUriHandler.current
    ListItem(
        trailingContent = {
            if (packSource.locked) {
                IconButton(onClick = { localUriHandler.openUri(packSource.link!!) }) {
                    Icon(Icons.Filled.Info, "More Info")
                }
            } else {
                IconButton(onClick = { onClick(packSource) }) {
                    Icon(Icons.Filled.Delete, "Delete Item", tint = Color.Red)
                }
            }
        },
        headlineContent = {
            Text(text = packSource.name)
        },
        supportingContent = {
            if (packSource.description?.isNotEmpty() == true) {
                Text(
                    text = packSource.description,
                    style = MaterialTheme.typography.labelSmall.copy()
                )
            } else {
                Text(
                    text = packSource.url,
                    style = MaterialTheme.typography.labelSmall.copy()
                )
            }
        },
    )
}
