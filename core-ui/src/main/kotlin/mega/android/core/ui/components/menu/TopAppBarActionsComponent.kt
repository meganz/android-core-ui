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
import mega.android.core.ui.components.image.MegaIcon
import mega.android.core.ui.model.menu.MenuActionWithClick
import mega.android.core.ui.model.menu.MenuActionWithIcon
import mega.android.core.ui.theme.values.IconColor
import mega.android.core.ui.theme.values.TextColor
import mega.android.core.ui.tokens.theme.DSTokens


/**
 * Main component that renders top app bar actions with intelligent overflow handling.
 *
 * This component efficiently manages menu actions by:
 * 1. Sorting actions by their orderInCategory property
 * 2. Separating icon actions from non-icon actions
 * 3. Displaying icon actions as toolbar buttons (up to maxActionsToShow limit)
 * 4. Moving overflow actions to a dropdown menu
 *
 * @param actions List of menu actions to display
 * @param enabled Whether all actions should be enabled/disabled globally
 * @param maxActionsToShow Maximum number of icon actions to show in the toolbar before overflow
 */
@Composable
internal fun RowScope.TopAppBarActionsComponent(
    actions: List<MenuActionWithClick>,
    enabled: Boolean,
    maxActionsToShow: Int,
) {
    // Sort actions once by their category order for consistent display
    val sortedActions = remember(actions) {
        actions.sortedBy { it.menuAction.orderInCategory }
    }

    // Partition actions into icon and non-icon categories for efficient processing
    val (iconActions, nonIconActions) = remember(sortedActions) {
        sortedActions.partition { it.menuAction is MenuActionWithIcon }
    }

    // Determine which icon actions can be displayed in the toolbar
    val (visibleIconActions, overflowIconActions) = remember(iconActions, maxActionsToShow) {
        iconActions.take(maxActionsToShow) to iconActions.drop(maxActionsToShow)
    }

    // Render visible icon actions as toolbar buttons
    RenderVisibleIconActions(
        actions = visibleIconActions,
        enabled = enabled
    )

    // Combine overflow icon actions with non-icon actions for dropdown menu
    val allOverflowActions = remember(overflowIconActions, nonIconActions) {
        overflowIconActions + nonIconActions
    }

    // Show overflow dropdown only if there are actions to display
    if (allOverflowActions.isNotEmpty()) {
        OverflowDropDown(
            actions = allOverflowActions,
            enabled = enabled,
        )
    }
}

/**
 * Renders visible icon actions as individual icon buttons in the toolbar.
 *
 * This function is extracted to improve readability and enable better composition.
 * Each action is rendered as an IconButtonWithTooltip with proper accessibility support.
 *
 * @param actions List of icon actions to render in the toolbar
 * @param enabled Global enabled state for all actions
 */
@Composable
private fun RenderVisibleIconActions(
    actions: List<MenuActionWithClick>,
    enabled: Boolean
) {
    actions.forEach { action ->
        // Safe cast since we know these are MenuActionWithIcon from partitioning
        val menuAction = action.menuAction as MenuActionWithIcon

        IconButtonWithTooltip(
            iconPainter = menuAction.getIconPainter(),
            description = menuAction.getDescription(),
            onClick = { action.onClick() },
            modifier = menuAction.modifier.testTag(menuAction.testTag),
            enabled = enabled && menuAction.enabled,
            highlightIconColor = menuAction.highlightIcon,
        )
    }
}

/**
 * Reusable icon button component with integrated tooltip functionality.
 *
 * Features:
 * - Material 3 tooltip that appears on long press
 * - Haptic feedback for better user experience
 * - Proper accessibility support with content descriptions
 * - Theme-aware icon coloring based on enabled/highlight state
 * - Standard 48dp minimum touch target for accessibility
 *
 * @param iconPainter The painter for the icon to display
 * @param description Accessible description and tooltip text
 * @param onClick Callback when the button is clicked
 * @param modifier Modifier for additional customization
 * @param enabled Whether the button is enabled and clickable
 * @param highlightIconColor Whether to use accent color for the icon
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun IconButtonWithTooltip(
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
                // Ensure minimum touch target size for accessibility
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
            MegaIcon(
                modifier = Modifier.align(Alignment.Center),
                painter = iconPainter,
                contentDescription = description,
                tint = when {
                    enabled && highlightIconColor -> IconColor.Accent
                    enabled -> IconColor.Primary
                    else -> IconColor.Disabled
                }
            )
        }
    }
}

/**
 * Overflow dropdown menu component for actions that don't fit in the toolbar.
 *
 * This component handles:
 * - Three-dot menu button with accessibility support
 * - Dropdown menu positioned above the toolbar icons
 * - Automatic menu dismissal on item selection
 * - Proper theming and disabled state handling
 *
 * The dropdown is positioned using a zero-height Box aligned to the top to ensure
 * it appears above the toolbar icons rather than below them.
 *
 * @param actions List of overflow actions to display in the dropdown
 * @param enabled Global enabled state for the overflow menu and its items
 */
@OptIn(ExperimentalComposeUiApi::class)
@Composable
private fun RowScope.OverflowDropDown(
    actions: List<MenuActionWithClick>,
    enabled: Boolean = true,
) {
    // State to control dropdown menu visibility
    var showMoreMenu by remember { mutableStateOf(false) }

    // Three-dot overflow button container
    Box(contentAlignment = Alignment.BottomEnd) {
        IconButtonWithTooltip(
            iconPainter = painterResource(id = R.drawable.ic_more_vertical_medium_thin_outline),
            description = stringResource(id = androidx.appcompat.R.string.abc_action_menu_overflow_description),
            onClick = { showMoreMenu = !showMoreMenu },
            modifier = Modifier.testTag(TAG_TOP_APP_BAR_ACTIONS_SHOW_MORE),
            enabled = enabled,
        )
    }

    // Dropdown menu positioned above the toolbar using zero-height top-aligned Box
    // This ensures the menu appears above the icons rather than below them
    Box(modifier = Modifier.align(Alignment.Top)) {
        DropdownMenu(
            modifier = Modifier
                .semantics { testTagsAsResourceId = true }
                .background(DSTokens.colors.background.surface1),
            expanded = showMoreMenu,
            onDismissRequest = { showMoreMenu = false }
        ) {
            // Render each overflow action as a dropdown menu item
            actions.forEach { (menuAction, onActionClick) ->
                DropdownMenuItem(
                    onClick = {
                        onActionClick()
                        showMoreMenu = false // Auto-dismiss menu after selection
                    },
                    modifier = Modifier.testTag(menuAction.testTag),
                    text = {
                        MegaText(
                            text = menuAction.getDescription(),
                            textColor = TextColor.Primary
                        )
                    },
                    // Respect both global and individual action enabled states
                    enabled = enabled && menuAction.enabled
                )
            }
        }
    }
}

/**
 * Test tag for the "Show more" top app bar actions button.
 */
const val TAG_TOP_APP_BAR_ACTIONS_SHOW_MORE = "top_app_bar_actions:show_more"
