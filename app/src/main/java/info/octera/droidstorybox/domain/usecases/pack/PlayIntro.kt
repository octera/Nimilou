package info.octera.droidstorybox.domain.usecases.pack

import info.octera.droidstorybox.data.mediaplayer.MediaPlayerManager
import info.octera.droidstorybox.domain.model.pack.Pack
import info.octera.droidstorybox.domain.repository.PackRepository
import javax.inject.Inject

class PlayIntro @Inject constructor(
    private val packRepository: PackRepository,
    private val playerManager: MediaPlayerManager
) {
    operator fun invoke(pack: Pack) {
        val introMedia = pack.getFirstStage()?.audio
        introMedia?.let {
            playerManager.play(introMedia)
        }
    }
}