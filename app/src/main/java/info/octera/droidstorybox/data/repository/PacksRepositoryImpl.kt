package info.octera.droidstorybox.data.repository

import android.util.Log
import info.octera.droidstorybox.data.file.FileSource
import info.octera.droidstorybox.data.remote.BasicHttpSource
import info.octera.droidstorybox.domain.model.DownloadState
import info.octera.droidstorybox.domain.model.RemotePack
import info.octera.droidstorybox.domain.repository.PacksRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.util.UUID
import javax.inject.Inject


class PacksRepositoryImpl @Inject constructor(
    val basicHttpSource: BasicHttpSource,
    val fileSource: FileSource
):PacksRepository {
    override suspend fun downloadPack(packSource: RemotePack): Flow<DownloadState> {
        return flow {
            emit(DownloadState.Downloading(0))
            try {
                val response = basicHttpSource.downloadFile(packSource.download)
                val fileOutputStream = fileSource.getOutputStreamToFile(UUID.randomUUID().toString() + ".zip")
                val body = response.body()!!

                response.headers().forEach {
                    Log.i("HEADER", it.first + "->" + it.second)
                }

                body.byteStream().use { inputStream->
                    fileOutputStream.use { outputStream->
                        val totalBytes = body.contentLength()
                        val buffer = ByteArray(DEFAULT_BUFFER_SIZE)
                        var progressBytes = 0L
                        var bytes = inputStream.read(buffer)
                        while (bytes >= 0) {
                            outputStream.write(buffer, 0, bytes)
                            progressBytes += bytes
                            bytes = inputStream.read(buffer)
                            emit(DownloadState.Downloading(((progressBytes * 100.0) / totalBytes).toInt()))
                        }
                        Log.i("File", "File written successfully : $totalBytes written")
                    }
                }
                emit(DownloadState.Finished)
            } catch (e: Exception) {
                emit(DownloadState.Failed(e))
            }
        }.flowOn(Dispatchers.IO).distinctUntilChanged()
    }

}