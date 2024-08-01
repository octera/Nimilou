package info.octera.droidstorybox.presentation.remote_pack

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import info.octera.droidstorybox.domain.model.PackSource
import info.octera.droidstorybox.domain.model.RemotePack
import info.octera.droidstorybox.domain.usecases.pack_sources.PackSourcesUseCases
<<<<<<< HEAD
import info.octera.droidstorybox.domain.usecases.packs.PacksUseCases
=======
>>>>>>> bdaedbf (wip)
import info.octera.droidstorybox.presentation.pack_sources.PackSourcesState
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RemotePackViewModel @Inject constructor(
<<<<<<< HEAD
    private val packSourcesUseCases: PackSourcesUseCases,
    private val packsUseCases: PacksUseCases
=======
    private val packSourcesUseCases: PackSourcesUseCases/*,
    private val packsUseCases: PacksUseCases*/
>>>>>>> bdaedbf (wip)
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
        }
    }

    fun fetchPack(remotePack: RemotePack) {
<<<<<<< HEAD
        viewModelScope.launch {
            packsUseCases.downloadPack(remotePack)
        }
=======
    /*    viewModelScope.launch {
            packsUseCases.downloadPack(remotePack)
        }*/
>>>>>>> bdaedbf (wip)
    }
}