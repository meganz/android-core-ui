package mega.android.core.ui.components.inputfields

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import mega.android.core.ui.R
import mega.android.core.ui.components.LinkSpannedText
import mega.android.core.ui.model.MegaSpanStyle
import mega.android.core.ui.model.SpanIndicator
import mega.android.core.ui.model.SpanStyleWithAnnotation
import mega.android.core.ui.preview.CombinedThemePreviews
import mega.android.core.ui.theme.AndroidThemeForPreviews
import mega.android.core.ui.theme.AppTheme
import mega.android.core.ui.tokens.theme.DSTokens
import mega.android.core.ui.theme.spacing.LocalSpacing
import mega.android.core.ui.theme.values.LinkColor
import mega.android.core.ui.theme.values.TextColor

@Composable
fun HelpTextSuccess(
    text: String,
    modifier: Modifier = Modifier,
    textColor: TextColor = TextColor.Success,
    textStyle: TextStyle = AppTheme.typography.bodySmall,
) {
    HelpText(
        modifier = modifier,
        text = text,
        iconColor = DSTokens.colors.text.success,
        textColor = textColor,
        textStyle = textStyle,
        iconResId = R.drawable.ic_check_circle_medium_thin_outline
    )
}

@Composable
fun HelpTextError(
    text: String,
    modifier: Modifier = Modifier,
    textColor: TextColor = TextColor.Error,
    textStyle: TextStyle = AppTheme.typography.bodySmall,
) {
    HelpText(
        modifier = modifier,
        text = text,
        iconColor = DSTokens.colors.text.error,
        textColor = textColor,
        textStyle = textStyle,
        iconResId = R.drawable.ic_alert_triangle_medium_thin_outline
    )
}

@Composable
fun HelpTextWarning(
    text: String,
    modifier: Modifier = Modifier,
    textColor: TextColor = TextColor.Warning,
    textStyle: TextStyle = AppTheme.typography.bodySmall,
) {
    HelpText(
        modifier = modifier,
        text = text,
        iconColor = DSTokens.colors.text.warning,
        textColor = textColor,
        textStyle = textStyle,
        iconResId = R.drawable.ic_alert_circle_medium_thin_outline
    )
}

@Composable
fun HelpTextInfo(
    text: String,
    modifier: Modifier = Modifier,
    iconResId: Int = R.drawable.ic_info_medium_thin_outline,
    textColor: TextColor = TextColor.Secondary,
    textStyle: TextStyle = AppTheme.typography.bodySmall,
) {
    HelpText(
        modifier = modifier,
        text = text,
        iconColor = DSTokens.colors.icon.secondary,
        textColor = textColor,
        textStyle = textStyle,
        iconResId = iconResId
    )
}

@Composable
fun HelpTextLink(
    text: String,
    modifier: Modifier = Modifier,
    iconResId: Int = R.drawable.ic_help_circle_medium_thin_outline,
    textColor: TextColor = TextColor.Primary,
    textStyle: TextStyle = AppTheme.typography.bodySmall,
    onAnnotationClick: (String) -> Unit,
    spanStyles: Map<SpanIndicator, SpanStyleWithAnnotation> = emptyMap(),
) {
    HelpText(
        modifier = modifier,
        text = text,
        iconColor = DSTokens.colors.icon.primary,
        textColor = textColor,
        textStyle = textStyle,
        iconResId = iconResId,
        onAnnotationClick = onAnnotationClick,
        spanStyles = spanStyles
    )
}

@Composable
private fun HelpText(
    text: String,
    iconColor: Color,
    textColor: TextColor,
    textStyle: TextStyle,
    @DrawableRes iconResId: Int,
    modifier: Modifier = Modifier,
    onAnnotationClick: (String) -> Unit = {},
    spanStyles: Map<SpanIndicator, SpanStyleWithAnnotation> = emptyMap(),
) {
    Row(modifier = modifier) {
        Icon(
            modifier = Modifier
                .padding(top = LocalSpacing.current.x2, end = LocalSpacing.current.x8)
                .size(16.dp)
                .testTag(HELP_TEXT_ICON_TEST_TAG),
            painter = painterResource(id = iconResId),
            tint = iconColor,
            contentDescription = text
        )
        LinkSpannedText(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.CenterVertically)
                .testTag(HELP_TEXT_TEXT_TEST_TAG),
            value = text,
            baseStyle = textStyle,
            baseTextColor = textColor,
            spanStyles = spanStyles,
            onAnnotationClick = onAnnotationClick
        )
    }
}

@CombinedThemePreviews
@Composable
private fun HelpTextErrorPreview() {
    AndroidThemeForPreviews {
        HelpTextError(text = "Error footer text example")
    }
}

@CombinedThemePreviews
@Composable
private fun HelpTextSuccessPreview() {
    AndroidThemeForPreviews {
        HelpTextSuccess(text = "Success footer text example")
    }
}

@CombinedThemePreviews
@Composable
private fun HelpTextWarningPreview() {
    AndroidThemeForPreviews {
        HelpTextWarning(text = "Warning footer text example")
    }
}

@CombinedThemePreviews
@Composable
private fun HelpTextInfoPreview() {
    AndroidThemeForPreviews {
        HelpTextInfo(text = "Warning footer text example")
    }
}

@CombinedThemePreviews
@Composable
private fun HelpTextLinkPreview() {
    AndroidThemeForPreviews {
        HelpTextLink(
            text = "Enable 2FA or OTP on this site or app, then paste the key or scan the QR code. [A]What is this?[/A]",
            textStyle = AppTheme.typography.bodySmall,
            spanStyles = hashMapOf(
                SpanIndicator('A') to SpanStyleWithAnnotation(
                    megaSpanStyle = MegaSpanStyle.LinkColorStyle(
                        spanStyle = AppTheme.typography.bodySmall.toSpanStyle(),
                        linkColor = LinkColor.Primary,
                    ),
                    annotation = "Enable 2FA or OTP on this site or app, then paste the key or scan the QR code. [A]What is this?[/A]"
                        .substringAfter("[A]")
                        .substringBefore("[/A]")
                )
            ),
            onAnnotationClick = { /* do something */ }
        )
    }
}

internal const val HELP_TEXT_ICON_TEST_TAG = "help_text:icon"
internal const val HELP_TEXT_TEXT_TEST_TAG = "help_text:text"