package info.octera.droidstorybox.util

import java.text.Normalizer
import kotlin.time.Duration

fun Duration.ToMinutesFormat(): String {
    val minutes = this.inWholeMinutes
    val seconds = this.inWholeSeconds % 60
    return String.format("%d:%02d", minutes, seconds)
}

private val REGEX_UNACCENT = "\\p{InCombiningDiacriticalMarks}+".toRegex()

fun CharSequence.unaccent(): String {
    val temp = Normalizer.normalize(this, Normalizer.Form.NFD)
    return REGEX_UNACCENT.replace(temp, "")
}