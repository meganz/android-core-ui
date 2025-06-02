package mega.android.core.ui.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import mega.android.core.ui.components.list.OneLineListItem
import mega.android.core.ui.components.tabs.MegaFixedTabRow
import mega.android.core.ui.components.toolbar.AppBarNavigationType
import mega.android.core.ui.components.toolbar.MegaTopAppBar
import mega.android.core.ui.model.TabItems
import mega.android.core.ui.preview.CombinedThemePreviews
import mega.android.core.ui.theme.AndroidThemeForPreviews
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
    content: @Composable (PaddingValues) -> Unit,
)  {
    @OptIn(ExperimentalMaterial3Api::class)
    MegaScaffoldWithTopAppBarScrollBehavior(
        modifier,
        snackbarHost,
        bottomBar,
        topBar,
        floatingActionButton,
        floatingActionButtonPosition,
        fromAutofill,
        null,
        content
    )
}

/**
 * Scaffold following Mega design system.
 * It also injects LocalSnackBarHostState with CompositionLocalProvider to show snackbar messages without the need to pass the state along views hierarchy
 * @param snackbarHost default to [MegaSnackbar]
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
    content: @Composable (PaddingValues) -> Unit,
) {
    val snackbarHostState = remember { SnackbarHostState() }
    CompositionLocalProvider(
        LocalSnackBarHostState provides snackbarHostState,
        LocalTopAppBarScrollBehavior provides scrollBehavior,
    ) {
        Scaffold(
            modifier = modifier.then(
                if (scrollBehavior == null) {
                    Modifier
                } else {
                    Modifier.nestedScroll(scrollBehavior.nestedScrollConnection)
                }
            ),
            snackbarHost = { snackbarHost(snackbarHostState) },
            topBar = topBar,
            bottomBar = bottomBar,
            floatingActionButton = floatingActionButton,
            floatingActionButtonPosition = floatingActionButtonPosition,
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


internal val LocalTopAppBarScrollBehavior =
    @OptIn(ExperimentalMaterial3Api::class)
    compositionLocalOf<TopAppBarScrollBehavior?> { null }

internal const val OVERLAP_FRACTION_THRESHOLD = 0.01f


@CombinedThemePreviews
@Composable
private fun MegaScaffoldPreview() {
    AndroidThemeForPreviews {
        @OptIn(ExperimentalMaterial3Api::class)
        MegaScaffoldWithTopAppBarScrollBehavior(
            topBar = {
                MegaTopAppBar(
                    title = "Top app bar",
                    navigationType = AppBarNavigationType.Back {},
                    drawBottomLineOnScrolledContent = true,
                )
            }
        ) {
            LazyColumn(modifier = Modifier.padding(it)) {
                items(100) {
                    OneLineListItem("List ${it + 1}")
                }
            }
        }
    }
}


@CombinedThemePreviews
@Composable
private fun MegaScaffoldWithTabRowPreview() {
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
            MegaFixedTabRow(
                modifier = Modifier.padding(it),
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
