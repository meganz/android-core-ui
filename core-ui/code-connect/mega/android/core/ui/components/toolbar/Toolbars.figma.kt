/*
 * Code Connect mapping for the Figma `Toolbars` component set.
 *
 * Figma file : HagXfSTF94ICEFj8rfNyLc  (ANDROID - New)
 * Component  : Toolbars  (node 62836:7514)
 * Variants matched:
 *   Floating = True | False
 *   (Slots axis NOT matched — icon slots are lambda content in Compose.)
 *
 * Compose side:
 *   - Floating=False → MegaTopAppBar(title, navigationType, …)
 *   - Floating=True  → MegaFloatingToolbar(actions, modifier, actionsEnabled, elevation)
 *
 * The other Compose top-bar variants (MegaLargeTopAppBar, MegaSearchTopAppBar,
 * TransparentTopBar) have no distinct Figma variant today — they are left unmapped
 * here; add @FigmaConnect classes when Figma exposes separate variants.
 *
 * NOTE — this file requires the `com.figma.code.connect` Gradle plugin.
 *
 * Property names VERIFIED via MCP on 2026-04-23.
 */

@file:OptIn(androidx.compose.material3.ExperimentalMaterial3Api::class)

package mega.android.core.ui.components.toolbar

import androidx.compose.runtime.Composable
import com.figma.code.connect.FigmaConnect
import com.figma.code.connect.FigmaVariant

@FigmaConnect(url = "https://www.figma.com/design/HagXfSTF94ICEFj8rfNyLc/ANDROID---New?node-id=62836-7514")
@FigmaVariant("Is floating", "False")
class MegaTopAppBarConnection {
    @Composable
    fun MegaTopAppBarExample() {
        MegaTopAppBar(
            title = "Title",
            navigationType = AppBarNavigationType.Back {},
        )
    }
}

@FigmaConnect(url = "https://www.figma.com/design/HagXfSTF94ICEFj8rfNyLc/ANDROID---New?node-id=62836-7514")
@FigmaVariant("Is floating", "True")
class MegaFloatingToolbarConnection {
    @Composable
    fun MegaFloatingToolbarExample() {
        MegaFloatingToolbar(
            actions = emptyList(), // TODO: populate from Figma list
        )
    }
}
