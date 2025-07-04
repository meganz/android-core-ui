package mega.android.core.ui.components.banner

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.font.FontStyle
import mega.android.core.ui.R
import mega.android.core.ui.components.text.SpannableText
import mega.android.core.ui.model.MegaSpanStyle
import mega.android.core.ui.model.SpanIndicator
import mega.android.core.ui.model.SpanStyleWithAnnotation
import mega.android.core.ui.preview.CombinedThemePreviews
import mega.android.core.ui.theme.AndroidThemeForPreviews
import mega.android.core.ui.tokens.theme.DSTokens

@Composable
fun InlineSuccessBanner(
    modifier: Modifier,
    body: SpannableText?,
    showCancelButton: Boolean,
    title: SpannableText? = null,
    actionButtonText: String? = null,
    onActionButtonClick: () -> Unit = {},
    onCancelButtonClick: () -> Unit = {},
) {
    SuccessBanner(
        modifier = modifier,
        backgroundShape = DSTokens.shapes.small,
        body = body,
        title = title,
        actionButtonText = actionButtonText,
        onActionButtonClick = onActionButtonClick,
        showCancelButton = showCancelButton,
        onCancelButtonClick = onCancelButtonClick
    )
}


@Composable
fun InlineSuccessBanner(
    modifier: Modifier,
    body: String?,
    showCancelButton: Boolean,
    title: String? = null,
    actionButtonText: String? = null,
    onActionButtonClick: () -> Unit = {},
    onCancelButtonClick: () -> Unit = {},
) {
    SuccessBanner(
        modifier = modifier,
        backgroundShape = DSTokens.shapes.small,
        body = body?.let { SpannableText(it) },
        title = title?.let { SpannableText(title) },
        actionButtonText = actionButtonText,
        onActionButtonClick = onActionButtonClick,
        showCancelButton = showCancelButton,
        onCancelButtonClick = onCancelButtonClick
    )
}

@Composable
fun TopSuccessBanner(
    modifier: Modifier,
    body: SpannableText?,
    showCancelButton: Boolean,
    title: SpannableText? = null,
    actionButtonText: String? = null,
    onActionButtonClick: () -> Unit = {},
    onCancelButtonClick: () -> Unit = {},
) {
    SuccessBanner(
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
fun TopSuccessBanner(
    modifier: Modifier,
    body: String?,
    showCancelButton: Boolean,
    title: String? = null,
    actionButtonText: String? = null,
    onActionButtonClick: () -> Unit = {},
    onCancelButtonClick: () -> Unit = {},
) {
    SuccessBanner(
        modifier = modifier,
        backgroundShape = RectangleShape,
        body = body?.let { SpannableText(it) },
        title = title?.let { SpannableText(title) },
        actionButtonText = actionButtonText,
        onActionButtonClick = onActionButtonClick,
        showCancelButton = showCancelButton,
        onCancelButtonClick = onCancelButtonClick
    )
}

@Composable
private fun SuccessBanner(
    modifier: Modifier,
    backgroundShape: Shape,
    body: SpannableText?,
    showCancelButton: Boolean,
    title: SpannableText? = null,
    actionButtonText: String? = null,
    onActionButtonClick: () -> Unit = {},
    onCancelButtonClick: () -> Unit = {},
) {
    BaseBanner(
        modifier = modifier,
        backgroundColor = DSTokens.colors.notifications.notificationSuccess,
        backgroundShape = backgroundShape,
        iconResId = R.drawable.ic_check_circle_medium_thin_outline,
        iconColor = DSTokens.colors.support.success,
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
private fun InlineSuccessBannerPreview() {
    AndroidThemeForPreviews {
        InlineSuccessBanner(
            modifier = Modifier,
            body = "Success Body",
            title = "Success Title",
            actionButtonText = "Action Button",
            showCancelButton = true
        )
    }
}

@CombinedThemePreviews
@Composable
private fun TopSuccessBannerPreview() {
    AndroidThemeForPreviews {
        TopSuccessBanner(
            modifier = Modifier,
            body = "Success Body",
            title = "Success Title",
            actionButtonText = "Action Button",
            showCancelButton = true
        )
    }
}

@CombinedThemePreviews
@Composable
private fun SpannableInlineSuccessBannerPreview() {
    AndroidThemeForPreviews {
        InlineSuccessBanner(
            modifier = Modifier,
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
