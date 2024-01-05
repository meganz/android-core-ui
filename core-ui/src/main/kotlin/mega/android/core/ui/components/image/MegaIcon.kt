package mega.android.core.ui.components.image

import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import mega.android.core.ui.R
import mega.android.core.ui.preview.CombinedThemePreviews
import mega.android.core.ui.theme.AndroidThemeForPreviews
import mega.android.core.ui.theme.AppTheme
import mega.android.core.ui.theme.tokens.IconColor
import mega.android.core.ui.theme.tokens.SupportColor

@Composable
fun MegaIcon(
    painter: Painter,
    tint: IconColor,
    modifier: Modifier = Modifier,
    contentDescription: String? = null,
) {
    Icon(
        painter = painter,
        tint = AppTheme.iconColor(iconColor = tint),
        contentDescription = contentDescription,
        modifier = modifier
    )
}

@Composable
fun MegaIcon(
    painter: Painter,
    tint: SupportColor,
    modifier: Modifier = Modifier,
    contentDescription: String? = null,
) {
    Icon(
        painter = painter,
        tint = AppTheme.supportColor(supportColor = tint),
        contentDescription = contentDescription,
        modifier = modifier
    )
}

@Composable
@CombinedThemePreviews
private fun MegaIconPreview() {
    AndroidThemeForPreviews {
        MegaIcon(
            painter = painterResource(id = R.drawable.ic_close),
            tint = IconColor.Primary
        )
    }
}

@Composable
@CombinedThemePreviews
private fun MegaIconPreviewDark() {
    AndroidThemeForPreviews {
        MegaIcon(
            painter = painterResource(id = R.drawable.ic_close),
            tint = SupportColor.Success
        )
    }
}
