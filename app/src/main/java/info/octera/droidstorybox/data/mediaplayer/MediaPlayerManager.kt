package info.octera.droidstorybox.data.mediaplayer

import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.source.ProgressiveMediaSource

interface MediaPlayerManager {
    fun play()

}

class ExoMediaPlayerManager(private val player: ExoPlayer) : MediaPlayerManager {
    override fun play() {
        player.stop()
        player.clearMediaItems()
        val foo: ProgressiveMediaSource
        player.addMediaSource(Medias)
    }

}