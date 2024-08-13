package info.octera.droidstorybox.util

import kotlin.time.Duration

fun Duration.ToMinutesFormat(): String {
    val minutes = this.inWholeMinutes
    val seconds = this.inWholeSeconds % 60
    return String.format("%d:%02d", minutes, seconds)
}