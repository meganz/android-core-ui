/*
 * Code Connect mapping for the Figma `Divider` component set.
 *
 * Figma file : HagXfSTF94ICEFj8rfNyLc  (ANDROID - New)
 * Component  : Divider  (node 57013:4600)
 * Variants matched: Weight (Subtle | Strong).
 *
 * Compose-only (no Figma counterpart — Figma Divider has only horizontal orientation):
 *   - SubtleVerticalDivider / StrongVerticalDivider
 *
 * Dark mode handled by AndroidTheme.
 *
 * Property names VERIFIED via MCP on 2026-04-23.
 */

package mega.android.core.ui.components.divider

import androidx.compose.runtime.Composable
import com.figma.code.connect.FigmaConnect
import com.figma.code.connect.FigmaVariant

@FigmaConnect(url = "https://www.figma.com/design/HagXfSTF94ICEFj8rfNyLc/ANDROID---New?node-id=57013-4600")
@FigmaVariant("Weight", "Subtle")
class SubtleDividerConnection {
    @Composable
    fun SubtleDividerExample() {
        SubtleDivider()
    }
}

@FigmaConnect(url = "https://www.figma.com/design/HagXfSTF94ICEFj8rfNyLc/ANDROID---New?node-id=57013-4600")
@FigmaVariant("Weight", "Strong")
class StrongDividerConnection {
    @Composable
    fun StrongDividerExample() {
        StrongDivider()
    }
}
