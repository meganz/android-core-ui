//
// Generated automatically by KotlinTokensGenerator.
// Do not modify this file manually.
//
package mega.android.core.ui.theme.values

import androidx.compose.ui.graphics.Color
import mega.android.core.ui.tokens.theme.tokens.Components

public enum class ComponentsColor {
    SelectionControl,
    Interactive,
    ToastBackground,
    ;

    public fun getComponentsColor(components: Components): Color = when (this) {
        SelectionControl -> components.selectionControl
        Interactive -> components.interactive
        ToastBackground -> components.toastBackground
    }
}
