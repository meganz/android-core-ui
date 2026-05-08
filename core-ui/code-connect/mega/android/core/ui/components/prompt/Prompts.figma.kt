/*
 * Code Connect mapping for the Figma `Prompt` component set.
 *
 * Figma file : HagXfSTF94ICEFj8rfNyLc  (ANDROID - New)
 * Component  : Prompt  (node 55776:8220) — VERIFIED via get_context_for_code_connect
 *
 * Figma variant properties:
 *   - Type      : Transparent | Error | Success | Warning      (4 values)
 *   - Dark mode : False | True                                 (handled by AndroidTheme)
 *
 * Figma text properties:
 *   - ✏️ Prompt text  (TEXT) → maps to Compose `message: String`
 *
 * Compose side:
 *   - SuccessPrompt(message, modifier, forceRiceTopAppBar)
 *   - ErrorPrompt(message, modifier, forceRiceTopAppBar)
 *   - TransparentPrompt(message, modifier, forceRiceTopAppBar)
 *
 * ⚠️ Missing-in-Compose: Figma has `Type=Warning` but there is no `WarningPrompt`
 * composable. Flagged for the Android team — either add `WarningPrompt` to core-ui or
 * remove the Warning variant from Figma.
 *
 * NOTE — this file is a DRAFT. It will not compile until the `com.figma.code.connect`
 * Gradle plugin is added to core-ui/build.gradle.kts.
 */

package mega.android.core.ui.components.prompt

import androidx.compose.runtime.Composable
import com.figma.code.connect.FigmaConnect
import com.figma.code.connect.FigmaProperty
import com.figma.code.connect.FigmaType
import com.figma.code.connect.FigmaVariant

@FigmaConnect(url = "https://www.figma.com/design/HagXfSTF94ICEFj8rfNyLc/ANDROID---New?node-id=55776-8220")
@FigmaVariant("Severity", "Success")
class SuccessPromptConnection {
    @FigmaProperty(FigmaType.Text, "Prompt text") val promptText: String = "Saved"

    @Composable
    fun SuccessPromptExample() {
        SuccessPrompt(message = promptText)
    }
}

@FigmaConnect(url = "https://www.figma.com/design/HagXfSTF94ICEFj8rfNyLc/ANDROID---New?node-id=55776-8220")
@FigmaVariant("Severity", "Error")
class ErrorPromptConnection {
    @FigmaProperty(FigmaType.Text, "Prompt text") val promptText: String = "Error"

    @Composable
    fun ErrorPromptExample() {
        ErrorPrompt(message = promptText)
    }
}

@FigmaConnect(url = "https://www.figma.com/design/HagXfSTF94ICEFj8rfNyLc/ANDROID---New?node-id=55776-8220")
@FigmaVariant("Severity", "Neutral")
class TransparentPromptConnection {
    @FigmaProperty(FigmaType.Text, "Prompt text") val promptText: String = "Message"

    @Composable
    fun TransparentPromptExample() {
        TransparentPrompt(message = promptText)
    }
}

// TODO(compose-team): Figma has `Type=Warning` but there is no WarningPrompt composable.
// When added, uncomment:
//
// @FigmaConnect(url = "https://www.figma.com/design/HagXfSTF94ICEFj8rfNyLc/ANDROID---New?node-id=55776-8220")
// @FigmaVariant("Type", "Warning")
// class WarningPromptConnection {
//     @FigmaProperty(FigmaType.Text, "Prompt text") val promptText: String = "Warning"
//
//     @Composable
//     fun WarningPromptExample() {
//         WarningPrompt(message = promptText)
//     }
// }
