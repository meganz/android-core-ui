/*
 * Code Connect mappings for the Figma `Basic dialog` / `List dialog` / `Promotion dialog`
 * component sets.
 *
 * Figma file : HagXfSTF94ICEFj8rfNyLc  (ANDROID - New)
 * Components :
 *   - Basic dialog     (node 54390:26816)
 *       variants matched: Buttons = 1 | 2 (Side by Side) | 2 (Stacked | 3
 *       (Note: "2 (Stacked" has a TYPO in Figma — missing close paren — preserved verbatim.)
 *       Dark mode axis NOT matched (handled by AndroidTheme).
 *   - List dialog      (node TODO — MCP data not yet fetched)
 *   - Promotion dialog (node 56312:6376)
 *       variants matched: Style = Full image | Image | Illustration | Plain
 *       Device axis NOT matched (Compose is phone-only; Phone is the default).
 *
 * Figma properties mapped (Basic dialog):
 *   ✏️ Header        → headerText
 *   ✏️ Body          → bodyText
 *   Show description → showDescription (bound as `description = if (showDescription) bodyText else null`)
 *   Show text field  → routes to BasicInputDialog (not mapped here as separate variant)
 *   Divider          → IGNORED (Compose does not expose a divider toggle)
 *
 * Figma properties mapped (Promotion dialog):
 *   ✏️ Small title → smallTitle     (bound via `showSmallTitle`)
 *   ✏️ Headline    → headline
 *   ✏️ Body        → body           (bound via `showBody`)
 *   ✏️ Footer      → footer         (bound via `showFooter`)
 *   Show X button, Show buttons, Show secondary button, Show list, Show divider —
 *   not all flow cleanly into minimal examples; bound where meaningful.
 *
 * Compose side:
 *   - BasicDialog(title, description, positiveButtonText, …, buttonDirection)
 *   - BasicInputDialog(title, description, inputLabel, …)
 *   - BasicContactDialog / BasicImageDialog / BasicSpinnerDialog / PasswordInputDialog
 *     (kept as-is — separate Figma variants not yet confirmed)
 *   - BasicRadioDialog (List dialog)
 *   - PromotionalFullImageDialog / PromotionalImageDialog /
 *     PromotionalIllustrationDialog / PromotionalPlainDialog
 *
 * NOTE — this file requires the `com.figma.code.connect` Gradle plugin.
 *
 * Property names VERIFIED via MCP on 2026-04-23 for Basic dialog + Promotion dialog.
 * List dialog (BasicRadioDialog) node-id still TODO.
 */

package mega.android.core.ui.components.dialogs

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import com.figma.code.connect.FigmaConnect
import com.figma.code.connect.FigmaProperty
import com.figma.code.connect.FigmaType
import com.figma.code.connect.FigmaVariant
import kotlinx.collections.immutable.persistentListOf
import mega.android.core.ui.R
import mega.android.core.ui.components.text.SpannableText

// ─── Basic dialog ──────────────────────────────────────────────────────────

@FigmaConnect(url = "https://www.figma.com/design/HagXfSTF94ICEFj8rfNyLc/ANDROID---New?node-id=54390-26816")
@FigmaVariant("Buttons", "One")
class BasicDialogSingleButtonConnection {
    @FigmaProperty(FigmaType.Text, "Header text")        val headerText: String = "Title"
    @FigmaProperty(FigmaType.Text, "Body text")          val bodyText: String = "Description"
    @FigmaProperty(FigmaType.Boolean, "Has description") val showDescription: Boolean = true

    @Composable
    fun BasicDialogSingleButtonExample() {
        BasicDialog(
            title = headerText,
            description = if (showDescription) bodyText else null,
            positiveButtonText = "OK",
            onPositiveButtonClicked = {},
            negativeButtonText = null,
            onNegativeButtonClicked = {},
        )
    }
}

@FigmaConnect(url = "https://www.figma.com/design/HagXfSTF94ICEFj8rfNyLc/ANDROID---New?node-id=54390-26816")
@FigmaVariant("Buttons", "Two (Side by Side)")
class BasicDialogSideBySideConnection {
    @FigmaProperty(FigmaType.Text, "Header text")        val headerText: String = "Title"
    @FigmaProperty(FigmaType.Text, "Body text")          val bodyText: String = "Description"
    @FigmaProperty(FigmaType.Boolean, "Has description") val showDescription: Boolean = true

    @Composable
    fun BasicDialogSideBySideExample() {
        BasicDialog(
            title = headerText,
            description = if (showDescription) bodyText else null,
            positiveButtonText = "OK",
            onPositiveButtonClicked = {},
            negativeButtonText = "Cancel",
            onNegativeButtonClicked = {},
        )
    }
}

