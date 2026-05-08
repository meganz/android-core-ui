/*
 * Code Connect mapping for the Figma `List item` / `Header` / `Profile list` component sets.
 *
 * Figma file : HagXfSTF94ICEFj8rfNyLc  (ANDROID - New)
 * Components :
 *   - List item    (node 62384:4497)   — MCP data fetched 2026-04-23
 *   - Header       (node TODO) — not yet fetched
 *   - Profile list (node TODO) — not yet fetched
 *
 * List item variants matched: none stacked. A single `TwoLineListItemConnection` maps the
 * generic two-line item, binding Figma text and `Show Subtitle` to a nullable subtitle.
 *
 * Figma properties mapped (List item):
 *   ✏️ Title        → titleText
 *   ✏️ Subtitle     → subtitleText  (bound as `subtitle = if (showSubtitle) subtitleText else null`)
 *   Show Subtitle   → showSubtitle
 *
 * Figma properties IGNORED (List item):
 *   Show Leading / Show Trailing / Dividers / Coloured label / Show favourite /
 *   Show link / Offline / Versioned — not represented as flat Compose params; the
 *   TwoLineListItem Compose API handles these via lambda slots or has no equivalent.
 *
 * Compose side — multiple list-item composables exist but are mapped jointly for now
 * (OneLineListItem, PlainContentListItem, IconContentListItem, ImageContentListItem,
 *  NumberContentListItem, FlexibleLineListItem, GenericListItem). Add distinct
 * @FigmaConnect classes here when Figma exposes matching variants.
 *
 * NOTE — this file requires the `com.figma.code.connect` Gradle plugin.
 *
 * Property names VERIFIED via MCP on 2026-04-23 for List item.
 */

package mega.android.core.ui.components.list

import androidx.compose.runtime.Composable
import com.figma.code.connect.FigmaConnect
import com.figma.code.connect.FigmaProperty
import com.figma.code.connect.FigmaType

@FigmaConnect(url = "https://www.figma.com/design/HagXfSTF94ICEFj8rfNyLc/ANDROID---New?node-id=62384-4497")
class TwoLineListItemConnection {
    @FigmaProperty(FigmaType.Text, "Title text")     val titleText: String = "Title"
    @FigmaProperty(FigmaType.Text, "Subtitle text")  val subtitleText: String = "Subtitle"
    @FigmaProperty(FigmaType.Boolean, "Has subtitle") val showSubtitle: Boolean = true

    @Composable
    fun TwoLineListItemExample() {
        TwoLineListItem(
            title = titleText,
            subtitle = if (showSubtitle) subtitleText else "",
        )
    }
}
