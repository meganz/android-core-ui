package mega.android.core.ui.components.slider

import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import mega.android.core.ui.preview.CombinedThemePreviews
import mega.android.core.ui.theme.AndroidThemeForPreviews
import mega.android.core.ui.theme.AppTheme

@Composable
fun MegaSlider(
    value: Float,
    onValueChange: (Float) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    valueRange: ClosedFloatingPointRange<Float> = 0f..1f,
    steps: Int = 0,
    onValueChangeFinished: (() -> Unit)? = null,
) {
    val sliderColors = SliderDefaults.colors(
        thumbColor = AppTheme.colors.button.primary,
        activeTrackColor = AppTheme.colors.button.primary,
        inactiveTrackColor = AppTheme.colors.border.strong
    )
    Slider(
        modifier = modifier,
        value = value,
        onValueChange = onValueChange,
        enabled = enabled,
        valueRange = valueRange,
        steps = steps,
        colors = sliderColors,
        onValueChangeFinished = onValueChangeFinished
    )
}

@CombinedThemePreviews
@Composable
private fun MegaSliderPreview() {
    AndroidThemeForPreviews {
        MegaSlider(value = 0.5f, onValueChange = {})
    }
}
