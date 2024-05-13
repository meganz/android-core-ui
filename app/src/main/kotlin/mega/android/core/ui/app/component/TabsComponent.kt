package mega.android.core.ui.app.component

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import mega.android.core.ui.app.util.Section
import mega.android.core.ui.components.tabs.MegaFixedTabRow
import mega.android.core.ui.components.tabs.MegaScrollableTabRow
import mega.android.core.ui.model.TabItems
import mega.android.core.ui.theme.spacing.LocalSpacing

@Composable
fun TabsComponentCatalog() {
    Spacer(modifier = Modifier.height(LocalSpacing.current.x16))

    Section(header = "Fixed Tabs") {
        var currentTabIndex by remember { mutableIntStateOf(0) }
        val tabItems = listOf(
            TabItems("Tab 1", false),
            TabItems("Tab 2", true),
            TabItems("Tab 3", false),
        )

        MegaFixedTabRow(
            tabIndex = currentTabIndex,
            items = tabItems,
            onClick = {
                currentTabIndex = it
            }
        )
    }

    Section(header = "Scrollable Tabs") {
        var currentTabIndex by remember { mutableIntStateOf(0) }
        var tabItems by remember {
            mutableStateOf(
                listOf(
                    TabItems("Tab 1", false),
                    TabItems("Tab 2", true),
                    TabItems("Tab 3", false),
                    TabItems("Tab 4", false),
                    TabItems("Tab 5", false),
                    TabItems("Tab 6", false),
                )
            )
        }

        MegaScrollableTabRow(
            tabIndex = currentTabIndex,
            items = tabItems,
            onClick = {
                //Example of dismissing badge once tab is visited
                if (tabItems[it].showBadge) {
                    tabItems = tabItems.mapIndexed { index, item ->
                        if (index == it) {
                            item.copy(showBadge = false)
                        } else {
                            item
                        }
                    }
                }
                currentTabIndex = it
            }
        )
    }
}