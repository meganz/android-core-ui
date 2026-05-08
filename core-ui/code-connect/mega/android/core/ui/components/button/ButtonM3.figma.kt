/*
 * Code Connect mapping for the Figma `Button (M3 Expressive)` component set.
 *
 * Figma file : HagXfSTF94ICEFj8rfNyLc  (ANDROID - New)
 * Component  : Button (M3 Expressive)  (node 63929:5728)
 * Variants matched: Type (Primary | Text only).
 * Variants IGNORED: Size / State — Compose handles at runtime.
 * Figma has extra Type values (Secondary | Destructive) with no Compose counterpart.
 *
 * Property names VERIFIED via MCP on 2026-04-23.
 */

package mega.android.core.ui.components.button

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.figma.code.connect.FigmaConnect
import com.figma.code.connect.FigmaProperty
import com.figma.code.connect.FigmaType
import com.figma.code.connect.FigmaVariant

@FigmaConnect(url = "https://www.figma.com/design/HagXfSTF94ICEFj8rfNyLc/ANDROID---New?node-id=63929-5728")
@FigmaVariant("Style", "Primary")
class PrimaryFilledButtonM3Connection {
    @FigmaProperty(FigmaType.Text, "Label") val label: String = "Button"

    @Composable
    fun PrimaryFilledButtonM3Example() {
        PrimaryFilledButtonM3(
            modifier = Modifier,
            text = label,
            onClick = {},
        )
    }
}

@FigmaConnect(url = "https://www.figma.com/design/HagXfSTF94ICEFj8rfNyLc/ANDROID---New?node-id=63929-5728")
@FigmaVariant("Style", "Text only")
class TextOnlyButtonM3Connection {
    @FigmaProperty(FigmaType.Text, "Label") val label: String = "Button"

    @Composable
    fun TextOnlyButtonM3Example() {
        TextOnlyButtonM3(
            text = label,
            onClick = {},
        )
    }
}
