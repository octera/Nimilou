package info.octera.droidstorybox.domain.usecases.pack_sources

import androidx.paging.PagingData
import info.octera.droidstorybox.domain.model.Article
import info.octera.droidstorybox.domain.model.PackSource
import info.octera.droidstorybox.domain.model.RemotePack
import info.octera.droidstorybox.domain.repository.PackSourcesRepository
import kotlinx.coroutines.flow.Flow

class FetchPacksFromPackSource(
    private val packSourcesRepository: PackSourcesRepository
) {

    suspend operator fun invoke(packSource: PackSource): List<RemotePack> {
        return packSourcesRepository.fetchPacksFromPackSource(packSource)
    }
}