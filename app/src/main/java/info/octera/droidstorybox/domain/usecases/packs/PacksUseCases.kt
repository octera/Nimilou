package info.octera.droidstorybox.domain.usecases.packs

import javax.inject.Inject

data class PacksUseCases @Inject constructor(
    val getPacks: GetPacks,
    val addPack: AddPack,
    val deletePack: DeletePack
)