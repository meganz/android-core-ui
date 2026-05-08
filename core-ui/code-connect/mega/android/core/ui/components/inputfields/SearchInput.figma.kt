/*
 * Code Connect mapping for the Figma `Search` component set.
 *
 * Figma file : HagXfSTF94ICEFj8rfNyLc  (ANDROID - New)
 * Component  : Search  (node 55685:3635)
 * Variants IGNORED: State — runtime concern.
 *
 * Property names VERIFIED via MCP on 2026-04-23.
 */

package mega.android.core.ui.components.inputfields

import androidx.compose.runtime.Composable
import com.figma.code.connect.FigmaConnect
import com.figma.code.connect.FigmaProperty
import com.figma.code.connect.FigmaType

@FigmaConnect(url = "https://www.figma.com/design/HagXfSTF94ICEFj8rfNyLc/ANDROID---New?node-id=55685-3635")
class SearchInputFieldConnection {
    @FigmaProperty(FigmaType.Boolean, "Has placeholder") val showPlaceholder: Boolean = true
    @FigmaProperty(FigmaType.Text, "Text")               val text: String = ""

    @Composable
    fun SearchInputFieldExample() {
        SearchInputField(
            placeHolderText = if (showPlaceholder) "Search" else "",
            text = text,
        )
    }
}
