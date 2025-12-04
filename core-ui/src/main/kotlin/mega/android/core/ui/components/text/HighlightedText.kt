package mega.android.core.ui.components.text

import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.text.InlineTextContent
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import mega.android.core.ui.components.util.normalize
import mega.android.core.ui.preview.CombinedThemePreviews
import mega.android.core.ui.theme.AndroidThemeForPreviews
import mega.android.core.ui.theme.backgroundColor
import mega.android.core.ui.theme.textColor
import mega.android.core.ui.theme.values.BackgroundColor
import mega.android.core.ui.theme.values.TextColor
import mega.android.core.ui.tokens.theme.DSTokens

/**
 * @param text Text to show
 * @param highlightText Text to background highlight
 * @param textColor Text color
 * @param modifier [Modifier]
 * @param highlightColor Optional color for background highlight
 * @param highlightFontWeight Optional font weight for highlight
 * @param maxLines Minimum lines
 * @param style Text style
 * @param overflow Overflow option
 * @param textAlign Text alignment
 */
@Deprecated("Replace with MegaText instead")
@Composable
fun HighlightedText(
    text: String,
    highlightText: String,
    modifier: Modifier = Modifier,
    textColor: TextColor = TextColor.Primary,
    highlightColor: BackgroundColor = BackgroundColor.Surface2,
    highlightFontWeight: FontWeight = FontWeight.Normal,
    applyMarqueEffect: Boolean = true,
    maxLines: Int = 1,
    style: TextStyle = LocalTextStyle.current,
    overflow: TextOverflow = TextOverflow.Ellipsis,
    textAlign: TextAlign = TextAlign.Start,
) {
    HighlightedText(
        text = androidx.compose.ui.text.AnnotatedString(text),
        highlightText = highlightText,
        modifier = modifier,
        textColor = textColor,
        highlightColor = highlightColor,
        highlightFontWeight = highlightFontWeight,
        applyMarqueEffect = applyMarqueEffect,
        maxLines = maxLines,
        style = style,
        overflow = overflow,
        textAlign = textAlign,
    )
}

/**
 * @param text Annotated string to show
 * @param highlightText Text to background highlight
 * @param textColor Text color
 * @param modifier [Modifier]
 * @param highlightColor Optional color for background highlight
 * @param highlightFontWeight Optional font weight for highlight
 * @param maxLines Minimum lines
 * @param style Text style
 * @param overflow Overflow option
 * @param textAlign Text alignment
 */
@Deprecated("Replace with MegaText instead")
@Composable
fun HighlightedText(
    text: AnnotatedString,
    highlightText: String,
    modifier: Modifier = Modifier,
    textColor: TextColor = TextColor.Primary,
    highlightColor: BackgroundColor = BackgroundColor.Surface2,
    highlightFontWeight: FontWeight = FontWeight.Normal,
    applyMarqueEffect: Boolean = true,
    maxLines: Int = 1,
    style: TextStyle = LocalTextStyle.current,
    overflow: TextOverflow = TextOverflow.Ellipsis,
    inlineContent: Map<String, InlineTextContent> = mapOf(),
    textAlign: TextAlign = TextAlign.Start,
) {
    if (text.isEmpty()) return

    if (highlightText.isEmpty()) {
        Text(
            text = text,
            modifier = modifier.basicMarquee(),
            maxLines = maxLines,
            overflow = overflow,
            style = style,
            color = DSTokens.textColor(textColor = textColor),
            textAlign = textAlign,
        )
        return
    }

    val highlightBackgroundColor = DSTokens.backgroundColor(highlightColor)
    val annotatedText: AnnotatedString = remember(text, highlightText) {
        buildAnnotatedString {
            append(text)
            val normalizedHighlight = highlightText.normalize()
            val normalizedText = text.text.normalize()
            var startIndex = normalizedText.indexOf(string = normalizedHighlight, ignoreCase = true)
            while (startIndex >= 0) {
                val endIndex = startIndex + normalizedHighlight.length
                if (endIndex <= text.length) {
                    addStyle(
                        style = SpanStyle(
                            background = highlightBackgroundColor,
                            fontWeight = highlightFontWeight,
                        ),
                        start = startIndex,
                        end = endIndex
                    )
                } else {
                    break
                }
                startIndex = normalizedText.indexOf(
                    string = normalizedHighlight,
                    startIndex = endIndex,
                    ignoreCase = true
                )
            }
        }
    }

    Text(
        text = annotatedText,
        modifier = modifier.then(if (applyMarqueEffect) Modifier.basicMarquee() else Modifier),
        maxLines = maxLines,
        overflow = overflow,
        style = style,
        color = DSTokens.textColor(textColor = textColor),
        inlineContent = inlineContent,
        textAlign = textAlign,
    )
}

@CombinedThemePreviews
@Composable
private fun HighlightedTextPreview() {
    AndroidThemeForPreviews{
        HighlightedText(
            text = "This is a title with Title highlight",
            highlightText = "TITLE",
            textColor = TextColor.Primary,
        )
    }
}

@CombinedThemePreviews
@Composable
private fun HighlightedTextBoldPreview() {
    AndroidThemeForPreviews {
        HighlightedText(
            text = "This is ä tìtle with TITLE highlight",
            highlightText = "TITLE",
            textColor = TextColor.Primary,
            highlightFontWeight = FontWeight.Bold,
        )
    }
}
