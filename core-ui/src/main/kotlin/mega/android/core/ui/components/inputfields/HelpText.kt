package mega.android.core.ui.components.inputfields

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import mega.android.core.ui.R
import mega.android.core.ui.preview.CombinedThemePreviews
import mega.android.core.ui.theme.AndroidThemeForPreviews
import mega.android.core.ui.theme.AppTheme
import mega.android.core.ui.theme.spacing.LocalSpacing
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
        iconColor = AppTheme.colors.text.success,
        textColor = textColor,
        textStyle = textStyle,
        iconResId = R.drawable.ic_check_circle
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
        iconColor = AppTheme.colors.text.error,
        textColor = textColor,
        textStyle = textStyle,
        iconResId = R.drawable.ic_alert_triangle
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
        iconColor = AppTheme.colors.text.warning,
        textColor = textColor,
        textStyle = textStyle,
        iconResId = R.drawable.ic_alert_circle
    )
}

@Composable
fun HelpTextInfo(
    text: String,
    modifier: Modifier = Modifier,
    textColor: TextColor = TextColor.Secondary,
    textStyle: TextStyle = AppTheme.typography.bodySmall,
) {
    HelpText(
        modifier = modifier,
        text = text,
        iconColor = AppTheme.colors.icon.secondary,
        textColor = textColor,
        textStyle = textStyle,
        iconResId = R.drawable.ic_info
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
) {
    Row(modifier = modifier) {
        Icon(
            modifier = Modifier
                .padding(top = LocalSpacing.current.x2, end = LocalSpacing.current.x8)
                .size(16.dp),
            painter = painterResource(id = iconResId),
            tint = iconColor,
            contentDescription = text
        )
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.CenterVertically),
            text = text,
            style = textStyle,
            color = AppTheme.textColor(textColor = textColor)
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