package mega.android.core.ui.components.banner

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import mega.android.core.ui.R
import mega.android.core.ui.components.text.SpannableText
import mega.android.core.ui.preview.CombinedThemePreviews
import mega.android.core.ui.theme.AndroidThemeForPreviews
import mega.android.core.ui.theme.AppTheme

@Composable
fun InlineErrorBanner(
    modifier: Modifier,
    body: String,
    showCancelButton: Boolean,
    title: String? = null,
    actionButtonText: String? = null,
    onActionButtonClick: () -> Unit = {},
    onCancelButtonClick: () -> Unit = {},
) {
    ErrorBanner(
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
fun TopErrorBanner(
    modifier: Modifier,
    body: String,
    showCancelButton: Boolean,
    title: String? = null,
    actionButtonText: String? = null,
    onActionButtonClick: () -> Unit = {},
    onCancelButtonClick: () -> Unit = {},
) {
    ErrorBanner(
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
private fun ErrorBanner(
    modifier: Modifier,
    backgroundShape: Shape,
    body: String,
    showCancelButton: Boolean,
    title: String? = null,
    actionButtonText: String? = null,
    onActionButtonClick: () -> Unit = {},
    onCancelButtonClick: () -> Unit = {},
) {
    BaseBanner(
        modifier = modifier,
        backgroundColor = AppTheme.colors.notifications.notificationError,
        backgroundShape = backgroundShape,
        iconResId = R.drawable.ic_alert_triangle,
        iconColor = AppTheme.colors.support.error,
        body = SpannableText(body),
        title = SpannableText(title),
        buttonText = actionButtonText,
        onButtonClick = onActionButtonClick,
        showCancelButton = showCancelButton,
        onCancelButtonClick = onCancelButtonClick
    )
}

@CombinedThemePreviews
@Composable
private fun InlineErrorBannerPreview() {
    AndroidThemeForPreviews {
        InlineErrorBanner(
            modifier = Modifier,
            body = "Error Body",
            title = "Error Title",
            actionButtonText = "Action Button",
            showCancelButton = true
        )
    }
}

@CombinedThemePreviews
@Composable
private fun TopErrorBannerPreview() {
    AndroidThemeForPreviews {
        TopErrorBanner(
            modifier = Modifier,
            body = "Error Body",
            title = "Error Title",
            actionButtonText = "Action Button",
            showCancelButton = true
        )
    }
}