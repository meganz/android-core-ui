package mega.android.core.ui.components.navigation

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import mega.android.core.ui.tokens.theme.DSTokens

@Composable
fun RowScope.NavigationBottomBarItem(
    @DrawableRes defaultIcon: Int,
    @DrawableRes selectedIcon: Int,
    label: String,
    isSelected: Boolean,
    onClick: () -> Unit,
) {
    NavigationBarItem(
        icon = {
            val icon = if (isSelected) selectedIcon else defaultIcon
            Icon(ImageVector.vectorResource(icon), contentDescription = label)
        },
        label = { Text(label) },
        selected = isSelected,
        onClick = onClick,
        colors = NavigationBarItemDefaults.colors(
            selectedIconColor = DSTokens.colors.button.brand,
            unselectedIconColor = DSTokens.colors.icon.secondary,
            selectedTextColor = DSTokens.colors.button.brand,
            unselectedTextColor = DSTokens.colors.text.secondary,
            indicatorColor = DSTokens.colors.background.pageBackground,
        )
    )
}