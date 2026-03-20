//
// Generated automatically by KotlinTokensGenerator.
// Do not modify this file manually.
//
package mega.android.core.ui.theme.values

import androidx.compose.ui.graphics.Color
import mega.android.core.ui.tokens.theme.tokens.Indicator

public enum class IndicatorColor {
    Pink,
    Yellow,
    Green,
    Blue,
    Indigo,
    Magenta,
    Orange,
    ;

    public fun getIndicatorColor(indicator: Indicator): Color = when (this) {
        Pink -> indicator.pink
        Yellow -> indicator.yellow
        Green -> indicator.green
        Blue -> indicator.blue
        Indigo -> indicator.indigo
        Magenta -> indicator.magenta
        Orange -> indicator.orange
    }
}
