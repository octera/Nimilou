package info.octera.droidstorybox.domain.model

import android.os.Parcelable
import info.octera.droidstorybox.util.unaccent
import kotlinx.parcelize.Parcelize

@Parcelize
data class RemoteThumbs(
    val small: String,
    val medium: String
) : Parcelable

@Parcelize
data class RemotePack(
    val age: Int,
    val title: String,
    val description: String,
    val download: String,
    val thumbs: RemoteThumbs,
    val awards: List<String>,
    val createdAt: String,
    val updatedAt: String,
) : Parcelable {
    fun isMatchWithQuery(queryString: String): Boolean {
        val matchResult = listOf(
            title.unaccent().lowercase(),
            description.unaccent().lowercase(),
        )
        return matchResult.any {
            it.contains(queryString.unaccent().lowercase())
        }
    }
}