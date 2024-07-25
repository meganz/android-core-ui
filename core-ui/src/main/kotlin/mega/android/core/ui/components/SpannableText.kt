package mega.android.core.ui.components

import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.LocalTextStyle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withAnnotation
import androidx.compose.ui.text.withStyle
import mega.android.core.ui.model.MegaSpanStyle
import mega.android.core.ui.model.SpanIndicator
import mega.android.core.ui.model.SpanStyleWithAnnotation
import mega.android.core.ui.preview.CombinedThemePreviews
import mega.android.core.ui.theme.AndroidThemeForPreviews
import mega.android.core.ui.theme.AppTheme
import mega.android.core.ui.theme.values.LinkColor
import mega.android.core.ui.theme.values.TextColor

/**
 * Text with link, you can use this component to show text with link
 *
 * @param value
 * @param spanStyles the list of the tag and the annotation with style
 * key is [SpanIndicator] for open and close tags, value is the annotation
 * eg: [SpanIndicator('A') to "The annotation string to link"]
 * @param onAnnotationClick will receive clicks on spanned text with not null annotation
 * @param modifier
 * @param baseStyle the style to apply for all text
 */
@Composable
fun LinkSpannedText(
    value: String,
    spanStyles: Map<SpanIndicator, SpanStyleWithAnnotation>,
    onAnnotationClick: (annotation: String) -> Unit,
    modifier: Modifier = Modifier,
    baseStyle: TextStyle = LocalTextStyle.current,
    baseTextColor: TextColor = TextColor.Primary
) {
    val annotatedLinkString = spannedTextWithAnnotation(value, spanStyles)
    ClickableText(
        modifier = modifier,
        text = annotatedLinkString,
        style = baseStyle.copy(color = AppTheme.textColor(textColor = baseTextColor)),
        onClick = { position ->
            annotatedLinkString
                .getStringAnnotations(ANNOTATION_TAG, position, position + 1)
                .firstOrNull()
                ?.let { onAnnotationClick(it.item) }
        }
    )
}

@OptIn(ExperimentalTextApi::class)
@Composable
internal fun spannedTextWithAnnotation(
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
            val spanStyle = item.value?.megaSpanStyle?.toSpanStyle()
            if (start >= 0 && (start + item.key.openTag.length < end)) {
                item.value.annotation?.let { annotation ->
                    spanStyle?.let {
                        withAnnotation(ANNOTATION_TAG, annotation) {
                            withStyle(spanStyle) {
                                append(temp.substring(start + item.key.openTag.length, end))
                            }
                        }
                    }
                } ?: run {
                    spanStyle?.let {
                        withStyle(spanStyle) {
                            append(temp.substring(start + item.key.openTag.length, end))
                        }
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

@CombinedThemePreviews
@Composable
private fun LinkTextPreview() {
    var counter by remember { mutableIntStateOf(1) }

    AndroidThemeForPreviews {
        LinkSpannedText(
            value = "Click [A]here[/A] to increase the counter: [B]$counter[/B]\n and [R]here[/R] to reset",
            spanStyles = hashMapOf(
                SpanIndicator('A') to SpanStyleWithAnnotation(
                    MegaSpanStyle.LinkColorStyle(
                        SpanStyle(),
                        LinkColor.Primary
                    ), "url or whatever you want to receive in onAnnotationClick"
                ),
                SpanIndicator('R') to SpanStyleWithAnnotation(
                    MegaSpanStyle.TextColorStyle(
                        SpanStyle(),
                        TextColor.Primary
                    ), "reset"
                ),
                SpanIndicator('B') to SpanStyleWithAnnotation(
                    MegaSpanStyle.LinkColorStyle(
                        SpanStyle(),
                        LinkColor.Primary
                    ), "d"
                ),
            ),
            onAnnotationClick = { annotation ->
                if (annotation == "reset") {
                    counter = 1
                } else {
                    counter += 1
                }
            },
            baseStyle = AppTheme.typography.titleSmall,
            baseTextColor = TextColor.Secondary
        )
    }
}