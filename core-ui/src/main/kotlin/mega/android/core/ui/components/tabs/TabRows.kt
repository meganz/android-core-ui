package mega.android.core.ui.components.tabs

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.launch
import mega.android.core.ui.components.MegaText
import mega.android.core.ui.components.button.MegaOutlinedButton
import mega.android.core.ui.components.divider.StrongDivider
import mega.android.core.ui.model.TabItems
import mega.android.core.ui.preview.CombinedThemePreviews
import mega.android.core.ui.theme.AndroidThemeForPreviews
import mega.android.core.ui.theme.spacing.LocalSpacing
import mega.android.core.ui.theme.values.TextColor
import mega.android.core.ui.tokens.theme.DSTokens

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
        divider = {
            if (withDivider) {
                StrongDivider()
            }
        },
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

/**
 * Scrollable tab row with a linked horizontal pager to show the content.
 * @param modifier to be applied to the whole component
 * @param pagerModifier to be applied to pager showing the content
 * @param initialSelectedIndex Please notice that as content is shown in a pager selected index is stored in an internal pager state
 * @param beyondViewportPageCount Pages to compose and layout before and after the list of visible pages.
 * @param onTabSelected
 * @param pagerScrollEnabled Whether scrolls in the pager change the tab or not
 * * @param hideTabs Tabs will be hidden if true. In some situation we need to temporarily hide the tabs to avoid changing current tab. Considering set [pagerScrollEnabled] to false in this case
 *
 */
@Composable
fun MegaFixedTabRow(
    modifier: Modifier = Modifier,
    pagerModifier: Modifier = Modifier,
    initialSelectedIndex: Int = 0,
    beyondViewportPageCount: Int = 0,
    onTabSelected: (Int) -> Boolean = { true },
    pagerScrollEnabled: Boolean = true,
    hideTabs: Boolean = false,
    cells: @Composable TabsScope.() -> Unit,
) = MegaTabRowWithContent(
    modifier = modifier,
    pagerModifier = pagerModifier,
    initialSelectedIndex = initialSelectedIndex,
    beyondViewportPageCount = beyondViewportPageCount,
    onTabSelected = onTabSelected,
    pagerScrollEnabled = pagerScrollEnabled,
    cells = cells,
    tabRow = { pagerState, tabs, onClick ->
        AnimatedVisibility(
            enter = enterTabsAnimation(),
            exit = exitTabsAnimation(),
            visible = !hideTabs
        ) {
            MegaFixedTabRow(
                tabIndex = pagerState.currentPage,
                items = tabs,
                onClick = onClick
            )
        }
    },
)

/**
 * Scrollable tab row with a linked horizontal pager to show the content.
 * @param modifier to be applied to the whole component
 * @param pagerModifier to be applied to pager showing the content
 * @param initialSelectedIndex Please notice that as content is shown in a pager selected index is stored in an internal pager state
 * @param beyondViewportPageCount Pages to compose and layout before and after the list of visible pages.
 * @param onTabSelected
 * @param pagerScrollEnabled Whether scrolls in the pager change the tab or not
 * @param hideTabs Tabs will be hidden if true. In some situation we need to temporarily hide the tabs to avoid changing current tab. Considering set [pagerScrollEnabled] to false in this case
 *
 */
@Composable
fun MegaScrollableTabRow(
    modifier: Modifier = Modifier,
    pagerModifier: Modifier = Modifier,
    initialSelectedIndex: Int = 0,
    beyondViewportPageCount: Int = 0,
    onTabSelected: (Int) -> Boolean = { true },
    pagerScrollEnabled: Boolean = true,
    hideTabs: Boolean = false,
    cells: @Composable TabsScope.() -> Unit,
) = MegaTabRowWithContent(
    modifier = modifier,
    pagerModifier = pagerModifier,
    initialSelectedIndex = initialSelectedIndex,
    beyondViewportPageCount = beyondViewportPageCount,
    onTabSelected = onTabSelected,
    pagerScrollEnabled = pagerScrollEnabled,
    cells = cells,
    tabRow = { pagerState, tabs, onClick ->
        AnimatedVisibility(
            enter = enterTabsAnimation(),
            exit = exitTabsAnimation(),
            visible = !hideTabs
        ) {
            Column {
                MegaScrollableTabRow(
                    tabIndex = pagerState.currentPage,
                    items = tabs,
                    withDivider = false, //we add it manually to fill width
                    onClick = onClick
                )
                StrongDivider()
            }
        }
    },
)

