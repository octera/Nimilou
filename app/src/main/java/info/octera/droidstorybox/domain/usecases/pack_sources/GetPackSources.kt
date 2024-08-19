package info.octera.droidstorybox.domain.usecases.pack_sources

import info.octera.droidstorybox.domain.model.PackSource
import info.octera.droidstorybox.domain.repository.PackSourcesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPackSources @Inject constructor(
    private val packSourcesRepository: PackSourcesRepository
) {

    operator fun invoke(): Flow<List<PackSource>> {
        return packSourcesRepository.getPackSources()
    }
}