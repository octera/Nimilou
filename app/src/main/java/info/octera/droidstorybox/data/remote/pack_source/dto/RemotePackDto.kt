package info.octera.droidstorybox.data.remote.pack_source.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.util.UUID

@Serializable
data class ThumbsDto (
    val small: String,
    val medium: String
)

@Serializable
data class RemotePackDto(
    val age: Int,
    val title: String,
    val description: String,
    val download: String,
    val thumbs : ThumbsDto,
    val awards : List<String>,
    @SerialName("created_at") val createdAt : String,
    @SerialName("updated_at") val updatedAt : String,
    val uuid: String  = ""
)