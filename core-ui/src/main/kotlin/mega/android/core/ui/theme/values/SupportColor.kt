//
// Generated automatically by KotlinTokensGenerator.
// Do not modify this file manually.
//
package mega.android.core.ui.theme.values

import androidx.compose.ui.graphics.Color
import mega.android.core.ui.tokens.theme.tokens.Support

public enum class SupportColor {
    Success,
    Warning,
    Error,
    Info,
    ;

    internal fun getSupportColor(support: Support): Color = when (this) {
        Success -> support.success
        Warning -> support.warning
        Error -> support.error
        Info -> support.info
    }
}
