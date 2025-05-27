package mega.android.core.ui.components.menu

import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.PlainTooltip
import androidx.compose.material3.Text
import androidx.compose.material3.TooltipBox
import androidx.compose.material3.TooltipDefaults
import androidx.compose.material3.rememberTooltipState
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import mega.android.core.ui.model.TopAppBarAction
import mega.android.core.ui.tokens.theme.DSTokens
import mega.privacy.android.shared.original.core.ui.model.TopAppBarActionWithClick

/**
 * Utility function to generate actions for [MegaTopAppBar]
 * @param actions the actions to be added
 * @param enabled if false, all actions will be disabled, if true each [TopAppBarAction.enabled] will be used to check if the action is enabled or not
 */
@Composable
internal fun TopAppBarActionsComponent(
    actions: List<TopAppBarActionWithClick>,
    enabled: Boolean = true,
) {
    val sortedActions = actions.sortedBy { it.topAppBarAction.orderInCategory }
    sortedActions.forEach { (menuAction, onActionClick) ->
        IconButtonWithTooltip(
            iconPainter = menuAction.getIconPainter(),
            description = menuAction.getDescription(),
            onClick = { onActionClick() },
            modifier = Modifier.testTag(menuAction.testTag),
            enabled = enabled,
            highlightIconColor = menuAction.highlightIcon,
        )
    }
}

/**
 * IconButton with a tooltip that shows the [description] when long clicked
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun IconButtonWithTooltip(
    iconPainter: Painter,
    description: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    highlightIconColor: Boolean = false,
) {
    val tooltipState = rememberTooltipState()
    val scope = rememberCoroutineScope()
    val haptic = LocalHapticFeedback.current
    TooltipBox(
        modifier = modifier,
        positionProvider = TooltipDefaults.rememberPlainTooltipPositionProvider(),
        tooltip = {
            PlainTooltip { Text(description) }
        },
        state = tooltipState
    ) {
        Box(modifier = Modifier
            .sizeIn(minWidth = 48.dp, minHeight = 48.dp)
            .combinedClickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = ripple(radius = 24.dp),
                role = Role.Button,
                onClick = onClick,
                enabled = enabled,
                onLongClick = {
                    haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                    scope.launch { tooltipState.show() }
                },
            )) {
            Icon(
                modifier = Modifier.align(Alignment.Center),
                painter = iconPainter,
                contentDescription = description,
                tint = when {
                    enabled && highlightIconColor -> DSTokens.colors.icon.accent
                    enabled -> DSTokens.colors.icon.primary
                    else -> DSTokens.colors.icon.disabled
                }
            )
        }
    }
}