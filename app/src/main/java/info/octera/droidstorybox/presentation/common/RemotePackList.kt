package info.octera.droidstorybox.presentation.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import info.octera.droidstorybox.R
import info.octera.droidstorybox.domain.model.RemotePack
import info.octera.droidstorybox.presentation.Dimens.ArticleCardSize
import info.octera.droidstorybox.presentation.Dimens.ExtraSmallPadding2
import info.octera.droidstorybox.presentation.PreviewFakeData

@Composable
@Preview
fun PreviewRemotePackList() {
    RemotePackList(
        remotePacks = PreviewFakeData.remotePacks,
        onClick = {})
}

@Composable
fun RemotePackList(
    modifier: Modifier = Modifier,
    remotePacks: List<RemotePack>,
    onClick: (RemotePack) -> Unit
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(ExtraSmallPadding2),
        contentPadding = PaddingValues(all = ExtraSmallPadding2)
    ) {
        items(
            count = remotePacks.size,
        ) {
            remotePacks[it].let { remotePack ->
                RemotePackListItem(remotePack = remotePack, onClick = { onClick(remotePack) })
            }
        }
    }
}

@Composable
fun RemotePackListItem(remotePack: RemotePack, onClick: (RemotePack) -> Unit) {
    val context = LocalContext.current
    ListItem(
        leadingContent = {
            AsyncImage(
                modifier =
                Modifier
                    .size(ArticleCardSize)
                    .clip(MaterialTheme.shapes.medium),
                model = ImageRequest.Builder(context).data(remotePack.thumbs.small).build(),
                contentDescription = null,
                contentScale = ContentScale.Crop,
            )
        },
        trailingContent = {
            IconButton(onClick = { onClick(remotePack) }) {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_cloud_download_24),
                    "Delete Item"
                )
            }
        },
        headlineContent = {
            Text(text = remotePack.title)
        },
        supportingContent = {
            Column {
                Text(
                    text = "Age: " + remotePack.age,
                    style = MaterialTheme.typography.labelSmall.copy()
                )
                Text(
                    text = remotePack.updatedAt,
                    style = MaterialTheme.typography.labelSmall.copy()
                )
                Text(
                    modifier = Modifier.padding(0.dp, 8.dp, 0.dp, 0.dp),
                    text = remotePack.description,
                    style = MaterialTheme.typography.bodySmall.copy()
                )
            }

        }
    )
}
