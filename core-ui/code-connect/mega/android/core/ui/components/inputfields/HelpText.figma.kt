/*
 * Code Connect mapping for the Figma `Help text` component set.
 *
 * Figma file : HagXfSTF94ICEFj8rfNyLc  (ANDROID - New)
 * Component  : Help text  (node 54458:4877)
 * Variants matched: Style (Help | Error | Warning | Success).
 * Variants IGNORED: Dark mode — handled by AndroidTheme.
 * Figma Style=Count and Compose HelpTextInfo / HelpTextLink have no direct counterpart.
 *
 * Property names VERIFIED via MCP on 2026-04-23.
 */

package mega.android.core.ui.components.inputfields

import androidx.compose.runtime.Composable
import com.figma.code.connect.FigmaConnect
import com.figma.code.connect.FigmaProperty
import com.figma.code.connect.FigmaType
import com.figma.code.connect.FigmaVariant

@FigmaConnect(url = "https://www.figma.com/design/HagXfSTF94ICEFj8rfNyLc/ANDROID---New?node-id=54458-4877")
@FigmaVariant("Style", "Help")
class HelpTextInfoConnection {
    @FigmaProperty(FigmaType.Text, "Text") val text: String = "Info"

    @Composable
    fun HelpTextInfoExample() {
        HelpTextInfo(text = text)
    }
}

@FigmaConnect(url = "https://www.figma.com/design/HagXfSTF94ICEFj8rfNyLc/ANDROID---New?node-id=54458-4877")
@FigmaVariant("Style", "Error")
class HelpTextErrorConnection {
    @FigmaProperty(FigmaType.Text, "Text") val text: String = "Error"

    @Composable
    fun HelpTextErrorExample() {
        HelpTextError(text = text)
    }
}

@FigmaConnect(url = "https://www.figma.com/design/HagXfSTF94ICEFj8rfNyLc/ANDROID---New?node-id=54458-4877")
@FigmaVariant("Style", "Warning")
class HelpTextWarningConnection {
    @FigmaProperty(FigmaType.Text, "Text") val text: String = "Warning"

    @Composable
    fun HelpTextWarningExample() {
        HelpTextWarning(text = text)
    }
}

@FigmaConnect(url = "https://www.figma.com/design/HagXfSTF94ICEFj8rfNyLc/ANDROID---New?node-id=54458-4877")
@FigmaVariant("Style", "Success")
class HelpTextSuccessConnection {
    @FigmaProperty(FigmaType.Text, "Text") val text: String = "Success"

    @Composable
    fun HelpTextSuccessExample() {
        HelpTextSuccess(text = text)
    }
}
