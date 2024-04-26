package mega.android.core.ui.model

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.dp
import mega.android.core.ui.theme.spacing.LocalSpacing

@Immutable
data class SnackBarAttributes(
    val message: String? = null,
    val safeAreaPadding: PaddingValues = PaddingValues(0.dp),
    val duration: SnackBarDuration = SnackBarDuration.Short,
    val withDismissAction: Boolean = false,
    val action: String? = null,
    val actionClick: (() -> Unit)? = null,
)

/**
 * Calculate the padding for the snackbar based on the position and safe area padding
 * if developer hasn't setting safe area padding, we will use the default padding (12dp) based on the position
 * if developer has setting safe area padding, we will use the safe area padding
 */
@Composable
fun SnackBarAttributes?.calculatePadding(isTablet: Boolean): PaddingValues {
    val verticalSpacing = LocalSpacing.current.x12
    val horizontalSpace = if (isTablet) 0.dp else LocalSpacing.current.x8
    val layoutDirection = LocalLayoutDirection.current

    return PaddingValues(
        start = this?.safeAreaPadding?.calculateStartPadding(layoutDirection) ?: horizontalSpace,
        end = this?.safeAreaPadding?.calculateEndPadding(layoutDirection) ?: horizontalSpace,
        bottom = this?.safeAreaPadding?.calculateBottomPadding() ?: verticalSpacing,
    )
}

sealed interface SnackBarDuration {
    data object Short : SnackBarDuration

    data object Long : SnackBarDuration

    data object Indefinite : SnackBarDuration
}