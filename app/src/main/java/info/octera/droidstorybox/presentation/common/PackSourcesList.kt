package info.octera.droidstorybox.presentation.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import info.octera.droidstorybox.R
import info.octera.droidstorybox.domain.model.Article
import info.octera.droidstorybox.domain.model.PackSource
import info.octera.droidstorybox.presentation.Dimens.ExtraSmallPadding2
import info.octera.droidstorybox.presentation.Dimens.MediumPadding1

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
        verticalArrangement = Arrangement.spacedBy(MediumPadding1),
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
fun PackSourceListItem(packSource: PackSource, onClick: () -> Unit) {
    Column() {
        Text(
            text = packSource.name,
            style = MaterialTheme.typography.bodyMedium.copy(),
            color = colorResource(id = R.color.text_title),
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )
        Text(
            text = packSource.url,
            style = MaterialTheme.typography.labelSmall,
            color = colorResource(id = R.color.body)
        )
    }
}
