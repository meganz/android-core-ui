package mega.android.core.ui.components

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
import androidx.compose.ui.unit.dp
import mega.android.core.ui.R
import mega.android.core.ui.preview.CombinedThemePreviews
import mega.android.core.ui.theme.AndroidThemeForPreviews
import mega.android.core.ui.theme.AppTheme
import mega.android.core.ui.theme.spacing.LocalSpacing

@Composable
fun SuccessFooter(modifier: Modifier = Modifier, text: String) {
    Footer(
        modifier = modifier,
        text = text,
        color = AppTheme.colors.text.success,
        iconResId = R.drawable.ic_check_circle
    )
}

@Composable
fun ErrorFooter(modifier: Modifier = Modifier, text: String) {
    Footer(
        modifier = modifier,
        text = text,
        color = AppTheme.colors.text.error,
        iconResId = R.drawable.ic_alert_triangle
    )
}

@Composable
private fun Footer(
    text: String,
    color: Color,
    @DrawableRes iconResId: Int,
    modifier: Modifier = Modifier,
) {
    Row(modifier = modifier) {
        Icon(
            modifier = Modifier
                .align(Alignment.CenterVertically)
                .padding(end = LocalSpacing.current.x12)
                .size(16.dp),
            painter = painterResource(id = iconResId),
            tint = color,
            contentDescription = text
        )
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.CenterVertically),
            text = text,
            style = AppTheme.typography.labelMedium,
            color = color
        )
    }
}

@CombinedThemePreviews
@Composable
fun ErrorFooterPreview() {
    AndroidThemeForPreviews {
        ErrorFooter(text = "Error footer text example")
    }
}

@CombinedThemePreviews
@Composable
fun SuccessFooterPreview() {
    AndroidThemeForPreviews {
        SuccessFooter(text = "Success footer text example")
    }
}