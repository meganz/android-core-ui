/*
 * Code Connect mapping for the Figma `Verification` component set.
 *
 * Figma file : HagXfSTF94ICEFj8rfNyLc  (ANDROID - New)
 * Component  : Verification  (node 54458:4914)
 * Variants IGNORED: Style / Dark mode — runtime / theme concerns.
 *
 * Property names VERIFIED via MCP on 2026-04-23.
 */

package mega.android.core.ui.components.inputfields

import androidx.compose.runtime.Composable
import com.figma.code.connect.FigmaConnect
import com.figma.code.connect.FigmaProperty
import com.figma.code.connect.FigmaType

@FigmaConnect(url = "https://www.figma.com/design/HagXfSTF94ICEFj8rfNyLc/ANDROID---New?node-id=54458-4914")
class VerificationTextInputFieldConnection {
    @FigmaProperty(FigmaType.Boolean, "Has message") val showMessage: Boolean = false

    @Composable
    fun VerificationTextInputFieldExample() {
        VerificationTextInputField(
            value = "",
            onValueChange = {},
            errorText = if (showMessage) "Invalid code" else "",
        )
    }
}
