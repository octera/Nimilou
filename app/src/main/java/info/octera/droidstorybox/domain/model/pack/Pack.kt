package info.octera.droidstorybox.domain.model.pack

data class Pack(
    val metadata: PackMetadata,
    val stages: List<Stage>,
    val actions: List<Action>
)