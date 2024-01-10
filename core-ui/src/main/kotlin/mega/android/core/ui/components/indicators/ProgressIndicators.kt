package mega.android.core.ui.components.indicators

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import mega.android.core.ui.components.util.blurShadow
import mega.android.core.ui.preview.CombinedThemePreviews
import mega.android.core.ui.theme.AndroidThemeForPreviews
import mega.android.core.ui.theme.AppTheme
import mega.android.core.ui.theme.LocalColorPalette
import mega.android.core.ui.theme.spacing.LocalSpacing
import mega.android.core.ui.theme.tokens.IconColor

private enum class SpinnerVariant(val size: Dp) {
    Small(24.dp),
    Large(48.dp)
}

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

@Composable
fun SmallSpinnerIndicator(
    modifier: Modifier = Modifier,
    progressPercentage: Float = 0f,
) {
    SpinnerIndicator(
        spinnerVariant = SpinnerVariant.Small,
        modifier = modifier,
        progressPercentage = progressPercentage
    )
}

@Composable
fun LargeSpinnerIndicator(
    modifier: Modifier = Modifier,
    progressPercentage: Float = 0f,
) {
    SpinnerIndicator(
        spinnerVariant = SpinnerVariant.Large,
        modifier = modifier,
        progressPercentage = progressPercentage
    )
}

@Composable
fun SmallHUD(modifier: Modifier = Modifier) {
    HUD(
        spinnerVariant = SpinnerVariant.Small,
        modifier = modifier
    )
}

@Composable
fun LargeHUD(modifier: Modifier = Modifier) {
    HUD(
        spinnerVariant = SpinnerVariant.Large,
        modifier = modifier
    )
}

@Composable
private fun SpinnerIndicator(
    spinnerVariant: SpinnerVariant,
    modifier: Modifier = Modifier,
    progressPercentage: Float = 0f,
) {
    CircularProgressIndicator(
        modifier = modifier
            .size(spinnerVariant.size),
        progress = progressPercentage / 100f,
        trackColor = Color.Transparent,
        color = LocalColorPalette.current.icon.getIconColor(IconColor.Accent)
    )
}

@Composable
private fun SpinnerIndicator(
    spinnerVariant: SpinnerVariant,
    modifier: Modifier = Modifier
) {
    CircularProgressIndicator(
        modifier = modifier
            .size(spinnerVariant.size),
        trackColor = Color.Transparent,
        color = LocalColorPalette.current.icon.getIconColor(IconColor.Accent)
    )
}

@Composable
private fun HUD(
    spinnerVariant: SpinnerVariant,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .wrapContentSize()
            .blurShadow(
                color = Color.Black.copy(alpha = 0.2f),
                cornerRadius = 8.dp,
                blurRadius = 20.dp,
            )
            .clip(AppTheme.shapes.small)
            .background(AppTheme.colors.background.surface1)
            .padding(LocalSpacing.current.x16)
    ) {
        SpinnerIndicator(spinnerVariant)
    }
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

@CombinedThemePreviews
@Composable
fun SpinnerIndicatorSmallPreview() {
    AndroidThemeForPreviews {
        SmallSpinnerIndicator(progressPercentage = 25f)
    }
}

@CombinedThemePreviews
@Composable
fun SpinnerIndicatorLargePreview() {
    AndroidThemeForPreviews {
        LargeSpinnerIndicator(progressPercentage = 25f)
    }
}

@CombinedThemePreviews
@Composable
fun SpinnerIndicatorIndeterminateSmallPreview() {
    AndroidThemeForPreviews {
        SmallSpinnerIndicator()
    }
}

@CombinedThemePreviews
@Composable
fun SpinnerIndicatorIndeterminateLargePreview() {
    AndroidThemeForPreviews {
        LargeSpinnerIndicator()
    }
}

@CombinedThemePreviews
@Composable
fun HUDSmallPreview() {
    AndroidThemeForPreviews {
        Box(modifier = Modifier.padding(16.dp)) {
            SmallHUD()
        }
    }
}

@CombinedThemePreviews
@Composable
fun HUDLargePreview() {
    AndroidThemeForPreviews {
        Box(modifier = Modifier.padding(16.dp)) {
            LargeHUD()
        }
    }
}

