package info.octera.droidstorybox.domain.usecases.pack_sources

import javax.inject.Inject

data class PackSourcesUseCases @Inject constructor(
    val getPackSources: GetPackSources,
    val upsertPackSource: UpsertPackSource,
    val deletePackSource: DeletePackSource,
    val fetchPacksFromPackSource: FetchPacksFromPackSource
)