package mega.android.core.ui.modifiers

import android.annotation.SuppressLint
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.dp

/**
 * Conditional execution wrapper for the Modifier class
 * it's the equivalent of `if (condition)` on the modifier
 * @param condition the condition of which the block will executes
 * @param modifier block to invoke and returns [Modifier] as self
 */
@SuppressLint("UnnecessaryComposedModifier")
fun Modifier.conditional(
    condition: Boolean,
    modifier: @Composable Modifier.() -> Modifier,
): Modifier = composed {
    if (condition) then(modifier(Modifier)) else this
}


fun Modifier.infiniteRotation(
    durationMillis: Int = 1000,
): Modifier = composed {
    val infiniteTransition = rememberInfiniteTransition(label = "infinite-rotation")
    val rotation by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "rotation"
    )
    this.rotate(rotation)
}

/**
 * Extension function to exclude top padding from PaddingValues
 */
@Composable
fun PaddingValues.excludeTopPadding(): PaddingValues {
    val layoutDirection = LocalLayoutDirection.current
    return PaddingValues(
        bottom = calculateBottomPadding(),
        start = calculateStartPadding(layoutDirection),
        end = calculateEndPadding(layoutDirection)
    )
}

/**
 * Extension function to exclude bottom padding from PaddingValues
 */
@Composable
fun PaddingValues.excludingBottomPadding(): PaddingValues {
    val layoutDirection = LocalLayoutDirection.current
    return PaddingValues(
        top = calculateTopPadding(),
        start = calculateStartPadding(layoutDirection),
        end = calculateEndPadding(layoutDirection)
    )
}

/**
 * Apply additional bottom space to follow our design system for lists content. Please notice that usually PaddingValues are preferable.
 */
fun Modifier.safeBottomPadding() = this.padding(bottom = BOTTOM_PADDING_DP.dp)

/**
 * Creates a new Padding values with additional bottom space to follow our design system for lists and grids content.
 */
@Composable
fun PaddingValues.plusSafeBottom(): PaddingValues {
    val layoutDirection = LocalLayoutDirection.current
    return PaddingValues(
        bottom = calculateBottomPadding() + BOTTOM_PADDING_DP.dp,
        top = calculateTopPadding(),
        start = calculateStartPadding(layoutDirection),
        end = calculateEndPadding(layoutDirection)
    )
}

/**
 * The bottom padding with additional bottom space to follow our design system for lists and grids content.
 */
fun PaddingValues.calculateSafeBottomPadding() =
    this.calculateBottomPadding() + BOTTOM_PADDING_DP.dp

private const val BOTTOM_PADDING_DP = 100