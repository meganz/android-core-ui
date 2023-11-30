package mega.android.core.ui.components

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextDecoration
import mega.android.core.ui.theme.AppTheme

@Composable
fun PrimaryTextButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true
) {
    BaseTextButton(
        modifier = modifier,
        text = text,
        enabled = enabled,
        textColorDefault = AppTheme.colors.text.primary,
        textColorDisabled = AppTheme.colors.text.disabled,
        textColorPressed = AppTheme.colors.button.primaryPressed,
        onClick = onClick
    )
}

@Composable
fun BaseTextButton(
    modifier: Modifier,
    text: String,
    enabled: Boolean,
    textColorDefault: Color,
    textColorDisabled: Color,
    textColorPressed: Color,
    onClick: () -> Unit,
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isButtonPressed by interactionSource.collectIsPressedAsState()
    val textColor = when {
        enabled.not() -> textColorDisabled
        isButtonPressed -> textColorPressed
        else -> textColorDefault
    }

    TextButton(
        modifier = modifier,
        onClick = onClick,
        enabled = enabled,
    ) {
        Text(
            text = text,
            textDecoration = TextDecoration.Underline,
            style = AppTheme.typography.labelLarge,
            color = textColor
        )
    }
}