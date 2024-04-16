package mega.android.core.ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarData
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import mega.android.core.ui.theme.AppTheme
import mega.android.core.ui.theme.spacing.LocalSpacing

private val snackBarWidthTablet = 344.dp

@Composable
fun MegaSnackbar(
    snackbarData: SnackbarData,
    isTablet: Boolean = false
) {
    val spacing = LocalSpacing.current
    val modifier = if (isTablet) Modifier
        .width(snackBarWidthTablet)
        .padding(bottom = spacing.x12) else Modifier.padding(
        bottom = spacing.x12,
        start = spacing.x8,
        end = spacing.x8
    )
    Snackbar(
        modifier = modifier,
        snackbarData = snackbarData,
        containerColor = AppTheme.colors.components.toastBackground,
        contentColor = AppTheme.colors.background.pageBackground
    )
}