package mega.android.core.ui.components.menu

import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.PlainTooltip
import androidx.compose.material3.Text
import androidx.compose.material3.TooltipBox
import androidx.compose.material3.TooltipDefaults
import androidx.compose.material3.rememberTooltipState
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTagsAsResourceId
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import mega.android.core.ui.R
import mega.android.core.ui.components.MegaText
import mega.android.core.ui.model.menu.MenuActionIconWithClick
import mega.android.core.ui.theme.values.TextColor
import mega.android.core.ui.tokens.theme.DSTokens

/**
 * Utility function to generate actions for [MegaTopAppBar]
 * @param actions the actions to be added
 * @param enabled if false, all actions will be disabled, if true each [TopAppBarAction.enabled] will be used to check if the action is enabled or not
 * @param maxActionsToShow if there are more actions than that the extra actions will be shown in a drop down menu
 */
@Composable
internal fun RowScope.TopAppBarActionsComponent(
    actions: List<MenuActionIconWithClick>,
    enabled: Boolean = true,
    maxActionsToShow: Int = Int.MAX_VALUE,
) {
    val sortedActions = actions.sortedBy { it.menuAction.orderInCategory }
    val visible = sortedActions.take(maxActionsToShow)
    visible.forEach { (menuAction, onActionClick) ->
        IconButtonWithTooltip(
            iconPainter = menuAction.getIconPainter(),
            description = menuAction.getDescription(),
            onClick = { onActionClick() },
            modifier = menuAction.modifier.testTag(menuAction.testTag),
            enabled = enabled && menuAction.enabled,
            highlightIconColor = menuAction.highlightIcon,
        )
    }
    sortedActions.filterNot { visible.contains(it) }.takeIf { it.isNotEmpty() }?.let { notVisible ->
        OverflowDropDown(
            actions = notVisible,
            enabled = enabled,
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
        Box(
            modifier = Modifier
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
                )
        ) {
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

@OptIn(ExperimentalComposeUiApi::class)
@Composable
private fun RowScope.OverflowDropDown(
    actions: List<MenuActionIconWithClick>,
    enabled: Boolean = true,
) {
    var showMoreMenu by remember {
        mutableStateOf(false)
    }
    Box(contentAlignment = Alignment.BottomEnd) {
        IconButtonWithTooltip(
            iconPainter = painterResource(id = R.drawable.ic_more_vertical_medium_thin_outline),
            description = stringResource(id = androidx.appcompat.R.string.abc_action_menu_overflow_description),
            onClick = { showMoreMenu = !showMoreMenu },
            modifier = Modifier.testTag(TAG_TOP_APP_BAR_ACTIONS_SHOW_MORE),
            enabled = enabled,
        )
    }
    // Overflow menu, as opposed to an ordinary DropdownMenu, is show on top of the icons. With this top 0 size box we do the trick
    Box(modifier = Modifier.align(Alignment.Top)) {
        DropdownMenu(
            modifier = Modifier
                .semantics { testTagsAsResourceId = true }
                .background(DSTokens.colors.background.surface1),
            expanded = showMoreMenu,
            onDismissRequest = {
                showMoreMenu = false
            }
        ) {
            actions.forEach { (menuAction, onActionClick) ->
                DropdownMenuItem(
                    onClick = {
                        onActionClick()
                        showMoreMenu = false
                    },
                    modifier = Modifier.testTag(menuAction.testTag),
                    text = {
                        MegaText(text = menuAction.getDescription(), textColor = TextColor.Primary)
                    }
                )
            }
        }
    }
}

/**
 * test tag for the "Show more" top app bar actions button
 */
const val TAG_TOP_APP_BAR_ACTIONS_SHOW_MORE = "top_app_bar_actions:show_more"