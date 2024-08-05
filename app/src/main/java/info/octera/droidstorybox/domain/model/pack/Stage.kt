package info.octera.droidstorybox.domain.model.pack

import java.util.UUID

data class Stage(
    val uuid: UUID,
    val type: StageType,
    val name: String,
    val image: String?,
    val audio: String,
    val okTransition: Transition?,
    val homeTransition: Transition?,
    val controlSettings: ControlSettings,
    val squareOne: Boolean = false
)
