package info.octera.droidstorybox.presentation.local_packs

import android.content.Context
import android.net.Uri
import android.widget.Toast
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import info.octera.droidstorybox.domain.model.ProgressState
import info.octera.droidstorybox.domain.model.pack.PackMetadata
import info.octera.droidstorybox.domain.usecases.packs.PacksUseCases
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LocalPacksViewModel @Inject constructor(
    private val packsUseCases: PacksUseCases,
    @ApplicationContext private val appContext: Context
) : ViewModel() {
    val state = mutableStateOf(LocalPacksState())

    init {
        getPacks()
    }

    private fun getPacks() {
        packsUseCases.getPacks().onEach {
            state.value = state.value.copy(packs = it)
        }.launchIn(viewModelScope)
    }

    fun addPack(uri: Uri) {
        viewModelScope.launch {
            packsUseCases.addPack(uri).collect { progressState ->
                when (progressState) {
                    is ProgressState.Progressing -> {}
                    is ProgressState.Failed -> {
                        Toast.makeText(appContext, "Copy Failed", Toast.LENGTH_LONG)
                    }

                    is ProgressState.Finished -> {
                        Toast.makeText(appContext, "Copy Success", Toast.LENGTH_LONG)
                        getPacks()
                    }
                }
            }
        }
    }

    fun deletePack(packMetadata: PackMetadata) {
        viewModelScope.launch {
            packsUseCases.deletePack(packMetadata)
        }
    }

}