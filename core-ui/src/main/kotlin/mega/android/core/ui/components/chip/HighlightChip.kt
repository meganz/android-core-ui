package mega.android.core.ui.components.chip

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import mega.android.core.ui.components.text.HighlightedText
import mega.android.core.ui.preview.BooleanProvider
import mega.android.core.ui.preview.CombinedThemePreviews
import mega.android.core.ui.theme.AndroidThemeForPreviews
import mega.android.core.ui.tokens.theme.DSTokens

/**
 * Chip with highlighted text inside
 *
 * @param selected if chip is selected or not
 * @param text text of chip
 * @param highlightText highlighted part of text
 * @param modifier optional modifier
 * @param style style of chip
 * @param onClick callback this chip is clicked
 * @param enabled if chip is enabled or grayed out
 */
@Composable
fun HighlightChip(
    text: String,
    highlightText: String,
    modifier: Modifier = Modifier,
    selected: Boolean = false,
    style: ChipStyle = TransparentChipStyle,
    onClick: () -> Unit = {},
    enabled: Boolean = true,
) {
    FilterChip(
        modifier = modifier
            .clearAndSetSemantics {
                this.contentDescription = text
            }
            .height(32.dp),
        selected = selected,
        enabled = enabled,
        onClick = onClick,
        colors = style.selectableChipColors(),
        border = FilterChipDefaults.filterChipBorder(
            enabled = enabled,
            selected = selected,
            selectedBorderWidth = 1.dp,
            borderColor = DSTokens.colors.border.strong,
            selectedBorderColor = DSTokens.colors.border.strongSelected
        ),
        shape = RoundedCornerShape(8.dp),
        label = {
            HighlightedText(
                text = text,
                highlightText = highlightText,
                highlightFontWeight = FontWeight.Medium,
                textAlign = TextAlign.Center
            )
        }
    )
}

@CombinedThemePreviews
@Composable
private fun HighlightChipPreview(
    @PreviewParameter(BooleanProvider::class) selected: Boolean,
) {
    AndroidThemeForPreviews {
        HighlightChip(
            selected = selected,
            text = "#ThisIsATag",
            highlightText = "ATag"
        )
    }
}
