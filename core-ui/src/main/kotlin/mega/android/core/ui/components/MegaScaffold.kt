package mega.android.core.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScaffoldDefaults
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.MutableIntState
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.SubcomposeLayout
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import mega.android.core.ui.R
import mega.android.core.ui.components.button.MegaOutlinedButton
import mega.android.core.ui.components.fab.MegaFab
import mega.android.core.ui.components.list.OneLineListItem
import mega.android.core.ui.components.prompt.ErrorPrompt
import mega.android.core.ui.components.scrollbar.fastscroll.FastScrollLazyColumn
import mega.android.core.ui.components.snackbar.MegaSnackbar
import mega.android.core.ui.components.tabs.LocalTabContentModifier
import mega.android.core.ui.components.tabs.MegaCollapsibleTabRow
import mega.android.core.ui.components.tabs.MegaFixedTabRow
import mega.android.core.ui.components.toolbar.AppBarNavigationType
import mega.android.core.ui.components.toolbar.MegaTopAppBar
import mega.android.core.ui.model.TabItems
import mega.android.core.ui.modifiers.ScrollToHideBehavior
import mega.android.core.ui.modifiers.applyScrollToHideBehavior
import mega.android.core.ui.modifiers.applyScrollToHideFabBehavior
import mega.android.core.ui.modifiers.rememberScrollToHideBehavior
import mega.android.core.ui.preview.CombinedThemePreviews
import mega.android.core.ui.theme.AndroidThemeForPreviews
import mega.android.core.ui.theme.values.TextColor
import mega.android.core.ui.tokens.theme.DSTokens


@Composable
fun MegaScaffold(
    modifier: Modifier = Modifier,
    snackbarHost: @Composable (snackbarHostState: SnackbarHostState) -> Unit = { MegaSnackbar(it) },
    bottomBar: @Composable () -> Unit = {},
    topBar: @Composable () -> Unit = {},
    floatingActionButton: @Composable () -> Unit = {},
    floatingActionButtonPosition: FabPosition = FabPosition.End,
    fromAutofill: Boolean = false,
    contentWindowInsets: WindowInsets = ScaffoldDefaults.contentWindowInsets,
    showTopBanner: Boolean = true,
    content: @Composable (PaddingValues) -> Unit,
) {
    @OptIn(ExperimentalMaterial3Api::class)
    MegaScaffoldWithTopAppBarScrollBehavior(
        modifier = modifier,
        snackbarHost = snackbarHost,
        bottomBar = bottomBar,
        topBar = topBar,
        floatingActionButton = floatingActionButton,
        floatingActionButtonPosition = floatingActionButtonPosition,
        fromAutofill = fromAutofill,
        scrollBehavior = null,
        scrollToHideBehavior = null,
        contentWindowInsets = contentWindowInsets,
        showTopBanner = showTopBanner,
        content = content,
    )
}

/**
 * Scaffold following Mega design system.
 * It also injects LocalSnackBarHostState with CompositionLocalProvider to show snackbar messages without the need to pass the state along views hierarchy
 * @param snackbarHost default to [MegaSnackbar]
 * @param showTopBanner whether the banner provided through [LocalMegaScaffoldTopBanner] is shown
 * beneath [topBar]. Pass false to hide it on this screen and every scaffold nested inside it.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MegaScaffoldWithTopAppBarScrollBehavior(
    modifier: Modifier = Modifier,
    snackbarHost: @Composable (snackbarHostState: SnackbarHostState) -> Unit = { MegaSnackbar(it) },
    bottomBar: @Composable () -> Unit = {},
    topBar: @Composable () -> Unit = {},
    floatingActionButton: @Composable () -> Unit = {},
    floatingActionButtonPosition: FabPosition = FabPosition.End,
    fromAutofill: Boolean = false,
    scrollBehavior: TopAppBarScrollBehavior? = TopAppBarDefaults.pinnedScrollBehavior(),
    scrollToHideBehavior: ScrollToHideBehavior? = rememberScrollToHideBehavior(),
    contentWindowInsets: WindowInsets = ScaffoldDefaults.contentWindowInsets,
    showTopBanner: Boolean = true,
    content: @Composable (PaddingValues) -> Unit,
) {
    val topBanner = LocalMegaScaffoldTopBanner.current?.takeIf { showTopBanner }
    // check if the root composable has a LocalSnackBarHostState, if not create a new one
    val snackbarHostState = LocalSnackBarHostState.current ?: remember { SnackbarHostState() }
    val forceRaisedTopAppBar = remember { mutableIntStateOf(0) }
    CompositionLocalProvider(
        LocalSnackBarHostState provides snackbarHostState,
        LocalTopAppBarScrollBehavior provides scrollBehavior,
        LocalForceRaiseTopAppBar provides forceRaisedTopAppBar,
        LocalScrollToHideBehavior provides scrollToHideBehavior,
        LocalMegaScaffoldTopBanner provides null,
    ) {
        Scaffold(
            modifier = modifier
                .run {
                    if (scrollToHideBehavior != null) {
                        nestedScroll(scrollToHideBehavior.nestedScrollConnection)
                    } else this
                }
                .run {
                    if (scrollBehavior != null) {
                        nestedScroll(scrollBehavior.nestedScrollConnection)
                    } else this
                },
            snackbarHost = { snackbarHost(snackbarHostState) },
            topBar = if (topBanner == null) topBar else {
                { TopBarWithBanner(topBar = topBar, banner = topBanner) }
            },
            bottomBar = bottomBar,
            floatingActionButton = floatingActionButton,
            floatingActionButtonPosition = floatingActionButtonPosition,
            contentWindowInsets = contentWindowInsets,
            content = content,
            containerColor = if (fromAutofill) Color.Transparent else DSTokens.colors.background.pageBackground
        )
    }
}

/**
 * Provides SnackbarHostState to be used to show a snackBar from any view inside this scaffold.
 * This is a convenient accessor to [SnackbarHostState] without the need to send it to all the view hierarchy
 */
