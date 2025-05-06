package mega.android.core.ui.components.surface

import androidx.compose.foundation.BorderStroke
import androidx.compose.material3.Surface
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import mega.android.core.ui.tokens.theme.DSTokens

@Composable
fun ThemedSurface(
    modifier: Modifier = Modifier,
    shape: Shape = RectangleShape,
    tonalElevation: Dp = 0.dp,
    shadowElevation: Dp = 0.dp,
    border: BorderStroke? = null,
    content: @Composable () -> Unit
) {
    val surfaceColor = DSTokens.colors.background.pageBackground

    Surface(
        modifier,
        shape,
        surfaceColor,
        contentColorFor(surfaceColor),
        tonalElevation,
        shadowElevation,
        border
    ) {
        content()
    }
}