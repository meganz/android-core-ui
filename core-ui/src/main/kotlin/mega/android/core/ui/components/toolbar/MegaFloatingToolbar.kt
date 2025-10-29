package mega.android.core.ui.components.toolbar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import mega.android.core.ui.R
import mega.android.core.ui.components.menu.TopAppBarActionsComponent
import mega.android.core.ui.model.menu.MenuActionWithClick
import mega.android.core.ui.model.menu.MenuActionWithIcon
import mega.android.core.ui.preview.CombinedThemePreviews
import mega.android.core.ui.theme.AndroidThemeForPreviews
import mega.android.core.ui.tokens.theme.DSTokens

/**
 * A floating toolbar component that follows Material Design 3 specifications.
 * This component displays a horizontal floating toolbar with action buttons.
 *
 * @param actions List of actions to display in the toolbar
 * @param modifier Modifier to be applied to the toolbar
 * @param actionsEnabled Whether the actions should be enabled
 * @param elevation The elevation of the floating toolbar
 */
@Composable
fun MegaFloatingToolbar(
    actions: List<MenuActionWithClick>,
    modifier: Modifier = Modifier,
    actionsEnabled: Boolean = true,
    elevation: Dp = 6.dp,
) {
    Card(
        modifier = modifier,
        shape = CircleShape,
        elevation = CardDefaults.cardElevation(defaultElevation = elevation),
        colors = CardDefaults.cardColors(
            containerColor = DSTokens.colors.background.surface1
        )
    ) {
        Row(
            modifier = Modifier
                .height(64.dp)
                .padding(DSTokens.spacings.s3),
            horizontalArrangement = Arrangement.spacedBy(DSTokens.spacings.s2),
            verticalAlignment = Alignment.CenterVertically
        ) {
            TopAppBarActionsComponent(
                actions = actions,
                enabled = actionsEnabled,
                maxActionsToShow = Int.MAX_VALUE
            )
        }
    }
}


/**
 * A floating toolbar component with actions and click handler.
 *
 * @param actions List of actions to display in the toolbar
 * @param onActionPressed Callback when an action is pressed
 * @param modifier Modifier to be applied to the toolbar
 * @param actionsEnabled Whether the actions should be enabled
 * @param elevation The elevation of the floating toolbar
 */
@Composable
fun MegaFloatingToolbar(
    actions: List<MenuActionWithIcon>,
    onActionPressed: ((MenuActionWithIcon) -> Unit),
    modifier: Modifier = Modifier,
    actionsEnabled: Boolean = true,
    elevation: Dp = 8.dp,
) = MegaFloatingToolbar(
    modifier = modifier,
    actions = actions.addClick(onActionPressed),
    actionsEnabled = actionsEnabled,
    elevation = elevation,
)


@CombinedThemePreviews
@Composable
private fun MegaFloatingToolbarPreview() {
    val actions = listOf(
        R.drawable.ic_alert_circle_medium_thin_outline,
        R.drawable.ic_alert_triangle_medium_thin_outline,
        R.drawable.ic_check_medium_thin_outline,
        R.drawable.ic_close_medium_thin_outline,
        R.drawable.ic_help_circle_medium_thin_outline
    ).mapIndexed { i, iconRes ->
        object : MenuActionWithIcon {
            @Composable
            override fun getDescription() = "Action $i"

            override val testTag = getDescription()

            @Composable
            override fun getIconPainter() = painterResource(id = iconRes)
        }
    }

    AndroidThemeForPreviews {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Black)
                .padding(16.dp),
            contentAlignment = Alignment.BottomCenter
        ) {
            MegaFloatingToolbar(
                actions = actions.map { MenuActionWithClick(it) {} },
                modifier = Modifier.align(Alignment.BottomCenter)
            )
        }
    }
}