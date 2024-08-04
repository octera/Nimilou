package info.octera.droidstorybox.domain.usecases.packs

import android.net.Uri
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
}