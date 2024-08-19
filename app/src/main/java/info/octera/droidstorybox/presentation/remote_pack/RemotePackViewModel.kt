package info.octera.droidstorybox.presentation.remote_pack

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import info.octera.droidstorybox.domain.model.PackSource
import info.octera.droidstorybox.domain.model.ProgressState
import info.octera.droidstorybox.domain.usecases.pack_sources.PackSourcesUseCases
import info.octera.droidstorybox.domain.usecases.packs.PacksUseCases
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class RemotePackViewModel @Inject constructor(
    private val packSourcesUseCases: PackSourcesUseCases,
    private val packsUseCases: PacksUseCases,
    @ApplicationContext private val appContext: Context
) : ViewModel() {

    val state = mutableStateOf(RemotePackState())

    init {
        getPackSources()
    }

    private fun getPackSources() {
        packSourcesUseCases.getPackSources().onEach {
            state.value = state.value.copy(packSources = it)
        }.launchIn(viewModelScope)
    }

    fun fetchPacksFromPackSource(packSource: PackSource) {
        viewModelScope.launch {
            val remotePack = packSourcesUseCases.fetchPacksFromPackSource(packSource)
            state.value = state.value.copy(remotePack = remotePack)
            onQueryTextChanged(state.value.queryText)
        }
    }

    fun fetchPack(downloadUrl: String, fileName: String) {
        viewModelScope.launch {
            packsUseCases.addPack(downloadUrl, fileName).collect { downloadState->
                when (downloadState) {
                    is ProgressState.Progressing -> {
                        Log.d("myTag", "progress=${downloadState.progress}")
                        state.value = state.value.copy(
                            downloading = true,
                            downloadProgress = downloadState.progress
                        )
                    }
                    is ProgressState.Failed -> {
                        state.value = state.value.copy(
                            downloading = false,
                        )
                        Toast.makeText(appContext, "Download Failed", Toast.LENGTH_LONG)
                    }
                    is ProgressState.Finished -> {
                        state.value = state.value.copy(
                            downloading = false,
                        )
                        Toast.makeText(appContext, "Download Success", Toast.LENGTH_LONG)
                    }
                }
            }
        }
    }

    fun onQueryTextChanged(query: String) {
        state.value = state.value.copy(queryText = query)
        val filteredRemotePack = if (query.isBlank()) {
            state.value.remotePack
        } else {
            state.value.remotePack.filter { it.isMatchWithQuery(query) }
        }
        state.value = state.value.copy(filteredRemotePack = filteredRemotePack)
    }
}