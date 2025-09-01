package mega.android.core.ui.components.dialogs

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import mega.android.core.ui.preview.CombinedThemePreviews
import mega.android.core.ui.theme.AndroidThemeForPreviews
import mega.android.core.ui.theme.AppTheme
import mega.android.core.ui.theme.spacing.LocalSpacing
import mega.android.core.ui.tokens.theme.DSTokens

@Composable
fun DialogButton(
    buttonText: String,
    onButtonClicked: () -> Unit = {},
    enabled: Boolean = true
) {
    val focusRequester = FocusRequester()
    val mutableInteractionSource = remember { MutableInteractionSource() }
    val isFocused by mutableInteractionSource.collectIsFocusedAsState()
    val borderColor = if (isFocused) DSTokens.colors.focus.colorFocus else Color.Unspecified
    val pressedBackgroundColor = DSTokens.colors.button.primaryPressed

    Box(
        modifier = Modifier
            .wrapContentSize()
            .focusRequester(focusRequester)
            .clip(RoundedCornerShape(8.dp))
            .then(
                if (enabled) {
                    Modifier.clickable(
                        interactionSource = mutableInteractionSource,
                        indication = ripple(color = pressedBackgroundColor),
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
    ) {
        Text(
            text = buttonText,
            color = DSTokens.colors.button.primary,
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