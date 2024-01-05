package mega.android.core.ui.components.dialogs

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import mega.android.core.ui.preview.CombinedThemePreviews
import mega.android.core.ui.theme.AndroidThemeForPreviews
import mega.android.core.ui.theme.AppTheme
import mega.android.core.ui.theme.spacing.LocalSpacing

@Composable
fun DialogButton(
    buttonText: String,
    onButtonClicked: () -> Unit = {},
    enabled: Boolean = true
) {
    val focusRequester = FocusRequester()
    val mutableInteractionSource = remember { MutableInteractionSource() }
    val isFocused by mutableInteractionSource.collectIsFocusedAsState()
    val borderColor = if (isFocused) AppTheme.colors.focus.colorFocus else Color.Unspecified
    val pressedBackgroundColor = AppTheme.colors.button.primaryPressed
    var componentWidth by remember { mutableStateOf(0.dp) }
    val density = LocalDensity.current

    Box(
        modifier = Modifier
            .wrapContentSize()
            .focusRequester(focusRequester)
            .then(
                if (enabled) {
                    Modifier.clickable(
                        interactionSource = mutableInteractionSource,
                        indication = rememberRipple(
                            bounded = false,
                            radius = componentWidth / 2,
                            color = pressedBackgroundColor
                        )
                    ) {
                        onButtonClicked()
                    }
                } else Modifier
            )
            .then(
                if (isFocused) {
                    Modifier
                        .border(
                            width = 4.dp,
                            color = borderColor,
                            shape = MaterialTheme.shapes.small
                        )
                } else {
                    Modifier
                }
            )
            .padding(
                horizontal = LocalSpacing.current.x16,
                vertical = LocalSpacing.current.x12
            )
            .onGloballyPositioned {
                componentWidth = with(density) {
                    it.size.width.toDp()
                }
            }
    ) {
        Text(
            text = buttonText,
            color = AppTheme.colors.button.primary,
            style = AppTheme.typography.labelLarge,
        )
    }
}

@Composable
@CombinedThemePreviews
private fun BasicDialogPreview() {
    AndroidThemeForPreviews {
        DialogButton(
            buttonText = "Button",
            onButtonClicked = {},
        )
    }
}