@FigmaConnect(url = "https://www.figma.com/design/HagXfSTF94ICEFj8rfNyLc/ANDROID---New?node-id=54390-26816")
@FigmaVariant("Buttons", "Two (Stacked)")
class BasicDialogStackedConnection {
    @FigmaProperty(FigmaType.Text, "Header text")        val headerText: String = "Title"
    @FigmaProperty(FigmaType.Text, "Body text")          val bodyText: String = "Description"
    @FigmaProperty(FigmaType.Boolean, "Has description") val showDescription: Boolean = true

    @Composable
    fun BasicDialogStackedExample() {
        BasicDialog(
            title = headerText,
            description = if (showDescription) bodyText else null,
            positiveButtonText = "OK",
            onPositiveButtonClicked = {},
            negativeButtonText = "Cancel",
            onNegativeButtonClicked = {},
            // TODO: stacked layout — BasicDialog exposes buttonDirection; verify parameter name
        )
    }
}

@FigmaConnect(url = "https://www.figma.com/design/HagXfSTF94ICEFj8rfNyLc/ANDROID---New?node-id=54390-26816")
@FigmaVariant("Buttons", "Three")
class BasicDialogThreeButtonConnection {
    @FigmaProperty(FigmaType.Text, "Header text")        val headerText: String = "Title"
    @FigmaProperty(FigmaType.Text, "Body text")          val bodyText: String = "Description"
    @FigmaProperty(FigmaType.Boolean, "Has description") val showDescription: Boolean = true

    @Composable
    fun BasicDialogThreeButtonExample() {
        // TODO: BasicDialog currently supports 2 buttons; 3-button variant needs Compose API work.
        BasicDialog(
            title = headerText,
            description = if (showDescription) bodyText else null,
            positiveButtonText = "OK",
            onPositiveButtonClicked = {},
            negativeButtonText = "Cancel",
            onNegativeButtonClicked = {},
        )
    }
}

// The following Basic-dialog-adjacent variants (Contact / Image / Spinner / Password input)
// are kept as-is: distinct Figma variants were not confirmed in the MCP fetch. They
// currently use the Basic dialog node URL; feature teams should treat them as drafts.

@FigmaConnect(url = "https://www.figma.com/design/HagXfSTF94ICEFj8rfNyLc/ANDROID---New?node-id=54390-26816")
class BasicContactDialogConnection {
    @Composable
    fun BasicContactDialogExample() {
        BasicContactDialog(
            title = "Share with contact",
            description = "Description",
            contactName = "FirstName LastName",
            contactEmail = "name@example.com",
            positiveButtonText = "Share",
            onPositiveButtonClicked = {},
            negativeButtonText = "Cancel",
            onNegativeButtonClicked = {},
        )
    }
}

@FigmaConnect(url = "https://www.figma.com/design/HagXfSTF94ICEFj8rfNyLc/ANDROID---New?node-id=54390-26816")
class BasicImageDialogConnection {
    @Composable
    fun BasicImageDialogExample() {
        BasicImageDialog(
            title = "Title",
            imagePainter = painterResource(R.drawable.ic_alert_circle_medium_thin_outline),
            description = "Description",
            positiveButtonText = "OK",
            onPositiveButtonClicked = {},
            negativeButtonText = "Cancel",
            onNegativeButtonClicked = {},
        )
    }
}

@FigmaConnect(url = "https://www.figma.com/design/HagXfSTF94ICEFj8rfNyLc/ANDROID---New?node-id=54390-26816")
class PasswordInputDialogConnection {
    @Composable
    fun PasswordInputDialogExample() {
        PasswordInputDialog(
            title = "Enter password",
            description = "Description",
            onValueChange = {},
            positiveButtonText = "Confirm",
            onPositiveButtonClicked = {},
            negativeButtonText = "Cancel",
            onNegativeButtonClicked = {},
        )
    }
}

@FigmaConnect(url = "https://www.figma.com/design/HagXfSTF94ICEFj8rfNyLc/ANDROID---New?node-id=54390-26816")
class BasicSpinnerDialogConnection {
    @Composable
    fun BasicSpinnerDialogExample() {
        BasicSpinnerDialog(
            contentText = "Loading...",
        )
    }
}

// ─── List dialog ───────────────────────────────────────────────────────────
//
// Figma `List dialog` (node 63682:1864) has variants Buttons (1 | 2) plus boolean
// Divider / Show description / ✏️ Header / ✏️ Body properties. Compose
// BasicRadioDialog covers this by accepting a `buttons` list of any length — no
// variant split needed.

@FigmaConnect(url = "https://www.figma.com/design/HagXfSTF94ICEFj8rfNyLc/ANDROID---New?node-id=63682-1864")
class BasicRadioDialogConnection {
    @FigmaProperty(FigmaType.Text, "Header text")         val headerText: String = "Pick an option"
    @FigmaProperty(FigmaType.Text, "Body text")           val bodyText: String = "Description"
    @FigmaProperty(FigmaType.Boolean, "Has description")  val showDescription: Boolean = true

