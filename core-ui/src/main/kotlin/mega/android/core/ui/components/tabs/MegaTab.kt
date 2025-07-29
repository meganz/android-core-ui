package mega.android.core.ui.components.tabs

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import mega.android.core.ui.theme.AppTheme
import mega.android.core.ui.tokens.theme.DSTokens

@Composable
internal fun MegaTab(
    isSelected: Boolean,
    title: String,
    showBadge: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isTabPressed by interactionSource.collectIsPressedAsState()
    val textStyle = AppTheme.typography.titleMedium.copy(
        fontWeight = if (isSelected) FontWeight.W500 else FontWeight.Normal
    )
    val textColor = when {
        isTabPressed -> DSTokens.colors.button.primaryPressed
        else -> DSTokens.colors.text.primary
    }
    Tab(
        modifier = modifier,
        selected = isSelected,
        onClick = onClick,
        interactionSource = interactionSource,
        text = {
            BadgedBox(
                badge = {
                    if (showBadge)
                        Badge(containerColor = DSTokens.colors.components.interactive)
                }
            ) {
                Text(text = title, color = textColor, style = textStyle)
            }
        },
    )
}