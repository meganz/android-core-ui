package mega.android.core.ui.components.button

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import mega.android.core.ui.R
import mega.android.core.ui.preview.CombinedThemePreviews
import mega.android.core.ui.theme.AndroidThemeForPreviews
import mega.android.core.ui.theme.AppTheme

private val buttonDefaultHeight = 48.dp

@Composable
fun PrimaryFilledButton(
    modifier: Modifier,
    text: String,
    onClick: () -> Unit,
    leadingIcon: Painter? = null,
    trailingIcon: Painter? = null,
    enabled: Boolean = true,
    isLoading: Boolean = false,
) {
    FilledButton(
        modifier = modifier.heightIn(buttonDefaultHeight),
        text = text,
        leadingIcon = leadingIcon,
        trailingIcon = trailingIcon,
        enabled = enabled,
        isLoading = isLoading,
        onClick = onClick,
        containerColorDefault = AppTheme.colors.button.primary,
        containerColorDisabled = AppTheme.colors.button.disabled,
        containerColorPressed = AppTheme.colors.button.primaryPressed,
        textColorDefault = AppTheme.colors.text.inverseAccent,
        textColorDisabled = AppTheme.colors.text.onColorDisabled,
        iconColorDefault = AppTheme.colors.icon.inverseAccent,
        iconColorDisabled = AppTheme.colors.icon.onColorDisabled,
        loaderIconColor = AppTheme.colors.icon.inverseAccent
    )
}

@Composable
fun SecondaryFilledButton(
    modifier: Modifier,
    text: String,
    onClick: () -> Unit,
    leadingIcon: Painter? = null,
    trailingIcon: Painter? = null,
    enabled: Boolean = true,
    isLoading: Boolean = false
) {
    FilledButton(
        modifier = modifier.heightIn(buttonDefaultHeight),
        text = text,
        leadingIcon = leadingIcon,
        trailingIcon = trailingIcon,
        enabled = enabled,
        isLoading = isLoading,
        onClick = onClick,
        containerColorDefault = AppTheme.colors.button.secondary,
        containerColorDisabled = AppTheme.colors.button.disabled,
        containerColorPressed = AppTheme.colors.button.secondaryPressed,
        textColorDefault = AppTheme.colors.text.accent,
        textColorDisabled = AppTheme.colors.text.onColorDisabled,
        iconColorDefault = AppTheme.colors.icon.accent,
        iconColorDisabled = AppTheme.colors.icon.onColorDisabled,
        loaderIconColor = AppTheme.colors.icon.accent
    )
}

@Composable
private fun FilledButton(
    modifier: Modifier,
    text: String,
    leadingIcon: Painter?,
    trailingIcon: Painter?,
    enabled: Boolean,
    isLoading: Boolean,
    onClick: () -> Unit,
    containerColorDefault: Color,
    containerColorPressed: Color,
    containerColorDisabled: Color,
    textColorDefault: Color,
    textColorDisabled: Color,
    iconColorDefault: Color,
    iconColorDisabled: Color,
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
        val textColor = if (enabled) textColorDefault else textColorDisabled
        val iconColor = if (enabled) iconColorDefault else iconColorDisabled

        if (isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.size(buttonDefaultHeight / 2),
                color = loaderIconColor,
            )
        } else {
            leadingIcon?.let {
                Icon(
                    modifier = Modifier
                        .size(buttonDefaultHeight / 2)
                        .padding(end = 8.dp),
                    painter = leadingIcon,
                    tint = iconColor,
                    contentDescription = "Leading Icon"
                )
            }
            Text(
                text = text, style = AppTheme.typography.labelLarge, color = textColor
            )
            trailingIcon?.let {
                Icon(
                    modifier = Modifier
                        .size(buttonDefaultHeight / 2)
                        .padding(start = 8.dp),
                    painter = trailingIcon,
                    tint = iconColor,
                    contentDescription = "Trailing Icon"
                )
            }

        }
    }
}

@CombinedThemePreviews
@Composable
fun PrimaryFilledButtonPreview() {
    AndroidThemeForPreviews {
        PrimaryFilledButton(modifier = Modifier.wrapContentWidth(),
            text = "Filled Button",
            onClick = { })
    }
}

@CombinedThemePreviews
@Composable
fun PrimaryFilledButtonLoadingPreview() {
    AndroidThemeForPreviews {
        PrimaryFilledButton(modifier = Modifier.wrapContentWidth(),
            text = "Filled Button",
            isLoading = true,
            onClick = { })
    }
}

@CombinedThemePreviews
@Composable
fun PrimaryFilledButtonWithIconsPreview() {
    AndroidThemeForPreviews {
        PrimaryFilledButton(modifier = Modifier.wrapContentWidth(),
            text = "Add Item",
            leadingIcon = painterResource(id = R.drawable.ic_search_large),
            trailingIcon = painterResource(id = R.drawable.ic_arrow_left),
            onClick = { })
    }
}