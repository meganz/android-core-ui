/*
 * Code Connect mapping for the Figma `Home banner` component.
 *
 * Figma file : HagXfSTF94ICEFj8rfNyLc  (ANDROID - New)
 * Component  : Home banner  (node 63850:784) — VERIFIED via get_context_for_code_connect
 *
 * Figma exposes **only one variant axis**: `Title = 1 line | 2 lines`. This controls the
 * layout of the headline text slot, not whether the title is shown. The Headline and
 * Label text slots inside the component are NOT editable instance properties (no
 * COMPONENT_PROPERTY_REFERENCE binding) — designers override them by editing the text
 * directly on the instance. So there are no `@FigmaProperty(Text, ...)` declarations
 * here; designers' text edits simply pass through to the Compose `title` / `buttonText`
 * params at render time via Dev Mode.
 *
 * The embedded `x` instance (close button) is static — the Compose `showDismissButton`
 * boolean controls whether it renders. We don't map that axis because Figma's Home banner
 * always shows the dismiss icon in the variants; the run-time "hide dismiss" is a
 * Compose-only capability not exposed on the design side.
 *
 * NOTE — this file is a DRAFT. It will not compile until the `com.figma.code.connect`
 * Gradle plugin is added to core-ui/build.gradle.kts.
 */

package mega.android.core.ui.components.banner

import androidx.compose.runtime.Composable
import com.figma.code.connect.FigmaConnect

@FigmaConnect(url = "https://www.figma.com/design/HagXfSTF94ICEFj8rfNyLc/ANDROID---New?node-id=63850-784")
class HomeBannerConnection {
    @Composable
    fun HomeBannerExample() {
        HomeBanner(
            backgroundImageUrl = null,
            imageUrl = null,
            title = "Title",
            buttonText = "Action",
            showDismissButton = true,
            onClick = {},
            onDismissClick = {},
        )
    }
}
