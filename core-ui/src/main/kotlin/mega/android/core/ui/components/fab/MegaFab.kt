package mega.android.core.ui.components.fab

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.FloatingActionButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import mega.android.core.ui.preview.CombinedThemePreviews
import mega.android.core.ui.theme.AndroidThemeForPreviews
import mega.android.core.ui.theme.spacing.LocalSpacing
import mega.android.core.ui.tokens.theme.DSTokens
import mega.android.core.ui.R
import mega.android.core.ui.components.image.MegaIcon
import mega.android.core.ui.theme.values.IconColor

@Composable
fun MegaFab(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    painter: Painter,
) {
    FloatingActionButton(
        modifier = modifier,
        shape = RoundedCornerShape(LocalSpacing.current.x8),
        containerColor = DSTokens.colors.button.primary,
        contentColor = DSTokens.colors.icon.inverseAccent,
        onClick = onClick,
        content = {
            MegaIcon(
                painter = painter,
                tint = IconColor.InverseAccent
            )
        }
    )
}

@CombinedThemePreviews
@Composable
fun MegaFabPreview() {
    AndroidThemeForPreviews {
        MegaFab(
            onClick = {},
            painter = painterResource(R.drawable.ic_arrow_left_medium_thin_outline),
        )
    }
}