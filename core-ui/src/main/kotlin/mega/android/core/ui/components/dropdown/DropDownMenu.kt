package mega.android.core.ui.components.dropdown

import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MenuDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import mega.android.core.ui.components.divider.StrongDivider
import mega.android.core.ui.theme.AppTheme
import mega.android.core.ui.tokens.theme.DSTokens

private val menuItemMinWidth = 220.dp

data class DropDownItem(
    val id: Int,
    val text: String,
    val leadingIcon: @Composable (() -> Unit)? = null,
    val trailingIcon: @Composable (() -> Unit)? = null,
    val enabled: Boolean = true,
    val hasDivider: Boolean = false
)

@Composable
fun MegaDropDownMenu(
    expanded: Boolean,
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier,
    offset: DpOffset = DpOffset(0.dp, 0.dp),
    dropdownItems: List<DropDownItem>,
    onItemClick: (DropDownItem) -> Unit
) {
    DropdownMenu(
        expanded = expanded,
        onDismissRequest = onDismissRequest,
        modifier = modifier.background(DSTokens.colors.background.surface1),
        offset = offset
    ) {
        dropdownItems.forEach { dropDownItem ->
            MegaDropDownMenuItem(dropDownItem = dropDownItem, onClick = {
                onItemClick(dropDownItem)
                onDismissRequest()
            })
        }
    }
}

@Composable
internal fun MegaDropDownMenuItem(
    modifier: Modifier = Modifier, dropDownItem: DropDownItem, onClick: () -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    val isHovered by interactionSource.collectIsHoveredAsState()

    val backgroundColor = when {
        isPressed -> DSTokens.colors.button.secondaryPressed
        isHovered -> DSTokens.colors.button.secondaryHover
        else -> DSTokens.colors.background.surface1
    }

    Column(
        modifier = modifier
            .widthIn(min = menuItemMinWidth)
            .wrapContentHeight()
    ) {
        DropdownMenuItem(
            modifier = modifier.background(backgroundColor),
            onClick = onClick,
            text = { Text(text = dropDownItem.text, style = AppTheme.typography.bodyLarge) },
            leadingIcon = dropDownItem.leadingIcon,
            trailingIcon = dropDownItem.trailingIcon,
            enabled = dropDownItem.enabled,
            contentPadding = PaddingValues(horizontal = 12.dp, vertical = 8.dp),
            colors = MenuDefaults.itemColors(
                textColor = DSTokens.colors.text.primary,
                leadingIconColor = DSTokens.colors.icon.primary,
                trailingIconColor = DSTokens.colors.icon.primary,
                disabledTextColor = DSTokens.colors.text.disabled,
                disabledLeadingIconColor = DSTokens.colors.icon.disabled,
                disabledTrailingIconColor = DSTokens.colors.icon.disabled
            ),
            interactionSource = interactionSource
        )
        if (dropDownItem.hasDivider) {
            StrongDivider(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp)
            )
        }
    }
}

