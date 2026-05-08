/*
 * Code Connect mapping for the Figma `Buttons` component set.
 *
 * Figma file : HagXfSTF94ICEFj8rfNyLc  (ANDROID - New)
 * Component  : Buttons  (node 54403:41645)
 * Variants matched:
 *   Type = Primary | Secondary | Outline | Text only
 *   (State axis is NOT matched — Compose expresses Hover/Focus/Pressed/Loader/Disabled
 *    dynamically via `enabled`/`isLoading`, and Figma State=Default is the canonical design.)
 *
 * Figma properties:
 *   ✏️ Text          → text    (bound as `label`)
 *   Show leading     → Boolean (NOT currently mapped — Compose leadingIcon is a lambda,
 *                        we cannot supply a runtime Painter from Figma)
 *   Show trailing    → Boolean (same — not mapped)
 *
 * Compose side:
 *   - PrimaryFilledButton(modifier, text, onClick, leadingIcon?, trailingIcon?, enabled, isLoading)
 *   - SecondaryFilledButton(modifier, text, onClick, leadingIcon?, trailingIcon?, enabled, isLoading)
 *   - MegaOutlinedButton(modifier, text, onClick, leadingIcon?, trailingIcon?, enabled, isLoading)
 *   - TextOnlyButton(text, onClick, modifier, enabled)
 *
 * Notes:
 *   - Figma Type=Destructive has no direct Compose equivalent — skipped (TODO: add destructive
 *     button to Compose, or map to PrimaryFilledButton + semantic color).
 *   - Instance swaps (Leading/Trailing icon) are not mapped (lambda params in Compose).
 *
 * NOTE — this file requires the `com.figma.code.connect` Gradle plugin. Current location
 * (core-ui/code-connect/) is outside src/main so it does not affect the normal build.
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

@FigmaConnect(url = "https://www.figma.com/design/HagXfSTF94ICEFj8rfNyLc/ANDROID---New?node-id=54403-41645")
@FigmaVariant("Style", "Primary")
class PrimaryFilledButtonConnection {
    @FigmaProperty(FigmaType.Text, "Label text") val label: String = "Button"

    @Composable
    fun PrimaryFilledButtonExample() {
        PrimaryFilledButton(
            modifier = Modifier,
            text = label,
            onClick = {},
        )
    }
}

@FigmaConnect(url = "https://www.figma.com/design/HagXfSTF94ICEFj8rfNyLc/ANDROID---New?node-id=54403-41645")
@FigmaVariant("Style", "Secondary")
class SecondaryFilledButtonConnection {
    @FigmaProperty(FigmaType.Text, "Label text") val label: String = "Button"

    @Composable
    fun SecondaryFilledButtonExample() {
        SecondaryFilledButton(
            modifier = Modifier,
            text = label,
            onClick = {},
        )
    }
}

@FigmaConnect(url = "https://www.figma.com/design/HagXfSTF94ICEFj8rfNyLc/ANDROID---New?node-id=54403-41645")
@FigmaVariant("Style", "Outline")
class MegaOutlinedButtonConnection {
    @FigmaProperty(FigmaType.Text, "Label text") val label: String = "Button"

    @Composable
    fun MegaOutlinedButtonExample() {
        MegaOutlinedButton(
            modifier = Modifier,
            text = label,
            onClick = {},
        )
    }
}

@FigmaConnect(url = "https://www.figma.com/design/HagXfSTF94ICEFj8rfNyLc/ANDROID---New?node-id=54403-41645")
@FigmaVariant("Style", "Text only")
class TextOnlyButtonConnection {
    @FigmaProperty(FigmaType.Text, "Label text") val label: String = "Button"

    @Composable
    fun TextOnlyButtonExample() {
        TextOnlyButton(
            text = label,
            onClick = {},
        )
    }
}
