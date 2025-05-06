package mega.android.core.ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import mega.android.core.ui.preview.CombinedThemePreviews
import mega.android.core.ui.theme.AndroidThemeForPreviews
import mega.android.core.ui.theme.linkColor
import mega.android.core.ui.theme.textColor
import mega.android.core.ui.tokens.theme.DSTokens
import mega.android.core.ui.theme.values.LinkColor
import mega.android.core.ui.theme.values.TextColor

/**
 * Text
 *
 * @param text to show
 * @param textColor
 * @param modifier
 * @param overflow
 * @param maxLines
 * @param minLines
 * @param style
 * @param textAlign
 * @param softWrap whether the text should break at soft line breaks. If false, the glyphs in the
 *   text will be positioned as if there was unlimited horizontal space. If [softWrap] is false,
 *   [overflow] and TextAlign may have unexpected effects.
 * @param onTextLayout callback that is executed when a new text layout is calculated. A
 *   [TextLayoutResult] object that callback provides contains paragraph information, size of the
 *   text, baselines and other details. The callback can be used to add additional decoration or
 *   functionality to the text. For example, to draw selection around the text.
 */
@Composable
fun MegaText(
    text: String,
    textColor: TextColor,
    modifier: Modifier = Modifier,
    overflow: TextOverflow = TextOverflow.Clip,
    maxLines: Int = Int.MAX_VALUE,
    minLines: Int = 1,
    style: TextStyle = LocalTextStyle.current,
    textAlign: TextAlign? = null,
    softWrap: Boolean = true,
    onTextLayout: ((TextLayoutResult) -> Unit)? = null,
) = Text(
    text,
    modifier = modifier,
    color = DSTokens.textColor(textColor = textColor),
    overflow = overflow,
    maxLines = maxLines,
    minLines = minLines,
    style = style,
    textAlign = textAlign,
    softWrap = softWrap,
    onTextLayout = onTextLayout
)

@Composable
fun MegaText(
    text: String,
    linkColor: LinkColor,
    modifier: Modifier = Modifier,
    overflow: TextOverflow = TextOverflow.Clip,
    maxLines: Int = Int.MAX_VALUE,
    minLines: Int = 1,
    style: TextStyle = LocalTextStyle.current,
    textAlign: TextAlign? = null,
) = Text(
    text,
    modifier = modifier,
    color = DSTokens.linkColor(linkColor = linkColor),
    overflow = overflow,
    maxLines = maxLines,
    minLines = minLines,
    style = style,
    textAlign = textAlign,
)

@CombinedThemePreviews
@Composable
private fun MegaTextPreview(
    @PreviewParameter(TextColorProvider::class) textColor: TextColor,
) {
    AndroidThemeForPreviews {
        MegaText(
            text = textColor.name,
            textColor = textColor,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp)
        )
    }
}

@CombinedThemePreviews
@Composable
private fun MegaTextPreview(
    @PreviewParameter(LinkColorProvider::class) linkColor: LinkColor
) {
    AndroidThemeForPreviews {
        MegaText(
            text = linkColor.name,
            linkColor = linkColor,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp)
        )
    }
}

private class TextColorProvider : PreviewParameterProvider<TextColor> {
    override val values = TextColor.entries.asSequence()
}

private class LinkColorProvider : PreviewParameterProvider<LinkColor> {
    override val values = LinkColor.entries.asSequence()
}