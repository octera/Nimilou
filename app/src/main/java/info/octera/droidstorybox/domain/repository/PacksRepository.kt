package info.octera.droidstorybox.domain.repository

import android.net.Uri
import info.octera.droidstorybox.domain.model.ProgressState
import info.octera.droidstorybox.domain.model.pack.PackMetadata
import kotlinx.coroutines.flow.Flow
import java.io.File

interface PacksRepository {
    fun getPacksFile(): List<File>
    suspend fun addPack(uri: Uri): Flow<ProgressState>
    suspend fun deletePack(packMetadata: PackMetadata)
}