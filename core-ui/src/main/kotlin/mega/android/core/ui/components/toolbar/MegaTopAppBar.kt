package mega.android.core.ui.components.toolbar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import mega.android.core.ui.R
import mega.android.core.ui.components.button.SecondarySmallIconButton
import mega.android.core.ui.components.menu.TopAppBarActionsComponent
import mega.android.core.ui.model.TopAppBarAction
import mega.android.core.ui.preview.CombinedThemePreviews
import mega.android.core.ui.theme.AndroidThemeForPreviews
import mega.android.core.ui.theme.AppTheme
import mega.android.core.ui.theme.spacing.LocalSpacing
import mega.android.core.ui.tokens.theme.DSTokens
import mega.privacy.android.shared.original.core.ui.model.TopAppBarActionWithClick

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun MegaTopAppBar(
    title: String,
    navigationType: AppBarNavigationType,
    modifier: Modifier = Modifier,
    trailingIcons: @Composable RowScope.() -> Unit = {},
) {
    DefaultTopAppBar(
        modifier = modifier,
        title = title,
        navigationIcon = navigationType.navigationIcon(),
        actions = trailingIcons
    )
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun MegaTopAppBar(
    title: String,
    navigationType: AppBarNavigationType,
    actions: List<TopAppBarActionWithClick>,
    modifier: Modifier = Modifier,
    actionsEnabled: Boolean = true,
) {
    DefaultTopAppBar(
        modifier = modifier,
        title = title,
        navigationIcon = navigationType.navigationIcon(),
        actions = {
            TopAppBarActionsComponent(actions, actionsEnabled)
        }
    )
}

@Composable
fun MegaTopAppBar(
    title: String,
    navigationType: AppBarNavigationType,
    actions: List<TopAppBarAction>,
    onActionPressed: ((TopAppBarAction) -> Unit),
    modifier: Modifier = Modifier,
    actionsEnabled: Boolean = true,
) = MegaTopAppBar(
    modifier = modifier,
    title = title,
    navigationType = navigationType,
    actions = actions.addClick(onActionPressed),
    actionsEnabled = actionsEnabled,
)

internal fun List<TopAppBarAction>.addClick(onActionPressed: ((TopAppBarAction) -> Unit)?): List<TopAppBarActionWithClick> =
    this.map { TopAppBarActionWithClick(it) { onActionPressed?.invoke(it) } }

@Deprecated(message = "Please use the version of MegaTopAppBar with AppBarNavigationType instead of injecting the navigation painter.")
@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun MegaTopAppBar(
    title: String,
    modifier: Modifier = Modifier,
    navigationIcon: Painter? = null,
    trailingIcons: @Composable RowScope.() -> Unit = {},
    onNavigationIconClicked: () -> Unit = {},
) {
    DefaultTopAppBar(
        modifier = modifier,
        title = title,
        navigationIcon = {
            navigationIcon?.let {
                IconButton(
                    modifier = Modifier
                        .wrapContentHeight(),
                    onClick = onNavigationIconClicked
                ) {
                    Icon(
                        painter = navigationIcon,
                        contentDescription = "$title Navigation Icon"
                    )
                }
            }
        },
        actions = trailingIcons
    )
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun TransparentTopBar(
    modifier: Modifier = Modifier,
    navigationIcon: Painter? = null,
    trailingIcons: @Composable RowScope.() -> Unit = {},
    onNavigationIconClicked: () -> Unit = {},
    backgroundAlpha: Float = 0f
) {
    DefaultTopAppBar(
        modifier = Modifier
            .background(DSTokens.colors.background.pageBackground.copy(alpha = backgroundAlpha))
            .then(modifier),
        title = "",
        navigationIcon = {
            navigationIcon?.let {
                SecondarySmallIconButton(
                    modifier = Modifier
                        .padding(start = LocalSpacing.current.x12)
                        .size(32.dp),
                    icon = navigationIcon,
                    onClick = onNavigationIconClicked
                )
            }
        },
        actions = trailingIcons,
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.Transparent,
            scrolledContainerColor = Color.Transparent,
            navigationIconContentColor = DSTokens.colors.icon.primary,
            titleContentColor = DSTokens.colors.text.primary,
            actionIconContentColor = DSTokens.colors.icon.primary
        )
    )
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun DefaultTopAppBar(
    modifier: Modifier,
    title: String,
    navigationIcon: @Composable () -> Unit = {},
    actions: @Composable RowScope.() -> Unit = {},
    colors: TopAppBarColors? = null
) {
    TopAppBar(
        modifier = modifier,
        windowInsets = WindowInsets(top = 0.dp, bottom = 0.dp),
        title = {
            Text(
                text = title,
                style = AppTheme.typography.titleLarge
            )
        },
        navigationIcon = navigationIcon,
        actions = actions,
        colors = colors ?: TopAppBarDefaults.topAppBarColors(
            containerColor = DSTokens.colors.background.pageBackground,
            scrolledContainerColor = DSTokens.colors.background.pageBackground,
            navigationIconContentColor = DSTokens.colors.icon.primary,
            titleContentColor = DSTokens.colors.text.primary,
            actionIconContentColor = DSTokens.colors.icon.primary
        )
    )
}

@CombinedThemePreviews
@Composable
private fun MegaTopAppBarPreview() {
    AndroidThemeForPreviews {
        MegaTopAppBar(
            title = "Title",
            navigationIcon = painterResource(id = R.drawable.ic_arrow_left),
            trailingIcons = {
                IconButton(modifier = Modifier.wrapContentHeight(), onClick = {}) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_close),
                        contentDescription = "Cancel Icon"
                    )
                }
            },
            onNavigationIconClicked = {}
        )
    }
}