private fun enterTabsAnimation() = fadeIn() + expandVertically()
private fun exitTabsAnimation() = fadeOut() + shrinkVertically()

@Composable
private fun MegaTabRowWithContent(
    modifier: Modifier = Modifier,
    pagerModifier: Modifier = Modifier,
    initialSelectedIndex: Int = 0,
    beyondViewportPageCount: Int = 0,
    onTabSelected: (Int) -> Boolean = { true },
    pagerScrollEnabled: Boolean = true,
    tabRow: @Composable (pagerState: PagerState, items: List<TabItems>, onClick: (Int) -> Unit) -> Unit,
    cells: @Composable TabsScope.() -> Unit,
) {
    val tabsScope = TabsScope()
    val coroutineScope = rememberCoroutineScope()
    val tabs = with(tabsScope) {
        cells()
        build()
    }
    val pagerState = rememberPagerState(
        initialPage = initialSelectedIndex,
        initialPageOffsetFraction = 0f
    ) {
        tabs.size
    }
    Column(modifier.fillMaxWidth()) {
        tabRow(pagerState, tabs.map { it.tabItem }) { index ->
            coroutineScope.launch {
                if (onTabSelected(index)) {
                    pagerState.animateScrollToPage(index)
                }
            }
        }
        HorizontalPager(
            state = pagerState,
            modifier = pagerModifier,
            userScrollEnabled = pagerScrollEnabled,
            beyondViewportPageCount = beyondViewportPageCount,
        ) { page ->
            Box(modifier = Modifier.fillMaxSize()) {
                tabs[page].content?.let {
                    this.it(page == pagerState.currentPage)
                }
            }
        }

        LaunchedEffect(pagerState.currentPage) {
            onTabSelected(pagerState.currentPage)
        }
    }
}

internal data class TabContent(
    val tabItem: TabItems,
    val content: @Composable (BoxScope.(isActive: Boolean) -> Unit)? = null,
)

/**
 * Scope for building tabs.
 */
class TabsScope {
    private val cells: MutableList<TabContent> = mutableListOf()
    fun addTextTab(
        tabItem: TabItems,
        content: @Composable (BoxScope.(isActive: Boolean) -> Unit)? = null,
    ) {
        cells.add(
            TabContent(
                tabItem = tabItem,
                content = content,
            )
        )
    }

    internal fun build(): ImmutableList<TabContent> = persistentListOf(*cells.toTypedArray())
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
        Column {
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

@CombinedThemePreviews
@Composable
private fun MegaFixedTabRowWithContentPreview() {
    AndroidThemeForPreviews {
        var hideTabs by remember { mutableStateOf(false) }
        MegaFixedTabRow(
            pagerModifier = Modifier.height(200.dp),
            hideTabs = hideTabs,
            pagerScrollEnabled = !hideTabs
        ) {
            addTextTab(TabItems("Tab A", false)) {
                MegaOutlinedButton(
                    text = if (hideTabs) "Show tabs" else "Hide tabs",
                    modifier = Modifier.align(Alignment.Center),
                    onClick = {
                        hideTabs = !hideTabs
                    },
                )
            }
            addTextTab(TabItems("Tab B", false)) {
                MegaText(
                    "Tab 2 content",
                    textColor = TextColor.Primary,
                    modifier = Modifier.align(Alignment.Center),
                )
            }
        }
    }
}

@CombinedThemePreviews
@Composable
private fun MegaScrollableTabRowWithContentPreview() {
    AndroidThemeForPreviews {
        var hideTabs by remember { mutableStateOf(false) }
        MegaScrollableTabRow(
            pagerModifier = Modifier.height(200.dp),
            hideTabs = hideTabs,
            pagerScrollEnabled = !hideTabs,
        ) {
            addTextTab(TabItems("Tab A", false)) {
                MegaOutlinedButton(
                    text = if (hideTabs) "Show tabs" else "Hide tabs",
                    modifier = Modifier.align(Alignment.Center),
                    onClick = {
                        hideTabs = !hideTabs
                    },
                )
            }
            addTextTab(TabItems("Tab B", false)) {
                MegaText(
                    "Tab 2 content",
                    textColor = TextColor.Primary,
                    modifier = Modifier.align(Alignment.Center),
                )
            }
        }
    }
}