package info.octera.droidstorybox.presentation.pack_sources

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import info.octera.droidstorybox.domain.model.PackSource
import info.octera.droidstorybox.domain.usecases.pack_sources.PackSourcesUseCases
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PackSourcesViewModel @Inject constructor(
    private val packSourcesUseCases: PackSourcesUseCases
) : ViewModel() {

    val state = mutableStateOf(PackSourcesState())


    init {
        getPackSources()
    }

    private fun getPackSources() {
        packSourcesUseCases.getPackSources().onEach {
            state.value = state.value.copy(packSources = it)
        }.launchIn(viewModelScope)
    }

    fun addPackSource(packSource: PackSource) {
        viewModelScope.launch {
            packSourcesUseCases.upsertPackSource(packSource)
        }
    }

    fun deletePackSource(packSource: PackSource) {
        viewModelScope.launch {
            packSourcesUseCases.deletePackSource(packSource)
        }
    }
}