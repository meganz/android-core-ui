package mega.android.core.ui.components.chip

import androidx.annotation.DrawableRes
import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import mega.android.core.ui.preview.CombinedThemePreviews
import mega.android.core.ui.theme.AndroidThemeForPreviews
import mega.android.core.ui.tokens.theme.DSTokens
import mega.android.core.ui.R

/**
 * Action carried by the trailing icon of a [MegaChip], such as removing the chip.
 *
 * @param contentDescription accessibility label of the trailing icon, for example "Remove pdf"
 * @param onClick callback when the trailing icon is clicked
 */
data class ChipTrailingAction(
    val contentDescription: String,
    val onClick: () -> Unit,
)

/**
 * Chip to filter lists based on user interaction
 *
 * @param selected if chip is selected or not
 * @param text text of chip
 * @param modifier optional modifier
 * @param style style of chip
 * @param onClick callback this chip is clicked
 * @param enabled if chip is enabled or grayed out
 * @param trailingAction when set, the trailing icon becomes its own hit target and stops
 * triggering [onClick]. Requires [trailingIcon]
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
    trailingAction: ChipTrailingAction? = null,
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
                    contentDescription = null,
                )
            }
        },
        trailingIcon = trailingIcon?.let {
            {
                Icon(
                    modifier = Modifier.size(16.dp),
                    imageVector = ImageVector.vectorResource(id = it),
                    contentDescription = trailingAction?.contentDescription,
                )
            }
        },
        onClick = onClick,
        trailingAction = trailingAction,
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
 * @param trailingAction when set, the trailing icon becomes its own hit target and stops
 * triggering [onClick]. Requires [trailingPainter]
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
    trailingAction: ChipTrailingAction? = null,
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
                    contentDescription = null,
                )
            }
        },
        trailingIcon = trailingPainter?.let {
            {
                Icon(
                    modifier = Modifier.size(16.dp),
                    painter = it,
                    contentDescription = trailingAction?.contentDescription,
                )
            }
        },
        onClick = onClick,
        trailingAction = trailingAction,
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
    trailingAction: ChipTrailingAction? = null,
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isFocused by interactionSource.collectIsFocusedAsState()
    val trailingInteractionSource = remember { MutableInteractionSource() }
    val isTrailingFocused by trailingInteractionSource.collectIsFocusedAsState()
    FilterChip(
        modifier = modifier
            .chipSemantics(text = text, hasTrailingAction = trailingAction != null)
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
        trailingIcon = trailingIcon?.let { icon ->
            if (trailingAction == null) icon else {
                {
                    Box(
                        modifier = Modifier
                            .fillMaxHeight()
                            .clip(CircleShape)
                            .then(
                                if (isTrailingFocused) {
                                    Modifier.border(
                                        width = 4.dp,
                                        color = DSTokens.colors.focus.colorFocus,
                                        shape = CircleShape,
                                    )
                                } else {
                                    Modifier
                                }
                            )
                            .clickable(
                                interactionSource = trailingInteractionSource,
                                indication = LocalIndication.current,
                                enabled = enabled,
                                role = Role.Button,
                                onClick = trailingAction.onClick,
                            )
                            .padding(horizontal = 4.dp),
                        contentAlignment = Alignment.Center,
                    ) {
                        icon()
                    }
                }
            }
        },
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

/**
 * A chip collapses into a single flat label, but it cannot do so once it holds a second hit
 * target: clearing the subtree would take the trailing action's node with it.
 */
private fun Modifier.chipSemantics(text: String, hasTrailingAction: Boolean) =
    if (hasTrailingAction) {
        semantics { contentDescription = text }
    } else {
        clearAndSetSemantics { contentDescription = text }
    }

@CombinedThemePreviews
@Composable
private fun ChipPreview() {
    AndroidThemeForPreviews {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            MegaChip(
                selected = true,
                text = "Type",
            )
            MegaChip(
                selected = false,
                text = "Type",
            )
            MegaChip(
                selected = false,
                text = "pdf",
                trailingIcon = R.drawable.ic_x_medium_thin_outline,
                trailingAction = ChipTrailingAction(
                    contentDescription = "Remove pdf",
                    onClick = {},
                ),
            )
        }
    }
}

@CombinedThemePreviews
@Composable
private fun ChipPreviewWithPainter() {
    AndroidThemeForPreviews {
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
