package info.octera.droidstorybox.presentation.pack_sources

import info.octera.droidstorybox.domain.model.PackSource

data class PackSourcesState(
    val packSources: List<PackSource> = emptyList(),
)
