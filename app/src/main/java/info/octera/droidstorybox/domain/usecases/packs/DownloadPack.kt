package info.octera.droidstorybox.domain.usecases.packs

import info.octera.droidstorybox.domain.model.PackSource
import info.octera.droidstorybox.domain.model.RemotePack
import info.octera.droidstorybox.domain.repository.PacksRepository

class DownloadPack(
    private val packsRepository: PacksRepository
) {
    suspend operator fun invoke(pack: RemotePack) {
        return packsRepository.downloadPack(pack)
    }
}