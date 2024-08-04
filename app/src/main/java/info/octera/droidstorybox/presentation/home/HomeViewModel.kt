package info.octera.droidstorybox.presentation.home

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import info.octera.droidstorybox.domain.usecases.packs.PacksUseCases
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
