//
// Generated automatically by KotlinTokensGenerator.
// Do not modify this file manually.
//
package mega.android.core.ui.theme.values

import androidx.compose.ui.graphics.Color
import mega.android.core.ui.tokens.theme.tokens.Components

public enum class ComponentsColor {
    SelectionControl,
    SelectionControlAlt,
    Interactive,
    ToastBackground,
    ;

    public fun getComponentsColor(components: Components): Color = when (this) {
        SelectionControl -> components.selectionControl
        SelectionControlAlt -> components.selectionControlAlt
        Interactive -> components.interactive
        ToastBackground -> components.toastBackground
    }
}
