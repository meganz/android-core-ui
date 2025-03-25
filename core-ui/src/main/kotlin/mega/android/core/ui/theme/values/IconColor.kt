//
// Generated automatically by KotlinTokensGenerator.
// Do not modify this file manually.
//
package mega.android.core.ui.theme.values

import androidx.compose.ui.graphics.Color
import mega.android.core.ui.tokens.theme.tokens.Icon

public enum class IconColor {
    Primary,
    Secondary,
    Accent,
    Brand,
    Inverse,
    InverseAccent,
    InverseSecondary,
    OnColor,
    OnColorDisabled,
    Disabled,
    ;

    public fun getIconColor(icon: Icon): Color = when (this) {
        Primary -> icon.primary
        Secondary -> icon.secondary
        Accent -> icon.accent
        Brand -> icon.brand
        Inverse -> icon.inverse
        InverseAccent -> icon.inverseAccent
        InverseSecondary -> icon.inverseSecondary
        OnColor -> icon.onColor
        OnColorDisabled -> icon.onColorDisabled
        Disabled -> icon.disabled
    }
}
