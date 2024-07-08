package mega.android.core.ui.components.text

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import mega.android.core.ui.components.textDefaultHeight
import mega.android.core.ui.preview.CombinedThemePreviews
import mega.android.core.ui.theme.AndroidThemeForPreviews
import mega.android.core.ui.theme.AppTheme
import mega.android.core.ui.theme.spacing.LocalSpacing

@Composable
fun PrimaryTopNavigationButton(
    modifier: Modifier,
    text: String,
    onClick: () -> Unit,
    enabled: Boolean = true
) {
    TopNavigationButtons(
        modifier = modifier,
        text = text,
        enabled = enabled,
        onClick = onClick,
        containerColorDefault = AppTheme.colors.button.primary,
        containerColorDisabled = AppTheme.colors.button.disabled,
        containerColorPressed = AppTheme.colors.button.primaryPressed,
        textColorDefault = AppTheme.colors.text.inverseAccent,
        textColorDisabled = AppTheme.colors.text.onColorDisabled,
    )
}

@Composable
fun SecondaryTopNavigationButton(
    modifier: Modifier,
    text: String,
    onClick: () -> Unit,
    enabled: Boolean = true,
) {
    TopNavigationButtons(
        modifier = modifier,
        text = text,
        enabled = enabled,
        onClick = onClick,
        containerColorDefault = AppTheme.colors.button.secondary,
        containerColorDisabled = AppTheme.colors.button.disabled,
        containerColorPressed = AppTheme.colors.button.secondaryPressed,
        textColorDefault = AppTheme.colors.text.accent,
        textColorDisabled = AppTheme.colors.text.onColorDisabled,
    )
}

@Composable
private fun TopNavigationButtons(
    modifier: Modifier,
    text: String,
    enabled: Boolean,
    onClick: () -> Unit,
    containerColorDefault: Color,
    containerColorPressed: Color,
    containerColorDisabled: Color,
    textColorDefault: Color,
    textColorDisabled: Color
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isTextPressed by interactionSource.collectIsPressedAsState()
    val containerColor = when {
        enabled.not() -> containerColorDisabled
        isTextPressed -> containerColorPressed
        else -> containerColorDefault
    }

    val textColor = if (enabled) textColorDefault else textColorDisabled
    Box(
        modifier = modifier
            .heightIn(textDefaultHeight)
            .clip(RoundedCornerShape(8.dp))
            .clickable { onClick() }
            .background(containerColor)
            .padding(horizontal = LocalSpacing.current.x12),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            color = textColor,
            textAlign = TextAlign.Center,
            style = AppTheme.typography.bodyLarge
        )
    }
}

@CombinedThemePreviews
@Composable
private fun PrimaryFilledTextPreview() {
    AndroidThemeForPreviews {
        PrimaryTopNavigationButton(modifier = Modifier.wrapContentWidth(),
            text = "Filled Text",
            onClick = { })
    }
}


@CombinedThemePreviews
@Composable
private fun SecondaryTopNavigationButtonsPreview() {
    AndroidThemeForPreviews {
        SecondaryTopNavigationButton(modifier = Modifier.wrapContentWidth(),
            text = "Filled Text",
            onClick = { })
    }
}