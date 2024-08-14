package info.octera.droidstorybox.presentation.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Card
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import coil.compose.rememberAsyncImagePainter
import info.octera.droidstorybox.domain.model.pack.PackMetadata
import info.octera.droidstorybox.presentation.PreviewFakeData
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.launch
import kotlin.math.absoluteValue

@Preview(
    showSystemUi = true,
    showBackground = true,
)
@Composable
fun PreviewHomeScreeen() {
    HomeScreen(
        packs = PreviewFakeData.localPacks,
        onSettingsClicked = {},
        onPackFocused = {},
        onPackSelected = {}
    )
}

@OptIn(FlowPreview::class)
@Composable
fun HomeScreen(
    packs: List<PackMetadata>,
    onSettingsClicked: () -> Unit,
    onPackFocused: (PackMetadata) -> Unit,
    onPackSelected: (PackMetadata) -> Unit,
) {
    val pagerState = rememberPagerState { packs.count() }
    val coroutineScope = rememberCoroutineScope()

    if(packs.isNotEmpty()) {
        LaunchedEffect(pagerState) {
            snapshotFlow { pagerState.currentPage }
                .debounce(500)
                .collect { page ->
                    onPackFocused(packs[page])
                }
        }
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        floatingActionButton = {
            FloatingActionButton(
                shape = CircleShape,
                onClick = {
                    onSettingsClicked()
                },
            ) {
                Icon(Icons.Filled.Settings, "Large floating action button")
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center
        ) {
            HorizontalPager(
                state = pagerState,
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.5F),
                contentPadding = PaddingValues(horizontal = 32.dp),
                pageSpacing = 8.dp
            ) { page ->
                val item = packs[page]
                Card(
                    modifier = Modifier
                        .graphicsLayer {
                            // Calculate the absolute offset for the current page from the
                            // scroll position. We use the absolute value which allows us to mirror
                            // any effects for both directions
                            val pageOffset = (
                                    (pagerState.currentPage - page) + pagerState
                                        .currentPageOffsetFraction
                                    ).absoluteValue

                            // We animate the alpha, between 50% and 100%
                            alpha = lerp(
                                start = 0.5f,
                                stop = 1f,
                                fraction = 1f - pageOffset.coerceIn(0f, 1f)
                            )
                        }

                ) {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Image(
                            modifier = Modifier
                                .height(205.dp),
                            painter = rememberAsyncImagePainter(model = item.thumbsnail),
                            contentDescription = item.title,
                            contentScale = ContentScale.Crop
                        )
                    }
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight()
                            .padding(8.dp),
                        textAlign = TextAlign.Center,
                        text = item.description
                    )
                }

            }
            Row(modifier = Modifier
                .padding(8.dp, 64.dp)
                .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround) {
                FilledIconButton(
                    modifier = Modifier.size(64.dp),
                    enabled = pagerState.canScrollBackward,
                    onClick = {
                        coroutineScope.launch {
                            if (pagerState.canScrollBackward) {
                                pagerState.animateScrollToPage(pagerState.currentPage - 1)
                            }
                        }
                    },
                )
                {
                    Icon(
                        Icons.AutoMirrored.Filled.ArrowBack,
                        "",
                        modifier = Modifier.fillMaxSize()
                    )
                }
                FilledIconButton(
                    modifier = Modifier.size(64.dp),
                    onClick = { onPackSelected(packs[pagerState.currentPage]) },
                ) {
                    Icon(
                        Icons.Filled.PlayArrow,
                        "",
                        modifier = Modifier.fillMaxSize()
                    )
                }
                FilledIconButton(
                    modifier = Modifier.size(64.dp),
                    enabled = pagerState.canScrollForward,
                    onClick = {
                        coroutineScope.launch {
                            if (pagerState.canScrollForward) {
                                pagerState.animateScrollToPage(pagerState.currentPage + 1)
                            }
                        }
                    },
                )
                {
                    Icon(
                        Icons.AutoMirrored.Filled.ArrowForward,
                        "",
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }
        }

    }

}
