package mega.android.core.ui.model

/**
 * SpanStyle and an associated annotation string to be used in clickable texts
 *
 * @param megaSpanStyle [MegaSpanStyle]
 * @param annotation [String] it can be null if we don't need to make it clickable, usually an url link
 */
data class SpanStyleWithAnnotation(val megaSpanStyle: MegaSpanStyle, val annotation: String?)
