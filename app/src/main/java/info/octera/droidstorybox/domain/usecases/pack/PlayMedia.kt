package info.octera.droidstorybox.domain.usecases.pack

import android.net.Uri
import info.octera.droidstorybox.data.mediaplayer.MediaPlayerManager
import javax.inject.Inject

class PlayMedia @Inject constructor(
    private val playerManager: MediaPlayerManager
) {

    operator fun invoke(media: Uri) {
        playerManager.play(media)
    }
}