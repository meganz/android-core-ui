package mega.android.core.ui.components

import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.LocalTextStyle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withAnnotation
import androidx.compose.ui.text.withStyle
import mega.android.core.ui.model.SpanIndicator
import mega.android.core.ui.model.SpanStyleWithAnnotation
import mega.android.core.ui.preview.CombinedThemePreviews
import mega.android.core.ui.theme.AndroidThemeForPreviews
import mega.android.core.ui.theme.AppTheme

/**
 * High light text from mega format and receive clicks on spanned text
 * @param value
 * @param baseStyle the style apply for all text
 * @param styles the list of the tag and the custom style
 * key is [SpanIndicator] for open and close tags, value is the [SpanStyleWithAnnotation]
 * with the style and the annotation, we will receive clicks for not null annotations
 * @param onAnnotationClick will receive clicks on spanned text with not null annotation
 * @param modifier
 */
@Composable
fun ClickableSpannedText(
    value: String,
    styles: Map<SpanIndicator, SpanStyleWithAnnotation>,
    onAnnotationClick: (annotation: String) -> Unit,
    modifier: Modifier = Modifier,
    baseStyle: TextStyle = LocalTextStyle.current,
) {
    val annotatedLinkString = remember(value, styles) { spannedTextWithAnnotation(value, styles) }

    ClickableText(
        modifier = modifier,
        text = annotatedLinkString,
        style = baseStyle,
        onClick = { position ->
            annotatedLinkString.getStringAnnotations(ANNOTATION_TAG, position, position + 1)
                .firstOrNull()?.let { onAnnotationClick(it.item) }
        }
    )
}

@Composable
fun getPrimaryLinkSpanStyle() = SpanStyle(color = AppTheme.colors.link.primary)

@OptIn(ExperimentalTextApi::class)
private fun spannedTextWithAnnotation(
    value: String,
    styles: Map<SpanIndicator, SpanStyleWithAnnotation>,
) =
    buildAnnotatedString {
        var temp = value
        styles.toSortedMap(compareBy { value.indexOf(it.openTag) }).forEach { item ->
            val start = temp.indexOf(string = item.key.openTag)
            val end = temp.indexOf(string = item.key.closeTag, startIndex = start)
            if (start > 0) {
                append(temp.substring(0, start))
            }
            if (start >= 0 && (start + item.key.openTag.length < end)) {
                item.value.annotation?.let {
                    withAnnotation(ANNOTATION_TAG, it) {
                        withStyle(item.value.spanStyle) {
                            append(temp.substring(start + item.key.openTag.length, end))
                        }
                    }
                } ?: run {
                    withStyle(item.value.spanStyle) {
                        append(temp.substring(start + item.key.openTag.length, end))
                    }
                }
                val index = end + item.key.closeTag.length
                if (index < temp.length + 1) {
                    temp = temp.substring(index)
                }
            }
        }
        if (temp.isNotEmpty()) {
            append(temp)
        }
    }

private const val ANNOTATION_TAG = "annotationTag"

/**
 * Clickable preview, you can test this preview in Interactive Mode
 */
@CombinedThemePreviews
@Composable
private fun ClickableSpannedTextPreview() {
    var counter by remember { mutableIntStateOf(1) }

    AndroidThemeForPreviews {
        ClickableSpannedText(
            value = "Click [A]here[/A] to increase the counter: [B]$counter[/B]\n and [R]here[/R] to reset",
            styles = hashMapOf(
                SpanIndicator('A') to SpanStyleWithAnnotation(
                    SpanStyle(
                        color = Color.Blue,
                        textDecoration = TextDecoration.Underline,
                    ), "url or whatever you want to receive in onAnnotationClick"
                ),
                SpanIndicator('R') to SpanStyleWithAnnotation(
                    SpanStyle(
                        color = Color.Green,
                        textDecoration = TextDecoration.Underline,
                    ), "reset"
                ),
                SpanIndicator('B') to SpanStyleWithAnnotation(SpanStyle(color = Color.Red), "d")
            ),
            onAnnotationClick = { annotation ->
                if (annotation == "reset") {
                    counter = 1
                } else {
                    counter += 1
                }
            },
            baseStyle = AppTheme.typography.titleSmall.copy(color = AppTheme.colors.text.primary)
        )
    }
}