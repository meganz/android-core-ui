package mega.android.core.ui.components.toolbar

import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.Text
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


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LargeToolbar(
    title: String,
    modifier: Modifier = Modifier,
    icon: Painter? = null,
    onIconClicked: () -> Unit = {},
    trailingElement: (@Composable (RowScope.() -> Unit))? = null,
) {
    MediumTopAppBar(modifier = modifier,
        windowInsets = WindowInsets(top = 0.dp, bottom = 0.dp),
        title = {
            Text(
                text = title, style = AppTheme.typography.headlineMedium
            )
        },
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
            containerColor = AppTheme.colors.background.pageBackground,
            navigationIconContentColor = AppTheme.colors.icon.primary,
            titleContentColor = AppTheme.colors.text.primary,
            actionIconContentColor = AppTheme.colors.icon.primary
        ),
        actions = {
            if (trailingElement != null) {
                trailingElement()
            }
        })
}

@CombinedThemePreviews
@Composable
private fun LargeToolbarPreview() {
    AndroidThemeForPreviews {
        LargeToolbar(title = "Title")
    }
}

@CombinedThemePreviews
@Composable
private fun LargeToolbarWithLeadingIconPreview() {
    AndroidThemeForPreviews {
        LargeToolbar(title = "Title", icon = painterResource(id = R.drawable.ic_arrow_left))
    }
}

@CombinedThemePreviews
@Composable
private fun LargeToolbarWithBothIconsPreview() {
    AndroidThemeForPreviews {
        LargeToolbar(title = "Title",
            icon = painterResource(id = R.drawable.ic_arrow_left),
            trailingElement = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_check_circle),
                    contentDescription = null,
                    modifier = Modifier.size(24.dp),
                    tint = AppTheme.colors.icon.primary
                )
            })
    }
}