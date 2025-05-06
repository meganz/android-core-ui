package mega.android.core.ui.components.tabs

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import mega.android.core.ui.components.divider.StrongDivider
import mega.android.core.ui.model.TabItems
import mega.android.core.ui.preview.CombinedThemePreviews
import mega.android.core.ui.theme.AndroidThemeForPreviews
import mega.android.core.ui.tokens.theme.DSTokens
import mega.android.core.ui.theme.spacing.LocalSpacing

@Composable
fun MegaFixedTabRow(
    tabIndex: Int,
    items: List<TabItems>,
    onClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    TabRow(
        modifier = modifier.fillMaxWidth(),
        selectedTabIndex = tabIndex,
        divider = { StrongDivider() },
        containerColor = Color.Transparent,
        indicator = { tabPositions ->
            TabRowDefaults.SecondaryIndicator(
                Modifier.tabIndicatorOffset(tabPositions[tabIndex]),
                color = DSTokens.colors.border.brand
            )
        }
    ) {
        items.forEachIndexed { index, item ->
            MegaTab(
                isSelected = tabIndex == index,
                title = item.title,
                showBadge = item.showBadge,
                onClick = { onClick(index) }
            )
        }
    }
}

@Composable
fun MegaScrollableTabRow(
    tabIndex: Int,
    items: List<TabItems>,
    onClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
    withDivider: Boolean = true
) {
    ScrollableTabRow(
        modifier = modifier.fillMaxWidth(),
        selectedTabIndex = tabIndex,
        divider = { if (withDivider) { StrongDivider() } },
        containerColor = Color.Transparent,
        edgePadding = LocalSpacing.current.x16,
        indicator = { tabPositions ->
            TabRowDefaults.SecondaryIndicator(
                Modifier.tabIndicatorOffset(tabPositions[tabIndex]),
                color = DSTokens.colors.border.brand
            )
        }
    ) {
        items.forEachIndexed { index, item ->
            MegaTab(
                isSelected = tabIndex == index,
                title = item.title,
                showBadge = item.showBadge,
                onClick = { onClick(index) }
            )
        }
    }
}

@CombinedThemePreviews
@Composable
private fun MegaTabRowPreview() {
    AndroidThemeForPreviews {
        MegaFixedTabRow(
            tabIndex = 0,
            items = listOf(
                TabItems(title = "Tab 1"),
                TabItems(title = "Tab 2", showBadge = true),
                TabItems(title = "Tab 3"),
            ),
            onClick = {},
        )
    }
}

@CombinedThemePreviews
@Composable
private fun MegaScrollableTabRowPreview() {
    AndroidThemeForPreviews {
        Column(modifier = Modifier.fillMaxSize()) {
            MegaScrollableTabRow(
                tabIndex = 0,
                items = listOf(
                    TabItems(title = "Tab 1"),
                    TabItems(title = "Tab 2", showBadge = true),
                    TabItems(title = "Tab 3")
                ),
                onClick = {},
            )
        }
    }
}