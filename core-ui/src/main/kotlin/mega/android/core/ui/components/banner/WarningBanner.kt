package mega.android.core.ui.components.banner

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.style.TextDecoration
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
fun InlineWarningBanner(
    body: String?,
    showCancelButton: Boolean,
    modifier: Modifier = Modifier,
    title: String? = null,
    actionButtonText: String? = null,
    onActionButtonClick: () -> Unit = {},
    onCancelButtonClick: () -> Unit = {},
) {
    InlineWarningBanner(
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
fun TopWarningBanner(
    body: String?,
    showCancelButton: Boolean,
    modifier: Modifier = Modifier,
    title: String? = null,
    actionButtonText: String? = null,
    forceRiceTopAppBar: Boolean = false,
    onActionButtonClick: () -> Unit = {},
    onCancelButtonClick: () -> Unit = {},
) {
    TopWarningBanner(
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
fun InlineWarningBanner(
    body: SpannableText?,
    showCancelButton: Boolean,
    modifier: Modifier = Modifier,
    title: SpannableText? = null,
    actionButtonText: String? = null,
    onActionButtonClick: () -> Unit = {},
    onCancelButtonClick: () -> Unit = {},
) {
    WarningBanner(
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

/**
 * @param forceRiceTopAppBar if true, the [ForceRiceTopAppBarEffect] will be applied, causing the top app bar to be raised while the banner is in the composition.
 * It should be true if the banner is sticky under the TopAppBar or TabRows
 */
@Composable
fun TopWarningBanner(
    body: SpannableText?,
    showCancelButton: Boolean,
    modifier: Modifier = Modifier,
    title: SpannableText? = null,
    actionButtonText: String? = null,
    forceRiceTopAppBar: Boolean = false,
    onActionButtonClick: () -> Unit = {},
    onCancelButtonClick: () -> Unit = {},
) {
    WarningBanner(
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

@Composable
private fun WarningBanner(
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
        backgroundColor = DSTokens.colors.notifications.notificationWarning,
        backgroundShape = backgroundShape,
        iconResId = R.drawable.ic_alert_circle_medium_thin_outline,
        iconColor = DSTokens.colors.support.warning,
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
private fun InlineWarningBannerPreview() {
    AndroidThemeForPreviews {
        InlineWarningBanner(
            body = SpannableText("Warning Body"),
            showCancelButton = true,
            title = SpannableText("Warning Title"),
            actionButtonText = "Action Button"
        )
    }
}

@CombinedThemePreviews
@Composable
private fun TopWarningBannerPreview() {
    AndroidThemeForPreviews {
        TopWarningBanner(
            body = SpannableText("Warning Body"),
            showCancelButton = true,
            title = SpannableText("Warning Title"),
            actionButtonText = "Action Button"
        )
    }
}

@CombinedThemePreviews
@Composable
private fun TopWarningBannerWithoutBodyPreview() {
    AndroidThemeForPreviews {
        TopWarningBanner(
            body = null,
            showCancelButton = true,
            title = SpannableText("Warning Title"),
            actionButtonText = "Action Button"
        )
    }
}

@CombinedThemePreviews
@Composable
private fun SpannableWarningBannerPreview() {
    AndroidThemeForPreviews {
        InlineWarningBanner(
            title = SpannableText(
                text = "[A]Warning Title[/A] with spannable text",
                annotations = mapOf(
                    SpanIndicator('A') to SpanStyleWithAnnotation(
                        megaSpanStyle = MegaSpanStyle.DefaultColorStyle(
                            spanStyle = SpanStyle(
                                textDecoration = TextDecoration.Underline
                            ),
                        ),
                        annotation = "Warning Title"
                    ),
                ),
            ),
            body = SpannableText(
                text = "[A]Warning Body[/A] with spannable text",
                annotations = mapOf(
                    SpanIndicator('A') to SpanStyleWithAnnotation(
                        megaSpanStyle = MegaSpanStyle.DefaultColorStyle(
                            spanStyle = SpanStyle(
                                textDecoration = TextDecoration.Underline
                            ),
                        ),
                        annotation = "Warning Body"
                    ),
                ),
            ),
            actionButtonText = "Action Button",
            showCancelButton = true
        )
    }
}
