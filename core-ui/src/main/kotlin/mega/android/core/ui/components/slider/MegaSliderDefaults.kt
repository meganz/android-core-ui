package mega.android.core.ui.components.slider

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.hoverable
import androidx.compose.foundation.indication
import androidx.compose.foundation.interaction.DragInteraction
import androidx.compose.foundation.interaction.Interaction
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SliderColors
import androidx.compose.material3.SliderDefaults.colors
import androidx.compose.material3.SliderState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.lerp
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp

/**
 * The latest version of compose material3 uses the new design guidelines for Slider, as can be seen here:
 * https://m3.material.io/components/sliders/overview.
 * Therefore, we need to create custom Thumb and Track components for the slider to match our designs.
 */
internal object MegaSliderDefaults {

    @Composable
    internal fun Thumb(
        interactionSource: MutableInteractionSource,
        modifier: Modifier = Modifier,
        colors: SliderColors = colors(),
        enabled: Boolean = true,
        thumbSize: DpSize = DpSize(20.dp, 20.dp)
    ) {
        val interactions = remember { mutableStateListOf<Interaction>() }
        LaunchedEffect(interactionSource) {
            interactionSource.interactions.collect { interaction ->
                when (interaction) {
                    is PressInteraction.Press -> interactions.add(interaction)
                    is PressInteraction.Release -> interactions.remove(interaction.press)
                    is PressInteraction.Cancel -> interactions.remove(interaction.press)
                    is DragInteraction.Start -> interactions.add(interaction)
                    is DragInteraction.Stop -> interactions.remove(interaction.start)
                    is DragInteraction.Cancel -> interactions.remove(interaction.start)
                }
            }
        }
        val elevation = if (interactions.isNotEmpty()) 6.dp else 1.dp
        Spacer(
            modifier
                .size(thumbSize)
                .indication(
                    interactionSource = interactionSource,
                    indication = rememberRipple(
                        bounded = false,
                        radius = 20.dp
                    )
                )
                .hoverable(interactionSource = interactionSource)
                .shadow(if (enabled) elevation else 0.dp, CircleShape, clip = false)
                .background(
                    if (enabled) colors.thumbColor else colors.disabledThumbColor,
                    CircleShape
                )
        )
    }

    @Composable
    @ExperimentalMaterial3Api
    internal fun Track(
        sliderState: SliderState,
        modifier: Modifier = Modifier,
        colors: SliderColors = colors(),
        enabled: Boolean = true
    ) {
        val inactiveTrackColor = colors.trackColor(enabled, active = false)
        val activeTrackColor = colors.trackColor(enabled, active = true)
        val inactiveTickColor = colors.tickColor(enabled, active = false)
        val activeTickColor = colors.tickColor(enabled, active = true)
        Canvas(
            modifier
                .fillMaxWidth()
                .height(4.dp)
        ) {
            drawTrack(
                stepsToTickFractions(sliderState.steps),
                0f,
                calcFraction(
                    sliderState.valueRange.start,
                    sliderState.valueRange.endInclusive,
                    sliderState.value.coerceIn(
                        sliderState.valueRange.start,
                        sliderState.valueRange.endInclusive
                    )
                ),
                inactiveTrackColor,
                activeTrackColor,
                inactiveTickColor,
                activeTickColor
            )
        }
    }

    private fun SliderColors.trackColor(enabled: Boolean, active: Boolean): Color =
        if (enabled) {
            if (active) activeTrackColor else inactiveTrackColor
        } else {
            if (active) disabledActiveTrackColor else disabledInactiveTrackColor
        }

    private fun SliderColors.tickColor(enabled: Boolean, active: Boolean): Color =
        if (enabled) {
            if (active) activeTickColor else inactiveTickColor
        } else {
            if (active) disabledActiveTickColor else disabledInactiveTickColor
        }

    private fun DrawScope.drawTrack(
        tickFractions: FloatArray,
        activeRangeStart: Float,
        activeRangeEnd: Float,
        inactiveTrackColor: Color,
        activeTrackColor: Color,
        inactiveTickColor: Color,
        activeTickColor: Color
    ) {
        val isRtl = layoutDirection == LayoutDirection.Rtl
        val sliderLeft = Offset(0f, center.y)
        val sliderRight = Offset(size.width, center.y)
        val sliderStart = if (isRtl) sliderRight else sliderLeft
        val sliderEnd = if (isRtl) sliderLeft else sliderRight
        val tickSize = 4.dp.toPx()
        val trackStrokeWidth = 4.dp.toPx()
        drawLine(
            inactiveTrackColor,
            sliderStart,
            sliderEnd,
            trackStrokeWidth,
            StrokeCap.Round
        )
        val sliderValueEnd = Offset(
            sliderStart.x +
                    (sliderEnd.x - sliderStart.x) * activeRangeEnd,
            center.y
        )

        val sliderValueStart = Offset(
            sliderStart.x +
                    (sliderEnd.x - sliderStart.x) * activeRangeStart,
            center.y
        )

        drawLine(
            activeTrackColor,
            sliderValueStart,
            sliderValueEnd,
            trackStrokeWidth,
            StrokeCap.Round
        )

        for (tick in tickFractions) {
            val outsideFraction = tick > activeRangeEnd || tick < activeRangeStart
            drawCircle(
                color = if (outsideFraction) inactiveTickColor else activeTickColor,
                center = Offset(lerp(sliderStart, sliderEnd, tick).x, center.y),
                radius = tickSize / 2f
            )
        }
    }

    private fun stepsToTickFractions(steps: Int): FloatArray {
        return if (steps == 0) floatArrayOf() else FloatArray(steps + 2) { it.toFloat() / (steps + 1) }
    }

    // Calculate the 0..1 fraction that `pos` value represents between `a` and `b`
    private fun calcFraction(a: Float, b: Float, pos: Float) =
        (if (b - a == 0f) 0f else (pos - a) / (b - a)).coerceIn(0f, 1f)
}
