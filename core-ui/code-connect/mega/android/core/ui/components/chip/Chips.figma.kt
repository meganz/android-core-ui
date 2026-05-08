/*
 * Code Connect mapping for the Figma `Chips` component set.
 *
 * Figma file : HagXfSTF94ICEFj8rfNyLc  (ANDROID - New)
 * Component  : Chips  (node 54957:6369)
 * Variants matched: none stacked — single mapping applies to any State/Focus/Dark mode combo.
 *
 * Figma properties mapped:
 *   ✏️ Text → label
 *
 * Figma properties IGNORED:
 *   Show leading icon / Show trailing icon — Compose chip icons are lambda slots and
 *   Figma cannot supply a runtime Painter.
 *
 * Compose side:
 *   - MegaChip(selected, text, style, enabled, leadingIcon?, trailingIcon?, onClick)
 *   - HighlightChip also exists for highlighted search text (not mapped here).
 *
 * NOTE — this file requires the `com.figma.code.connect` Gradle plugin.
 *
 * Property names VERIFIED via MCP on 2026-04-23.
 */

package mega.android.core.ui.components.chip

import androidx.compose.runtime.Composable
import com.figma.code.connect.FigmaConnect
import com.figma.code.connect.FigmaProperty
import com.figma.code.connect.FigmaType

@FigmaConnect(url = "https://www.figma.com/design/HagXfSTF94ICEFj8rfNyLc/ANDROID---New?node-id=54957-6369")
class MegaChipConnection {
    @FigmaProperty(FigmaType.Text, "Text") val label: String = "Chip"

    @Composable
    fun MegaChipExample() {
        MegaChip(
            selected = false,
            text = label,
            onClick = {},
        )
    }
}
