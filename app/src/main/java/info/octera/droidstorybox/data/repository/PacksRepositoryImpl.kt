package info.octera.droidstorybox.data.repository

import android.util.Log
import info.octera.droidstorybox.data.file.FileSource
import info.octera.droidstorybox.data.remote.BasicHttpSource
import info.octera.droidstorybox.domain.model.DownloadState
import info.octera.droidstorybox.domain.model.RemotePack
import info.octera.droidstorybox.domain.repository.PacksRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.util.UUID
import javax.inject.Inject


class PacksRepositoryImpl @Inject constructor(
    val basicHttpSource: BasicHttpSource,
    val fileSource: FileSource
):PacksRepository {
}