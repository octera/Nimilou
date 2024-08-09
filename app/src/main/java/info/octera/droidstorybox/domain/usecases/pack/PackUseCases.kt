package info.octera.droidstorybox.domain.usecases.pack

import javax.inject.Inject

data class PackUseCases @Inject constructor(
    val getPack: GetPack,
    val playMedia: PlayMedia,
    val togglePause: TogglePause,
    val getPlayerState: GetPlayerState
)