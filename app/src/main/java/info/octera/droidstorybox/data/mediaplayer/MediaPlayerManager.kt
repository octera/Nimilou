package info.octera.droidstorybox.data.mediaplayer

import android.app.Application
import android.net.Uri
import android.os.Handler
import android.os.Looper
import androidx.annotation.OptIn
import androidx.media3.common.MediaItem
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.source.ProgressiveMediaSource
import info.octera.droidstorybox.data.mediaplayer.mediasource.MyDatasourceFactory
import info.octera.droidstorybox.domain.model.PlaybackState
import info.octera.droidstorybox.domain.model.PlayerState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlin.time.Duration
import kotlin.time.DurationUnit
import kotlin.time.toDuration

interface MediaPlayerManager {
    fun play(introMedia: Uri)

    fun getFlow(): StateFlow<PlayerState>
    fun togglePause()
}

@OptIn(UnstableApi::class)
class ExoMediaPlayerManager(
    private val application: Application,
    private val player: ExoPlayer
) : MediaPlayerManager {
    private val flow = MutableStateFlow(PlayerState())
    private val handler = Handler(Looper.getMainLooper())

    init {
        checkPlaybackPosition()
    }

    private fun checkPlaybackPosition(): Boolean = handler.postDelayed({
        if (isPaused) {
            flow.value = flow.value.copy(
                playbackState = PlaybackState.PAUSED)
        } else if (isEnded) {
            flow.value = flow.value.copy(
                playbackState = PlaybackState.ENDED,
                duration = Duration.ZERO,
                position = Duration.ZERO)
        } else {
            val duration = player.duration.toDuration(DurationUnit.MILLISECONDS)
            val position = player.contentPosition.toDuration(DurationUnit.MILLISECONDS)
            flow.value = flow.value.copy(
                playbackState = PlaybackState.PLAYING,
                duration = duration,
                position = position)
        }
        checkPlaybackPosition()
    }, 100L)

    override fun getFlow(): StateFlow<PlayerState> {
        return flow.asStateFlow()
    }

    override fun togglePause() {
        if (isPaused) {
            player.play()
        } else {
            player.pause()
        }
    }

    override fun play(introMedia: Uri) {
        val dsf = MyDatasourceFactory(introMedia)

        val mediaSource = ProgressiveMediaSource
            .Factory(dsf)
            .createMediaSource(MediaItem.fromUri(introMedia))

        player.stop()
        player.clearMediaItems()
        player.addMediaSource(mediaSource)
        player.prepare()
        player.play()

        flow.value = flow.value.copy(
            playbackState = PlaybackState.IDLE,
            duration = Duration.ZERO,
            position = Duration.ZERO)
    }

    private val isPaused: Boolean
        get() {
            return !player.isPlaying
                    && player.currentPosition > 1
                    && player.currentPosition < player.duration
        }

    private val isEnded: Boolean
        get() {
            return !player.isPlaying
                    && player.currentPosition > 1
                    && player.currentPosition >= player.duration
        }
}