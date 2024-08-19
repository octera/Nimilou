package info.octera.droidstorybox.data.mediaplayer.mediasource

import android.net.Uri
import androidx.annotation.OptIn
import androidx.media3.common.C
import androidx.media3.common.util.UnstableApi
import androidx.media3.datasource.DataSource
import androidx.media3.datasource.DataSpec
import androidx.media3.datasource.TransferListener
import java.io.EOFException
import java.io.File
import java.io.IOException
import java.io.InputStream
import java.util.zip.ZipEntry
import java.util.zip.ZipFile

@OptIn(UnstableApi::class)
class ZipAssetDataSource() : DataSource{
    companion object {
        val URI_SCHEME : String = "pack"
    }
    private var dataspec: DataSpec? = null
    private var listener: TransferListener? = null
    private var zis: InputStream? = null
    private var entry: ZipEntry? = null
    private var uri: Uri? = null
    private var zipFile : ZipFile? = null

    override fun open(dataSpec: DataSpec): Long {
        if (dataSpec.uri.scheme != URI_SCHEME) {
            throw IllegalStateException("bad URI scheme :" + dataSpec.uri)
        }
        if (dataSpec.uri.path == null || dataSpec.uri.path!!.isEmpty()) {
            throw IllegalStateException("file not defined " + dataSpec.uri.path)
        }
        if (dataSpec.uri.fragment == null || dataSpec.uri.fragment!!.isEmpty()) {
            throw IllegalStateException("asset not defined " + dataSpec.uri.fragment)
        }
        this.uri = dataSpec.uri
        val zipFilePath = dataSpec.uri.path!!
        val assetFilePath = dataSpec.uri.fragment!!

        val zipFile = ZipFile(File(zipFilePath))
        this.zipFile = zipFile

        val entry = this.zipFile!!.entries().asSequence().first { it.name == assetFilePath }
        this.entry = entry
        val zis = this.zipFile!!.getInputStream(entry)
        this.zis = zis

        if (dataSpec.position == entry.size) {
            return C.RESULT_END_OF_INPUT.toLong()
        }

        if (dataSpec.position == entry.size) {
            throw IOException()
        }
        this.dataspec = dataSpec

        zis.skip(dataSpec.position)

        return entry!!.size - dataSpec.position
    }

    override fun read(buffer: ByteArray, offset: Int, readLength: Int): Int {
        if (this.zis == null) throw IOException("Attemp to read a non openend stream")

        val zis = this.zis!!
        val bytesRemaining = zis.available()

        if (readLength == 0) {
            return 0
        } else if (bytesRemaining == 0) {
            return C.RESULT_END_OF_INPUT;
        }

        val bytesToRead = if (bytesRemaining == C.LENGTH_UNSET)
            readLength else Math.min(bytesRemaining, readLength)

        val bytesRead = zis.read(buffer, offset, bytesToRead);

        if (bytesRead == -1) {
            if (bytesRemaining != C.LENGTH_UNSET) {
                // End of stream reached having not read sufficient data.
                throw IOException(EOFException())
            }
            return C.RESULT_END_OF_INPUT;
        }
        dataspec?.let { listener?.onBytesTransferred(this, it, false, bytesRead) };

        return bytesRead;
    }

    override fun addTransferListener(transferListener: TransferListener) {
        this.listener = transferListener
    }

    override fun getUri(): Uri? {
        return uri
    }

    override fun close() {
        this.zis?.close()
        this.zipFile?.close()
        this.uri = null
        this.entry = null
        this.listener = null
        this.dataspec = null
    }
}