@CombinedThemePreviews
@Composable
private fun MegaTransparentTopAppBarPreview() {
    AndroidThemeForPreviews {
        Box(modifier = Modifier.background(color = Color.Red)) {
            TransparentTopBar(
                navigationIcon = painterResource(id = R.drawable.ic_arrow_left),
                trailingIcons = {
                    SecondarySmallIconButton(
                        modifier = Modifier
                            .padding(end = LocalSpacing.current.x12)
                            .size(32.dp),
                        icon = painterResource(id = R.drawable.ic_close),
                        onClick = { })
                },
                onNavigationIconClicked = {}
            )
        }
    }
}

@CombinedThemePreviews
@Composable
private fun MegaTopAppBarTypePreview(
    @PreviewParameter(NavigationTypeProvider::class) navigationType: AppBarNavigationType
) {
    AndroidThemeForPreviews {
        MegaTopAppBar(
            title = "Title",
            navigationType = navigationType,
            trailingIcons = {
                IconButton(modifier = Modifier.wrapContentHeight(), onClick = {}) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_close),
                        contentDescription = "Cancel Icon"
                    )
                }
            },
        )
    }
}

@CombinedThemePreviews
@Composable
private fun MegaTopAppBarActionsPreview() {
    val actions =
        listOf(R.drawable.ic_alert_circle, R.drawable.ic_alert_triangle).mapIndexed { i, iconRes ->
            object : TopAppBarAction {
                @Composable
                override fun getDescription() = "Action $i"

                override val testTag = getDescription()

                @Composable
                override fun getIconPainter() = painterResource(id = iconRes)

            }
        }
    AndroidThemeForPreviews {
        MegaTopAppBar(
            title = "Title",
            navigationType = AppBarNavigationType.Back {},
            actions = actions.map { TopAppBarActionWithClick(it) {} },
            modifier = Modifier.padding(bottom = 80.dp) //make some space for the tooltip in interactive mode
        )
    }
}

private class NavigationTypeProvider : PreviewParameterProvider<AppBarNavigationType> {
    override val values: Sequence<AppBarNavigationType>
        get() = sequenceOf(
            AppBarNavigationType.None,
            AppBarNavigationType.Back {},
            AppBarNavigationType.Close {},
        )

}