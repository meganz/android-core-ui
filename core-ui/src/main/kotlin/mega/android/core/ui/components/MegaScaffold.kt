package mega.android.core.ui.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import mega.android.core.ui.tokens.theme.DSTokens

/**
 * Scaffold following Mega design system.
 * It also injects LocalSnackBarHostState with CompositionLocalProvider to show snackbar messages without the need to pass the state along views hierarchy
 * @param snackbarHost default to [MegaSnackbar]
 */
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
) {
    val snackbarHostState = remember { SnackbarHostState() }
    CompositionLocalProvider(
        LocalSnackBarHostState provides snackbarHostState,
    ) {
        Scaffold(
            modifier = modifier,
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
