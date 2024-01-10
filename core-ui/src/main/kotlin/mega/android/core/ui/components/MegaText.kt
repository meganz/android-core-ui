package mega.android.core.ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import mega.android.core.ui.preview.CombinedThemePreviews
import mega.android.core.ui.theme.AndroidThemeForPreviews
import mega.android.core.ui.theme.AppTheme
import mega.android.core.ui.theme.tokens.TextColor

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
) = Text(
    text,
    modifier = modifier,
    color = AppTheme.textColor(textColor = textColor),
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

private class TextColorProvider : PreviewParameterProvider<TextColor> {
    override val values = TextColor.values().asSequence()
}