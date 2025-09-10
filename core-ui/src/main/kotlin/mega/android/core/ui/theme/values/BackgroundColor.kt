//
// Generated automatically by KotlinTokensGenerator.
// Do not modify this file manually.
//
package mega.android.core.ui.theme.values

import androidx.compose.ui.graphics.Color
import mega.android.core.ui.tokens.theme.tokens.Background

public enum class BackgroundColor {
    PageBackground,
    Inverse,
    Surface1,
    Surface3,
    Surface2,
    Blur,
    SurfaceTransparent,
    SurfaceInverseAccent,
    ;

    public fun getBackgroundColor(background: Background): Color = when (this) {
        PageBackground -> background.pageBackground
        Inverse -> background.inverse
        Surface1 -> background.surface1
        Surface3 -> background.surface3
        Surface2 -> background.surface2
        Blur -> background.blur
        SurfaceTransparent -> background.surfaceTransparent
        SurfaceInverseAccent -> background.surfaceInverseAccent
    }
}
