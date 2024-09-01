package info.octera.droidstorybox.domain.usecases.pack_sources

import info.octera.droidstorybox.domain.model.PackSource
import info.octera.droidstorybox.domain.repository.PackSourcesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.forEach
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetPackSources @Inject constructor(
    private val packSourcesRepository: PackSourcesRepository
) {

    operator fun invoke(): Flow<List<PackSource>> {
        return packSourcesRepository
            .getPackSources()
            .map { listOf(DEFAULT_PACK_SOURCE) + it }
    }

    private val DEFAULT_PACK_SOURCE = PackSource(
        url = "https://gist.githubusercontent.com/DantSu/3aea4c1fe15070bcf394a40b89aec33e/raw/stories.json ",
        name = "Raconte moi une histoire",
        link = "https://monurl.ca/lunii.creations",
        description =  "Histoires libres de droits réalisées par des passionés",
        locked = true
    )
}