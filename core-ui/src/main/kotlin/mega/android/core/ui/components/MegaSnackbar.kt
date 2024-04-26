package mega.android.core.ui.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import mega.android.core.ui.theme.AppTheme
import mega.android.core.ui.theme.spacing.LocalSpacing
import mega.android.core.ui.model.MegaSnackBarVisuals
import mega.android.core.ui.model.calculatePadding

private val snackBarWidthTablet = 344.dp

@Composable
fun MegaSnackbar(
    snackBarHostState: SnackbarHostState,
    isTablet: Boolean = false
) {
    SnackbarHost(snackBarHostState) { data ->
        val customVisuals = data.visuals as? MegaSnackBarVisuals
        val snackBarAttributes = customVisuals?.attributes
        val horizontalSpace = if (isTablet) 0.dp else LocalSpacing.current.x8
        val snackBarPadding = snackBarAttributes?.calculatePadding(isTablet)
            ?: PaddingValues(
                bottom = LocalSpacing.current.x12,
                start = horizontalSpace,
                end = horizontalSpace
            )

        Snackbar(
            modifier = if (isTablet) {
                Modifier
                    .width(snackBarWidthTablet)
                    .padding(snackBarPadding)
            } else {
                Modifier.padding(snackBarPadding)
            },
            snackbarData = data,
            containerColor = AppTheme.colors.components.toastBackground,
            contentColor = AppTheme.colors.background.pageBackground,
            actionColor = AppTheme.colors.link.primary
        )
    }
}
