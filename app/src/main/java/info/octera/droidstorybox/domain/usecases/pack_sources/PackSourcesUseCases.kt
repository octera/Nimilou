package info.octera.droidstorybox.domain.usecases.pack_sources

data class PackSourcesUseCases (
    val getPackSources: GetPackSources,
    val upsertPackSource: UpsertPackSource,
    val deletePackSource: DeletePackSource,
)