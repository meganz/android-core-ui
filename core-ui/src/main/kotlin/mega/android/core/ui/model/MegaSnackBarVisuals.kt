package mega.android.core.ui.model

import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarVisuals

class MegaSnackBarVisuals(
    override val actionLabel: String?,
    override val duration: SnackbarDuration,
    override val message: String,
    override val withDismissAction: Boolean,
    val attributes: SnackbarAttributes
) : SnackbarVisuals