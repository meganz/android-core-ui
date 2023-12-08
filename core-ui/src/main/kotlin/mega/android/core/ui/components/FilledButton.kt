package mega.android.core.ui.components

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import mega.android.core.ui.preview.CombinedThemePreviews
import mega.android.core.ui.theme.AndroidThemeForPreviews
import mega.android.core.ui.theme.AppTheme

private val buttonDefaultHeight = 48.dp

@Composable
fun PrimaryFilledButton(
    modifier: Modifier,
    text: String,
    onClick: () -> Unit,
    enabled: Boolean = true,
    isLoading: Boolean = false,
    height: Dp = buttonDefaultHeight
) {
    FilledButton(
        modifier = modifier
            .height(height),
        text = text,
        enabled = enabled,
        isLoading = isLoading,
        onClick = onClick,
        containerColorDefault = AppTheme.colors.button.primary,
        containerColorDisabled = AppTheme.colors.button.disabled,
        containerColorPressed = AppTheme.colors.button.primaryPressed,
        textColorDefault = AppTheme.colors.text.inverseAccent,
        textColorDisabled = AppTheme.colors.text.onColorDisabled,
        loaderIconColor = AppTheme.colors.icon.inverseAccent
    )
}

@Composable
fun SecondaryFilledButton(
    modifier: Modifier,
    text: String,
    onClick: () -> Unit,
    enabled: Boolean = true,
    isLoading: Boolean = false,
    height: Dp = buttonDefaultHeight
) {
    FilledButton(
        modifier = modifier
            .height(height),
        text = text,
        enabled = enabled,
        isLoading = isLoading,
        onClick = onClick,
        containerColorDefault = AppTheme.colors.button.secondary,
        containerColorDisabled = AppTheme.colors.button.disabled,
        containerColorPressed = AppTheme.colors.button.secondaryPressed,
        textColorDefault = AppTheme.colors.text.accent,
        textColorDisabled = AppTheme.colors.text.onColorDisabled,
        loaderIconColor = AppTheme.colors.icon.accent
    )
}

@Composable
private fun FilledButton(
    modifier: Modifier,
    text: String,
    enabled: Boolean,
    isLoading: Boolean,
    onClick: () -> Unit,
    containerColorDefault: Color,
    containerColorPressed: Color,
    containerColorDisabled: Color,
    textColorDefault: Color,
    textColorDisabled: Color,
    loaderIconColor: Color,
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isButtonPressed by interactionSource.collectIsPressedAsState()
    val containerColor = when {
        enabled.not() -> containerColorDisabled
        isButtonPressed -> containerColorPressed
        else -> containerColorDefault
    }

    Button(
        modifier = modifier,
        interactionSource = interactionSource,
        shape = RoundedCornerShape(8.dp),
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = containerColor,
        ),
        enabled = enabled
    ) {
        val contentColor = if (enabled) textColorDefault else textColorDisabled

        if (isLoading) {
            CircularProgressIndicator(
                modifier = Modifier
                    .size(buttonDefaultHeight / 2),
                color = loaderIconColor,
            )
        } else {
            Text(
                text = text,
                style = AppTheme.typography.labelLarge,
                color = contentColor
            )
        }
    }
}

@CombinedThemePreviews
@Composable
fun FilledButtonPreview() {
    AndroidThemeForPreviews {
        PrimaryFilledButton(
            modifier = Modifier.fillMaxWidth(),
            text = "Filled Button",
            onClick = { }
        )
    }
}

@CombinedThemePreviews
@Composable
fun FilledButtonLoadingPreview() {
    AndroidThemeForPreviews {
        PrimaryFilledButton(
            modifier = Modifier.fillMaxWidth(),
            text = "Filled Button",
            isLoading = true,
            onClick = { }
        )
    }
}