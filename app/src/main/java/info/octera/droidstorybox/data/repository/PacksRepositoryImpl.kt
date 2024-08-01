package info.octera.droidstorybox.data.repository

import info.octera.droidstorybox.data.file.FileSource
import info.octera.droidstorybox.domain.model.RemotePack
import info.octera.droidstorybox.domain.repository.PacksRepository
import khttp.get

class PacksRepositoryImpl(
    val fileSource: FileSource
):PacksRepository {
    override fun downloadPack(packSource: RemotePack) {
        val response = get(url = packSource.download)

    }

}