package info.octera.droidstorybox.domain.model

import kotlin.time.Duration

data class PlayerState (
    val playbackState : PlaybackState = PlaybackState.IDLE,
    val duration: Duration = Duration.ZERO,
    val position: Duration = Duration.ZERO
)

enum class PlaybackState { IDLE, PLAYING, ENDED, PAUSED }