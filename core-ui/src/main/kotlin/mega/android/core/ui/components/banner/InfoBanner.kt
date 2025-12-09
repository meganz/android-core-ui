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
fun InlineInfoBanner(
    body: SpannableText?,
    showCancelButton: Boolean,
    modifier: Modifier = Modifier,
    title: SpannableText? = null,
    actionButtonText: String? = null,
    onActionButtonClick: () -> Unit = {},
    onCancelButtonClick: () -> Unit = {},
) {
    InfoBanner(
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
fun InlineInfoBanner(
    body: String?,
    showCancelButton: Boolean,
    modifier: Modifier = Modifier,
    title: String? = null,
    actionButtonText: String? = null,
    onActionButtonClick: () -> Unit = {},
    onCancelButtonClick: () -> Unit = {},
) {
    InfoBanner(
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
fun TopInfoBanner(
    body: SpannableText?,
    showCancelButton: Boolean,
    modifier: Modifier = Modifier,
    title: SpannableText? = null,
    actionButtonText: String? = null,
    forceRiceTopAppBar: Boolean = false,
    onActionButtonClick: () -> Unit = {},
    onCancelButtonClick: () -> Unit = {},
) {
    InfoBanner(
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
fun TopInfoBanner(
    body: String?,
    showCancelButton: Boolean,
    modifier: Modifier = Modifier,
    title: String? = null,
    actionButtonText: String? = null,
    forceRiceTopAppBar: Boolean = false,
    onActionButtonClick: () -> Unit = {},
    onCancelButtonClick: () -> Unit = {},
) {
    InfoBanner(
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
private fun InfoBanner(
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
        backgroundColor = DSTokens.colors.notifications.notificationInfo,
        backgroundShape = backgroundShape,
        iconResId = R.drawable.ic_info_medium_thin_outline,
        iconColor = DSTokens.colors.support.info,
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
private fun TopInfoBannerPreview() {
    AndroidThemeForPreviews {
        TopInfoBanner(
            body = "Info Body",
            showCancelButton = true,
            title = "Info Title",
            actionButtonText = "Action Button"
        )
    }
}

@CombinedThemePreviews
@Composable
private fun InlineInfoBannerPreview() {
    AndroidThemeForPreviews {
        InlineInfoBanner(
            body = "Info Body",
            showCancelButton = false,
            title = "Info Title",
            actionButtonText = "Action Button"
        )
    }
}

@CombinedThemePreviews
@Composable
private fun SpannableInlineInfoBannerPreview() {
    AndroidThemeForPreviews {
        InlineInfoBanner(
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
