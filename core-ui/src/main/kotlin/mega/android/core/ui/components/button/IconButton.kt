package mega.android.core.ui.components.button

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import mega.android.core.ui.R
import mega.android.core.ui.preview.CombinedThemePreviews
import mega.android.core.ui.theme.AndroidThemeForPreviews
import mega.android.core.ui.tokens.theme.DSTokens
import mega.android.core.ui.theme.spacing.LocalSpacing

@Composable
fun PrimaryLargeIconButton(
    icon: Painter,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    contentDescription: String? = null
) {
    BaseIconButton(
        modifier = modifier.size(LocalSpacing.current.x56),
        icon = icon,
        shape = DSTokens.shapes.small,
        enabled = enabled,
        containerColorDefault = DSTokens.colors.button.primary,
        containerColorPressed = DSTokens.colors.button.primaryPressed,
        containerColorDisabled = DSTokens.colors.button.disabled,
        iconColorDefault = DSTokens.colors.icon.inverseAccent,
        iconColorDisabled = DSTokens.colors.icon.onColorDisabled,
        contentDescription = contentDescription,
        onClick = onClick
    )
}

@Composable
fun PrimarySmallIconButton(
    icon: Painter,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    contentDescription: String? = null
) {
    BaseIconButton(
        modifier = modifier.size(LocalSpacing.current.x40),
        icon = icon,
        shape = DSTokens.shapes.extraSmall,
        enabled = enabled,
        containerColorDefault = DSTokens.colors.button.primary,
        containerColorPressed = DSTokens.colors.button.primaryPressed,
        containerColorDisabled = DSTokens.colors.button.disabled,
        iconColorDefault = DSTokens.colors.icon.inverseAccent,
        iconColorDisabled = DSTokens.colors.icon.onColorDisabled,
        contentDescription = contentDescription,
        onClick = onClick
    )
}

@Composable
fun SecondaryLargeIconButton(
    icon: Painter,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    contentDescription: String? = null
) {
    BaseIconButton(
        modifier = modifier.size(LocalSpacing.current.x56),
        icon = icon,
        shape = DSTokens.shapes.small,
        enabled = enabled,
        containerColorDefault = DSTokens.colors.button.secondary,
        containerColorPressed = DSTokens.colors.button.secondary,
        containerColorDisabled = DSTokens.colors.button.disabled,
        iconColorDefault = DSTokens.colors.icon.primary,
        iconColorDisabled = DSTokens.colors.icon.onColorDisabled,
        contentDescription = contentDescription,
        onClick = onClick
    )
}

@Composable
fun SecondarySmallIconButton(
    icon: Painter,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    contentDescription: String? = null
) {
    BaseIconButton(
        modifier = modifier.size(LocalSpacing.current.x40),
        icon = icon,
        shape = DSTokens.shapes.extraSmall,
        enabled = enabled,
        containerColorDefault = DSTokens.colors.button.secondary,
        containerColorPressed = DSTokens.colors.button.secondary,
        containerColorDisabled = DSTokens.colors.button.disabled,
        iconColorDefault = DSTokens.colors.icon.primary,
        iconColorDisabled = DSTokens.colors.icon.onColorDisabled,
        contentDescription = contentDescription,
        onClick = onClick
    )
}

@Composable
fun SecondaryNavigationIconButton(
    icon: Painter,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    contentDescription: String? = null
) {
    BaseIconButton(
        modifier = modifier.size(LocalSpacing.current.x32),
        icon = icon,
        shape = RoundedCornerShape(LocalSpacing.current.x8),
        enabled = enabled,
        containerColorDefault = DSTokens.colors.button.secondary,
        containerColorPressed = DSTokens.colors.button.secondary,
        containerColorDisabled = DSTokens.colors.button.disabled,
        iconColorDefault = DSTokens.colors.icon.primary,
        iconColorDisabled = DSTokens.colors.icon.onColorDisabled,
        contentDescription = contentDescription,
        onClick = onClick
    )
}

@Composable
fun BaseIconButton(
    icon: Painter,
    shape: Shape,
    enabled: Boolean,
    containerColorDefault: Color,
    containerColorPressed: Color,
    containerColorDisabled: Color,
    iconColorDefault: Color,
    iconColorDisabled: Color,
    contentDescription: String?,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isButtonPressed by interactionSource.collectIsPressedAsState()
    val containerColor = when {
        isButtonPressed -> containerColorPressed
        else -> containerColorDefault
    }
    val iconColor = when {
        enabled.not() -> iconColorDisabled
        else -> iconColorDefault
    }

    FilledIconButton(
        modifier = modifier,
        shape = shape,
        colors = IconButtonDefaults.iconButtonColors(
            containerColor = containerColor,
            disabledContainerColor = containerColorDisabled
        ),
        interactionSource = interactionSource,
        enabled = enabled,
        onClick = onClick,
    ) {
        Icon(
            modifier = Modifier.size(LocalSpacing.current.x24),
            painter = icon,
            tint = iconColor,
            contentDescription = contentDescription
        )
    }
}

@CombinedThemePreviews
@Composable
private fun PrimaryLargeIconButtonPreview() {
    AndroidThemeForPreviews {
        PrimaryLargeIconButton(
            icon = painterResource(id = R.drawable.ic_close_medium_thin_outline),
            onClick = {}
        )
    }
}

@CombinedThemePreviews
@Composable
private fun PrimarySmallIconButtonPreview() {
    AndroidThemeForPreviews {
        PrimarySmallIconButton(
            icon = painterResource(id = R.drawable.ic_close_medium_thin_outline),
            onClick = {}
        )
    }
}

@CombinedThemePreviews
@Composable
private fun SecondaryLargeIconButtonPreview() {
    AndroidThemeForPreviews {
        SecondaryLargeIconButton(
            icon = painterResource(id = R.drawable.ic_close_medium_thin_outline),
            onClick = {}
        )
    }
}

@CombinedThemePreviews
@Composable
private fun SecondarySmallIconButtonPreview() {
    AndroidThemeForPreviews {
        SecondarySmallIconButton(
            icon = painterResource(id = R.drawable.ic_close_medium_thin_outline),
            onClick = {}
        )
    }
}

@CombinedThemePreviews
@Composable
private fun SecondaryNavigationIconButtonPreview() {
    AndroidThemeForPreviews {
        SecondaryNavigationIconButton(
            icon = painterResource(id = R.drawable.ic_close_medium_thin_outline),
            onClick = {}
        )
    }
}