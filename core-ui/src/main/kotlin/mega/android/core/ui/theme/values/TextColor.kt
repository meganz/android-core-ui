//
// Generated automatically by KotlinTokensGenerator.
// Do not modify this file manually.
//
package mega.android.core.ui.theme.values

import androidx.compose.ui.graphics.Color
import mega.android.core.ui.tokens.theme.tokens.Text

public enum class TextColor {
    Primary,
    Secondary,
    Accent,
    Brand,
    Placeholder,
    Inverse,
    InverseAccent,
    InverseSecondary,
    OnColor,
    OnColorDisabled,
    Error,
    Success,
    Info,
    Warning,
    Disabled,
    ;

    public fun getTextColor(text: Text): Color = when (this) {
        Primary -> text.primary
        Secondary -> text.secondary
        Accent -> text.accent
        Brand -> text.brand
        Placeholder -> text.placeholder
        Inverse -> text.inverse
        InverseAccent -> text.inverseAccent
        InverseSecondary -> text.inverseSecondary
        OnColor -> text.onColor
        OnColorDisabled -> text.onColorDisabled
        Error -> text.error
        Success -> text.success
        Info -> text.info
        Warning -> text.warning
        Disabled -> text.disabled
    }
}
