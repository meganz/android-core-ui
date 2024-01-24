package mega.android.core.ui.components.banner

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import mega.android.core.ui.R
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
    body: String,
    showCancelButton: Boolean,
    title: String? = null,
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
    body: String,
    showCancelButton: Boolean,
    title: String? = null,
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
            body = "Warning Body",
            title = "Warning Title",
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
            body = "Warning Body",
            title = "Warning Title",
            actionButtonText = "Action Button",
            showCancelButton = true
        )
    }
}
