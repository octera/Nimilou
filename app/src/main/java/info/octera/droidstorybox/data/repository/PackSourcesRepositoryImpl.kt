package info.octera.droidstorybox.data.repository

import info.octera.droidstorybox.data.local.PackSourcesDao
import info.octera.droidstorybox.domain.model.PackSource
import info.octera.droidstorybox.domain.model.RemotePack
import info.octera.droidstorybox.domain.repository.PackSourcesRepository
import kotlinx.coroutines.flow.Flow

class PackSourcesRepositoryImpl(
    private val packSourcesDao: PackSourcesDao
) :
    PackSourcesRepository {

    override suspend fun upsertPackSource(packSource: PackSource) {
        packSourcesDao.upsert(packSource)
    }

    override suspend fun deletePackSource(packSource: PackSource) {
        packSourcesDao.delete(packSource)
    }

    override fun getPackSources(): Flow<List<PackSource>> {
        return packSourcesDao.getPackSource()
    }

    override fun fetchPacksFromPackSource(packSource: PackSource): Flow<List<RemotePack>> {

    }
}