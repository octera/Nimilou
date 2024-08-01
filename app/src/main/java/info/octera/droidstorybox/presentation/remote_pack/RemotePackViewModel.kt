package info.octera.droidstorybox.presentation.remote_pack

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import info.octera.droidstorybox.domain.model.PackSource
import info.octera.droidstorybox.domain.model.RemotePack
import info.octera.droidstorybox.domain.usecases.pack_sources.PackSourcesUseCases
import info.octera.droidstorybox.presentation.pack_sources.PackSourcesState
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RemotePackViewModel @Inject constructor(
    private val packSourcesUseCases: PackSourcesUseCases
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
            packSourcesUseCases
                .fetchPacksFromPackSource(packSource)
                .onEach {
                    state.value = state.value.copy(remotePack = it)
                }.launchIn(viewModelScope)
        }
    }

    fun fetchPack(remotePack: RemotePack) {

    }
}