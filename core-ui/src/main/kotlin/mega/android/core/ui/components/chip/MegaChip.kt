package mega.android.core.ui.components.chip

import androidx.annotation.DrawableRes
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import mega.android.core.ui.preview.CombinedThemePreviews
import mega.android.core.ui.theme.AndroidTheme
import mega.android.core.ui.tokens.theme.DSTokens
import mega.android.core.ui.R

/**
 * Chip to filter lists based on user interaction
 *
 * @param selected if chip is selected or not
 * @param text text of chip
 * @param modifier optional modifier
 * @param style style of chip
 * @param onClick callback this chip is clicked
 * @param enabled if chip is enabled or grayed out
 */
@Composable
fun MegaChip(
    selected: Boolean,
    text: String,
    modifier: Modifier = Modifier,
    style: ChipStyle = DefaultChipStyle,
    enabled: Boolean = true,
    @DrawableRes leadingIcon: Int? = null,
    @DrawableRes trailingIcon: Int? = null,
    onClick: () -> Unit = {},
) {
    MegaChipContent(
        selected = selected,
        text = text,
        modifier = modifier,
        style = style,
        enabled = enabled,
        leadingIcon = leadingIcon?.let {
            {
                Icon(
                    modifier = Modifier.size(16.dp),
                    imageVector = ImageVector.vectorResource(id = it),
                    contentDescription = "Leading icon",
                )
            }
        },
        trailingIcon = trailingIcon?.let {
            {
                Icon(
                    modifier = Modifier.size(16.dp),
                    imageVector = ImageVector.vectorResource(id = it),
                    contentDescription = "Trailing icon",
                )
            }
        },
        onClick = onClick
    )
}

/**
 * Chip to filter lists based on user interaction with painter icons
 *
 * @param selected if chip is selected or not
 * @param content text of chip
 * @param modifier optional modifier
 * @param style style of chip
 * @param onClick callback this chip is clicked
 * @param enabled if chip is enabled or grayed out
 * @param leadingPainter painter for leading icon
 * @param trailingPainter painter for trailing icon
 */
@Composable
fun MegaChip(
    selected: Boolean,
    content: String,
    modifier: Modifier = Modifier,
    style: ChipStyle = DefaultChipStyle,
    enabled: Boolean = true,
    leadingPainter: Painter? = null,
    trailingPainter: Painter? = null,
    onClick: () -> Unit = {},
) {
    MegaChipContent(
        selected = selected,
        text = content,
        modifier = modifier,
        style = style,
        enabled = enabled,
        leadingIcon = leadingPainter?.let {
            {
                Icon(
                    modifier = Modifier.size(16.dp),
                    painter = it,
                    contentDescription = "Leading icon",
                )
            }
        },
        trailingIcon = trailingPainter?.let {
            {
                Icon(
                    modifier = Modifier.size(16.dp),
                    painter = it,
                    contentDescription = "Trailing icon"
                )
            }
        },
        onClick = onClick
    )
}

/**
 * Shared implementation for MegaChip with different icon types
 */
@Composable
private fun MegaChipContent(
    selected: Boolean,
    text: String,
    modifier: Modifier = Modifier,
    style: ChipStyle = DefaultChipStyle,
    enabled: Boolean = true,
    leadingIcon: (@Composable () -> Unit)? = null,
    trailingIcon: (@Composable () -> Unit)? = null,
    onClick: () -> Unit = {},
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isFocused by interactionSource.collectIsFocusedAsState()
    FilterChip(
        modifier = modifier
            .clearAndSetSemantics {
                this.contentDescription = text
            }
            .height(32.dp),
        selected = selected,
        enabled = enabled,
        onClick = { onClick() },
        colors = style.selectableChipColors(),
        interactionSource = interactionSource,
        border = FilterChipDefaults.filterChipBorder(
            enabled = enabled,
            selected = isFocused,
            selectedBorderWidth = 4.dp,
            borderColor = Color.Transparent,
            selectedBorderColor = DSTokens.colors.focus.colorFocus
        ),
        leadingIcon = leadingIcon,
        trailingIcon = trailingIcon,
        shape = RoundedCornerShape(16.dp),
        label = {
            Text(
                text = text,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    )
}

@CombinedThemePreviews
@Composable
private fun ChipPreview() {
    AndroidTheme(isDark = isSystemInDarkTheme()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            repeat(3) {
                MegaChip(
                    selected = it == 0,
                    text = "Type",
                )
            }
        }
    }
}

@CombinedThemePreviews
@Composable
private fun ChipPreviewWithPainter() {
    AndroidTheme(isDark = isSystemInDarkTheme()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            repeat(3) {
                MegaChip(
                    selected = it == 0,
                    content = "Painter",
                    leadingPainter = painterResource(R.drawable.ic_check_circle_medium_thin_outline),
                )
            }
        }
    }
}