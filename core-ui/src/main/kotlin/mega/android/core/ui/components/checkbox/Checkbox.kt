package mega.android.core.ui.components.checkbox

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import mega.android.core.ui.R
import mega.android.core.ui.modifiers.conditional
import mega.android.core.ui.preview.CombinedThemePreviews
import mega.android.core.ui.theme.AndroidThemeForPreviews
import mega.android.core.ui.tokens.theme.DSTokens

private val checkboxContainerSize = 42.dp
private val checkboxSize = 20.dp
private val checkboxBorderStroke = 1.dp
private val checkMarkSize = 16.dp
private const val checkboxFocusedCornerRadius = 16f

private data class CheckboxColors(
    val checkedCheckmarkColor: Color = Color.Unspecified,
    val disabledCheckedCheckmarkColor: Color = Color.Unspecified,
    val checkedBoxColor: Color = Color.Unspecified,
    val uncheckedBoxColor: Color = Color.Unspecified,
    val disabledCheckedBoxColor: Color = Color.Unspecified,
    val disabledUncheckedBoxColor: Color = Color.Unspecified,
    val checkedBorderColor: Color = Color.Unspecified,
    val uncheckedBorderColor: Color = Color.Unspecified,
    val disabledBorderColor: Color = Color.Unspecified,
) {
    companion object {
        @Composable
        fun default() = CheckboxColors(
            checkedCheckmarkColor = DSTokens.colors.icon.inverse,
            disabledCheckedCheckmarkColor = DSTokens.colors.icon.disabled,
            checkedBoxColor = DSTokens.colors.components.selectionControl,
            uncheckedBoxColor = DSTokens.colors.icon.inverse,
            disabledCheckedBoxColor = DSTokens.colors.button.disabled,
            disabledUncheckedBoxColor = DSTokens.colors.button.disabled,
            checkedBorderColor = DSTokens.colors.components.selectionControl,
            uncheckedBorderColor = DSTokens.colors.border.strongSelected,
            disabledBorderColor = DSTokens.colors.button.disabled,
        )
    }
}

private fun getCheckedBoxColor(color: CheckboxColors, enabled: Boolean) =
    if (enabled) color.checkedBoxColor else color.disabledCheckedBoxColor

private fun getUncheckedBoxColor(color: CheckboxColors, enabled: Boolean) =
    if (enabled) color.uncheckedBoxColor else color.disabledUncheckedBoxColor

private fun getCheckedBorderColor(color: CheckboxColors, enabled: Boolean) =
    if (enabled) color.checkedBorderColor else color.disabledBorderColor

private fun getUncheckedBorderColor(color: CheckboxColors, enabled: Boolean) =
    if (enabled) color.uncheckedBorderColor else color.disabledBorderColor

private fun getBoxColor(isChecked: Boolean, color: CheckboxColors, enabled: Boolean) =
    if (isChecked) getCheckedBoxColor(color, enabled) else getUncheckedBoxColor(color, enabled)

private fun getBorderColor(isChecked: Boolean, color: CheckboxColors, enabled: Boolean) =
    if (isChecked) getCheckedBorderColor(color, enabled) else getUncheckedBorderColor(
        color,
        enabled
    )

@Composable
fun Checkbox(
    checked: Boolean,
    onCheckStateChanged: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    clickable: Boolean = true,
    tapTargetArea: Boolean = true
) {
    val mutableInteractionSource = remember { MutableInteractionSource() }
    val focusRequester = remember { FocusRequester() }
    val color = CheckboxColors.default()
    var isChecked by remember(checked) { mutableStateOf(checked) }
    val focusColor = DSTokens.colors.focus.colorFocus
    val isFocused by mutableInteractionSource.collectIsFocusedAsState()
    val isPressed by mutableInteractionSource.collectIsPressedAsState()

    Box(
        modifier = Modifier
            .conditional(tapTargetArea) { size(checkboxContainerSize) }
            .then(
                if (enabled && clickable) {
                    modifier
                        .clickable(
                            interactionSource = mutableInteractionSource,
                            indication = ripple(
                                bounded = true,
                                radius = checkboxContainerSize / 2f,
                                color = DSTokens.colors.icon.primary
                            )
                        ) {
                            isChecked = !isChecked
                            onCheckStateChanged(isChecked)
                        }
                } else modifier
            )
    ) {
        Box(
            modifier = Modifier
                .align(Alignment.Center)
                .wrapContentSize()
                .focusRequester(focusRequester)
                .then(
                    if (isFocused) {
                        modifier
                            .drawBehind {
                                drawRoundRect(
                                    color = focusColor,
                                    cornerRadius = CornerRadius(checkboxFocusedCornerRadius)
                                )
                            }
                            .padding(4.dp)
                    } else modifier
                )
                .then(
                    if (isPressed) {
                        modifier
                            .padding(12.dp)
                    } else modifier
                )

        ) {
            Box(
                modifier = modifier
                    .size(checkboxSize)
                    .border(
                        width = checkboxBorderStroke,
                        color = getBorderColor(isChecked, color, enabled),
                        shape = RoundedCornerShape(2.dp)
                    )
                    .background(
                        color = getBoxColor(isChecked, color, enabled),
                        shape = RoundedCornerShape(2.dp)
                    )
            ) {
                AnimatedVisibility(
                    modifier = Modifier
                        .align(Alignment.Center),
                    visible = isChecked,
                    enter = scaleIn(),
                    exit = scaleOut()
                ) {
                    Icon(
                        modifier = Modifier
                            .size(checkMarkSize),
                        painter = painterResource(id = R.drawable.ic_check_medium_regular_outline),
                        contentDescription = "check icon",
                        tint = if (enabled) color.checkedCheckmarkColor else color.disabledCheckedCheckmarkColor
                    )
                }
            }
        }
    }
}

@CombinedThemePreviews
@Composable
private fun CheckBoxUncheckedPreview() {
    AndroidThemeForPreviews {
        Checkbox(checked = false, onCheckStateChanged = {})
    }
}

@CombinedThemePreviews
@Composable
private fun CheckBoxPreview() {
    AndroidThemeForPreviews {
        Checkbox(checked = true, onCheckStateChanged = {})
    }
}

@CombinedThemePreviews
@Composable
private fun CheckBoxDisabledUncheckedPreview() {
    AndroidThemeForPreviews {
        Checkbox(checked = false, onCheckStateChanged = {}, enabled = false)
    }
}

@CombinedThemePreviews
@Composable
private fun CheckBoxDisabledPreview() {
    AndroidThemeForPreviews {
        Checkbox(checked = true, onCheckStateChanged = {}, enabled = false)
    }
}