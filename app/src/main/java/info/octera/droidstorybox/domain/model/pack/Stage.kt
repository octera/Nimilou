package info.octera.droidstorybox.domain.model.pack

import android.net.Uri
import java.util.UUID

data class Stage(
    val uuid: UUID,
    val type: StageType,
    val name: String,
    val image: ByteArray?,
    val audio: Uri,
    val okTransition: Transition?,
    val homeTransition: Transition?,
    val controlSettings: ControlSettings,
    val squareOne: Boolean = false
)
