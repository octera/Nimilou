package info.octera.droidstorybox.domain.usecases.pack

import info.octera.droidstorybox.data.mediaplayer.MediaPlayerManager
import javax.inject.Inject

class TogglePause @Inject constructor(
    private val playerManager: MediaPlayerManager
) {

    operator fun invoke() {
        playerManager.togglePause()
    }
}