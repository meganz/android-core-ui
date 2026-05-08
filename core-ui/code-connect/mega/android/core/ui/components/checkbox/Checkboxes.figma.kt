/*
 * Code Connect mapping for the Figma `Checkboxes` component set.
 *
 * Figma file : HagXfSTF94ICEFj8rfNyLc  (ANDROID - New)
 * Component  : Checkboxes  (node 51859:5628)
 * Variants matched: none stacked — single mapping using Figma.mapping for Checked.
 *   Checked axis values:  True | False  → Boolean
 *   (State, Dark mode, Focus axes are NOT matched — Compose handles dynamically.)
 *
 * Compose side:
 *   - Checkbox(checked, onCheckStateChanged, modifier, enabled, clickable, tapTargetArea)
 *
 * Notes:
 *   - Figma State=Error has no direct Compose equivalent — skipped.
 *
 * NOTE — this file requires the `com.figma.code.connect` Gradle plugin.
 *
 * Property names VERIFIED via MCP on 2026-04-23.
 */

package mega.android.core.ui.components.checkbox

import androidx.compose.runtime.Composable
import com.figma.code.connect.Figma
import com.figma.code.connect.FigmaConnect

@FigmaConnect(url = "https://www.figma.com/design/HagXfSTF94ICEFj8rfNyLc/ANDROID---New?node-id=51859-5628")
class CheckboxConnection {
    val checked: Boolean = Figma.mapping(
        "True" to true,
        "False" to false,
    )

    @Composable
    fun CheckboxExample() {
        Checkbox(
            checked = checked,
            onCheckStateChanged = {},
        )
    }
}
