/*
 * Code Connect mapping for the Figma `Badge` component set.
 *
 * Figma file : HagXfSTF94ICEFj8rfNyLc  (ANDROID - New)
 * Component  : Badge  (node 60665:591)
 * Variants matched: none stacked — a single @FigmaConnect maps the Figma `Type:`
 *   property (note literal trailing colon) to Compose's `BadgeType` enum via Figma.mapping.
 *
 * Figma properties mapped:
 *   Type: → badgeType (enum mapping)
 *
 * Figma properties IGNORED:
 *   Icon / Size — kept simple for now; Compose Badge has optional icon/size params
 *   that we don't surface through Code Connect today.
 *
 * Compose side:
 *   - Badge(badgeType: BadgeType, text, modifier, icon)
 *
 * NOTE — this file requires the `com.figma.code.connect` Gradle plugin.
 *
 * Property names VERIFIED via MCP on 2026-04-23.
 */

package mega.android.core.ui.components.badge

import androidx.compose.runtime.Composable
import com.figma.code.connect.Figma
import com.figma.code.connect.FigmaConnect
import mega.android.core.ui.components.badge.BadgeType

@FigmaConnect(url = "https://www.figma.com/design/HagXfSTF94ICEFj8rfNyLc/ANDROID---New?node-id=60665-591")
class BadgeConnection {
    // Note: Figma values "Mega-primary" → BadgeType.Mega;
    // "InfoSecondary" has no Compose equivalent yet — falls through to BadgeType.Info.
    val badgeType: BadgeType = Figma.mapping(
        "Info" to BadgeType.Info,
        "Success" to BadgeType.Success,
        "Warning" to BadgeType.Warning,
        "Error" to BadgeType.Error,
        "Mega-primary" to BadgeType.Mega,
        "Mega-secondary" to BadgeType.MegaSecondary,
        "InfoSecondary" to BadgeType.Info,
    )

    @Composable
    fun BadgeExample() {
        Badge(
            badgeType = badgeType,
            text = "New",
        )
    }
}
