package info.octera.droidstorybox.data.mediaplayer

import android.net.Uri
import androidx.annotation.OptIn
import androidx.media3.common.C
import androidx.media3.common.util.UnstableApi
import androidx.media3.datasource.DataSource
import androidx.media3.datasource.DataSpec
import androidx.media3.datasource.TransferListener
import java.io.EOFException
import java.io.File
import java.io.InputStream
import java.util.zip.ZipEntry
import java.util.zip.ZipFile

@OptIn(UnstableApi::class)
class ZipAssetMediaSource() : DataSource{
    private var zis: InputStream? = null
    private var entry: ZipEntry? = null
    private var uri: Uri? = null
    private var zipFile : ZipFile? = null

    override fun open(dataSpec: DataSpec): Long {
        if (dataSpec.uri.scheme != "pack") {
            throw IllegalStateException("bad URI scheme :" + dataSpec.uri)
        }
        if (dataSpec.uri.path?.contains("##") != true) {
            throw IllegalStateException("bad URI format ## " + dataSpec.uri.path)
        }
        val zipFilePath = dataSpec.uri.path!!.split("##")[0]
        val assetFilePath = dataSpec.uri.path!!.split("##")[1]

        val zipFile = ZipFile(File(zipFilePath))
        this.zipFile = zipFile

        val entry = this.zipFile!!.entries().asSequence().first { it.name == assetFilePath }
        this.entry = entry
        val zis = this.zipFile!!.getInputStream(entry)
        this.zis = zis

        val skipped = zis.skip(dataSpec.position)
        if (skipped < dataSpec.position) {
            // assetManager.open() returns an AssetInputStream, whose skip() implementation only skips
            // fewer bytes than requested if the skip is beyond the end of the asset's data.
            throw EOFException()
        }

        var bytesRemaining = 0L
        if (dataSpec.length.toInt() != C.LENGTH_UNSET) {
            bytesRemaining = dataSpec.length;
        } else {
            bytesRemaining = zis.available().toLong()
            if (bytesRemaining.toInt() == Integer.MAX_VALUE) {
                // assetManager.open() returns an AssetInputStream, whose available() implementation
                // returns Integer.MAX_VALUE if the remaining length is greater than (or equal to)
                // Integer.MAX_VALUE. We don't know the true length in this case, so treat as unbounded.
                bytesRemaining = C.LENGTH_UNSET.toLong();
            }
        }


        return entry!!.size

    }

    override fun read(buffer: ByteArray, offset: Int, length: Int): Int {
        TODO("Not yet implemented")
    }

    override fun addTransferListener(transferListener: TransferListener) {
        TODO("Not yet implemented")
    }



    override fun getUri(): Uri? {
        return uri
    }

    override fun close() {
        TODO("Not yet implemented")
    }
}

private final TransferListener<? super OBBDataSource> listener;

private Uri uri;
private InputStream inputStream;
private long bytesRemaining;
private boolean opened;
private Context context;

private String TAG = "OBB_DATA_SOURCE";

/**
 * @param context A context.
 */
public OBBDataSource(Context context) {
    this(context, null);
    this.context = context;
}

/**
 * @param context A context.
 * @param listener An optional listener.
 */
public OBBDataSource(Context context, TransferListener<? super OBBDataSource> listener) {
    this.context = context;
    this.listener = listener;
}

@Override
public long open(DataSpec dataSpec) throws OBBDataSourceException {
    try {
        uri = dataSpec.uri;
        String path = uri.getPath();
        if (path.startsWith("/android_asset/")) {
            path = path.substring(15);
        } else if (path.startsWith("/")) {
            path = path.substring(1);
        }
        inputStream = getInputStreamFromExpansionAPKFile(path);
        long skipped = inputStream.skip(dataSpec.position);
        if (skipped < dataSpec.position) {
            // assetManager.open() returns an AssetInputStream, whose skip() implementation only skips
            // fewer bytes than requested if the skip is beyond the end of the asset's data.
            throw new EOFException();
        }
        if (dataSpec.length != C.LENGTH_UNSET) {
            bytesRemaining = dataSpec.length;
        } else {
            bytesRemaining = inputStream.available();
            if (bytesRemaining == Integer.MAX_VALUE) {
                // assetManager.open() returns an AssetInputStream, whose available() implementation
                // returns Integer.MAX_VALUE if the remaining length is greater than (or equal to)
                // Integer.MAX_VALUE. We don't know the true length in this case, so treat as unbounded.
                bytesRemaining = C.LENGTH_UNSET;
            }
        }
    } catch (IOException e) {
        throw new OBBDataSourceException(e);
    }

    opened = true;
    if (listener != null) {
        listener.onTransferStart(this, dataSpec);
    }
    return bytesRemaining;
}

@Override
public int read(byte[] buffer, int offset, int readLength) throws OBBDataSourceException {
    if (readLength == 0) {
        return 0;
    } else if (bytesRemaining == 0) {
        return C.RESULT_END_OF_INPUT;
    }

    int bytesRead;
    try {
        int bytesToRead = bytesRemaining == C.LENGTH_UNSET ? readLength
        : (int) Math.min(bytesRemaining, readLength);
        bytesRead = inputStream.read(buffer, offset, bytesToRead);
    } catch (IOException e) {
        throw new OBBDataSourceException(e);
    }

    if (bytesRead == -1) {
        if (bytesRemaining != C.LENGTH_UNSET) {
            // End of stream reached having not read sufficient data.
            throw new OBBDataSourceException(new EOFException());
        }
        return C.RESULT_END_OF_INPUT;
    }
    if (bytesRemaining != C.LENGTH_UNSET) {
        bytesRemaining -= bytesRead;
    }
    if (listener != null) {
        listener.onBytesTransferred(this, bytesRead);
    }
    return bytesRead;
}

@Override
public Uri getUri() {
    return uri;
}

@Override
public void close() throws OBBDataSourceException {
    uri = null;
    try {
        if (inputStream != null) {
            inputStream.close();
        }
    } catch (IOException e) {
        throw new OBBDataSourceException(e);
    } finally {
        inputStream = null;
        if (opened) {
            opened = false;
            if (listener != null) {
                listener.onTransferEnd(this);
            }
        }
    }
}


private InputStream getInputStreamFromExpansionAPKFile(String fileName){

    // Get a ZipResourceFile representing a merger of both the main and patch files
    try {
        ZipResourceFile expansionFile = APKExpansionSupport.getAPKExpansionZipFile(context, BuildConfig.OBB_FILE_VERSION , -1);
        return expansionFile.getInputStream(fileName);


    } catch (IOException e) {
        Log.w(TAG, "Failed to find expansion file", e);
        return null;
    }

}