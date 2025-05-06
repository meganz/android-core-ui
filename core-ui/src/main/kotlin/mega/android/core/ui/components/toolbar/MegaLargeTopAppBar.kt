package mega.android.core.ui.components.toolbar

import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import mega.android.core.ui.R
import mega.android.core.ui.preview.CombinedThemePreviews
import mega.android.core.ui.theme.AndroidThemeForPreviews
import mega.android.core.ui.tokens.theme.DSTokens

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MegaLargeTopAppBar(
    title: String,
    modifier: Modifier = Modifier,
    icon: Painter? = null,
    onIconClicked: () -> Unit = {},
    trailingElement: (@Composable (RowScope.() -> Unit))? = null,
    scrollBehavior: TopAppBarScrollBehavior? = null
) {
    LargeTopAppBar(
        modifier = modifier,
        windowInsets = WindowInsets(top = 0.dp, bottom = 0.dp),
        title = { Text(text = title) },
        navigationIcon = {
            if (icon != null) {
                IconButton(
                    modifier = Modifier.wrapContentHeight(), onClick = onIconClicked
                ) {
                    Icon(
                        painter = icon, contentDescription = "$title Navigation Icon"
                    )
                }
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = DSTokens.colors.background.pageBackground,
            scrolledContainerColor = DSTokens.colors.background.pageBackground,
            navigationIconContentColor = DSTokens.colors.icon.primary,
            titleContentColor = DSTokens.colors.text.primary,
            actionIconContentColor = DSTokens.colors.icon.primary
        ),
        actions = {
            if (trailingElement != null) {
                trailingElement()
            }
        },
        scrollBehavior = scrollBehavior
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@CombinedThemePreviews
@Composable
private fun LargeToolbarPreview() {
    AndroidThemeForPreviews {
        MegaLargeTopAppBar(title = "Title")
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@CombinedThemePreviews
@Composable
private fun LargeToolbarWithLeadingIconPreview() {
    AndroidThemeForPreviews {
        MegaLargeTopAppBar(title = "Title", icon = painterResource(id = R.drawable.ic_arrow_left))
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@CombinedThemePreviews
@Composable
private fun LargeToolbarWithBothIconsPreview() {
    AndroidThemeForPreviews {
        MegaLargeTopAppBar(title = "Title",
            icon = painterResource(id = R.drawable.ic_arrow_left),
            trailingElement = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_check_circle),
                    contentDescription = null,
                    modifier = Modifier.size(24.dp),
                    tint = DSTokens.colors.icon.primary
                )
            })
    }
}