package info.octera.droidstorybox.presentation.read_pack

import info.octera.droidstorybox.domain.model.pack.Pack
import info.octera.droidstorybox.domain.model.pack.Stage
import kotlin.time.Duration

data class ReadPackState (
    val pack: Pack? = null,
    val currendStages : List<Stage> = emptyList(),
    val selectedStageIndex: Int = 0,
    val playerInfo: PlayerInfo = PlayerInfo()
)

data class PlayerInfo(
    val playing: Boolean = false,
    val duration: Duration = Duration.ZERO,
    val position: Duration = Duration.ZERO
)