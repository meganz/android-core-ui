/*
 * Code Connect mapping for the Figma `Tabs` component set.
 *
 * Figma file : HagXfSTF94ICEFj8rfNyLc  (ANDROID - New)
 * Component  : Tabs  (node 59033:5972)
 * Variants matched:
 *   Type = Fixed | Scrollable
 *   (Tabs # and Selected Tab axes NOT matched — runtime state.)
 *
 * Compose side:
 *   - MegaFixedTabRow(tabIndex, items, onClick, modifier, withDivider)
 *   - MegaScrollableTabRow(tabIndex, items, onClick, modifier, withDivider)
 *   - MegaCollapsibleTabRow has no Figma equivalent — skipped.
 *
 * Notes:
 *   - `items: List<TabItems>` cannot be flattened into Figma properties — emitted as
 *     `emptyList()` with a TODO.
 *
 * NOTE — this file requires the `com.figma.code.connect` Gradle plugin.
 *
 * Property names VERIFIED via MCP on 2026-04-23.
 */

package mega.android.core.ui.components.tabs

import androidx.compose.runtime.Composable
import com.figma.code.connect.FigmaConnect
import com.figma.code.connect.FigmaVariant

@FigmaConnect(url = "https://www.figma.com/design/HagXfSTF94ICEFj8rfNyLc/ANDROID---New?node-id=59033-5972")
@FigmaVariant("Style", "Fixed")
class MegaFixedTabRowConnection {
    @Composable
    fun MegaFixedTabRowExample() {
        MegaFixedTabRow(
            tabIndex = 0,
            items = emptyList(), // TODO: populate from Figma list
            onClick = {},
        )
    }
}

@FigmaConnect(url = "https://www.figma.com/design/HagXfSTF94ICEFj8rfNyLc/ANDROID---New?node-id=59033-5972")
@FigmaVariant("Style", "Scrollable")
class MegaScrollableTabRowConnection {
    @Composable
    fun MegaScrollableTabRowExample() {
        MegaScrollableTabRow(
            tabIndex = 0,
            items = emptyList(), // TODO: populate from Figma list
            onClick = {},
        )
    }
}
