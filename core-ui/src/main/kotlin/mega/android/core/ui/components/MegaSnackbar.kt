package mega.android.core.ui.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import mega.android.core.ui.theme.AppTheme
import mega.android.core.ui.theme.spacing.LocalSpacing
import mega.android.core.ui.theme.values.DeviceType
import mega.android.core.ui.theme.values.LocalDeviceType

private val snackBarWidthTablet = 344.dp

@Composable
fun MegaSnackbar(
    snackBarHostState: SnackbarHostState,
    safeAreaPadding: PaddingValues? = null,
) {
    val isTablet = LocalDeviceType.current == DeviceType.Tablet
    SnackbarHost(snackBarHostState) { data ->
        val horizontalSpace = if (isTablet) 0.dp else LocalSpacing.current.x8
        val snackbarModifier = Modifier
            .then(if (isTablet) Modifier.widthIn(max = snackBarWidthTablet) else Modifier)
            .padding(
                safeAreaPadding ?: PaddingValues(
                    bottom = LocalSpacing.current.x12,
                    start = horizontalSpace,
                    end = horizontalSpace
                )
            )

        Snackbar(
            modifier = snackbarModifier,
            snackbarData = data,
            containerColor = AppTheme.colors.components.toastBackground,
            contentColor = AppTheme.colors.background.pageBackground,
            actionColor = AppTheme.colors.link.primary
        )
    }
}
