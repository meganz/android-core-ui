package mega.android.core.ui.model

import androidx.compose.runtime.Stable
import androidx.compose.ui.graphics.painter.Painter

/**
 * Data class to represent the content of an accessory bar button.
 *
 * @param icon The icon to be displayed in the button.
 * @param text The text to be displayed in the button.
 * @param onClick The action to be executed when the button is clicked.
 */
@Stable
data class AccessoryBarButtonContent(
    val icon: Painter? = null,
    val text: String,
    val onClick: () -> Unit
)