package mega.android.core.ui.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import mega.android.core.ui.theme.AppTheme

@Composable
fun MegaScaffold(
    modifier: Modifier,
    snackbarHost: @Composable () -> Unit,
    content: @Composable (PaddingValues) -> Unit,
) {
    Scaffold(
        modifier = modifier,
        snackbarHost = snackbarHost,
        content = content,
        containerColor = AppTheme.colors.background.pageBackground
    )
}
