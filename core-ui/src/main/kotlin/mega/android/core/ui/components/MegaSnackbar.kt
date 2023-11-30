package mega.android.core.ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarData
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import mega.android.core.ui.theme.AppTheme
import mega.android.core.ui.theme.spacing.LocalSpacing

@Composable
fun MegaSnackbar(
    snackbarData: SnackbarData,
) {
    val spacing = LocalSpacing.current
    Snackbar(
        modifier = Modifier.padding(
            bottom = spacing.x12,
            start = spacing.x8,
            end = spacing.x8
        ),
        snackbarData = snackbarData,
        containerColor = AppTheme.colors.components.toastBackground,
        contentColor = AppTheme.colors.background.pageBackground
    )
}