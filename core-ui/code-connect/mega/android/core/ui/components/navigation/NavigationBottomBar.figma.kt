/*
 * Code Connect mapping for the Figma `Bottom app bar` component set.
 *
 * Figma file : HagXfSTF94ICEFj8rfNyLc  (ANDROID - New)
 * Component  : Bottom app bar  (node 50721:10382)
 * Variants IGNORED: Segments / Selected / Dark mode — Compose is content-driven;
 *   item count and selection state are determined at runtime, theme is handled by
 *   AndroidTheme.
 *
 * Compose-only (no Figma counterpart as a standalone publishable component):
 *   - NavigationBottomBarItem — Figma models per-item segments as descendants of
 *     `Bottom app bar` via an internal `Building Blocks / segment` component,
 *     which isn't exposed as a top-level component set that Code Connect can
 *     target independently. The item properties (`Show Badge`, `✏️ Text`,
 *     `Icon outline`, `Icon filled`, `Active`) live on those descendants.
 *
 * Property names VERIFIED via MCP on 2026-04-23.
 */

package mega.android.core.ui.components.navigation

import androidx.compose.runtime.Composable
import com.figma.code.connect.FigmaConnect
import mega.android.core.ui.R

@FigmaConnect(url = "https://www.figma.com/design/HagXfSTF94ICEFj8rfNyLc/ANDROID---New?node-id=50721-10382")
class NavigationBottomBarConnection {
    @Composable
    fun NavigationBottomBarExample() {
        NavigationBottomBar {
            NavigationBottomBarItem(
                defaultIcon = R.drawable.ic_alert_circle_medium_thin_outline,
                selectedIcon = R.drawable.ic_alert_circle_medium_thin_outline,
                label = "Item 1",
                isSelected = true,
                onClick = {},
            )
            NavigationBottomBarItem(
                defaultIcon = R.drawable.ic_alert_circle_medium_thin_outline,
                selectedIcon = R.drawable.ic_alert_circle_medium_thin_outline,
                label = "Item 2",
                isSelected = false,
                onClick = {},
            )
            NavigationBottomBarItem(
                defaultIcon = R.drawable.ic_alert_circle_medium_thin_outline,
                selectedIcon = R.drawable.ic_alert_circle_medium_thin_outline,
                label = "Item 3",
                isSelected = false,
                onClick = {},
            )
        }
    }
}
