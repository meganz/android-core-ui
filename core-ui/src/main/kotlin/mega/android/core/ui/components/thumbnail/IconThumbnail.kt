package mega.android.core.ui.components.thumbnail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import mega.android.core.ui.R
import mega.android.core.ui.components.image.MegaIcon
import mega.android.core.ui.preview.CombinedThemePreviews
import mega.android.core.ui.theme.AndroidThemeForPreviews
import mega.android.core.ui.theme.values.IconColor
import mega.android.core.ui.tokens.theme.DSTokens

@Composable
fun IconThumbnail(
    modifier: Modifier = Modifier,
    content: @Composable BoxScope.() -> Unit
) {
    Box(
        modifier = modifier.background(color = DSTokens.colors.background.surface3),
        content = content
    )
}

@CombinedThemePreviews
@Composable
private fun IconThumbnailPreview() {
    AndroidThemeForPreviews {
        IconThumbnail(
            content = {
                MegaIcon(
                    painter = painterResource(id = R.drawable.ic_eye),
                    tint = IconColor.Primary
                )
            }
        )
    }
}
