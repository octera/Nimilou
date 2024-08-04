package info.octera.droidstorybox.presentation.local_packs

import info.octera.droidstorybox.domain.model.pack.PackMetadata

data class LocalPacksState(
    val packs: List<PackMetadata> = emptyList()
)
