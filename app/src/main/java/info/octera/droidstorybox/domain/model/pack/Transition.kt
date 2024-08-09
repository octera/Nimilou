package info.octera.droidstorybox.domain.model.pack

import java.util.UUID

data class Transition (
    val actionNode: UUID,
    val optionIndex: Int
)