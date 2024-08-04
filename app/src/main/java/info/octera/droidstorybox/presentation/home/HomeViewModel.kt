package info.octera.droidstorybox.presentation.home

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import info.octera.droidstorybox.domain.usecases.news.NewsUseCases
import info.octera.droidstorybox.domain.usecases.packs.PacksUseCases
import info.octera.droidstorybox.presentation.local_packs.LocalPacksState
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val packsUseCases: PacksUseCases,
) : ViewModel() {
    val state = mutableStateOf(HomeScreenState())

    init {
        getPacks()
    }

    private fun getPacks() {
        state.value = state.value.copy(packs = packsUseCases.getPacks())
    }
}
