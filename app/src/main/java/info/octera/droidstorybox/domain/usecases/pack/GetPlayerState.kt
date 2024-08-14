package info.octera.droidstorybox.domain.usecases.pack

import info.octera.droidstorybox.data.mediaplayer.MediaPlayerManager
import info.octera.droidstorybox.domain.model.PlayerState
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class GetPlayerState @Inject constructor(
    private val playerManager: MediaPlayerManager
) {

    operator fun invoke(): StateFlow<PlayerState> {
        return playerManager.getFlow()
    }
}