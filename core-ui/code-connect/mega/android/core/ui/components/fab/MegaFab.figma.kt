/*
 * Code Connect mapping for the Figma `FAB` component set.
 *
 * Figma file : HagXfSTF94ICEFj8rfNyLc  (ANDROID - New)
 * Component  : FAB  (node 54820:2041)
 *
 * Note: this node previously hosted `Icon button`. After the designer's library
 * cleanup it was repurposed for the new FAB component (`Style`, `Status`, `Enabled`,
 * `Loading` axes). The IconButton mappings that used to live here have been moved to
 * a Missing-in-Figma stub in button/IconButtons.figma.kt.
 *
 * Compose side:
 *   - MegaFab(onClick, modifier, painter)
 *
 * No `@FigmaProperty` mappings: the Compose `MegaFab` API has no flat parameters that
 * line up with the Figma `Style` / `Status` / `Enabled` / `Loading` axes. Wiring those
 * up will require a Compose-side API expansion and is tracked as a follow-up. For now
 * the connection produces a placeholder painter, mirroring the pattern in
 * dialogs/Dialogs.figma.kt :: BasicImageDialogConnection.
 *
 * NOTE — this file requires the `com.figma.code.connect` Gradle plugin.
 *
 * Property names VERIFIED via MCP on 2026-05-01.
 */

package mega.android.core.ui.components.fab

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import com.figma.code.connect.FigmaConnect
import mega.android.core.ui.R

@FigmaConnect(url = "https://www.figma.com/design/HagXfSTF94ICEFj8rfNyLc/ANDROID---New?node-id=54820-2041")
class MegaFabConnection {
    @Composable
    fun MegaFabExample() {
        MegaFab(
            onClick = {},
            painter = painterResource(R.drawable.ic_alert_circle_medium_thin_outline),
        )
    }
}
