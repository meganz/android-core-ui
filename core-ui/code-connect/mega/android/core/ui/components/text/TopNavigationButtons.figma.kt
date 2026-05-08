/*
 * Code Connect mapping for the Figma `Top navigation buttons` component set.
 *
 * Figma file : HagXfSTF94ICEFj8rfNyLc  (ANDROID - New)
 * Component  : Top navigation buttons  (node 59846:181)
 * Variants matched: Style (Text button). The Figma `Icon` variant (icon-only form) is
 *                   used by designers directly and has no matching Compose function.
 *
 * Compose PrimaryTopNavigationButton / SecondaryTopNavigationButton both render as
 * text-only buttons and differ only by emphasis (color). Figma has no emphasis axis
 * here, so we map Primary to Style=Text button and leave Secondary as an emphasis
 * variation Compose exposes but Figma does not distinguish.
 *
 * Compose-only (no Figma equivalent): MegaText, MegaClickableText, LinkSpannedText,
 *   SpannedText, SpannableText, NetworkConnectionBanner, HighlightedText.
 *
 * Property names VERIFIED via MCP on 2026-04-23.
 */

package mega.android.core.ui.components.text

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.figma.code.connect.FigmaConnect
import com.figma.code.connect.FigmaProperty
import com.figma.code.connect.FigmaType
import com.figma.code.connect.FigmaVariant

@FigmaConnect(url = "https://www.figma.com/design/HagXfSTF94ICEFj8rfNyLc/ANDROID---New?node-id=59846-181")
@FigmaVariant("Style", "Text button")
class PrimaryTopNavigationButtonConnection {
    @FigmaProperty(FigmaType.Text, "Text") val label: String = "Next"

    @Composable
    fun PrimaryTopNavigationButtonExample() {
        PrimaryTopNavigationButton(
            modifier = Modifier,
            text = label,
            onClick = {},
        )
    }
}
