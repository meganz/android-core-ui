package mega.android.core.ui.components.navigation

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.NavigationBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.PreviewParameter
import mega.android.core.ui.R
import mega.android.core.ui.preview.BooleanProvider
import mega.android.core.ui.preview.CombinedThemePreviews
import mega.android.core.ui.theme.AndroidThemeForPreviews
import mega.android.core.ui.tokens.theme.DSTokens

@Composable
fun NavigationBottomBar(
    content: @Composable RowScope.() -> Unit
) {
    NavigationBar(
        containerColor = DSTokens.colors.background.pageBackground,
        content = content
    )
}

@CombinedThemePreviews
@Composable
private fun NavigationBottomBarPreview(
    @PreviewParameter(BooleanProvider::class) isSelected: Boolean,
) {
    AndroidThemeForPreviews {
        NavigationBottomBar {
            NavigationBottomBarItem(
                defaultIcon = R.drawable.ic_alert_circle,
                selectedIcon = R.drawable.ic_alert_circle,
                label = "Test",
                isSelected = isSelected,
                onClick = {}
            )
        }
    }
}