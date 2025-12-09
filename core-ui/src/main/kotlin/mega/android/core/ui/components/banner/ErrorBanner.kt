package mega.android.core.ui.components.banner

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.font.FontStyle
import mega.android.core.ui.R
import mega.android.core.ui.components.text.SpannableText
import mega.android.core.ui.components.toolbar.ForceRiceTopAppBarEffect
import mega.android.core.ui.model.MegaSpanStyle
import mega.android.core.ui.model.SpanIndicator
import mega.android.core.ui.model.SpanStyleWithAnnotation
import mega.android.core.ui.preview.CombinedThemePreviews
import mega.android.core.ui.theme.AndroidThemeForPreviews
import mega.android.core.ui.tokens.theme.DSTokens

@Composable
fun InlineErrorBanner(
    body: SpannableText?,
    showCancelButton: Boolean,
    modifier: Modifier = Modifier,
    title: SpannableText? = null,
    actionButtonText: String? = null,
    onActionButtonClick: () -> Unit = {},
    onCancelButtonClick: () -> Unit = {},
) {
    ErrorBanner(
        backgroundShape = DSTokens.shapes.small,
        body = body,
        showCancelButton = showCancelButton,
        modifier = modifier,
        title = title,
        actionButtonText = actionButtonText,
        onActionButtonClick = onActionButtonClick,
        onCancelButtonClick = onCancelButtonClick
    )
}

@Composable
fun InlineErrorBanner(
    body: String?,
    showCancelButton: Boolean,
    modifier: Modifier = Modifier,
    title: String? = null,
    actionButtonText: String? = null,
    onActionButtonClick: () -> Unit = {},
    onCancelButtonClick: () -> Unit = {},
) {
    ErrorBanner(
        backgroundShape = DSTokens.shapes.small,
        body = body?.let { SpannableText(it) },
        showCancelButton = showCancelButton,
        modifier = modifier,
        title = title?.let { SpannableText(title) },
        actionButtonText = actionButtonText,
        onActionButtonClick = onActionButtonClick,
        onCancelButtonClick = onCancelButtonClick
    )
}

/**
 * @param forceRiceTopAppBar if true, the [ForceRiceTopAppBarEffect] will be applied, causing the top app bar to be raised while the banner is in the composition.
 * It should be true if the banner is sticky under the TopAppBar or TabRows
 */
@Composable
fun TopErrorBanner(
    body: SpannableText?,
    showCancelButton: Boolean,
    modifier: Modifier = Modifier,
    title: SpannableText? = null,
    actionButtonText: String? = null,
    forceRiceTopAppBar: Boolean = false,
    onActionButtonClick: () -> Unit = {},
    onCancelButtonClick: () -> Unit = {},
) {
    ErrorBanner(
        backgroundShape = RectangleShape,
        body = body,
        showCancelButton = showCancelButton,
        modifier = modifier,
        title = title,
        actionButtonText = actionButtonText,
        forceRiceTopAppBar = forceRiceTopAppBar,
        onActionButtonClick = onActionButtonClick,
        onCancelButtonClick = onCancelButtonClick
    )
}

/**
 * @param forceRiceTopAppBar if true, the [ForceRiceTopAppBarEffect] will be applied, causing the top app bar to be raised while the banner is in the composition.
 * It should be true if the banner is sticky under the TopAppBar or TabRows
 */
@Composable
fun TopErrorBanner(
    body: String?,
    showCancelButton: Boolean,
    modifier: Modifier = Modifier,
    title: String? = null,
    actionButtonText: String? = null,
    forceRiceTopAppBar: Boolean = false,
    onActionButtonClick: () -> Unit = {},
    onCancelButtonClick: () -> Unit = {},
) {
    ErrorBanner(
        backgroundShape = RectangleShape,
        body = body?.let { SpannableText(it) },
        showCancelButton = showCancelButton,
        modifier = modifier,
        title = title?.let { SpannableText(title) },
        actionButtonText = actionButtonText,
        forceRiceTopAppBar = forceRiceTopAppBar,
        onActionButtonClick = onActionButtonClick,
        onCancelButtonClick = onCancelButtonClick
    )
}

@Composable
private fun ErrorBanner(
    backgroundShape: Shape,
    body: SpannableText?,
    showCancelButton: Boolean,
    modifier: Modifier = Modifier,
    title: SpannableText? = null,
    actionButtonText: String? = null,
    forceRiceTopAppBar: Boolean = false,
    onActionButtonClick: () -> Unit = {},
    onCancelButtonClick: () -> Unit = {},
) {
    BaseBanner(
        backgroundColor = DSTokens.colors.notifications.notificationError,
        backgroundShape = backgroundShape,
        iconResId = R.drawable.ic_alert_triangle_medium_thin_outline,
        iconColor = DSTokens.colors.support.error,
        body = body,
        showCancelButton = showCancelButton,
        modifier = modifier,
        title = title,
        buttonText = actionButtonText,
        forceRiceTopAppBar = forceRiceTopAppBar,
        onButtonClick = onActionButtonClick,
        onCancelButtonClick = onCancelButtonClick
    )
}

@CombinedThemePreviews
@Composable
private fun InlineErrorBannerPreview() {
    AndroidThemeForPreviews {
        InlineErrorBanner(
            body = "Error Body",
            showCancelButton = true,
            title = "Error Title",
            actionButtonText = "Action Button"
        )
    }
}

@CombinedThemePreviews
@Composable
private fun TopErrorBannerPreview() {
    AndroidThemeForPreviews {
        TopErrorBanner(
            body = "Error Body",
            showCancelButton = true,
            title = "Error Title",
            actionButtonText = "Action Button"
        )
    }
}

@CombinedThemePreviews
@Composable
private fun SpannableInlineErrorBannerPreview() {
    AndroidThemeForPreviews {
        InlineErrorBanner(
            body = SpannableText(
                text = "[A]Banner[/A] Body",
                annotations = mapOf(
                    SpanIndicator('A') to SpanStyleWithAnnotation(
                        megaSpanStyle = MegaSpanStyle.DefaultColorStyle(
                            spanStyle = SpanStyle(fontStyle = FontStyle.Italic),
                        ),
                        annotation = "[A]Banner[/A] Body"
                            .substringAfter("[A]")
                            .substringBefore("[/A]")
                    )
                )
            ),
            title = SpannableText(
                text = "[A]Banner[/A] Title",
                annotations = mapOf(
                    SpanIndicator('A') to SpanStyleWithAnnotation(
                        megaSpanStyle = MegaSpanStyle.DefaultColorStyle(
                            spanStyle = SpanStyle(fontStyle = FontStyle.Italic),
                        ),
                        annotation = "[A]Banner[/A] Title"
                            .substringAfter("[A]")
                            .substringBefore("[/A]")
                    )
                )
            ),
            actionButtonText = "Action Button",
            showCancelButton = true
        )
    }
}