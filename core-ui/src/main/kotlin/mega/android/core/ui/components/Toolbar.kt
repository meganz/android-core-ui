package mega.android.core.ui.components

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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DefaultToolbar(
    title: String,
    icon: Painter,
    modifier: Modifier = Modifier,
    onIconClicked: () -> Unit = {}
) {
    TopAppBar(
        modifier = modifier,
        windowInsets = WindowInsets(top = 0.dp, bottom = 0.dp),
        title = { Text(text = title) },
        navigationIcon = {
            IconButton(
                modifier = Modifier
                    .wrapContentHeight(),
                onClick = onIconClicked
            ) {
                Icon(
                    painter = icon,
                    contentDescription = "$title Navigation Icon"
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = AppTheme.colors.background.pageBackground,
            navigationIconContentColor = AppTheme.colors.text.primary,
            titleContentColor = AppTheme.colors.text.primary,
            actionIconContentColor = AppTheme.colors.text.primary
        )
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoIconToolbar(
    title: String,
    modifier: Modifier = Modifier,
) {
    TopAppBar(
        modifier = modifier,
        windowInsets = WindowInsets(top = 0.dp, bottom = 0.dp),
        title = { Text(text = title) },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = AppTheme.colors.background.pageBackground,
            navigationIconContentColor = AppTheme.colors.text.primary,
            titleContentColor = AppTheme.colors.text.primary,
            actionIconContentColor = AppTheme.colors.text.primary
        )
    )
}

@CombinedThemePreviews
@Composable
private fun DefaultToolbarPreview() {
    AndroidThemeForPreviews {
        DefaultToolbar(
            title = "Title",
            icon = painterResource(id = R.drawable.ic_arrow_back)
        ) {}
    }
}

@CombinedThemePreviews
@Composable
private fun NoIconToolbarPreview() {
    AndroidThemeForPreviews {
        NoIconToolbar(title = "Title")
    }
}