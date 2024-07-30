package info.octera.droidstorybox.domain.usecases.pack_sources

import info.octera.droidstorybox.domain.model.Article
import info.octera.droidstorybox.domain.model.PackSource
import info.octera.droidstorybox.domain.repository.PackSourcesRepository

class DeletePackSource(
    private val packSourcesRepository: PackSourcesRepository
) {

    suspend operator fun invoke(packSource: PackSource){
        packSourcesRepository.deletePackSource(packSource)
    }
}