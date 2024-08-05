package info.octera.droidstorybox.data.pack_reader.json_story.model

import java.util.UUID

data class Story(
    val format: String,
    val title: String,
    val version: Int,
    val description: String,
    val nightModeAvailable: Boolean,
    val stageNodes: List<StoryStageNode>,
    val actionNodes: List<StoryActionNode>
)

data class StoryStageNode(
    val uuid: UUID,
    val type: StoryStageType,
    val name: String,
    val position: StoryPoint,
    val image: String?,
    val audio: String,
    val okTransition: StoryTransition?,
    val homeTransition: StoryTransition?,
    val controlSettings: StoryControlSettings,
    val squareOne: Boolean = false
)

data class StoryActionNode(
    val id: UUID,
    val name: String,
    val position: StoryPoint,
    val options: List<UUID>
)

enum class StoryStageType {
    STAGE
}

data class StoryPoint(
    val x:Int,
    val y:Int
)

data class StoryTransition(
    val actionNode: String,
    val optionIndex: Int
)

data class StoryControlSettings(
    val wheel: Boolean,
    val ok: Boolean,
    val home: Boolean,
    val pause: Boolean,
    val autoplay: Boolean,
)