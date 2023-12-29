package mega.android.core.ui.components.image

import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import mega.android.core.ui.theme.AppTheme
import mega.android.core.ui.theme.tokens.IconColor

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