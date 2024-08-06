package info.octera.droidstorybox.presentation.home

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import info.octera.droidstorybox.domain.model.pack.PackMetadata
import info.octera.droidstorybox.domain.usecases.pack.PackUseCases
import info.octera.droidstorybox.domain.usecases.packs.PacksUseCases
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
        state.value = state.value.copy(packs = packsUseCases.getPacks())
    }

    fun setPackFocused(packMetadata: PackMetadata) {
        viewModelScope.launch {
            state.value = state.value.copy(packFocused = packUseCases.getPack(packMetadata.uri))
            state.value.packFocused?.let {
                packUseCases.playIntro(it)
            }
        }
    }
}
