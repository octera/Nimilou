package info.octera.droidstorybox.domain.model

import android.os.Parcelable
import androidx.room.Entity
import kotlinx.parcelize.Parcelize
import java.time.LocalDateTime
import java.time.ZonedDateTime

@Parcelize
data class RemoteThumbs (
    val small: String,
    val medium: String
) : Parcelable

@Parcelize
data class RemotePack(
    val age: Int,
    val title: String,
    val description: String,
    val download: String,
    val thumbs : RemoteThumbs,
    val awards : List<String>,
    val createdAt : String,
    val updatedAt : String

) : Parcelable