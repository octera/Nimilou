package info.octera.droidstorybox.domain.usecases.pack_sources

import androidx.paging.PagingData
import info.octera.droidstorybox.domain.model.Article
import info.octera.droidstorybox.domain.model.PackSource
import info.octera.droidstorybox.domain.repository.PackSourcesRepository
import kotlinx.coroutines.flow.Flow

class GetPackSources(
    private val packSourcesRepository: PackSourcesRepository
) {

    operator fun invoke(): Flow<List<PackSource>> {
        return packSourcesRepository.getPackSources()
    }
}