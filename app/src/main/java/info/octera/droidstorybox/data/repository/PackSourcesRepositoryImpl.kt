package info.octera.droidstorybox.data.repository

import info.octera.droidstorybox.data.local.PackSourcesDao
import info.octera.droidstorybox.data.remote.pack_source.PackSourceApi
import info.octera.droidstorybox.domain.model.PackSource
import info.octera.droidstorybox.domain.model.RemotePack
import info.octera.droidstorybox.domain.repository.PackSourcesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PackSourcesRepositoryImpl @Inject constructor(
    private val packSourcesDao: PackSourcesDao,
    private val packSourceApi: PackSourceApi
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

    override suspend fun fetchPacksFromPackSource(packSource: PackSource): List<RemotePack> {
        return packSourceApi.fetchPacks(packSource.url)
    }
}