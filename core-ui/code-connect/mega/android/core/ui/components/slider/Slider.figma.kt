/*
 * Code Connect mapping for the Figma `Slider` component set.
 *
 * Figma file : HagXfSTF94ICEFj8rfNyLc  (ANDROID - New)
 * Component  : Slider  (node 55305:1046)
 * Variants matched: none — Figma's % axis is a visual snapshot only; Compose's `value`
 *   is runtime state. Dark mode axis handled by AndroidTheme.
 *
 * Compose side:
 *   - MegaSlider(value, onValueChange, modifier, enabled, valueRange, steps, onValueChangeFinished)
 *
 * NOTE — this file requires the `com.figma.code.connect` Gradle plugin.
 *
 * Property names VERIFIED via MCP on 2026-04-23.
 */

package mega.android.core.ui.components.slider

import androidx.compose.runtime.Composable
import com.figma.code.connect.FigmaConnect

@FigmaConnect(url = "https://www.figma.com/design/HagXfSTF94ICEFj8rfNyLc/ANDROID---New?node-id=55305-1046")
class MegaSliderConnection {
    @Composable
    fun MegaSliderExample() {
        MegaSlider(
            value = 0.5f,
            onValueChange = {},
        )
    }
}
