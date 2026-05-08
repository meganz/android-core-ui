/*
 * Code Connect mapping for the Figma `sf_Read-only input field` component set.
 *
 * Figma file : HagXfSTF94ICEFj8rfNyLc  (ANDROID - New)
 * Component  : sf_Read-only input field  (node 58722:7051)
 *              (`sf_` prefix = shared-with-iOS; still valid for Android Code Connect.)
 * Variants matched: Type (Singular | Grouped).
 *
 * Compose side:
 *   - Singular  → ReadOnlyTextInputField (standalone)
 *   - Grouped   → ReadOnlyInputField (ColumnScope container)
 *
 * Compose-only (no Figma counterpart — sit inside a Grouped container):
 *   - ReadOnlyInputFieldItem / ReadOnlyPasswordInputFieldItem
 *
 * Property names VERIFIED via MCP on 2026-04-23.
 */

package mega.android.core.ui.components.inputfields

import androidx.compose.runtime.Composable
import com.figma.code.connect.FigmaConnect
import com.figma.code.connect.FigmaVariant

@FigmaConnect(url = "https://www.figma.com/design/HagXfSTF94ICEFj8rfNyLc/ANDROID---New?node-id=58722-7051")
@FigmaVariant("Type", "Singular")
class ReadOnlyTextInputFieldConnection {
    @Composable
    fun ReadOnlyTextInputFieldExample() {
        ReadOnlyTextInputField(
            text = "Value",
            label = "Label",
        )
    }
}

@FigmaConnect(url = "https://www.figma.com/design/HagXfSTF94ICEFj8rfNyLc/ANDROID---New?node-id=58722-7051")
@FigmaVariant("Type", "Grouped")
class ReadOnlyInputFieldConnection {
    @Composable
    fun ReadOnlyInputFieldExample() {
        ReadOnlyInputField {
            // TODO: more faithful example — host ReadOnlyInputFieldItem rows here
        }
    }
}
