package info.octera.droidstorybox.domain.repository

import info.octera.droidstorybox.domain.model.PackSource
import info.octera.droidstorybox.domain.model.RemotePack
import kotlinx.coroutines.flow.Flow

interface PacksRepository {
    fun downloadPack(packSource: RemotePack)
}