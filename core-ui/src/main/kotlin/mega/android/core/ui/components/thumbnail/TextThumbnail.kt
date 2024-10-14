package mega.android.core.ui.components.thumbnail

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.isSpecified
import mega.android.core.ui.components.MegaText
import mega.android.core.ui.preview.CombinedThemePreviews
import mega.android.core.ui.theme.AndroidThemeForPreviews
import mega.android.core.ui.theme.AppTheme
import mega.android.core.ui.theme.spacing.LocalSpacing
import mega.android.core.ui.theme.values.TextColor


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
    val chars = text.trim().onlyTake(numberOfCharacters)

    val defaultTextStyle = AppTheme.typography.titleSmall
    var finalTextStyle by remember { mutableStateOf(defaultTextStyle) }
    var shouldDrawText by remember { mutableStateOf(false) }

    Box(
        modifier = modifier.background(
            color = if (chars.isEmpty()) AppTheme.colors.background.surface3 else backgroundColor,
            shape = shape
        )
    ) {
        MegaText(
            modifier = Modifier
                .align(Alignment.Center)
                .drawWithContent {
                    if (shouldDrawText) drawContent()
                },
            text = chars,
            textColor = if (isSystemInDarkTheme()) TextColor.Inverse else TextColor.Primary,
            style = finalTextStyle,
            softWrap = false,
            onTextLayout = { result ->
                if (result.didOverflowWidth && defaultTextStyle.fontSize.isSpecified) {
                    finalTextStyle = finalTextStyle.copy(
                        fontSize = finalTextStyle.fontSize * 0.8
                    )
                } else {
                    shouldDrawText = true
                }
            }
        )
    }
}

/**
 * Returns a string containing only the first n characters from this string, or the entire string if this string is shorter.
 * This method will consider surrogate pair characters, e.g. emoticons.
 *
 * @throws IllegalArgumentException if [n] is negative.
 * @return The first n characters from this string.
 */
private fun String.onlyTake(n: Int): String {
    require(n >= 0) { "Requested character count $n is less than zero." }

    val originalText = this
    val originalTextLength = length
    return buildString {
        var nextCharIndex = 0
        var currentTotalAddedCharacters = 0
        while (currentTotalAddedCharacters < n && nextCharIndex < originalTextLength) {
            val codePoint = originalText.codePointAt(nextCharIndex)
            nextCharIndex += Character.charCount(codePoint)
            appendCodePoint(codePoint)
            currentTotalAddedCharacters++
        }
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