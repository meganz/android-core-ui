package mega.android.core.ui.components.button

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import mega.android.core.ui.R
import mega.android.core.ui.components.buttonDefaultHeight
import mega.android.core.ui.preview.CombinedThemePreviews
import mega.android.core.ui.theme.AndroidThemeForPreviews
import mega.android.core.ui.theme.AppTheme

@Composable
fun MegaOutlinedButton(
    modifier: Modifier,
    text: String,
    onClick: () -> Unit,
    leadingIcon: Painter? = null,
    trailingIcon: Painter? = null,
    enabled: Boolean = true,
    isLoading: Boolean = false,
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isButtonPressed by interactionSource.collectIsPressedAsState()
    val borderColor = when {
        enabled.not() -> AppTheme.colors.border.disabled
        isButtonPressed -> AppTheme.colors.button.primary
        else -> AppTheme.colors.button.outline
    }

    OutlinedButton(
        modifier = modifier.heightIn(buttonDefaultHeight),
        interactionSource = interactionSource,
        shape = AppTheme.shapes.small,
        onClick = { if (!isLoading) onClick() },
        border = BorderStroke(width = 2.dp, color = borderColor),
        enabled = enabled
    ) {
        val textColor = if (enabled) AppTheme.colors.text.primary else AppTheme.colors.text.disabled
        val iconColor = if (enabled) AppTheme.colors.icon.primary else AppTheme.colors.icon.disabled

        if (isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.size(buttonDefaultHeight / 2),
                color = AppTheme.colors.icon.accent,
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
                text = text,
                style = AppTheme.typography.labelLarge,
                color = textColor
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
fun MegaOutlinedButtonPreview() {
    AndroidThemeForPreviews {
        MegaOutlinedButton(modifier = Modifier.wrapContentWidth(),
            text = "Outlined Button",
            onClick = { })
    }
}

@CombinedThemePreviews
@Composable
fun MegaOutlinedButtonLoadingPreview() {
    AndroidThemeForPreviews {
        MegaOutlinedButton(modifier = Modifier.wrapContentWidth(),
            text = "Outlined Button",
            isLoading = true,
            onClick = { })
    }
}

@CombinedThemePreviews
@Composable
fun MegaOutlinedButtonWithIconsPreview() {
    AndroidThemeForPreviews {
        MegaOutlinedButton(modifier = Modifier.wrapContentWidth(),
            text = "Add Item",
            leadingIcon = painterResource(id = R.drawable.ic_search_large),
            trailingIcon = painterResource(id = R.drawable.ic_arrow_left),
            onClick = { })
    }
}