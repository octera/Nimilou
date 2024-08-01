package info.octera.droidstorybox.data.remote.pack_source.dto

import com.google.gson.annotations.SerializedName

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
    @SerializedName("created_at") val createdAt : String,
    @SerializedName("updated_at") val updatedAt : String,
    val uuid: String  = ""
)