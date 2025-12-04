package mega.android.core.ui.model

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import mega.android.core.ui.tokens.theme.DSTokens

/**
 * Represents text with optional highlighting for a specific portion.
 *
 * Used to display text where a matching substring (e.g., search query) is visually
 * distinguished with custom styling such as font weight and background color.
 *
 * @property full The full text to display.
 * @property highlighted The substring within [full] to highlight. If empty, no highlighting
 *                     is applied. The match is case-insensitive.
 * @property highlightFontWeight The font weight applied to the highlighted portion.
 *                           Defaults to [FontWeight.Normal].
 * @property highlightColor The background color applied to the highlighted portion.
 *                               Defaults to [BackgroundColor.Surface2].
 */
data class HighlightedText(
    val full: String,
    val highlighted: String = "",
    val highlightFontWeight: FontWeight = FontWeight.Normal,
    val highlightColor: TextHighlightColor = TextHighlightColor.NotificationSuccess,
    val ignoreCase: Boolean = true
)

/**
 * List of available colors available for text highlighting.
 */
enum class TextHighlightColor {
    // Add more colors here
    NotificationSuccess;

    @Composable
    fun parseColor(): Color {
        return when (this) {
            NotificationSuccess -> DSTokens.colors.notifications.notificationSuccess
        }
    }
}