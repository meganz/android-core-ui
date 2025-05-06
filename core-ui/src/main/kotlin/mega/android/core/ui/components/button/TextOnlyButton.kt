package mega.android.core.ui.components.button

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextDecoration
import mega.android.core.ui.preview.CombinedThemePreviews
import mega.android.core.ui.theme.AndroidThemeForPreviews
import mega.android.core.ui.theme.AppTheme
import mega.android.core.ui.tokens.theme.DSTokens

@Composable
fun TextOnlyButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true
) {
    BaseTextButton(
        modifier = modifier,
        text = text,
        enabled = enabled,
        textColorDefault = DSTokens.colors.button.primary,
        textColorDisabled = DSTokens.colors.text.disabled,
        textColorPressed = DSTokens.colors.button.primary,
        onClick = onClick
    )
}

@Composable
private fun BaseTextButton(
    modifier: Modifier,
    text: String,
    enabled: Boolean,
    textColorDefault: Color,
    textColorDisabled: Color,
    textColorPressed: Color,
    onClick: () -> Unit,
    contentPadding: PaddingValues = ButtonDefaults.TextButtonContentPadding
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
        contentPadding = contentPadding,
    ) {
        Text(
            text = text,
            textDecoration = TextDecoration.Underline,
            style = AppTheme.typography.labelLarge,
            color = textColor
        )
    }
}

@CombinedThemePreviews
@Composable
private fun PrimaryTextButtonPreview() {
    AndroidThemeForPreviews {
        TextOnlyButton(text = "TextButton", onClick = {})
    }
}