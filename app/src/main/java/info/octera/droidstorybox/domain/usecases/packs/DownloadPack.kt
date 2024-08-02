package info.octera.droidstorybox.domain.usecases.packs

import info.octera.droidstorybox.domain.model.DownloadState
import info.octera.droidstorybox.domain.model.PackSource
import info.octera.droidstorybox.domain.model.RemotePack
import info.octera.droidstorybox.domain.repository.PacksRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DownloadPack @Inject constructor(
    private val packsRepository: PacksRepository
) {
    suspend operator fun invoke(pack: RemotePack): Flow<DownloadState> {
        return packsRepository.downloadPack(pack)
    }
}