val LocalSnackBarHostState = compositionLocalOf<SnackbarHostState?> { null }

/**
 * Banner shown beneath the top bar of every [MegaScaffoldWithTopAppBarScrollBehavior] (and
 * [MegaScaffold]) inside this provider, e.g. an app-wide status banner provided by the app shell.
 * The scaffold does not know what the banner is; it only reserves space for it under the top bar.
 *
 * The banner is not shown when the scaffold has no top bar, or when the scaffold opts out with
 * `showTopBanner = false`. A scaffold consumes the banner, so nested scaffolds never show it twice.
 */
val LocalMegaScaffoldTopBanner = compositionLocalOf<(@Composable () -> Unit)?> { null }

/**
 * Places [banner] directly beneath [topBar]. The banner is only composed when [topBar] has a height,
 * so a scaffold without a top bar shows no banner and [Scaffold] keeps falling back to its content
 * window insets.
 */
@Composable
private fun TopBarWithBanner(
    topBar: @Composable () -> Unit,
    banner: @Composable () -> Unit,
) {
    SubcomposeLayout { constraints ->
        val looseConstraints = constraints.copy(minWidth = 0, minHeight = 0)
        val topBarPlaceables = subcompose(TopBarSlot.TopBar, topBar).map {
            it.measure(looseConstraints)
        }
        val topBarHeight = topBarPlaceables.maxOfOrNull { it.height } ?: 0
        val bannerPlaceables = if (topBarHeight == 0) {
            emptyList()
        } else {
            val bannerConstraints = if (looseConstraints.hasBoundedHeight) {
                looseConstraints.copy(
                    maxHeight = (looseConstraints.maxHeight - topBarHeight).coerceAtLeast(0)
                )
            } else {
                looseConstraints
            }
            subcompose(TopBarSlot.Banner, banner).map { it.measure(bannerConstraints) }
        }
        val bannerHeight = bannerPlaceables.maxOfOrNull { it.height } ?: 0
        val width = (topBarPlaceables + bannerPlaceables).maxOfOrNull { it.width } ?: 0
        layout(width, topBarHeight + bannerHeight) {
            topBarPlaceables.forEach { it.placeRelative(0, 0) }
            bannerPlaceables.forEach { it.placeRelative(0, topBarHeight) }
        }
    }
}

private enum class TopBarSlot { TopBar, Banner }


internal val LocalTopAppBarScrollBehavior =
    @OptIn(ExperimentalMaterial3Api::class)
    compositionLocalOf<TopAppBarScrollBehavior?> { null }

/**
 * Behavior used to hide UI components like FAB and tabs when scrolling.
 */
internal val LocalScrollToHideBehavior = compositionLocalOf<ScrollToHideBehavior?> { null }

/**
 * Used to force raised top app bar when there's a banner under the top app bar, check ForceRiceTopAppBarEffect
 */
internal val LocalForceRaiseTopAppBar =
    compositionLocalOf<MutableIntState?> { null }

internal const val OVERLAP_FRACTION_THRESHOLD = 0.01f


@CombinedThemePreviews
@Composable
private fun MegaScaffoldPreview() {
    AndroidThemeForPreviews {
        var showBanner by remember { mutableStateOf(false) }
        @OptIn(ExperimentalMaterial3Api::class)
        MegaScaffoldWithTopAppBarScrollBehavior(
            topBar = {
                MegaTopAppBar(
                    title = "Top app bar",
                    navigationType = AppBarNavigationType.Back {},
                    drawBottomLineOnScrolledContent = true,
                    trailingIcons = {
                        MegaOutlinedButton(
                            Modifier,
                            if (showBanner) "Hide Banner" else "Show Banner",
                            onClick = {
                                showBanner = !showBanner
                            })
                    }
                )
            }
        ) {
            Column(modifier = Modifier.padding(it)) {
                AnimatedVisibility(
                    showBanner,
                    enter = enterAnimation(),
                    exit = exitAnimation(),
                ) {
                    ErrorPrompt("Error", forceRiceTopAppBar = true)
                }
                LazyColumn {
                    items(100) {
                        OneLineListItem("List ${it + 1}")
                    }
                }
            }
        }
    }
}


