package info.octera.droidstorybox.data.remote.pack_source.dto

data class ThumbsDto (
    val small: String,
    val medium: String
)

data class RemotePackDto(
    val age: Int,
    val title: String,
    val description: String,
    val download: String,
    val thumbs : ThumbsDto,
    val awards : List<String>,
    val createdAt : String,
    val updatedAt : String

)