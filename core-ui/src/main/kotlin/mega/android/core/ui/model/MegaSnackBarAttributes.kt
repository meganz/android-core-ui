package mega.android.core.ui.model

import androidx.compose.runtime.Immutable

/**
 * Represents the attributes for a Snackbar.
 *
 * This SnackbarAttributes only works with M3 (Material 3) and is used to define the properties
 *
 * @property message The message to be displayed in the Snackbar.
 * @property duration The duration for which the Snackbar should be shown.
 * @property withDismissAction Whether the Snackbar should have a dismiss action.
 * @property action The text for the action button in the Snackbar, if any.
 * @property actionClick The callback to be invoked when the action button is clicked.
 */
@Immutable
data class SnackbarAttributes(
    val message: String? = null,
    val duration: SnackbarDuration = SnackbarDuration.Short,
    val withDismissAction: Boolean = false,
    val action: String? = null,
    val actionClick: (() -> Unit)? = null,
)

enum class SnackbarDuration {
    Short,
    Long,
    Indefinite
}