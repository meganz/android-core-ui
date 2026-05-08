/*
 * Code Connect mapping for the Figma `Snackbar` component set.
 *
 * Figma file : HagXfSTF94ICEFj8rfNyLc  (ANDROID - New)
 * Component  : Snackbar  (node 53977:33575)
 * Variants matched: none — single mapping applies to all Type / Dark mode combinations.
 *
 * Figma properties:
 *   Text → bound but not passed to MegaSnackbar (host-state driven) — see TODO.
 *
 * Compose side:
 *   - MegaSnackbar(snackBarHostState, safeAreaPadding?)
 *   The Compose API takes a SnackbarHostState rather than a flat text param, so the
 *   Figma `Text` property cannot flow directly. A remembered host state is used.
 *
 * NOTE — this file requires the `com.figma.code.connect` Gradle plugin.
 *
 * Property names VERIFIED via MCP on 2026-04-23.
 */

package mega.android.core.ui.components.snackbar

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import com.figma.code.connect.FigmaConnect
import com.figma.code.connect.FigmaProperty
import com.figma.code.connect.FigmaType

@FigmaConnect(url = "https://www.figma.com/design/HagXfSTF94ICEFj8rfNyLc/ANDROID---New?node-id=53977-33575")
class MegaSnackbarConnection {
    @FigmaProperty(FigmaType.Text, "Message text") val text: String = "Message"

    @Composable
    fun MegaSnackbarExample() {
        // TODO: MegaSnackbar uses SnackbarHostState; the Figma 'Text' does not flow to it directly.
        val state = remember { SnackbarHostState() }
        MegaSnackbar(snackBarHostState = state)
    }
}
