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
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import mega.android.core.ui.components.util.blurShadow
import mega.android.core.ui.preview.CombinedThemePreviews
import mega.android.core.ui.theme.AndroidThemeForPreviews
import mega.android.core.ui.theme.iconColor
import mega.android.core.ui.theme.spacing.LocalSpacing
import mega.android.core.ui.theme.supportColor
import mega.android.core.ui.theme.values.IconColor
import mega.android.core.ui.theme.values.SupportColor
import mega.android.core.ui.tokens.theme.DSTokens

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
        progress = { progressPercentage / 100f },
        color = DSTokens.colors.icon.accent,
        trackColor = Color.Transparent,
    )
}

@Composable
fun ProgressBarIndicator(
    modifier: Modifier = Modifier,
    progressPercentage: Float = 0f,
    supportColor: SupportColor
) {
    LinearProgressIndicator(
        modifier = modifier
            .wrapContentHeight()
            .fillMaxWidth(),
        progress = { progressPercentage / 100f },
        color = DSTokens.supportColor(supportColor),
        trackColor = Color.Transparent,
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
        progressPercentage = progressPercentage,
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
        progressPercentage = progressPercentage,
    )
}

@Composable
fun SmallInfiniteSpinnerIndicator(
    modifier: Modifier = Modifier,
    iconColor: IconColor = IconColor.Accent,
) {
    SpinnerIndicator(
        spinnerVariant = SpinnerVariant.Small,
        modifier = modifier,
        iconColor = iconColor,
    )
}

@Composable
fun LargeInfiniteSpinnerIndicator(
    modifier: Modifier = Modifier,
    iconColor: IconColor = IconColor.Accent,
) {
    SpinnerIndicator(
        spinnerVariant = SpinnerVariant.Large,
        modifier = modifier,
        iconColor = iconColor,
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
    iconColor: IconColor = IconColor.Accent
) {
    CircularProgressIndicator(
        progress = { progressPercentage / 100f },
        modifier = modifier.size(spinnerVariant.size),
        color = DSTokens.iconColor(iconColor),
        trackColor = Color.Transparent,
        strokeWidth = spinnerVariant.spinnerThickness(),
    )
}

@Composable
private fun SpinnerIndicator(
    spinnerVariant: SpinnerVariant,
    modifier: Modifier = Modifier,
    iconColor: IconColor = IconColor.Accent
) {
    CircularProgressIndicator(
        modifier = modifier.size(spinnerVariant.size),
        trackColor = Color.Transparent,
        color = DSTokens.iconColor(iconColor),
        strokeWidth = spinnerVariant.spinnerThickness()
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
            .clip(DSTokens.shapes.small)
            .background(DSTokens.colors.background.surface1)
            .padding(LocalSpacing.current.x16)
    ) {
        SpinnerIndicator(spinnerVariant)
    }
}

private fun SpinnerVariant.spinnerThickness() = when (this) {
    SpinnerVariant.Small -> 2.dp
    SpinnerVariant.Large -> 4.dp
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
private fun ProgressBarIndicatorSupportColorPreview(
    @PreviewParameter(SupportColorProvider::class) supportColor: SupportColor,
) {
    AndroidThemeForPreviews {
        Box(modifier = Modifier.padding(16.dp)) {
            ProgressBarIndicator(progressPercentage = 50f, supportColor = supportColor)
        }
    }
}

@CombinedThemePreviews
@Composable
private fun SpinnerIndicatorSmallPreview() {
    AndroidThemeForPreviews {
        SmallSpinnerIndicator(progressPercentage = 25f)
    }
}

@CombinedThemePreviews
@Composable
private fun SpinnerIndicatorLargePreview() {
    AndroidThemeForPreviews {
        LargeSpinnerIndicator(progressPercentage = 25f)
    }
}

@CombinedThemePreviews
@Composable
private fun SpinnerIndicatorIndeterminateSmallPreview() {
    AndroidThemeForPreviews {
        SmallSpinnerIndicator()
    }
}

@CombinedThemePreviews
@Composable
private fun SpinnerIndicatorIndeterminateLargePreview() {
    AndroidThemeForPreviews {
        LargeSpinnerIndicator()
    }
}

@CombinedThemePreviews
@Composable
private fun HUDSmallPreview() {
    AndroidThemeForPreviews {
        Box(modifier = Modifier.padding(16.dp)) {
            SmallHUD()
        }
    }
}

@CombinedThemePreviews
@Composable
private fun HUDLargePreview() {
    AndroidThemeForPreviews {
        Box(modifier = Modifier.padding(16.dp)) {
            LargeHUD()
        }
    }
}

private class SupportColorProvider : PreviewParameterProvider<SupportColor> {
    override val values = SupportColor.entries.asSequence()
}