    @Composable
    fun BasicRadioDialogExample() {
        BasicRadioDialog(
            title = SpannableText(headerText),
            subtitle = if (showDescription) SpannableText(bodyText) else null,
            options = persistentListOf(),
            buttons = persistentListOf(),
            onDismissRequest = {},
            onOptionSelected = {},
        )
    }
}

// ─── Promotion dialog ──────────────────────────────────────────────────────

@FigmaConnect(url = "https://www.figma.com/design/HagXfSTF94ICEFj8rfNyLc/ANDROID---New?node-id=56312-6376")
@FigmaVariant("Style", "Full image")
class PromotionalFullImageDialogConnection {
    @FigmaProperty(FigmaType.Text, "Small title text") val smallTitle: String = "Promo"
    @FigmaProperty(FigmaType.Text, "Headline text")    val headline: String = "Headline"
    @FigmaProperty(FigmaType.Text, "Body text")        val body: String = "Body"
    @FigmaProperty(FigmaType.Text, "Footer text")      val footer: String = "Footer"
    @FigmaProperty(FigmaType.Boolean, "Has small title") val showSmallTitle: Boolean = true
    @FigmaProperty(FigmaType.Boolean, "Has body")      val showBody: Boolean = true
    @FigmaProperty(FigmaType.Boolean, "Has footer")    val showFooter: Boolean = true

    @Composable
    fun PromotionalFullImageDialogExample() {
        PromotionalFullImageDialog(
            image = null,
            title = smallTitle,
            headline = headline,
            onDismissRequest = {},
        )
    }
}

@FigmaConnect(url = "https://www.figma.com/design/HagXfSTF94ICEFj8rfNyLc/ANDROID---New?node-id=56312-6376")
@FigmaVariant("Style", "Image")
class PromotionalImageDialogConnection {
    @FigmaProperty(FigmaType.Text, "Small title text") val smallTitle: String = "Promo"
    @FigmaProperty(FigmaType.Text, "Headline text")    val headline: String = "Headline"
    @FigmaProperty(FigmaType.Text, "Body text")        val body: String = "Body"
    @FigmaProperty(FigmaType.Text, "Footer text")      val footer: String = "Footer"
    @FigmaProperty(FigmaType.Boolean, "Has small title") val showSmallTitle: Boolean = true
    @FigmaProperty(FigmaType.Boolean, "Has body")      val showBody: Boolean = true
    @FigmaProperty(FigmaType.Boolean, "Has footer")    val showFooter: Boolean = true

    @Composable
    fun PromotionalImageDialogExample() {
        PromotionalImageDialog(
            image = null,
            title = smallTitle,
            headline = headline,
            onDismissRequest = {},
        )
    }
}

@FigmaConnect(url = "https://www.figma.com/design/HagXfSTF94ICEFj8rfNyLc/ANDROID---New?node-id=56312-6376")
@FigmaVariant("Style", "Illustration")
class PromotionalIllustrationDialogConnection {
    @FigmaProperty(FigmaType.Text, "Small title text") val smallTitle: String = "Promo"
    @FigmaProperty(FigmaType.Text, "Headline text")    val headline: String = "Headline"
    @FigmaProperty(FigmaType.Text, "Body text")        val body: String = "Body"
    @FigmaProperty(FigmaType.Text, "Footer text")      val footer: String = "Footer"
    @FigmaProperty(FigmaType.Boolean, "Has small title") val showSmallTitle: Boolean = true
    @FigmaProperty(FigmaType.Boolean, "Has body")      val showBody: Boolean = true
    @FigmaProperty(FigmaType.Boolean, "Has footer")    val showFooter: Boolean = true

    @Composable
    fun PromotionalIllustrationDialogExample() {
        PromotionalIllustrationDialog(
            title = smallTitle,
            headline = headline,
            onDismissRequest = {},
        )
    }
}

@FigmaConnect(url = "https://www.figma.com/design/HagXfSTF94ICEFj8rfNyLc/ANDROID---New?node-id=56312-6376")
@FigmaVariant("Style", "Plain")
class PromotionalPlainDialogConnection {
    @FigmaProperty(FigmaType.Text, "Small title text") val smallTitle: String = "Promo"
    @FigmaProperty(FigmaType.Text, "Headline text")    val headline: String = "Headline"
    @FigmaProperty(FigmaType.Text, "Body text")        val body: String = "Body"
    @FigmaProperty(FigmaType.Text, "Footer text")      val footer: String = "Footer"
    @FigmaProperty(FigmaType.Boolean, "Has small title") val showSmallTitle: Boolean = true
    @FigmaProperty(FigmaType.Boolean, "Has body")      val showBody: Boolean = true
    @FigmaProperty(FigmaType.Boolean, "Has footer")    val showFooter: Boolean = true

    @Composable
    fun PromotionalPlainDialogExample() {
        PromotionalPlainDialog(
            title = smallTitle,
            headline = headline,
            onDismissRequest = {},
        )
    }
}
