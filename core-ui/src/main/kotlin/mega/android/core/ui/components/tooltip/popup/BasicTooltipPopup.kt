package mega.android.core.ui.components.tooltip.popup

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.Transition
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.rememberTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupPositionProvider
import androidx.compose.ui.window.PopupProperties
import mega.android.core.ui.components.tooltip.state.MegaTooltipState
import mega.android.core.ui.components.tooltip.state.rememberMegaTooltipState

@Composable
internal fun BasicTooltipPopup(
    popupPositionProvider: PopupPositionProvider,
    properties: PopupProperties = PopupProperties(),
    tooltipState: MegaTooltipState = rememberMegaTooltipState(),
    onDismissRequest: (() -> Unit)? = null,
    content: @Composable () -> Unit
) {
    val mutableTransitionState = remember(tooltipState.visibilityState) {
        MutableTransitionState(initialState = !tooltipState.visibilityState).apply {
            targetState = tooltipState.visibilityState
        }
    }
    val transitionState = rememberTransition(mutableTransitionState)

    LaunchedEffect(mutableTransitionState.targetState, mutableTransitionState.isIdle) {
        // Enter animation finished
        if (mutableTransitionState.targetState && mutableTransitionState.isIdle) {
            tooltipState.tryToStartDismissCountDown()
            return@LaunchedEffect
        }

        // Exit animation finished
        if (!mutableTransitionState.targetState && mutableTransitionState.isIdle) {
            onDismissRequest?.invoke()
            return@LaunchedEffect
        }
    }

    LaunchedEffect(tooltipState.dismissCountDownEnd) {
        if (tooltipState.dismissCountDownEnd) {
            tooltipState.dismiss()
        }
    }

    Popup(
        popupPositionProvider = popupPositionProvider,
        properties = properties,
        onDismissRequest = {
            if (tooltipState.dismissDuration == null) {
                tooltipState.dismiss()
            }
        }
    ) {
        Box(modifier = Modifier.animateTooltip(transitionState)) {
            content()
        }
    }
}

@Composable
private fun Modifier.animateTooltip(transition: Transition<Boolean>): Modifier {
    val scale by transition.animateFloat(
        transitionSpec = {
            tween(
                durationMillis = TooltipFadeDuration,
                easing = LinearOutSlowInEasing
            )
        }
    ) {
        if (it) 1f else 0.8f
    }

    val alpha by transition.animateFloat(
        transitionSpec = {
            tween(
                durationMillis = TooltipFadeDuration,
                easing = LinearEasing
            )
        }
    ) {
        if (it) 1f else 0f
    }

    return this then Modifier.graphicsLayer(scaleX = scale, scaleY = scale, alpha = alpha)
}

// No specification for fade in and fade out duration, so aligning it with the behavior for snack bar
private const val TooltipFadeDuration = 150
