package mega.android.core.ui.components.indicators

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import mega.android.core.ui.preview.CombinedThemePreviews
import mega.android.core.ui.theme.AndroidThemeForPreviews
import mega.android.core.ui.theme.LocalColorPalette
import mega.android.core.ui.theme.tokens.IconColor

@Composable
fun ProgressBarIndicator(
    modifier: Modifier = Modifier,
    progressPercentage: Float = 0f,
) {
    LinearProgressIndicator(
        modifier = modifier
            .wrapContentHeight()
            .fillMaxWidth(),
        progress = progressPercentage / 100f,
        trackColor = Color.Transparent,
        color = LocalColorPalette.current.icon.getIconColor(IconColor.Accent)
    )
}

@CombinedThemePreviews
@Composable
private fun ProgressBarIndicatorPreview() {
    AndroidThemeForPreviews {
        Box(modifier = Modifier.padding(16.dp)) {
            ProgressBarIndicator(progressPercentage = 50f)
        }
    }
}