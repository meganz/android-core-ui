package mega.android.core.ui.components.toolbar

import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import mega.android.core.ui.R
import mega.android.core.ui.preview.CombinedThemePreviews
import mega.android.core.ui.theme.AndroidThemeForPreviews
import mega.android.core.ui.theme.AppTheme

val TOOLBAR_DEFAULT_HEIGHT = 64.dp

@Composable
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
private fun DefaultTopAppBar(
    modifier: Modifier,
    title: String,
    navigationIcon: @Composable () -> Unit = {},
    actions: @Composable RowScope.() -> Unit = {},
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
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = AppTheme.colors.background.pageBackground,
            navigationIconContentColor = AppTheme.colors.icon.primary,
            titleContentColor = AppTheme.colors.text.primary,
            actionIconContentColor = AppTheme.colors.icon.primary
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