package info.octera.droidstorybox.domain.usecases.packs

import info.octera.droidstorybox.domain.model.pack.PackMetadata
import info.octera.droidstorybox.domain.repository.PackRepository
import info.octera.droidstorybox.domain.repository.PacksRepository
import javax.inject.Inject

class GetPacks @Inject constructor(
    private val packsRepository: PacksRepository,
    private val packRepository: PackRepository
) {
    operator fun invoke(): List<PackMetadata> {
        return packsRepository.getPacksFile()
            .map {
                packRepository.readPackMetaData(it)
            }
    }
}