package info.octera.droidstorybox.domain.usecases.packs

import info.octera.droidstorybox.domain.model.pack.PackMetadata
import info.octera.droidstorybox.domain.repository.PacksRepository
import javax.inject.Inject

class DeletePack @Inject constructor(
    private val packsRepository: PacksRepository
) {
    suspend operator fun invoke(packMetadata: PackMetadata) {
        return packsRepository.deletePack(packMetadata)
    }
}