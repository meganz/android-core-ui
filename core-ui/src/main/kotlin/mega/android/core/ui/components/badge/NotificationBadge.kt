package mega.android.core.ui.components.badge

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import mega.android.core.ui.R
import mega.android.core.ui.components.MegaText
import mega.android.core.ui.components.image.MegaIcon
import mega.android.core.ui.preview.BooleanProvider
import mega.android.core.ui.preview.CombinedThemePreviews
import mega.android.core.ui.theme.AndroidThemeForPreviews
import mega.android.core.ui.theme.AppTheme
import mega.android.core.ui.theme.values.IconColor
import mega.android.core.ui.theme.values.TextColor
import mega.android.core.ui.tokens.theme.DSTokens

@Composable
fun NotificationBadge(
    imageVector: ImageVector,
    modifier: Modifier = Modifier,
    small: Boolean = false
) {
    MegaIcon(
        imageVector,
        tint = IconColor.InverseAccent,
        modifier = modifier
            .size(if (small) 10.dp else 16.dp)
            .background(DSTokens.colors.components.interactive, shape = CircleShape)
            .padding(if (small) 1.875.dp else 3.dp)
    )
}

@Composable
fun NotificationBadge(
    count: Int,
    modifier: Modifier = Modifier,
    max: Int = 99,
) {
    val text = if (count > max) "$max+" else count.toString()
    NotificationBadge(text, modifier)
}

@Composable
fun NotificationBadge(
    text: String,
    modifier: Modifier = Modifier,
) {
    MegaText(
        text,
        textColor = TextColor.Inverse,
        textAlign = TextAlign.Center,
        style = AppTheme.typography.bodySmall,
        modifier = modifier
            .sizeIn(16.dp)
            .background(DSTokens.colors.components.interactive, shape = CircleShape)
            .padding(horizontal = 5.dp)
    )
}



@Composable
fun NotificationBadge(
    modifier: Modifier = Modifier
) = Box(
    modifier = modifier
        .size(6.dp)
        .background(DSTokens.colors.components.interactive, shape = CircleShape)
)

@Composable
@CombinedThemePreviews
fun NotificationBadgeIconPreview(
    @PreviewParameter(BooleanProvider::class) small: Boolean
) {
    AndroidThemeForPreviews {
        NotificationBadge(
            imageVector = ImageVector.vectorResource(R.drawable.ic_eye_medium_thin_outline),
            small = small,
        )
    }
}

@Composable
@CombinedThemePreviews
fun NotificationBadgeNoNumberPreview() {
    AndroidThemeForPreviews {
        NotificationBadge()
    }
}

@Composable
@CombinedThemePreviews
fun NotificationBadgeNumberPreview(
    @PreviewParameter(NumberPreviewParameterProvider::class) count: Int
) {
    AndroidThemeForPreviews {
        NotificationBadge(count)
    }
}

@Composable
@CombinedThemePreviews
fun NotificationBadgeTextPreview() {
    AndroidThemeForPreviews {
        NotificationBadge("foo")
    }
}

private class NumberPreviewParameterProvider : PreviewParameterProvider<Int> {
    override val values = sequenceOf(0, 1, 10, 55, 99, 100)

}