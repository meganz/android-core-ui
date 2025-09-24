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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocal
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.launch
import mega.android.core.ui.components.LocalTopAppBarScrollBehavior
import mega.android.core.ui.components.MegaText
import mega.android.core.ui.components.button.MegaOutlinedButton
import mega.android.core.ui.components.divider.StrongDivider
import mega.android.core.ui.model.TabItems
import mega.android.core.ui.modifiers.scrolledTopAppBarBackgroundColor
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
 * @param applyScrolledBackgroundColor Whether to apply scrolled background color to tabs when content is scrolled or not.
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
    applyScrolledBackgroundColor: Boolean = true,
    initialSelectedIndex: Int = 0,
    beyondViewportPageCount: Int = 0,
    onTabSelected: (Int) -> Boolean = { true },
    pagerScrollEnabled: Boolean = true,
    hideTabs: Boolean = false,
    cells: @Composable TabsScope.() -> Unit,
) = MegaTabRowWithContent(
    modifier = modifier,
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
                onClick = onClick,
                modifier = if (applyScrolledBackgroundColor) Modifier.scrolledTopAppBarBackgroundColor() else Modifier,
            )
        }
    },
)

/**
 * Scrollable tab row with a linked horizontal pager to show the content.
 * @param modifier to be applied to the whole component
 * @param applyScrolledBackgroundColor Whether to apply scrolled background color to tabs when content is scrolled or not.
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
    applyScrolledBackgroundColor: Boolean = true,
    initialSelectedIndex: Int = 0,
    beyondViewportPageCount: Int = 0,
    onTabSelected: (Int) -> Boolean = { true },
    pagerScrollEnabled: Boolean = true,
    hideTabs: Boolean = false,
    cells: @Composable TabsScope.() -> Unit,
) = MegaTabRowWithContent(
    modifier = modifier,
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
                    onClick = onClick,
                    modifier = if (applyScrolledBackgroundColor) Modifier.scrolledTopAppBarBackgroundColor() else Modifier,
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
    var scrollOffsets by rememberSaveable {
        mutableStateOf(mapOf<Int, Float>())
    }
    @OptIn(ExperimentalMaterial3Api::class)
    Column(modifier.fillMaxWidth()) {
        var selectedTabIndex by remember { mutableIntStateOf(0) }
        val currentScrollBehaviorState = LocalTopAppBarScrollBehavior.current
        LaunchedEffect(currentScrollBehaviorState?.state?.contentOffset) {
            //save content offset for current tab
            scrollOffsets = scrollOffsets.toMutableMap().also {
                it[selectedTabIndex] = currentScrollBehaviorState?.state?.contentOffset ?: 0f
            }
        }
        LaunchedEffect(selectedTabIndex) {
            //set content offset to the selected tab when it changes
            currentScrollBehaviorState?.state?.contentOffset = scrollOffsets[selectedTabIndex] ?: 0f
        }
        tabRow(pagerState, tabs.map { it.tabItem }) { index ->
            coroutineScope.launch {
                if (onTabSelected(index)) {
                    selectedTabIndex = index
                    pagerState.animateScrollToPage(index)
                }
            }
        }
        HorizontalPager(
            state = pagerState,
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
            selectedTabIndex = pagerState.currentPage
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

    /**
     * Adds a new text tab with its content in a Box
     * @param tabItem Tab definition
     * @param content the BoxScope content to show when this tab is selected
     */
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

    /**
     * Adds a new text tab with its content in a [LazyColumn]
     * @param tabItem Tab definition
     * @param listState This will be used to update LocalTopAppBarScrollBehavior, so MegaTopAppBar will synchronize with the current selected tab when changed
     * @param content the LazyListScope content to show when this tab is selected
     */
    @Composable
    fun addLazyListTextTab(
        tabItem: TabItems,
        listState: LazyListState = rememberLazyListState(),
        content: (LazyListScope.(isActive: Boolean) -> Unit),
    ) = addTextTabWithScrollableContent(
        tabItem = tabItem,
    ) { isActive, modifier ->
        LazyColumn(
            state = listState,
            modifier = modifier,
            content = {
                content(isActive)
            }
        )
    }

    /**
     * Adds a new text tab with a modifier that allows to synchronize the scrollable content with the top app bar scroll behaviour
     * this is a general solution, if a simple LazyColumn is used to show the content, consider using [addLazyListTextTab] instead
     * @param tabItem Tab definition
     * @param content the scrollable content to show when this tab is selected. IMPORTANT: `modifer` should be used in the scrollable component to ensure correct synchronization between the content and the top app bar scroll behaviour
     */
    @Composable
    fun addTextTabWithScrollableContent(
        tabItem: TabItems,
        content: @Composable (BoxScope.(isActive: Boolean, modifier: Modifier) -> Unit),
    ) = cells.add(
        TabContent(
            tabItem = tabItem,
            content = { isActive ->
                @OptIn(ExperimentalMaterial3Api::class)
                content(
                    isActive,
                    Modifier.then(
                        if (LocalTopAppBarScrollBehavior.current == null) Modifier else Modifier.nestedScroll(
                            LocalTopAppBarScrollBehavior.current!!.nestedScrollConnection
                        )
                    )
                )
            }
        )
    )

    /**
     * Adds a new text tab with and provides a modifier trough [LocalTabContentModifier] that allows to synchronize the scrollable content with the top app bar scroll behaviour
     * It's your responsibility to use the modifier in the scrollable content to ensure correct synchronization between the content and the top app bar scroll behaviour
     * this is a general solution, if a simple LazyColumn is used to show the content, consider using [addLazyListTextTab] instead
     * @param tabItem Tab definition
     * @param content the scrollable content to show when this tab is selected. IMPORTANT: the Modifer provided by [LocalTabContentModifier] should be used in the scrollable component to ensure correct synchronization between the content and the top app bar scroll behaviour
     */
    @Composable
    fun addTextTabWithProvidedScrollableModifier(
        tabItem: TabItems,
        content: @Composable (BoxScope.(isActive: Boolean) -> Unit),
    ): Boolean {
        return cells.add(
            TabContent(
                tabItem = tabItem,
                content = { isActive ->
                    @OptIn(ExperimentalMaterial3Api::class)
                    CompositionLocalProvider(
                        LocalTabContentModifierInternal.provides(
                            Modifier.then(
                                if (LocalTopAppBarScrollBehavior.current == null) Modifier else Modifier.nestedScroll(
                                    LocalTopAppBarScrollBehavior.current!!.nestedScrollConnection
                                )
                            )
                        )
                    ) {
                        content(isActive)
                    }
                }
            )
        )
    }

    internal fun build(): ImmutableList<TabContent> = persistentListOf(*cells.toTypedArray())
}

private val LocalTabContentModifierInternal =
    compositionLocalOf<Modifier?> { null }

val LocalTabContentModifier: CompositionLocal<Modifier?> =
    LocalTabContentModifierInternal


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
            modifier = Modifier.height(260.dp),
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
            modifier = Modifier.height(260.dp),
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