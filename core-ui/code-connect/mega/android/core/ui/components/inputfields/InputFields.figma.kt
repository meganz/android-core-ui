/*
 * Code Connect mapping for the Figma `Input` component set.
 *
 * Figma file : HagXfSTF94ICEFj8rfNyLc  (ANDROID - New)
 * Component  : Input  (node 54458:4666)
 * Variants matched: Type (Input | Text Area).
 * Variants IGNORED: Style (Default/Error/Active/Set/Success/Warning/Disabled) — runtime;
 *                   Dark mode — handled by AndroidTheme.
 * Type=Dropdown and Type=Search have their own dedicated Compose components, mapped in
 *   dropdown/DropDown.figma.kt (node 54061:36963) and
 *   inputfields/SearchInput.figma.kt (node 55685:3635) respectively.
 *
 * Compose-only (no Figma counterpart in the `Input` set):
 *   - PasswordTextInputField / PasswordGeneratorInputField / PasswordGeneratorInputBox
 *   - ExpirationDateInputField
 *   - AnnotatedLabelTextInputField / AnnotatedLabelTextInputBox
 *   - LabelledTextInputWithAction / LabelledTextInputAction
 *
 * Property names VERIFIED via MCP on 2026-04-23.
 */

package mega.android.core.ui.components.inputfields

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import com.figma.code.connect.FigmaConnect
import com.figma.code.connect.FigmaProperty
import com.figma.code.connect.FigmaType
import com.figma.code.connect.FigmaVariant

@FigmaConnect(url = "https://www.figma.com/design/HagXfSTF94ICEFj8rfNyLc/ANDROID---New?node-id=54458-4666")
@FigmaVariant("Style", "Text field")
class TextInputFieldConnection {
    @FigmaProperty(FigmaType.Boolean, "Has label") val showLabel: Boolean = true
    @FigmaProperty(FigmaType.Boolean, "Has help")  val showHelp: Boolean = false

    @Composable
    fun TextInputFieldExample() {
        TextInputField(
            modifier = Modifier,
            keyboardType = KeyboardType.Text,
            text = "",
            label = if (showLabel) "Label" else "",
            placeholder = "Placeholder",
            errorText = if (showHelp) "Help message" else null,
        )
    }
}

@FigmaConnect(url = "https://www.figma.com/design/HagXfSTF94ICEFj8rfNyLc/ANDROID---New?node-id=54458-4666")
@FigmaVariant("Style", "Large text field")
class TextInputBoxConnection {
    @FigmaProperty(FigmaType.Boolean, "Has label") val showLabel: Boolean = true

    @Composable
    fun TextInputBoxExample() {
        TextInputBox(
            modifier = Modifier,
            keyboardType = KeyboardType.Text,
            text = "",
            label = if (showLabel) "Label" else "",
        )
    }
}
