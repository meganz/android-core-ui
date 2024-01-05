package mega.android.core.ui.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import mega.android.core.ui.theme.AppTheme

@Composable
fun MegaScaffold(
    modifier: Modifier,
    snackbarHost: @Composable () -> Unit,
    bottomBar: @Composable () -> Unit,
    content: @Composable (PaddingValues) -> Unit,
    topBar: @Composable () -> Unit = {},
    floatingActionButton: @Composable () -> Unit = {},
    floatingActionButtonPosition: FabPosition = FabPosition.End,
) {
    Scaffold(
        modifier = modifier,
        snackbarHost = snackbarHost,
        topBar = topBar,
        bottomBar = bottomBar,
        floatingActionButton = floatingActionButton,
        floatingActionButtonPosition = floatingActionButtonPosition,
        content = content,
        containerColor = AppTheme.colors.background.pageBackground
    )
}
