package info.octera.droidstorybox.domain.model.pack

import java.util.UUID

data class Action(
    val id: UUID,
    val name: String,
    val options: List<UUID>
)
