package info.octera.droidstorybox.domain.model.pack

data class Pack(
    val metadata: PackMetadata,
    val stages: List<Stage>,
    val actions: List<Action>
) {
    fun getFirstStage(): Stage? {
        return stages.firstOrNull{it.squareOne}
    }

    fun nextStageFrom(stage: Stage?): List<Stage> {
        if (stage?.okTransition == null) {
            return  emptyList()
        }
        val nextStageId = actions.first{it.id == stage.okTransition.actionNode }.options
        return nextStageId
            .map { stageId -> stages.first{it.uuid == stageId} }
    }
}
