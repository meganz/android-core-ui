/*
 * Code Connect mapping for the Figma `Banners` component set.
 *
 * Figma file : HagXfSTF94ICEFj8rfNyLc  (ANDROID - New)
 * Component  : Banners  (node 54390:26198)
 * Variants   : 8 total = Placement (Inline | Top)  ×  Severity (Info | Success | Warning | Error)
 *
 * Property names are the REAL values read from Figma via get_context_for_code_connect
 * on 2026-04-23 (after the designer renamed properties for Compose-API alignment).
 *
 * Previous names (pre-rename, 2026-04-22) — for reviewers comparing versions:
 *   Type    → Placement                       values: Inline + "Top alert" → "Top"
 *   State   → Severity                        values unchanged
 *   Dark mode variant REMOVED (now handled by tokens only)
 *   ✏️ Title (text)      → Title text         (emoji dropped)
 *   ✏️ Body              → Body               (emoji dropped)
 *   ✏️ Button text       → Action label       (emoji dropped + renamed)
 *   Title (boolean)      → Has title
 *   Show x (boolean)     → Dismissible
 *   Button (boolean)     → Has action
 *
 * Compose side:
 *   - 8 public functions: Inline{Info,Success,Warning,Error}Banner + Top{Info,Success,Warning,Error}Banner
 *   - Compose expresses title/action visibility via nullable params.
 *   - State-specific icons (info / check-circle / alert-circle / alert-triangle) are hardcoded
 *     inside each Compose function, so we do NOT expose them as FigmaProperty.
 *
 * NOTE — this file is a DRAFT. It will not compile until the `com.figma.code.connect`
 * Gradle plugin is added to core-ui/build.gradle.kts. Current location
 * (core-ui/code-connect/) is outside src/main so it does not affect the normal build.
 */

package mega.android.core.ui.components.banner

import androidx.compose.runtime.Composable
import com.figma.code.connect.FigmaConnect
import com.figma.code.connect.FigmaProperty
import com.figma.code.connect.FigmaType
import com.figma.code.connect.FigmaVariant

// ─── Inline ─────────────────────────────────────────────────────────────────

@FigmaConnect(url = "https://www.figma.com/design/HagXfSTF94ICEFj8rfNyLc/ANDROID---New?node-id=54390-26198")
@FigmaVariant("Placement", "Inline")
@FigmaVariant("Severity", "Info")
class InlineInfoBannerConnection {
    @FigmaProperty(FigmaType.Boolean, "Has title")    val showTitle: Boolean = true
    @FigmaProperty(FigmaType.Text, "Title text")      val titleText: String = "Title"
    @FigmaProperty(FigmaType.Text, "Body text")       val bodyText: String = "Body"
    @FigmaProperty(FigmaType.Boolean, "Dismissible")  val showCancel: Boolean = true
    @FigmaProperty(FigmaType.Boolean, "Has action")   val showButton: Boolean = false
    @FigmaProperty(FigmaType.Text, "Action label")    val buttonText: String = "Action"

    @Composable
    fun InlineInfoBannerExample() {
        InlineInfoBanner(
            title = if (showTitle) titleText else null,
            body = bodyText,
            showCancelButton = showCancel,
            actionButtonText = if (showButton) buttonText else null,
        )
    }
}

@FigmaConnect(url = "https://www.figma.com/design/HagXfSTF94ICEFj8rfNyLc/ANDROID---New?node-id=54390-26198")
@FigmaVariant("Placement", "Inline")
@FigmaVariant("Severity", "Success")
class InlineSuccessBannerConnection {
    @FigmaProperty(FigmaType.Boolean, "Has title")    val showTitle: Boolean = true
    @FigmaProperty(FigmaType.Text, "Title text")      val titleText: String = "Title"
    @FigmaProperty(FigmaType.Text, "Body text")       val bodyText: String = "Body"
    @FigmaProperty(FigmaType.Boolean, "Dismissible")  val showCancel: Boolean = true
    @FigmaProperty(FigmaType.Boolean, "Has action")   val showButton: Boolean = false
    @FigmaProperty(FigmaType.Text, "Action label")    val buttonText: String = "Action"

    @Composable
    fun InlineSuccessBannerExample() {
        InlineSuccessBanner(
            title = if (showTitle) titleText else null,
            body = bodyText,
            showCancelButton = showCancel,
            actionButtonText = if (showButton) buttonText else null,
        )
    }
}

@FigmaConnect(url = "https://www.figma.com/design/HagXfSTF94ICEFj8rfNyLc/ANDROID---New?node-id=54390-26198")
@FigmaVariant("Placement", "Inline")
@FigmaVariant("Severity", "Warning")
class InlineWarningBannerConnection {
    @FigmaProperty(FigmaType.Boolean, "Has title")    val showTitle: Boolean = true
    @FigmaProperty(FigmaType.Text, "Title text")      val titleText: String = "Title"
    @FigmaProperty(FigmaType.Text, "Body text")       val bodyText: String = "Body"
    @FigmaProperty(FigmaType.Boolean, "Dismissible")  val showCancel: Boolean = true
    @FigmaProperty(FigmaType.Boolean, "Has action")   val showButton: Boolean = false
    @FigmaProperty(FigmaType.Text, "Action label")    val buttonText: String = "Action"

    @Composable
    fun InlineWarningBannerExample() {
        InlineWarningBanner(
            title = if (showTitle) titleText else null,
            body = bodyText,
            showCancelButton = showCancel,
            actionButtonText = if (showButton) buttonText else null,
        )
    }
}

