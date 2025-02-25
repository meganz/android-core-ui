package mega.android.core.ui.components.indicators

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import mega.android.core.ui.preview.CombinedThemePreviews
import mega.android.core.ui.theme.AndroidTheme
import mega.android.core.ui.theme.AppTheme

/**
 * Wrapper for [MegaAnimatedLinearProgressIndicator] to set default parameters to better represent the project theme
 * @param indicatorProgress set the current progress [0..1] or null for an indeterminate progress indicator
 * @param progressAnimDuration set the duration of the progress animation in milliseconds
 */
@Composable
fun MegaAnimatedLinearProgressIndicator(
    modifier: Modifier = Modifier,
    indicatorProgress: Float? = null,
    progressAnimDuration: Int = 500,
    height: Dp = 8.dp,
    clip: RoundedCornerShape = RoundedCornerShape(20.dp),
    strokeCap: StrokeCap = StrokeCap.Square,
    gapSize: Dp = 0.dp
) {
    if (indicatorProgress != null) {// If progress is not null, it will be a determinate progress indicator
        val isInPreview = LocalInspectionMode.current
        var progress by remember { mutableFloatStateOf(if (isInPreview) indicatorProgress else 0f) }
        val progressAnimation by animateFloatAsState(
            targetValue = progress,
            animationSpec = tween(
                durationMillis = progressAnimDuration,
                easing = FastOutSlowInEasing
            ),
            label = "Progress Animation"
        )

        LinearProgressIndicator(
            modifier = modifier
                .fillMaxWidth()
                .height(height)
                .clip(clip),
            progress = { progressAnimation },
            color = AppTheme.colors.border.brand,
            strokeCap = strokeCap,
            trackColor = AppTheme.colors.background.surface2,
            gapSize = gapSize,
            drawStopIndicator = {}
        )

        LaunchedEffect(indicatorProgress) {
            progress = indicatorProgress
        }
    } else {
        //If progress is null, it will be an indeterminate progress indicator
        LinearProgressIndicator(
            modifier = modifier
                .fillMaxWidth()
                .height(height)
                .clip(clip),
            color = AppTheme.colors.border.brand,
            strokeCap = strokeCap,
            trackColor = AppTheme.colors.background.surface2,
            gapSize = gapSize,
        )
    }
}

@CombinedThemePreviews
@Composable
private fun MegaAnimatedLinearProgressIndicatorPreview() {
    AndroidTheme(isDark = isSystemInDarkTheme()) {
        Box(modifier = Modifier.padding(16.dp)) {
            MegaAnimatedLinearProgressIndicator(
                indicatorProgress = 0.5f
            )
        }
    }
}