package info.octera.droidstorybox.presentation.read_pack

import android.net.Uri
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import info.octera.droidstorybox.domain.model.PlaybackState
import info.octera.droidstorybox.domain.model.pack.Stage
import info.octera.droidstorybox.domain.usecases.pack.PackUseCases
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import java.net.URLDecoder
import javax.inject.Inject
import kotlin.random.Random

@HiltViewModel
class ReadPackViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val packUseCases: PackUseCases
) : ViewModel() {
    private val packUriArgument = checkNotNull(savedStateHandle.get<String>("packUri"))
    val uri: Uri = Uri.parse(URLDecoder.decode(packUriArgument))

    val state = mutableStateOf(ReadPackState())

    init {
        val pack = packUseCases.getPack(uri)
        val firstStage = pack.getFirstStage()
        state.value = state.value.copy(pack = pack, currendStages = listOfNotNull(firstStage))
        val flow = packUseCases.getPlayerState()
        flow.onEach {
            state.value = state.value.copy(playerInfo = PlayerInfo(
                playing = it.playbackState == PlaybackState.PLAYING,
                duration = it.duration,
                position = it.position,
            ))
            if (it.playbackState == PlaybackState.ENDED
                && currentStageSelected()?.controlSettings?.autoplay == true
            ) {
                //autoplay, then simulate OK push
                ok()
            }
        }.launchIn(viewModelScope)
        runStage()
    }

    fun runStage() {
        currentStageSelected()?.let { stage ->
            if (stage.audio == null) {
                ok()
            } else {
                packUseCases.playMedia(stage.audio)
            }

        }
    }

    fun pause() {
        packUseCases.togglePause()
    }

    fun ok() {
        val nextStages = state.value.pack
            ?.nextStageFrom(
                currentStageSelected()!!
            )
            ?: emptyList()

        val optionIndex = state.value.currendStages[state.value.selectedStageIndex].okTransition?.optionIndex

        state.value = state.value.copy(
            currendStages = nextStages.mapForOption(optionIndex),
            selectedStageIndex = 0
        )
        runStage()
    }

    fun setSelectedStage(index: Int) {
        state.value = state.value.copy(
            selectedStageIndex = index
        )
        runStage()
    }

    fun currentStageSelected(): Stage? {
        return state.value.currendStages
            .getOrNull(state.value.selectedStageIndex)
    }

    private fun List<Stage>.mapForOption(optionIndex : Int?): List<Stage> {
        val list = when {
            optionIndex == -1 && this.isNotEmpty() -> this.shuffled()
            else -> this
        }
        if (list.isNotEmpty() && list.first().controlSettings.autoplay) {
            return listOf(list.first())
        }
        return list
    }
}