package info.octera.droidstorybox.domain.model

 sealed class ProgressState {
    data class Progressing(val progress: Int) : ProgressState()
    object Finished : ProgressState()
    data class Failed(val error: Throwable? = null) : ProgressState()
}
