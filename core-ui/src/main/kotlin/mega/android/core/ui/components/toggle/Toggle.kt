package mega.android.core.ui.components.toggle

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.indication
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.minimumInteractiveComponentSize
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import mega.android.core.ui.R
import mega.android.core.ui.preview.CombinedThemePreviews
import mega.android.core.ui.theme.AndroidThemeForPreviews
import mega.android.core.ui.tokens.theme.DSTokens

/**
 * Toggles: Switches the state of a single item on or off
 * @param isChecked         Is Toggle initially checked
 * @param onCheckedChange   What happens if toggle state changes
 * @param isEnabled         Is Toggle enabled (switchable)
 */
@Composable
fun Toggle(
    isChecked: Boolean,
    onCheckedChange: ((Boolean) -> Unit)?,
    modifier: Modifier = Modifier,
    isEnabled: Boolean = true,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    onClickListener: (() -> Unit)? = null,
) {
    val isPressed by interactionSource.collectIsPressedAsState()

    val thumbOffsetRaw = remember(isChecked) {
        val offset = (trackWidth - trackHeight) / 2
        if (isChecked) offset else -offset
    }

    val thumbOffset by animateDpAsState(targetValue = thumbOffsetRaw, label = "thumb Offset")

    val trackAnimationSpecs = tween<Color>()
    val thumbAnimationSpecs = tween<Color>(easing = LinearOutSlowInEasing)

    val trackColor by animateColorAsState(
        targetValue = getColorForTrack(
            checked = isChecked,
            pressed = isPressed,
            enabled = isEnabled
        ),
        animationSpec = trackAnimationSpecs,
        label = "track color"
    )
    val borderColor by animateColorAsState(
        targetValue = getColorForBorder(pressed = isPressed, enabled = isEnabled),
        animationSpec = trackAnimationSpecs,
        label = "border color"
    )
    val thumbColor by animateColorAsState(
        targetValue = getColorForThumb(
            checked = isChecked,
            pressed = isPressed,
            enabled = isEnabled
        ),
        animationSpec = thumbAnimationSpecs,
        label = "thumb color"
    )
    Box(
        modifier = modifier
            .minimumInteractiveComponentSize()
            .then(
                if (onCheckedChange != null) {
                    Modifier.toggleable(
                        value = isChecked,
                        onValueChange = {
                            onCheckedChange(it)
                        },
                        enabled = isEnabled,
                        role = Role.Switch,
                        interactionSource = interactionSource,
                        indication = null
                    )
                } else if (onClickListener != null) {
                    Modifier.clickable(
                        enabled = isEnabled,
                        role = Role.Switch,
                        interactionSource = interactionSource,
                        onClick = onClickListener,
                        indication = null
                    )
                } else Modifier
            )
            .padding((rippleSize - trackHeight) / 2)
            .size(width = trackWidth, height = trackHeight)
            .background(trackColor, CircleShape)
            .border(borderWidth, borderColor, CircleShape),
        contentAlignment = Alignment.Center,
    ) {
        Box(
            Modifier
                .size(thumbSize)
                .offset(x = thumbOffset)
                .background(thumbColor, shape = CircleShape)
                .indication(
                    interactionSource,
                    ripple(
                        bounded = false,
                        radius = rippleSize / 2,
                    )
                ),
            contentAlignment = Alignment.Center,
        ) {
            AnimatedContent(
                targetState = isChecked,
                label = "CrossFadeChecked",
                contentAlignment = Alignment.Center,
                transitionSpec = {
                    fadeIn(spring()).togetherWith(fadeOut(spring()))
                }
            ) { checked ->
                if (checked) {
                    Icon(
                        modifier = Modifier.testTag(CHECKED_TAG),
                        painter = painterResource(id = R.drawable.ic_check_medium_thin_outline),
                        tint = trackColor,
                        contentDescription = "Checked",
                    )
                } else {
                    Spacer(
                        Modifier
                            .size(width = 10.dp, height = 2.dp)
                            .background(trackColor, CircleShape)
                    )
                }
            }
        }
    }
}

private val borderWidth = 1.dp
private val thumbSize = 16.dp
private val trackHeight = 24.dp
private val trackWidth = 48.dp
private val rippleSize = 32.dp
internal const val CHECKED_TAG = "toggle:icon_checked"

@Composable
private fun getColorForThumb(checked: Boolean, pressed: Boolean, enabled: Boolean) =
    if (enabled) {
        when {
            !checked && !pressed -> DSTokens.colors.components.selectionControl
            !checked && pressed -> DSTokens.colors.button.outlinePressed
            else /*checked*/ -> DSTokens.colors.background.surface1
        }
    } else {
        if (checked) DSTokens.colors.background.pageBackground else DSTokens.colors.border.disabled
    }

@Composable
private fun getColorForTrack(checked: Boolean, pressed: Boolean, enabled: Boolean) =
    getColorForThumb(
        checked = !checked,
        pressed = pressed,
        enabled = enabled
    )

@Composable
private fun getColorForBorder(pressed: Boolean, enabled: Boolean) =
    getColorForThumb(
        checked = false,
        pressed = pressed,
        enabled = enabled
    )

@CombinedThemePreviews
@Composable
private fun ToggleOffEnabledPreview() {
    AndroidThemeForPreviews {
        Toggle(isChecked = false, onCheckedChange = {})
    }
}

@CombinedThemePreviews
@Composable
private fun ToggleOnEnabledPreview() {
    AndroidThemeForPreviews {
        Toggle(isChecked = true, onCheckedChange = {})
    }
}

@CombinedThemePreviews
@Composable
private fun ToggleOffDisabledPreview() {
    AndroidThemeForPreviews {
        Toggle(isChecked = false, onCheckedChange = {}, isEnabled = false)
    }
}

@CombinedThemePreviews
@Composable
private fun ToggleOnDisabledPreview() {
    AndroidThemeForPreviews {
        Toggle(isChecked = true, onCheckedChange = {}, isEnabled = false)
    }
}
