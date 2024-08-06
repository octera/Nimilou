package info.octera.droidstorybox.presentation.read_pack

import info.octera.droidstorybox.domain.model.pack.Pack
import info.octera.droidstorybox.domain.model.pack.Stage

data class ReadPackState (
    val pack: Pack? = null,
    val currendStage : Stage? = null
)