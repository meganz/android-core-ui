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
    val duration: SnackbarDuration,
    val withDismissAction: Boolean = false,
    val action: String? = null,
    val actionClick: (() -> Unit)? = null,
) {
    /**
     * Secondary constructor for [SnackbarAttributes] that automatically determines the duration.
     *
     * The duration is set to [SnackbarDuration.Long] if the message is longer than 50 characters
     * or if an action is present, otherwise it's set to [SnackbarDuration.Short].
     *
     * @param message The message to be displayed in the Snackbar.
     * @param withDismissAction Whether the Snackbar should have a dismiss action.
     * @param action The text for the action button in the Snackbar, if any.
     * @param actionClick The callback to be invoked when the action button is clicked.
     */
    constructor(
        message: String? = null,
        withDismissAction: Boolean = false,
        action: String? = null,
        actionClick: (() -> Unit)? = null,
    ) : this(
        message = message,
        duration = determineDuration(message, action),
        withDismissAction = withDismissAction,
        action = action,
        actionClick = actionClick,
    )

    companion object {
        fun determineDuration(message: String?, action: String?): SnackbarDuration =
            if ((message?.length ?: 0) > 50 || action != null) {
                SnackbarDuration.Long
            } else {
                SnackbarDuration.Short
            }
    }
}

enum class SnackbarDuration {
    Short,
    Long,
    Indefinite
}
