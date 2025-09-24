package mega.android.core.ui.components.surface

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import mega.android.core.ui.components.MegaText
import mega.android.core.ui.preview.CombinedThemePreviews
import mega.android.core.ui.theme.AndroidThemeForPreviews
import mega.android.core.ui.theme.values.TextColor
import mega.android.core.ui.tokens.theme.DSTokens

@Composable
fun BoxSurface(
    surfaceColor: SurfaceColor,
    modifier: Modifier = Modifier,
    content: @Composable BoxScope.() -> Unit,
) {
    Box(
        modifier = modifier
            .background(surfaceColor.toBackgroundColor()),
        content = content
    )
}

@Composable
fun ColumnSurface(
    surfaceColor: SurfaceColor,
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit,
) {
    Column(
        modifier = modifier
            .background(surfaceColor.toBackgroundColor()),
        content = content
    )
}

@Composable
fun RowSurface(
    surfaceColor: SurfaceColor,
    modifier: Modifier = Modifier,
    content: @Composable RowScope.() -> Unit,
) {
    Row(
        modifier = modifier
            .background(surfaceColor.toBackgroundColor()),
        content = content
    )
}

@Composable
fun CardSurface(
    surfaceColor: SurfaceColor,
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit,
) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(
            containerColor = surfaceColor.toBackgroundColor()
        ),
        content = content
    )
}

@Composable
@CombinedThemePreviews
private fun BackgroundSurfacePreview() {
    AndroidThemeForPreviews {
        BoxSurface(
            modifier = Modifier.fillMaxSize(),
            surfaceColor = SurfaceColor.Blur,
        ) {
            MegaText("Hello", textColor = TextColor.Primary)
        }
    }
}

@Composable
@CombinedThemePreviews
private fun CardSurfacePreview() {
    AndroidThemeForPreviews {
        CardSurface(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            surfaceColor = SurfaceColor.Surface2,
        ) {
            MegaText(
                modifier = Modifier.padding(8.dp),
                text = "Hello",
                textColor = TextColor.Primary
            )
        }
    }
}

enum class SurfaceColor {
    Surface1,
    Surface2,
    Surface3,
    PageBackground,
    Blur,
    Inverse
}

@Composable
internal fun SurfaceColor.toBackgroundColor(): Color {
    return when (this) {
        SurfaceColor.Surface1 -> DSTokens.colors.background.surface1
        SurfaceColor.Surface2 -> DSTokens.colors.background.surface2
        SurfaceColor.Surface3 -> DSTokens.colors.background.surface3
        SurfaceColor.PageBackground -> DSTokens.colors.background.pageBackground
        SurfaceColor.Blur -> DSTokens.colors.background.blur
        SurfaceColor.Inverse -> DSTokens.colors.background.inverse
    }
}