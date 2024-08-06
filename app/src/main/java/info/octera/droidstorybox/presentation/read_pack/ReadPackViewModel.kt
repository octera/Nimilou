package info.octera.droidstorybox.presentation.read_pack

import android.net.Uri
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import info.octera.droidstorybox.domain.usecases.pack.PackUseCases
import javax.inject.Inject

@HiltViewModel
class ReadPackViewModel @Inject constructor(
    private val packUseCases: PackUseCases
) : ViewModel() {
    val state = mutableStateOf(ReadPackState())

    fun setPackUri(uri: Uri) {
        val pack = packUseCases.getPack(uri)
        val firstStage = pack.getFirstStage()
        state.value = state.value.copy(pack = pack, currendStage = firstStage)
    }
}