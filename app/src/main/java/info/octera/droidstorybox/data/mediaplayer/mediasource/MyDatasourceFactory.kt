package info.octera.droidstorybox.data.mediaplayer.mediasource

import android.net.Uri
import androidx.annotation.OptIn
import androidx.media3.common.util.UnstableApi
import androidx.media3.datasource.DataSource

class MyDatasourceFactory(private val uri: Uri) : DataSource.Factory{
    @OptIn(UnstableApi::class)
    override fun createDataSource(): DataSource {
        return when(uri.scheme) {
            ZipAssetDataSource.URI_SCHEME -> ZipAssetDataSource()
            else -> throw IllegalArgumentException("Unsupported scheme : " + uri.scheme)
        }
    }

}