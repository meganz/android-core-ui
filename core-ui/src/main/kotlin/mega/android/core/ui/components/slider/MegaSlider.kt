package mega.android.core.ui.components.slider

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.DpSize
import mega.android.core.ui.components.slider.MegaSliderDefaults.Thumb
import mega.android.core.ui.components.slider.MegaSliderDefaults.Track
import mega.android.core.ui.preview.CombinedThemePreviews
import mega.android.core.ui.theme.AndroidThemeForPreviews
import mega.android.core.ui.tokens.theme.DSTokens
import mega.android.core.ui.theme.spacing.LocalSpacing

@OptIn(ExperimentalMaterial3Api::class)
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
    val spacing = LocalSpacing.current
    val interactionSource = remember { MutableInteractionSource() }
    val sliderColors = SliderDefaults.colors(
        thumbColor = DSTokens.colors.button.primary,
        activeTrackColor = DSTokens.colors.button.primary,
        inactiveTrackColor = DSTokens.colors.border.strong
    )
    Slider(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier,
        enabled = enabled,
        onValueChangeFinished = onValueChangeFinished,
        colors = sliderColors,
        interactionSource = interactionSource,
        steps = steps,
        thumb = {
            Thumb(
                interactionSource = interactionSource,
                colors = sliderColors,
                enabled = enabled,
                thumbSize = DpSize(spacing.x24, spacing.x24)
            )
        },
        track = { sliderState ->
            Track(
                colors = sliderColors,
                enabled = enabled,
                sliderState = sliderState
            )
        },
        valueRange = valueRange
    )
}

@CombinedThemePreviews
@Composable
private fun MegaSliderPreview() {
    AndroidThemeForPreviews {
        MegaSlider(value = 0.5f, onValueChange = {})
    }
}
