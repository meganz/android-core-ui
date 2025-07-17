package mega.android.core.ui.components.util

import java.text.Normalizer
import kotlin.getOrDefault
import kotlin.runCatching
import kotlin.text.toRegex

private val REGEX_REMOVE_ACCENT = "\\p{InCombiningDiacriticalMarks}+".toRegex()

/**
 * Normalize a string by removing accents.
 *
 * @return the normalized string
 */
fun String.normalize(): String = runCatching {
    val normalisedText = Normalizer.normalize(this, Normalizer.Form.NFD)
    return REGEX_REMOVE_ACCENT.replace(normalisedText, "")
}.getOrDefault(this)