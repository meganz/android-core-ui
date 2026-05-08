/*
 * Code Connect mapping for the Figma `Radio buttons` component set.
 *
 * Figma file : HagXfSTF94ICEFj8rfNyLc  (ANDROID - New)
 * Component  : Radio buttons  (node 51739:4608)
 * Variants matched: Selected (true | false).
 * Variants IGNORED: State / Focus / Dark mode — handled at runtime / by theme.
 *
 * Property names VERIFIED via MCP on 2026-04-23.
 */

package mega.android.core.ui.components.button

import androidx.compose.runtime.Composable
import com.figma.code.connect.FigmaConnect
import com.figma.code.connect.FigmaVariant

@FigmaConnect(url = "https://www.figma.com/design/HagXfSTF94ICEFj8rfNyLc/ANDROID---New?node-id=51739-4608")
@FigmaVariant("Selected", "True")
class SelectedMegaRadioButtonConnection {
    @Composable
    fun SelectedMegaRadioButtonExample() {
        MegaRadioButton(
            identifier = "option",
            onOptionSelected = {},
            selected = true,
        )
    }
}

@FigmaConnect(url = "https://www.figma.com/design/HagXfSTF94ICEFj8rfNyLc/ANDROID---New?node-id=51739-4608")
@FigmaVariant("Selected", "False")
class UnselectedMegaRadioButtonConnection {
    @Composable
    fun UnselectedMegaRadioButtonExample() {
        MegaRadioButton(
            identifier = "option",
            onOptionSelected = {},
            selected = false,
        )
    }
}
