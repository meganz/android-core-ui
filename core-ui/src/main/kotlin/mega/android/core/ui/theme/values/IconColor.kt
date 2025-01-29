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
    InverseAccent,
    OnColor,
    OnColorDisabled,
    Inverse,
    Disabled,
    ;

    internal fun getIconColor(icon: Icon): Color = when (this) {
        Primary -> icon.primary
        Secondary -> icon.secondary
        Accent -> icon.accent
        Brand -> icon.brand
        InverseAccent -> icon.inverseAccent
        OnColor -> icon.onColor
        OnColorDisabled -> icon.onColorDisabled
        Inverse -> icon.inverse
        Disabled -> icon.disabled
    }
}
