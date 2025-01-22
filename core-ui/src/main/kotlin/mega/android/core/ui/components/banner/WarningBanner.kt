package mega.android.core.ui.components.banner

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.style.TextDecoration
import mega.android.core.ui.R
import mega.android.core.ui.components.text.SpannableText
import mega.android.core.ui.model.MegaSpanStyle
import mega.android.core.ui.model.SpanIndicator
import mega.android.core.ui.model.SpanStyleWithAnnotation
import mega.android.core.ui.preview.CombinedThemePreviews
import mega.android.core.ui.theme.AndroidThemeForPreviews
import mega.android.core.ui.theme.AppTheme

@Composable
fun InlineWarningBanner(
    modifier: Modifier,
    body: String,
    showCancelButton: Boolean,
    title: String? = null,
    actionButtonText: String? = null,
    onActionButtonClick: () -> Unit = {},
    onCancelButtonClick: () -> Unit = {},
) {
    InlineWarningBanner(
        modifier = modifier,
        body = SpannableText(body),
        title = SpannableText(title),
        actionButtonText = actionButtonText,
        onActionButtonClick = onActionButtonClick,
        showCancelButton = showCancelButton,
        onCancelButtonClick = onCancelButtonClick
    )
}

@Composable
fun TopWarningBanner(
    modifier: Modifier,
    body: String,
    showCancelButton: Boolean,
    title: String? = null,
    actionButtonText: String? = null,
    onActionButtonClick: () -> Unit = {},
    onCancelButtonClick: () -> Unit = {},
) {
    TopWarningBanner(
        modifier = modifier,
        body = SpannableText(body),
        title = SpannableText(title),
        actionButtonText = actionButtonText,
        onActionButtonClick = onActionButtonClick,
        showCancelButton = showCancelButton,
        onCancelButtonClick = onCancelButtonClick
    )
}

@Composable
fun InlineWarningBanner(
    modifier: Modifier,
    body: SpannableText,
    showCancelButton: Boolean,
    title: SpannableText? = null,
    actionButtonText: String? = null,
    onActionButtonClick: () -> Unit = {},
    onCancelButtonClick: () -> Unit = {},
) {
    WarningBanner(
        modifier = modifier,
        backgroundShape = AppTheme.shapes.small,
        body = body,
        title = title,
        actionButtonText = actionButtonText,
        onActionButtonClick = onActionButtonClick,
        showCancelButton = showCancelButton,
        onCancelButtonClick = onCancelButtonClick
    )
}

@Composable
fun TopWarningBanner(
    modifier: Modifier,
    body: SpannableText,
    showCancelButton: Boolean,
    title: SpannableText? = null,
    actionButtonText: String? = null,
    onActionButtonClick: () -> Unit = {},
    onCancelButtonClick: () -> Unit = {},
) {
    WarningBanner(
        modifier = modifier,
        backgroundShape = RectangleShape,
        body = body,
        title = title,
        actionButtonText = actionButtonText,
        onActionButtonClick = onActionButtonClick,
        showCancelButton = showCancelButton,
        onCancelButtonClick = onCancelButtonClick
    )
}

@Composable
private fun WarningBanner(
    modifier: Modifier,
    backgroundShape: Shape,
    body: SpannableText,
    showCancelButton: Boolean,
    title: SpannableText? = null,
    actionButtonText: String? = null,
    onActionButtonClick: () -> Unit = {},
    onCancelButtonClick: () -> Unit = {},
) {
    BaseBanner(
        modifier = modifier,
        backgroundColor = AppTheme.colors.notifications.notificationWarning,
        backgroundShape = backgroundShape,
        iconResId = R.drawable.ic_alert_circle,
        iconColor = AppTheme.colors.support.warning,
        body = body,
        title = title,
        buttonText = actionButtonText,
        onButtonClick = onActionButtonClick,
        showCancelButton = showCancelButton,
        onCancelButtonClick = onCancelButtonClick
    )
}


@CombinedThemePreviews
@Composable
private fun InlineWarningBannerPreview() {
    AndroidThemeForPreviews {
        InlineWarningBanner(
            modifier = Modifier,
            body = SpannableText("Warning Body"),
            title = SpannableText("Warning Title"),
            actionButtonText = "Action Button",
            showCancelButton = true
        )
    }
}

@CombinedThemePreviews
@Composable
private fun TopWarningBannerPreview() {
    AndroidThemeForPreviews {
        TopWarningBanner(
            modifier = Modifier,
            body = SpannableText("Warning Body"),
            title = SpannableText("Warning Title"),
            actionButtonText = "Action Button",
            showCancelButton = true
        )
    }
}

@CombinedThemePreviews
@Composable
private fun SpannableWarningBannerPreview() {
    AndroidThemeForPreviews {
        InlineWarningBanner(
            modifier = Modifier,
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
