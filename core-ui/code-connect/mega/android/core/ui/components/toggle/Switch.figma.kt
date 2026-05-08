/*
 * Code Connect mapping for the Figma `Switch` component set.
 *
 * Figma file : HagXfSTF94ICEFj8rfNyLc  (ANDROID - New)
 * Component  : Switch  (node 51945:10135)
 * Variants matched: none stacked — single mapping using Figma.mapping for Checked.
 *   Checked axis values:  True | False  → Boolean
 *   (Status, Enabled axes NOT matched — Compose handles dynamically via isEnabled.)
 *
 * Compose side:
 *   - Toggle(isChecked, onCheckedChange, modifier, isEnabled, interactionSource, onClickListener)
 *
 * Notes:
 *   - Off-disabled / On-disabled Figma states map to isEnabled=false in Compose,
 *     but we don't bind that here — interaction states are runtime.
 *
 * NOTE — this file requires the `com.figma.code.connect` Gradle plugin.
 *
 * Property names VERIFIED via MCP on 2026-04-23.
 */

package mega.android.core.ui.components.toggle

import androidx.compose.runtime.Composable
import com.figma.code.connect.Figma
import com.figma.code.connect.FigmaConnect

@FigmaConnect(url = "https://www.figma.com/design/HagXfSTF94ICEFj8rfNyLc/ANDROID---New?node-id=51945-10135")
class ToggleConnection {
    val checked: Boolean = Figma.mapping(
        "True" to true,
        "False" to false,
    )

    @Composable
    fun ToggleExample() {
        Toggle(
            isChecked = checked,
            onCheckedChange = {},
        )
    }
}
