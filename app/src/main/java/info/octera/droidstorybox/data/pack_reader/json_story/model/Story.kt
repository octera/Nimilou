package info.octera.droidstorybox.data.pack_reader.json_story.model

data class Story(
    val format: String,
    val title: String,
    val version: Int,
    val description: String,
    val nightModeAvailable: Boolean
)