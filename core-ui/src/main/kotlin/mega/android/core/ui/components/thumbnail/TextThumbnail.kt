package mega.android.core.ui.components.thumbnail

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import mega.android.core.ui.components.MegaText
import mega.android.core.ui.preview.CombinedThemePreviews
import mega.android.core.ui.theme.AndroidThemeForPreviews
import mega.android.core.ui.theme.AppTheme
import mega.android.core.ui.theme.spacing.LocalSpacing
import mega.android.core.ui.theme.tokens.TextColor


val numberOfTextThumbnailBackgrounds = 3

@Composable
private fun getTextThumbnailBackgroundColors(): Map<Int, Color> {
    return mapOf(
        1 to AppTheme.colors.indicator.green,
        2 to AppTheme.colors.indicator.blue,
        3 to AppTheme.colors.indicator.yellow
    )
}

@Composable
fun TextThumbnail(
    modifier: Modifier,
    text: String,
    numberOfCharacters: Int,
    randomInt: Int,
    shape: Shape = AppTheme.shapes.extraSmall
) {
    val backgroundColor =
        getTextThumbnailBackgroundColors()[randomInt] ?: AppTheme.colors.indicator.yellow
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