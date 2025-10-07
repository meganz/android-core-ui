package mega.android.core.ui.components

import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.LinkAnnotation
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withLink
import androidx.compose.ui.text.withStyle
import mega.android.core.ui.model.MegaSpanStyle
import mega.android.core.ui.model.SpanIndicator
import mega.android.core.ui.model.SpanStyleWithAnnotation
import mega.android.core.ui.preview.CombinedThemePreviews
import mega.android.core.ui.theme.AndroidThemeForPreviews
import mega.android.core.ui.theme.AppTheme
import mega.android.core.ui.theme.textColor
import mega.android.core.ui.theme.values.LinkColor
import mega.android.core.ui.theme.values.TextColor
import mega.android.core.ui.tokens.theme.DSTokens

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
    baseTextColor: TextColor = TextColor.Primary,
    maxLines: Int = Int.MAX_VALUE,
    overflow: TextOverflow = TextOverflow.Clip,
) {
    val annotatedLinkString = spannedTextWithAnnotation(value, spanStyles, onAnnotationClick)
    Text(
        modifier = modifier,
        text = annotatedLinkString,
        style = baseStyle.copy(color = DSTokens.textColor(textColor = baseTextColor)),
        maxLines = maxLines,
        overflow = overflow,
    )
}

@Composable
internal fun spannedTextWithAnnotation(
    value: String,
    styles: Map<SpanIndicator, SpanStyleWithAnnotation>,
    onAnnotationClick: (annotation: String) -> Unit = {},
) =
    buildAnnotatedString {
        val temp = value
        var lastProcessedIndex = 0
        
        // Find all span positions first, then process them in order
        val allSpanPositions = mutableListOf<Triple<Int, SpanIndicator, SpanStyleWithAnnotation>>()
        
        styles.forEach { (indicator, style) ->
            var searchStart = 0
            while (true) {
                val start = temp.indexOf(indicator.openTag, searchStart)
                if (start == -1) break
                val end = temp.indexOf(indicator.closeTag, start + indicator.openTag.length)
                if (end == -1) break
                
                allSpanPositions.add(Triple(start, indicator, style))
                searchStart = end + indicator.closeTag.length
            }
        }
        
        // Sort by position
        allSpanPositions.sortBy { it.first }
        
        // Process spans in order
        allSpanPositions.forEach { (start, indicator, style) ->
            val end = temp.indexOf(indicator.closeTag, start + indicator.openTag.length)
            
            // Add text before this span
            if (start > lastProcessedIndex) {
                append(temp.substring(lastProcessedIndex, start))
            }
            
            val spanStyle = style.megaSpanStyle.toSpanStyle()
            if (start >= 0 && (start + indicator.openTag.length < end)) {
                style.annotation?.let { annotation ->
                    val linkAnnotation = LinkAnnotation.Clickable(
                        tag = annotation,
                        linkInteractionListener = { 
                            onAnnotationClick(annotation)
                        }
                    )
                    withLink(linkAnnotation) {
                        withStyle(spanStyle) {
                            append(temp.substring(start + indicator.openTag.length, end))
                        }
                    }
                } ?: run {
                    withStyle(spanStyle) {
                        append(temp.substring(start + indicator.openTag.length, end))
                    }
                }
                lastProcessedIndex = end + indicator.closeTag.length
            }
        }
        
        // Add remaining text
        if (lastProcessedIndex < temp.length) {
            append(temp.substring(lastProcessedIndex))
        }
    }

/**
 * Non-clickable spanned text component that supports span styles without intercepting touch events.
 */
@Composable
fun SpannedText(
    value: String,
    spanStyles: Map<SpanIndicator, MegaSpanStyle>,
    modifier: Modifier = Modifier,
    baseStyle: TextStyle = LocalTextStyle.current,
    baseTextColor: TextColor = TextColor.Primary,
    maxLines: Int = Int.MAX_VALUE,
    overflow: TextOverflow = TextOverflow.Clip,
) {
    val annotatedString = spannedText(value, spanStyles)
    Text(
        modifier = modifier,
        text = annotatedString,
        style = baseStyle.copy(color = DSTokens.textColor(textColor = baseTextColor)),
        maxLines = maxLines,
        overflow = overflow,
    )
}

@Composable
private fun spannedText(value: String, styles: Map<SpanIndicator, MegaSpanStyle>) =
    spannedTextWithAnnotation(
        value = value,
        styles = styles.mapValues { SpanStyleWithAnnotation(it.value, null) },
    )

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

@CombinedThemePreviews
@Composable
private fun SpannedTextPreview() {
    AndroidThemeForPreviews {
        SpannedText(
            value = "Simple Text with span style [A]here[/A] and [R]one more[/R]",
            spanStyles = hashMapOf(
                SpanIndicator('A') to
                        MegaSpanStyle.TextColorStyle(
                            SpanStyle(),
                            TextColor.Primary
                        ),
                SpanIndicator('R') to
                        MegaSpanStyle.TextColorStyle(
                            SpanStyle(),
                            TextColor.Secondary
                        )
            ),
            baseStyle = AppTheme.typography.titleSmall,
            baseTextColor = TextColor.Secondary
        )
    }
}