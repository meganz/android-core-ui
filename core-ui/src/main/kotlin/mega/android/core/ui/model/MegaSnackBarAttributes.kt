package mega.android.core.ui.model

import androidx.compose.runtime.Immutable

@Immutable
data class SnackBarAttributes(
    val message: String? = null,
    val duration: SnackBarDuration = SnackBarDuration.Short,
    val withDismissAction: Boolean = false,
    val action: String? = null,
    val actionClick: (() -> Unit)? = null,
)

sealed interface SnackBarDuration {
    data object Short : SnackBarDuration

    data object Long : SnackBarDuration

    data object Indefinite : SnackBarDuration
}