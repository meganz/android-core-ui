//
// Generated automatically by KotlinTokensGenerator.
// Do not modify this file manually.
//
package mega.android.core.ui.theme.values

import androidx.compose.ui.graphics.Color
import mega.android.core.ui.tokens.theme.tokens.Link

public enum class LinkColor {
    Primary,
    Inverse,
    Visited,
    ;

    public fun getLinkColor(link: Link): Color = when (this) {
        Primary -> link.primary
        Inverse -> link.inverse
        Visited -> link.visited
    }
}
