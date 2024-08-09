package info.octera.droidstorybox.presentation.read_pack

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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import info.octera.droidstorybox.R
import info.octera.droidstorybox.domain.model.pack.ControlSettings
import info.octera.droidstorybox.domain.model.pack.Pack
import info.octera.droidstorybox.domain.model.pack.Stage
import info.octera.droidstorybox.presentation.PreviewFakeData
import kotlinx.coroutines.launch
import kotlin.time.DurationUnit
import kotlin.time.toDuration

@Preview(showBackground = true)
@Composable
fun PreviewReadPackScreen() {
    ReadPackScreen(
        pack = PreviewFakeData.pack,
        currentStages = listOf(PreviewFakeData.stage),
        playerInfo = PlayerInfo(
            duration = 10.toDuration(DurationUnit.SECONDS),
            position = 5.toDuration(DurationUnit.SECONDS),
        ),
        onBackPressed = {},
        onPauseClick = {},
        onOkClick = {},
        setSelectedStage = {}
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReadPackScreen(
    pack: Pack?,
    currentStages: List<Stage>,
    playerInfo: PlayerInfo,
    onBackPressed: () -> Unit,
    onPauseClick: () -> Unit,
    onOkClick: () -> Unit,
    setSelectedStage: (Int) -> Unit
) {
    val pagerState = rememberPagerState { currentStages.count() }
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
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
            HorizontalPager(
                state = pagerState,
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.5F),
                contentPadding = PaddingValues(horizontal = 32.dp),
                pageSpacing = 8.dp
            ) { page ->
                val stage = currentStages[page]
                Card {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Image(
                            modifier = Modifier
                                .height(240.dp)
                                .width(320.dp),
                            painter = rememberAsyncImagePainter(model = stage.image),
                            contentDescription = stage.name,
                            contentScale = ContentScale.Crop
                        )
                    }
                }
            }
            Row(
                modifier = Modifier
                    .padding(20.dp, 8.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "${playerInfo.position.inWholeMinutes}:${playerInfo.position.inWholeSeconds % 60}")
                LinearProgressIndicator(
                    modifier = Modifier.fillMaxWidth(0.7f),
                    progress = { playerInfo.position.div(playerInfo.duration).toFloat() }
                )
                Text(text = "${playerInfo.duration.inWholeMinutes}:${playerInfo.duration.inWholeSeconds % 60}")
            }
            Controls(
                playerInfo = playerInfo,
                controlSettings = currentStages
                    .getOrNull(pagerState.currentPage)
                    ?.controlSettings ?: ControlSettings(),
                onPauseClick = onPauseClick,
                onOkClick = onOkClick,
                onLeftClick = {
                    if (pagerState.canScrollBackward) {
                        coroutineScope.launch {
                            pagerState.animateScrollToPage(pagerState.currentPage - 1)
                            setSelectedStage(pagerState.currentPage)
                        }
                    }
                },
                onRightClick = {
                    if (pagerState.canScrollForward) {
                        coroutineScope.launch {
                            pagerState.animateScrollToPage(pagerState.currentPage + 1)
                            setSelectedStage(pagerState.currentPage)
                        }
                    }
                },
            )


        }
    }
}

@Composable
fun Controls(
    controlSettings: ControlSettings,
    onPauseClick: () -> Unit,
    onOkClick: () -> Unit,
    onLeftClick: () -> Unit,
    onRightClick: () -> Unit,
    playerInfo: PlayerInfo,
) {
    Row(
        modifier = Modifier
            .padding(8.dp, 32.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        FilledIconButton(
            onClick = { onPauseClick() },
            enabled = controlSettings.pause,
            modifier = Modifier.size(64.dp)
        ) {
            if (playerInfo.playing) {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_pause_24),
                    "",
                    modifier = Modifier.fillMaxSize()
                )
            } else {
                Icon(
                    imageVector = Icons.Filled.PlayArrow,
                    "",
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
        FilledIconButton(
            onClick = { onOkClick() },
            enabled = controlSettings.ok || !playerInfo.playing,
            modifier = Modifier.size(64.dp)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.baseline_check_24),
                "",
                modifier = Modifier.fillMaxSize()
            )
        }
    }
    Row(
        modifier = Modifier
            .padding(8.dp, 32.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        FilledIconButton(
            onClick = { onLeftClick() },
            enabled = controlSettings.wheel,
            modifier = Modifier.size(64.dp)
        )
        {
            Icon(
                Icons.AutoMirrored.Filled.ArrowBack,
                "",
                modifier = Modifier.fillMaxSize()
            )
        }
        FilledIconButton(
            onClick = { onRightClick() },
            enabled = controlSettings.wheel,
            modifier = Modifier.size(64.dp)
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