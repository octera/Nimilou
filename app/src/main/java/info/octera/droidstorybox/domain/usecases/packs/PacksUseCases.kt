package info.octera.droidstorybox.domain.usecases.packs

import javax.inject.Inject

data class PacksUseCases @Inject constructor(
    val downloadPack: DownloadPack
)