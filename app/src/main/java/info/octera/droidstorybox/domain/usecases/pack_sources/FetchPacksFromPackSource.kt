package info.octera.droidstorybox.domain.usecases.pack_sources

import info.octera.droidstorybox.domain.model.PackSource
import info.octera.droidstorybox.domain.model.RemotePack
import info.octera.droidstorybox.domain.repository.PackSourcesRepository
import javax.inject.Inject

class FetchPacksFromPackSource @Inject constructor(
    private val packSourcesRepository: PackSourcesRepository
) {

    suspend operator fun invoke(packSource: PackSource): List<RemotePack> {
        return packSourcesRepository.fetchPacksFromPackSource(packSource)
    }
}