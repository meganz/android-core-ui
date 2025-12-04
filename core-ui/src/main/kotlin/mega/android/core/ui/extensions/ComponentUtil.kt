package mega.android.core.ui.extensions

import androidx.compose.runtime.Composable
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import mega.android.core.ui.components.util.normalize
import mega.android.core.ui.model.HighlightedText

@Composable
internal fun HighlightedText.toAnnotatedString(): AnnotatedString {
    val (text, query) = this

    if (query.isBlank()) {
        return AnnotatedString(text)
    }

    return buildAnnotatedString {
        append(text)

        val normalizedText = text.normalize()
        val normalizedHighlight = query.normalize()

        if (normalizedHighlight.isBlank()) return@buildAnnotatedString

        val highlightStyle = SpanStyle(
            background = highlightColor.parseColor(),
            fontWeight = highlightFontWeight,
        )
        var startIndex = normalizedText.indexOf(
            string = normalizedHighlight,
            ignoreCase = ignoreCase,
        )

        while (startIndex >= 0) {
            val endIndex = startIndex + normalizedHighlight.length
            if (endIndex > text.length) break

            addStyle(
                style = highlightStyle,
                start = startIndex,
                end = endIndex,
            )

            startIndex = normalizedText.indexOf(
                string = normalizedHighlight,
                startIndex = endIndex,
                ignoreCase = ignoreCase,
            )
        }
    }
}