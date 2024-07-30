package info.octera.droidstorybox.domain.usecases.pack_sources

import info.octera.droidstorybox.domain.model.Article
import info.octera.droidstorybox.domain.model.PackSource
import info.octera.droidstorybox.domain.repository.NewsRepository
import info.octera.droidstorybox.domain.repository.PackSourcesRepository

class UpsertPackSource(
    private val packSourcesRepository: PackSourcesRepository
) {

    suspend operator fun invoke(packSource: PackSource){
        packSourcesRepository.upsertPackSource(packSource)
    }

}