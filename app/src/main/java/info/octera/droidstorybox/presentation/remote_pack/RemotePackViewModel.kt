package info.octera.droidstorybox.presentation.remote_pack

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import info.octera.droidstorybox.domain.model.PackSource
import info.octera.droidstorybox.domain.model.RemotePack
import info.octera.droidstorybox.domain.usecases.pack_sources.PackSourcesUseCases
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class RemotePackViewModel @Inject constructor(
    private val packSourcesUseCases: PackSourcesUseCases,
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
        }
    }

    fun fetchPack(remotePack: RemotePack) {
        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(remotePack.download))
        browserIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        appContext.startActivity(browserIntent)
    }
}