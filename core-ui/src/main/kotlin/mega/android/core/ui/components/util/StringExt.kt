package mega.android.core.ui.components.util

import java.text.Normalizer

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

/**
 * Replace newline characters with spaces.
 * This handles both Unix-style (\n) and Windows-style (\r) line breaks.
 *
 * @return the string with newlines replaced by spaces
 */
fun String.replaceNewlinesWithSpaces(): String =
    replace("\n", " ").replace("\r", " ")