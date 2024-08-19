package info.octera.droidstorybox.data.repository

import android.annotation.SuppressLint
import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.os.Handler
import android.os.Looper
import android.provider.OpenableColumns
import android.util.Log
import androidx.core.net.toFile
import dagger.hilt.android.qualifiers.ApplicationContext
import info.octera.droidstorybox.data.file.FileSource
import info.octera.droidstorybox.data.remote.BasicHttpSource
import info.octera.droidstorybox.domain.model.ProgressState
import info.octera.droidstorybox.domain.model.pack.PackMetadata
import info.octera.droidstorybox.domain.repository.PacksRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onEach
import java.io.File
import java.io.InputStream
import java.io.OutputStream
import javax.inject.Inject


class PacksRepositoryImpl @Inject constructor(
    @ApplicationContext private val appContext: Context,
    private val basicHttpSource: BasicHttpSource,
    private val fileSource: FileSource
) : PacksRepository {
    private val packFileList = MutableStateFlow(emptyList<File>())
    private val location = "packs"

    init {
        refreshPackFiles()
    }

    override fun getPacksFile(): Flow<List<File>> {
        return packFileList
    }

    override suspend fun addPack(uri: Uri): Flow<ProgressState> {
        if (uri.scheme == "content") {
            val istream = appContext.contentResolver.openInputStream(uri)!!
            val fileName = getFileName(uri)
            val fileSize = getFileSize(uri)

            val fileOutputStream = fileSource.getOutputStreamToFile(location, fileName)
            return copyStreamWithProgress(istream, fileOutputStream, fileSize.toLong())
                .onEach { if (it is ProgressState.Finished) refreshPackFiles() }
        } else {
            throw IllegalArgumentException("Protocol not supporter " + uri.encodedSchemeSpecificPart)
        }
    }

    override suspend fun deletePack(packMetadata: PackMetadata) {
        val res = packMetadata.uri.toFile().delete()
        refreshPackFiles()
        Log.e("DELETE", "deleted? $res")
    }

    override suspend fun downloadPackFile(downloadUrl: String, fileName: String): Flow<ProgressState> {
        val response = basicHttpSource.downloadFile(downloadUrl)
        val fileOutputStream = fileSource.getOutputStreamToFile(location, fileName)
        val body = response.body()!!

        val inputStream = body.byteStream()
        val outputStream= fileOutputStream
        val totalBytes = body.contentLength()

        return copyStreamWithProgress(inputStream, outputStream, totalBytes)
            .onEach { if (it is ProgressState.Finished) refreshPackFiles() }
    }

    private suspend fun copyStreamWithProgress(
        inputStream: InputStream,
        outputStream: OutputStream,
        totalBytes: Long = -1
    ): Flow<ProgressState> {
        return flow {
            emit(ProgressState.Progressing(0))
            try {
                val buffer = ByteArray(DEFAULT_BUFFER_SIZE)
                var progressBytes = 0L
                var bytes = inputStream.read(buffer)
                while (bytes >= 0) {
                    outputStream.write(buffer, 0, bytes)
                    progressBytes += bytes
                    bytes = inputStream.read(buffer)
                    emit(ProgressState.Progressing(((progressBytes * 100.0) / totalBytes).toInt()))
                }
                Log.i("File", "File written successfully : $totalBytes written")
                emit(ProgressState.Finished)
            } catch (e: Exception) {
                emit(ProgressState.Failed(e))
            }
        }.flowOn(Dispatchers.IO).distinctUntilChanged()
    }

    @SuppressLint("Range")
    private fun getFileName(uri: Uri): String {
        val cursor: Cursor? = appContext.contentResolver.query(
            uri,
            null,
            null,
            null,
            null
        )

        cursor?.use {
            if (cursor.moveToFirst()) {
                val displayName = it.getString(it.getColumnIndex(OpenableColumns.DISPLAY_NAME))
                Log.i("FILE", "Display Name: $displayName")
                return displayName
            }
        }
        throw IllegalStateException("Unknown filename")
    }

    @SuppressLint("Range")
    private fun getFileSize(uri: Uri): Int {
        val cursor: Cursor? = appContext.contentResolver.query(
            uri,
            null,
            null,
            null,
            null
        )

        cursor?.use {
            if (cursor.moveToFirst()) {
                val size = it.getInt(it.getColumnIndex(OpenableColumns.SIZE))
                Log.i("FILE", "Size: $size")
                return size
            }
        }
        throw IllegalStateException("Unknown filename")
    }

    private fun refreshPackFiles() {
        packFileList.value = fileSource.listFiles(location)
    }


}