@FigmaConnect(url = "https://www.figma.com/design/HagXfSTF94ICEFj8rfNyLc/ANDROID---New?node-id=54390-26198")
@FigmaVariant("Placement", "Inline")
@FigmaVariant("Severity", "Error")
class InlineErrorBannerConnection {
    @FigmaProperty(FigmaType.Boolean, "Has title")    val showTitle: Boolean = true
    @FigmaProperty(FigmaType.Text, "Title text")      val titleText: String = "Title"
    @FigmaProperty(FigmaType.Text, "Body text")       val bodyText: String = "Body"
    @FigmaProperty(FigmaType.Boolean, "Dismissible")  val showCancel: Boolean = true
    @FigmaProperty(FigmaType.Boolean, "Has action")   val showButton: Boolean = false
    @FigmaProperty(FigmaType.Text, "Action label")    val buttonText: String = "Action"

    @Composable
    fun InlineErrorBannerExample() {
        InlineErrorBanner(
            title = if (showTitle) titleText else null,
            body = bodyText,
            showCancelButton = showCancel,
            actionButtonText = if (showButton) buttonText else null,
        )
    }
}

// ─── Top ────────────────────────────────────────────────────────────────────

@FigmaConnect(url = "https://www.figma.com/design/HagXfSTF94ICEFj8rfNyLc/ANDROID---New?node-id=54390-26198")
@FigmaVariant("Placement", "Top")
@FigmaVariant("Severity", "Info")
class TopInfoBannerConnection {
    @FigmaProperty(FigmaType.Boolean, "Has title")    val showTitle: Boolean = true
    @FigmaProperty(FigmaType.Text, "Title text")      val titleText: String = "Title"
    @FigmaProperty(FigmaType.Text, "Body text")       val bodyText: String = "Body"
    @FigmaProperty(FigmaType.Boolean, "Dismissible")  val showCancel: Boolean = true
    @FigmaProperty(FigmaType.Boolean, "Has action")   val showButton: Boolean = false
    @FigmaProperty(FigmaType.Text, "Action label")    val buttonText: String = "Action"

    @Composable
    fun TopInfoBannerExample() {
        TopInfoBanner(
            title = if (showTitle) titleText else null,
            body = bodyText,
            showCancelButton = showCancel,
            actionButtonText = if (showButton) buttonText else null,
        )
    }
}

@FigmaConnect(url = "https://www.figma.com/design/HagXfSTF94ICEFj8rfNyLc/ANDROID---New?node-id=54390-26198")
@FigmaVariant("Placement", "Top")
@FigmaVariant("Severity", "Success")
class TopSuccessBannerConnection {
    @FigmaProperty(FigmaType.Boolean, "Has title")    val showTitle: Boolean = true
    @FigmaProperty(FigmaType.Text, "Title text")      val titleText: String = "Title"
    @FigmaProperty(FigmaType.Text, "Body text")       val bodyText: String = "Body"
    @FigmaProperty(FigmaType.Boolean, "Dismissible")  val showCancel: Boolean = true
    @FigmaProperty(FigmaType.Boolean, "Has action")   val showButton: Boolean = false
    @FigmaProperty(FigmaType.Text, "Action label")    val buttonText: String = "Action"

    @Composable
    fun TopSuccessBannerExample() {
        TopSuccessBanner(
            title = if (showTitle) titleText else null,
            body = bodyText,
            showCancelButton = showCancel,
            actionButtonText = if (showButton) buttonText else null,
        )
    }
}

@FigmaConnect(url = "https://www.figma.com/design/HagXfSTF94ICEFj8rfNyLc/ANDROID---New?node-id=54390-26198")
@FigmaVariant("Placement", "Top")
@FigmaVariant("Severity", "Warning")
class TopWarningBannerConnection {
    @FigmaProperty(FigmaType.Boolean, "Has title")    val showTitle: Boolean = true
    @FigmaProperty(FigmaType.Text, "Title text")      val titleText: String = "Title"
    @FigmaProperty(FigmaType.Text, "Body text")       val bodyText: String = "Body"
    @FigmaProperty(FigmaType.Boolean, "Dismissible")  val showCancel: Boolean = true
    @FigmaProperty(FigmaType.Boolean, "Has action")   val showButton: Boolean = false
    @FigmaProperty(FigmaType.Text, "Action label")    val buttonText: String = "Action"

    @Composable
    fun TopWarningBannerExample() {
        TopWarningBanner(
            title = if (showTitle) titleText else null,
            body = bodyText,
            showCancelButton = showCancel,
            actionButtonText = if (showButton) buttonText else null,
        )
    }
}

@FigmaConnect(url = "https://www.figma.com/design/HagXfSTF94ICEFj8rfNyLc/ANDROID---New?node-id=54390-26198")
@FigmaVariant("Placement", "Top")
@FigmaVariant("Severity", "Error")
class TopErrorBannerConnection {
    @FigmaProperty(FigmaType.Boolean, "Has title")    val showTitle: Boolean = true
    @FigmaProperty(FigmaType.Text, "Title text")      val titleText: String = "Title"
    @FigmaProperty(FigmaType.Text, "Body text")       val bodyText: String = "Body"
    @FigmaProperty(FigmaType.Boolean, "Dismissible")  val showCancel: Boolean = true
    @FigmaProperty(FigmaType.Boolean, "Has action")   val showButton: Boolean = false
    @FigmaProperty(FigmaType.Text, "Action label")    val buttonText: String = "Action"

    @Composable
    fun TopErrorBannerExample() {
        TopErrorBanner(
            title = if (showTitle) titleText else null,
            body = bodyText,
            showCancelButton = showCancel,
            actionButtonText = if (showButton) buttonText else null,
        )
    }
}
