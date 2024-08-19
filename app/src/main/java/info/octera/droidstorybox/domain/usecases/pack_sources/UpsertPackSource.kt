package info.octera.droidstorybox.domain.usecases.pack_sources

import info.octera.droidstorybox.domain.model.PackSource
import info.octera.droidstorybox.domain.repository.PackSourcesRepository
import javax.inject.Inject

class UpsertPackSource @Inject constructor(
    private val packSourcesRepository: PackSourcesRepository
) {

    suspend operator fun invoke(packSource: PackSource) {
        packSourcesRepository.upsertPackSource(packSource)
    }

}