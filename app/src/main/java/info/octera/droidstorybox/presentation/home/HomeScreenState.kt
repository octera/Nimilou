package info.octera.droidstorybox.presentation.home

import info.octera.droidstorybox.domain.model.pack.PackMetadata

data class HomeScreenState(
    val packs: List<PackMetadata> = emptyList()
)
