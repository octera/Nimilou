package info.octera.droidstorybox.domain.repository

import info.octera.droidstorybox.domain.model.PackSource
import kotlinx.coroutines.flow.Flow

interface PackSourcesRepository {
    suspend fun upsertPackSource(packSource: PackSource)
    suspend fun deletePackSource(packSource: PackSource)
    fun getPackSources(): Flow<List<PackSource>>
}