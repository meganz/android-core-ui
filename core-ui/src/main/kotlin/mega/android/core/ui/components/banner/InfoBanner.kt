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
import mega.android.core.ui.theme.AppTheme

@Composable
fun InlineInfoBanner(
    modifier: Modifier,
    body: SpannableText,
    showCancelButton: Boolean,
    title: SpannableText? = null,
    actionButtonText: String? = null,
    onActionButtonClick: () -> Unit = {},
    onCancelButtonClick: () -> Unit = {},
) {
    InfoBanner(
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
fun InlineInfoBanner(
    modifier: Modifier,
    body: String,
    showCancelButton: Boolean,
    title: String? = null,
    actionButtonText: String? = null,
    onActionButtonClick: () -> Unit = {},
    onCancelButtonClick: () -> Unit = {},
) {
    InfoBanner(
        modifier = modifier,
        backgroundShape = AppTheme.shapes.small,
        body = SpannableText(body),
        title = SpannableText(title),
        actionButtonText = actionButtonText,
        onActionButtonClick = onActionButtonClick,
        showCancelButton = showCancelButton,
        onCancelButtonClick = onCancelButtonClick
    )
}

@Composable
fun TopInfoBanner(
    modifier: Modifier,
    body: SpannableText,
    showCancelButton: Boolean,
    title: SpannableText? = null,
    actionButtonText: String? = null,
    onActionButtonClick: () -> Unit = {},
    onCancelButtonClick: () -> Unit = {},
) {
    InfoBanner(
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
fun TopInfoBanner(
    modifier: Modifier,
    body: String,
    showCancelButton: Boolean,
    title: String? = null,
    actionButtonText: String? = null,
    onActionButtonClick: () -> Unit = {},
    onCancelButtonClick: () -> Unit = {},
) {
    InfoBanner(
        modifier = modifier,
        backgroundShape = RectangleShape,
        body = SpannableText(body),
        title = SpannableText(title),
        actionButtonText = actionButtonText,
        onActionButtonClick = onActionButtonClick,
        showCancelButton = showCancelButton,
        onCancelButtonClick = onCancelButtonClick
    )
}

@Composable
private fun InfoBanner(
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
        backgroundColor = AppTheme.colors.notifications.notificationInfo,
        backgroundShape = backgroundShape,
        iconResId = R.drawable.ic_info,
        iconColor = AppTheme.colors.support.info,
        body = body,
        title =     title,
        buttonText = actionButtonText,
        onButtonClick = onActionButtonClick,
        showCancelButton = showCancelButton,
        onCancelButtonClick = onCancelButtonClick
    )
}

@CombinedThemePreviews
@Composable
private fun TopInfoBannerPreview() {
    AndroidThemeForPreviews {
        TopInfoBanner(
            modifier = Modifier,
            body = "Info Body",
            title = "Info Title",
            actionButtonText = "Action Button",
            showCancelButton = true
        )
    }
}

@CombinedThemePreviews
@Composable
private fun InlineInfoBannerPreview() {
    AndroidThemeForPreviews {
        InlineInfoBanner(
            modifier = Modifier,
            body = "Info Body",
            title = "Info Title",
            actionButtonText = "Action Button",
            showCancelButton = false
        )
    }
}

@CombinedThemePreviews
@Composable
private fun SpannableInlineInfoBannerPreview() {
    AndroidThemeForPreviews {
        InlineInfoBanner(
            modifier = Modifier,
            body = SpannableText(
                text = "[A]Banner[/A] Body",
                annotations =  mapOf(
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
                annotations =  mapOf(
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
