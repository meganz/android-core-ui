/*
 * Code Connect mapping for the Figma `Anchored buttons` component set.
 *
 * Figma file : HagXfSTF94ICEFj8rfNyLc  (ANDROID - New)
 * Component  : Anchored buttons  (node 57789:12062)
 * Variants matched: Type (Stacked | Split (Tablet only) | Inline).
 *
 * The button-group Compose APIs take ColumnScope / RowScope DSL lambdas which can't
 * be mapped from flat Figma properties — examples use empty lists.
 *
 * Property names VERIFIED via MCP on 2026-04-23.
 */

package mega.android.core.ui.components.button

import androidx.compose.runtime.Composable
import com.figma.code.connect.FigmaConnect
import com.figma.code.connect.FigmaProperty
import com.figma.code.connect.FigmaType
import com.figma.code.connect.FigmaVariant

@FigmaConnect(url = "https://www.figma.com/design/HagXfSTF94ICEFj8rfNyLc/ANDROID---New?node-id=57789-12062")
@FigmaVariant("Type", "Stacked")
class AnchoredButtonGroupConnection {
    @FigmaProperty(FigmaType.Boolean, "Has header text") val showTitle: Boolean = false
    @FigmaProperty(FigmaType.Text, "Header text")        val titleText: String = "Title"

    @Composable
    fun AnchoredButtonGroupExample() {
        AnchoredButtonGroup(
            buttonGroup = emptyList(),
            title = if (showTitle) titleText else null,
            withDivider = false,
        )
    }
}

@FigmaConnect(url = "https://www.figma.com/design/HagXfSTF94ICEFj8rfNyLc/ANDROID---New?node-id=57789-12062")
@FigmaVariant("Type", "Side-by-side")
class HorizontalAnchoredButtonGroupConnection {
    @Composable
    fun HorizontalAnchoredButtonGroupExample() {
        HorizontalAnchoredButtonGroup(
            buttonGroup = emptyList(),
            withDivider = false,
        )
    }
}

@FigmaConnect(url = "https://www.figma.com/design/HagXfSTF94ICEFj8rfNyLc/ANDROID---New?node-id=57789-12062")
@FigmaVariant("Type", "Inline")
class InlineAnchoredButtonGroupConnection {
    @Composable
    fun InlineAnchoredButtonGroupExample() {
        InlineAnchoredButtonGroup(
            primaryButtonText = "Primary",
            textOnlyButtonText = "Cancel",
            onPrimaryButtonClick = {},
            onTextOnlyButtonClick = {},
        )
    }
}
