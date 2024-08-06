package info.octera.droidstorybox.data.mediaplayer

import android.net.Uri
import androidx.annotation.OptIn
import androidx.media3.common.MediaItem
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.source.ProgressiveMediaSource
import info.octera.droidstorybox.data.mediaplayer.mediasource.MyDatasourceFactory

interface MediaPlayerManager {
    fun play(introMedia: Uri)

}

@OptIn(UnstableApi::class)
class ExoMediaPlayerManager(private val player: ExoPlayer) : MediaPlayerManager {
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
    }

}