@Preview
@Composable
private fun MegaScaffoldCollapsibleHeaderPreview() {
    AndroidThemeForPreviews {
        @OptIn(ExperimentalMaterial3Api::class)
        MegaScaffoldWithTopAppBarScrollBehavior(
            topBar = {
                MegaTopAppBar(
                    title = "Scaffold with Collapsible Header",
                    navigationType = AppBarNavigationType.Back {},
                )
            },
            floatingActionButton = {
                MegaFab(
                    modifier = Modifier.applyScrollToHideFabBehavior(),
                    onClick = {},
                    painter = painterResource(R.drawable.ic_arrow_left_medium_thin_outline),
                    contentDescription = "Back",
                )
            },
            scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
        ) { paddingValues ->
            Column(modifier = Modifier.padding(paddingValues)) {
                MegaText(
                    "Collapsible component",
                    modifier = Modifier
                        .applyScrollToHideBehavior()
                        .fillMaxWidth()
                        .background(Color.Gray)
                        .height(100.dp),
                    textColor = TextColor.Primary,
                    textAlign = TextAlign.Center
                )

                LazyColumn {
                    items(100) {
                        OneLineListItem("List ${it + 1}")
                    }
                }
            }
        }
    }
}


@CombinedThemePreviews
@Composable
fun MegaScaffoldWithTabRowPreview() {
    AndroidThemeForPreviews {
        @OptIn(ExperimentalMaterial3Api::class)
        MegaScaffoldWithTopAppBarScrollBehavior(
            topBar = {
                MegaTopAppBar(
                    title = "Top app bar",
                    navigationType = AppBarNavigationType.Back {},
                )
            }
        ) {
            MegaCollapsibleTabRow(
                contentPadding = it,
            ) {
                (0..1).forEach { tabIndex ->
                    addLazyListTextTab(TabItems("Tab $tabIndex", false)) {
                        items(100) {
                            OneLineListItem("List$tabIndex - ${it + 1}")
                        }
                    }
                }
            }
        }
    }
}

@CombinedThemePreviews
@Composable
private fun MegaScaffoldWithTabRowProvidedPreview() {
    AndroidThemeForPreviews {
        var showBanner by remember { mutableStateOf(false) }
        @OptIn(ExperimentalMaterial3Api::class)
        MegaScaffoldWithTopAppBarScrollBehavior(
            topBar = {
                MegaTopAppBar(
                    title = "Top app bar provided list states",
                    navigationType = AppBarNavigationType.Back {},
                    trailingIcons = {
                        MegaOutlinedButton(
                            Modifier,
                            if (showBanner) "Hide Banner" else "Show Banner",
                            onClick = {
                                showBanner = !showBanner
                            })
                    }
                )
            }
        ) {
            MegaFixedTabRow(
                contentPadding = it,
                fixedHeader = {
                    AnimatedVisibility(
                        showBanner,
                        enter = enterAnimation(),
                        exit = exitAnimation(),
                    ) {
                        ErrorPrompt("Error", forceRiceTopAppBar = true)
                    }
                }
            ) {
                (0..1).forEach { tabIndex ->
                    addTextTabWithProvidedScrollableModifier(TabItems("Tab $tabIndex", false)) {
                        ExampleInnerLazyList(Modifier.fillMaxSize())
                    }
                }
            }
        }
    }
}

@CombinedThemePreviews
@Composable
private fun MegaScaffoldWithTopBannerPreview() {
    AndroidThemeForPreviews {
        CompositionLocalProvider(
            LocalMegaScaffoldTopBanner provides { ErrorPrompt("Top banner") }
        ) {
            MegaScaffold(
                topBar = {
                    MegaTopAppBar(
                        title = "Scaffold with top banner",
                        navigationType = AppBarNavigationType.Back {},
                    )
                }
            ) { paddingValues ->
                LazyColumn(modifier = Modifier.padding(paddingValues)) {
                    items(100) {
                        OneLineListItem("List ${it + 1}")
                    }
                }
            }
        }
    }
}

private fun enterAnimation() = fadeIn() + expandVertically()
private fun exitAnimation() = fadeOut() + shrinkVertically()

@Composable
private fun ExampleInnerLazyList(
    modifier: Modifier = Modifier,
) {
    val modifierContent = LocalTabContentModifier.current ?: Modifier
    FastScrollLazyColumn(
        100,
        modifier = modifier.then(modifierContent)
    ) {
        items(100) {
            OneLineListItem("List ${it + 1}")
        }
    }
}
