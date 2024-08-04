package info.octera.droidstorybox.presentation.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import info.octera.droidstorybox.domain.model.pack.PackMetadata
import info.octera.droidstorybox.presentation.Dimens.ArticleCardSize
import info.octera.droidstorybox.presentation.Dimens.ExtraSmallPadding2
import info.octera.droidstorybox.presentation.PreviewFakeData

@Composable
@Preview
fun PreviewLocalPackList() {
    LocalPackList(
        packsMetadata = PreviewFakeData.localPacks,
        onClick = {}
        )
}

@Composable
fun LocalPackList(
    modifier: Modifier = Modifier,
    packsMetadata: List<PackMetadata>,
    onClick: (PackMetadata) -> Unit
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(ExtraSmallPadding2),
        contentPadding = PaddingValues(all = ExtraSmallPadding2)
    ) {
        items(
            count = packsMetadata.size,
        ) {
            LocalPacksListItem(packMetadata = packsMetadata[it], onClick= onClick)
            }
        }
}

@Composable
fun LocalPacksListItem(packMetadata: PackMetadata, onClick: (PackMetadata) -> Unit) {
    val context = LocalContext.current
    ListItem(
        leadingContent = {
            AsyncImage(
                modifier =
                Modifier
                    .size(ArticleCardSize)
                    .clip(MaterialTheme.shapes.medium),
                model = ImageRequest.Builder(context).data(packMetadata.thumbsnail).build(),
                contentDescription = null,
                contentScale = ContentScale.Crop,
            )
        },
        trailingContent = {
            IconButton(onClick = { onClick(packMetadata) }) {
                Icon(Icons.Filled.Delete, "Delete Item", tint = Color.Red)
            }
        },
        headlineContent = {
            Text(text = packMetadata.title)
        },
        supportingContent = {
            Column {
                Text(
                    text = "Age: " + packMetadata.age,
                    style = MaterialTheme.typography.labelSmall.copy()
                )
                Text(
                    modifier = Modifier.padding(0.dp, 8.dp, 0.dp,0.dp),
                    text = packMetadata.description,
                    style = MaterialTheme.typography.bodySmall.copy()
                )
            }

        }
    )
}
