package info.octera.droidstorybox.domain.model.pack

import android.net.Uri

data class PackMetadata(
    val format: String,
    val title: String,
    val version: Int,
    val description: String,
    val nightModeAvailable: Boolean,
    val uri: Uri,
    val thumbsnail: ByteArray,
    val age: Int
)