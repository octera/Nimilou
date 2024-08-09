package info.octera.droidstorybox.domain.usecases.pack

import android.net.Uri
import info.octera.droidstorybox.data.mediaplayer.MediaPlayerManager
import info.octera.droidstorybox.domain.model.PlayerState
import info.octera.droidstorybox.domain.repository.PackRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject
import kotlin.time.Duration

class PlayMedia @Inject constructor(
    private val playerManager: MediaPlayerManager
) {

    operator fun invoke(media: Uri) {
        playerManager.play(media)
    }
}