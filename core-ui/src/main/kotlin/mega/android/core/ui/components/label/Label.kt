package mega.android.core.ui.components.label

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import mega.android.core.ui.preview.CombinedThemePreviews
import mega.android.core.ui.theme.AndroidThemeForPreviews
import mega.android.core.ui.tokens.theme.DSTokens


enum class LabelColor(private val colorFun: @Composable () -> Color) {
    Red({ DSTokens.colors.indicator.pink }),
    Orange({ DSTokens.colors.indicator.orange }),
    Yellow({ DSTokens.colors.indicator.yellow }),
    Green({ DSTokens.colors.indicator.green }),
    Blue({ DSTokens.colors.indicator.blue }),
    Purple({ DSTokens.colors.indicator.magenta }),
    Grey({ DSTokens.colors.icon.onColorDisabled });

    val color: Color
        @Composable
        get() = colorFun()
}

/**
 * Composable function to display a circle with a color
 *
 * @param color
 * @param modifier
 */
@Composable
fun Label(
    color: LabelColor,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .size(16.dp)
            .padding(3.dp)
            .background(color.color, shape = CircleShape)
    )
}

@CombinedThemePreviews
@Composable
private fun LabelPreview(@PreviewParameter(LabelColorProvider::class) color: LabelColor) {
    AndroidThemeForPreviews {
        Label(color)
    }
}

private class LabelColorProvider : PreviewParameterProvider<LabelColor> {
    override val values = LabelColor.entries.asSequence()
}
