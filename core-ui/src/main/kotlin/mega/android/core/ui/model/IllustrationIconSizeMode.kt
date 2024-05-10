package mega.android.core.ui.model

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * Enum class for illustration icon size mode for PromotionalSheet and PromotionalDialog
 * Large is 200dp
 * Small is 120dp
 */
enum class IllustrationIconSizeMode (internal val size: Dp) {
    Large(200.dp),
    Small(120.dp)
}
