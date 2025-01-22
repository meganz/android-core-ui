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
fun InlineSuccessBanner(
    modifier: Modifier,
    body: String,
    showCancelButton: Boolean,
    title: String? = null,
    actionButtonText: String? = null,
    onActionButtonClick: () -> Unit = {},
    onCancelButtonClick: () -> Unit = {},
) {
    SuccessBanner(
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
fun TopSuccessBanner(
    modifier: Modifier,
    body: String,
    showCancelButton: Boolean,
    title: String? = null,
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
fun SuccessBanner(
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
        backgroundColor = AppTheme.colors.notifications.notificationSuccess,
        backgroundShape = backgroundShape,
        iconResId = R.drawable.ic_check_circle,
        iconColor = AppTheme.colors.support.success,
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
