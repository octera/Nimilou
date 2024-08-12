package info.octera.droidstorybox.domain.usecases.packs

import android.app.DownloadManager
import android.net.Uri
import androidx.core.content.ContextCompat.getSystemService
import info.octera.droidstorybox.domain.model.ProgressState
import info.octera.droidstorybox.domain.repository.PacksRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AddPack @Inject constructor(
    private val packsRepository: PacksRepository
) {
    suspend operator fun invoke(uri: Uri): Flow<ProgressState> {
        return packsRepository.addPack(uri)
    }

    operator fun invoke(downloadUrl: String, fileName: String): Flow<ProgressState> {
        return packsRepository.downloadPackFile(downloadUrl, fileName)
    }
}