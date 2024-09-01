package info.octera.droidstorybox.domain.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity
data class PackSource(
    @PrimaryKey val url: String,
    val name: String,
    val locked: Boolean = false,
    val link: String? = null,
    val description: String? = null
) : Parcelable