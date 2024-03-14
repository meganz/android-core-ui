package mega.android.core.ui.components.thumbnail

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import mega.android.core.ui.components.MegaText
import mega.android.core.ui.preview.CombinedThemePreviews
import mega.android.core.ui.theme.AndroidThemeForPreviews
import mega.android.core.ui.theme.AppTheme
import mega.android.core.ui.theme.spacing.LocalSpacing
import mega.android.core.ui.theme.tokens.TextColor

@Composable
fun TextThumbnail(
    modifier: Modifier,
    text: String,
    numberOfCharacters: Int,
    randomInt: Int,
    shape: Shape = AppTheme.shapes.extraSmall
) {
    val backgroundColor = when (randomInt) {
        1 -> AppTheme.colors.indicator.yellow
        2 -> AppTheme.colors.indicator.green
        3 -> AppTheme.colors.indicator.blue
        else -> AppTheme.colors.indicator.blue
    }
    val chars = text.trim().take(numberOfCharacters)
    Box(
        modifier = modifier.background(
            color = if (chars.isEmpty()) AppTheme.colors.background.surface3 else backgroundColor,
            shape = shape
        )
    ) {
        MegaText(
            modifier = Modifier.align(Alignment.Center),
            text = chars,
            textColor = if (isSystemInDarkTheme()) TextColor.Inverse else TextColor.Primary,
            style = AppTheme.typography.titleSmall
        )
    }
}

@Composable
@CombinedThemePreviews
private fun TextThumbnailPreview() {
    AndroidThemeForPreviews {
        TextThumbnail(
            modifier = Modifier.size(LocalSpacing.current.x32),
            text = "Hello",
            numberOfCharacters = 2,
            randomInt = 1
        )
    }
}