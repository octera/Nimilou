package info.octera.droidstorybox.presentation.remote_pack

import info.octera.droidstorybox.domain.model.PackSource
import info.octera.droidstorybox.domain.model.RemotePack

data class RemotePackState(
    val queryText: String = "",
    val packSources: List<PackSource> = emptyList(),
    val remotePack: List<RemotePack> = emptyList(),
    val filteredRemotePack: List<RemotePack> = emptyList(),
    val downloading: Boolean = false,
    val downloadProgress: Int = 0,
)
