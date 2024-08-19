package info.octera.droidstorybox.presentation.home

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import info.octera.droidstorybox.domain.model.pack.PackMetadata
import info.octera.droidstorybox.domain.usecases.pack.PackUseCases
import info.octera.droidstorybox.domain.usecases.packs.PacksUseCases
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val packsUseCases: PacksUseCases,
    private val packUseCases: PackUseCases
) : ViewModel() {
    val state = mutableStateOf(HomeScreenState())

    init {
        getPacks()
    }

    private fun getPacks() {
        packsUseCases.getPacks().onEach {
            state.value = state.value.copy(packs = it)
        }.launchIn(viewModelScope)
    }

    fun setPackFocused(packMetadata: PackMetadata) {
        viewModelScope.launch {
            val pack = packUseCases.getPack(packMetadata.uri)
            val firstStage = pack.getFirstStage()
            state.value = state.value.copy(packFocused = pack)
            firstStage?.audio?.let { packUseCases.playMedia(it) }
        }
    }
}
