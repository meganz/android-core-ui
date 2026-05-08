/*
 * Code Connect mapping for the Figma `Empty state` component.
 *
 * Figma file : HagXfSTF94ICEFj8rfNyLc  (ANDROID - New)
 * Component  : Empty state  (node 58415:825) — note this is a single COMPONENT, not a
 *              COMPONENT_SET, so there are no variant axes.
 *
 * Compose side (mega.android.core.ui.components.state.EmptyStateView):
 *   EmptyStateView(
 *       imagePainter: Painter,
 *       title: String,
 *       description: SpannableText? = null,
 *       primaryAction: (@Composable () -> Unit)? = null,
 *       secondaryAction: (@Composable () -> Unit)? = null,
 *   )
 *
 * The previous `MegaEmptyView` / `MegaEmptyViewWithImage` Compose helpers were removed in
 * AND-23267 — the new `EmptyStateView` overload is the single supported entry point.
 *
 * Property names VERIFIED via MCP on 2026-05-08. Figma side gained `Has primary action`
 * (renamed from `Has button`) and `Has secondary action`. The `Has illustration` boolean
 * and `Graphics` SLOT are intentionally NOT mapped — Compose's `imagePainter: Painter`
 * parameter is required and the painter is supplied by the caller, so neither hiding the
 * image nor swapping it from a Figma slot maps cleanly to the current Compose API.
 */

package mega.android.core.ui.components.empty

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.figma.code.connect.FigmaConnect
import com.figma.code.connect.FigmaProperty
import com.figma.code.connect.FigmaType
import mega.android.core.ui.R
import mega.android.core.ui.components.button.PrimaryFilledButton
import mega.android.core.ui.components.button.SecondaryFilledButton
import mega.android.core.ui.components.state.EmptyStateView
import mega.android.core.ui.components.text.SpannableText

@FigmaConnect(url = "https://www.figma.com/design/HagXfSTF94ICEFj8rfNyLc/ANDROID---New?node-id=58415-825")
class EmptyStateViewConnection {
    @FigmaProperty(FigmaType.Text, "Title text")              val titleText: String = "Nothing here"
    @FigmaProperty(FigmaType.Text, "Message text")            val messageText: String = "Description"
    @FigmaProperty(FigmaType.Boolean, "Has message")          val showMessage: Boolean = true
    @FigmaProperty(FigmaType.Boolean, "Has primary action")   val showPrimary: Boolean = true
    @FigmaProperty(FigmaType.Boolean, "Has secondary action") val showSecondary: Boolean = false

    @Composable
    fun EmptyStateViewExample() {
        EmptyStateView(
            imagePainter = painterResource(id = R.drawable.ic_alert_circle_medium_thin_outline),
            title = titleText,
            description = if (showMessage) SpannableText(text = messageText) else null,
            primaryAction = if (showPrimary) {
                { PrimaryFilledButton(modifier = Modifier, text = "Primary", onClick = {}) }
            } else {
                null
            },
            secondaryAction = if (showSecondary) {
                { SecondaryFilledButton(modifier = Modifier, text = "Secondary", onClick = {}) }
            } else {
                null
            },
        )
    }
}
