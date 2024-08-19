package info.octera.droidstorybox.domain.usecases.packs

import info.octera.droidstorybox.domain.model.pack.PackMetadata
import info.octera.droidstorybox.domain.repository.PackRepository
import info.octera.droidstorybox.domain.repository.PacksRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetPacks @Inject constructor(
    private val packsRepository: PacksRepository,
    private val packRepository: PackRepository
) {
    operator fun invoke(): Flow<List<PackMetadata>> {
        return packsRepository.getPacksFile()
            .map { files ->
                files.mapNotNull {
                    try {
                        packRepository.readPackMetaData(it)
                    } catch (e: Exception) {
                        null
                    }
                }
            }
    }
}