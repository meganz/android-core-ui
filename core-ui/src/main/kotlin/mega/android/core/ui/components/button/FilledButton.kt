package mega.android.core.ui.components.button

import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import mega.android.core.ui.R
import mega.android.core.ui.components.buttonDefaultHeight
import mega.android.core.ui.components.buttonDefaultHeightM3
import mega.android.core.ui.components.buttonXSmallHeightM3
import mega.android.core.ui.preview.CombinedThemePreviews
import mega.android.core.ui.theme.AndroidThemeForPreviews
import mega.android.core.ui.theme.AppTheme
import mega.android.core.ui.tokens.theme.DSTokens

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
        modifier = modifier,
        text = text,
        leadingIcon = leadingIcon,
        trailingIcon = trailingIcon,
        enabled = enabled,
        isLoading = isLoading,
        onClick = onClick,
        containerColorDefault = DSTokens.colors.button.primary,
        containerColorDisabled = DSTokens.colors.button.disabled,
        containerColorPressed = DSTokens.colors.button.primaryPressed,
        textColorDefault = DSTokens.colors.text.inverseAccent,
        textColorDisabled = DSTokens.colors.text.onColorDisabled,
        iconColorDefault = DSTokens.colors.icon.inverseAccent,
        iconColorDisabled = DSTokens.colors.icon.onColorDisabled,
        loaderIconColor = DSTokens.colors.icon.inverseAccent
    )
}

@Composable
fun PrimaryFilledButtonM3(
    modifier: Modifier,
    text: String,
    onClick: () -> Unit,
    leadingIcon: Painter? = null,
    trailingIcon: Painter? = null,
    enabled: Boolean = true,
    isLoading: Boolean = false,
) {
    FilledButton(
        modifier = modifier,
        cornerSize = 12.dp,
        height = buttonDefaultHeightM3,
        text = text,
        leadingIcon = leadingIcon,
        trailingIcon = trailingIcon,
        enabled = enabled,
        isLoading = isLoading,
        onClick = onClick,
        containerColorDefault = DSTokens.colors.button.primary,
        containerColorDisabled = DSTokens.colors.button.disabled,
        containerColorPressed = DSTokens.colors.button.primaryPressed,
        textColorDefault = DSTokens.colors.text.inverseAccent,
        textColorDisabled = DSTokens.colors.text.onColorDisabled,
        iconColorDefault = DSTokens.colors.icon.inverseAccent,
        iconColorDisabled = DSTokens.colors.icon.onColorDisabled,
        loaderIconColor = DSTokens.colors.icon.inverseAccent
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
        modifier = modifier,
        text = text,
        leadingIcon = leadingIcon,
        trailingIcon = trailingIcon,
        enabled = enabled,
        isLoading = isLoading,
        onClick = onClick,
        containerColorDefault = DSTokens.colors.button.secondary,
        containerColorDisabled = DSTokens.colors.button.disabled,
        containerColorPressed = DSTokens.colors.button.secondaryPressed,
        textColorDefault = DSTokens.colors.text.accent,
        textColorDisabled = DSTokens.colors.text.onColorDisabled,
        iconColorDefault = DSTokens.colors.icon.accent,
        iconColorDisabled = DSTokens.colors.icon.onColorDisabled,
        loaderIconColor = DSTokens.colors.icon.accent
    )
}

@Composable
fun SecondaryFilledButtonM3(
    modifier: Modifier,
    text: String,
    onClick: () -> Unit,
    leadingIcon: Painter? = null,
    trailingIcon: Painter? = null,
    enabled: Boolean = true,
    isLoading: Boolean = false,
) {
    FilledButton(
        modifier = modifier,
        cornerSize = 12.dp,
        height = buttonDefaultHeightM3,
        text = text,
        leadingIcon = leadingIcon,
        trailingIcon = trailingIcon,
        enabled = enabled,
        isLoading = isLoading,
        onClick = onClick,
        containerColorDefault = DSTokens.colors.button.secondary,
        containerColorDisabled = DSTokens.colors.button.disabled,
        containerColorPressed = DSTokens.colors.button.secondaryPressed,
        textColorDefault = DSTokens.colors.text.accent,
        textColorDisabled = DSTokens.colors.text.onColorDisabled,
        iconColorDefault = DSTokens.colors.icon.accent,
        iconColorDisabled = DSTokens.colors.icon.onColorDisabled,
        loaderIconColor = DSTokens.colors.icon.accent
    )
}

/**
 * Material 3 Filled button at the XSmall size (32dp height, 12dp corner).
 *
 * Use for compact contexts like banners and inline prompts where the standard
 * [PrimaryFilledButtonM3] would feel too tall.
 */
@Composable
fun PrimaryFilledButtonM3XSmall(
    modifier: Modifier,
    text: String,
    onClick: () -> Unit,
    leadingIcon: Painter? = null,
    trailingIcon: Painter? = null,
    enabled: Boolean = true,
    isLoading: Boolean = false,
) {
    FilledButton(
        modifier = modifier,
        cornerSize = xSmallCornerSize,
        height = buttonXSmallHeightM3,
        contentPadding = xSmallContentPadding,
        text = text,
        leadingIcon = leadingIcon,
        trailingIcon = trailingIcon,
        enabled = enabled,
        isLoading = isLoading,
        onClick = onClick,
        containerColorDefault = DSTokens.colors.button.primary,
        containerColorDisabled = DSTokens.colors.button.disabled,
        containerColorPressed = DSTokens.colors.button.primaryPressed,
        textColorDefault = DSTokens.colors.text.inverseAccent,
        textColorDisabled = DSTokens.colors.text.onColorDisabled,
        iconColorDefault = DSTokens.colors.icon.inverseAccent,
        iconColorDisabled = DSTokens.colors.icon.onColorDisabled,
        loaderIconColor = DSTokens.colors.icon.inverseAccent,
    )
}

/**
 * Material 3 Filled button (secondary variant) at the XSmall size (32dp height, 12dp corner).
 */
@Composable
fun SecondaryFilledButtonM3XSmall(
    modifier: Modifier,
    text: String,
    onClick: () -> Unit,
    leadingIcon: Painter? = null,
    trailingIcon: Painter? = null,
    enabled: Boolean = true,
    isLoading: Boolean = false,
) {
    FilledButton(
        modifier = modifier,
        cornerSize = xSmallCornerSize,
        height = buttonXSmallHeightM3,
        contentPadding = xSmallContentPadding,
        text = text,
        leadingIcon = leadingIcon,
        trailingIcon = trailingIcon,
        enabled = enabled,
        isLoading = isLoading,
        onClick = onClick,
        containerColorDefault = DSTokens.colors.button.secondary,
        containerColorDisabled = DSTokens.colors.button.disabled,
        containerColorPressed = DSTokens.colors.button.secondaryPressed,
        textColorDefault = DSTokens.colors.text.accent,
        textColorDisabled = DSTokens.colors.text.onColorDisabled,
        iconColorDefault = DSTokens.colors.icon.accent,
        iconColorDisabled = DSTokens.colors.icon.onColorDisabled,
        loaderIconColor = DSTokens.colors.icon.accent,
    )
}

/**
 * Material 3 Filled button (brand variant) at the XSmall size (32dp height, 12dp corner).
 *
 * Uses MEGA brand colors for the container. Pair with [SecondaryFilledButtonM3XSmall] for
 * a primary brand CTA + secondary action layout (e.g. "Create a free account" + "Log in").
 */
@Composable
fun BrandFilledButtonM3XSmall(
    modifier: Modifier,
    text: String,
    onClick: () -> Unit,
    leadingIcon: Painter? = null,
    trailingIcon: Painter? = null,
    enabled: Boolean = true,
    isLoading: Boolean = false,
) {
    FilledButton(
        modifier = modifier,
        cornerSize = xSmallCornerSize,
        height = buttonXSmallHeightM3,
        contentPadding = xSmallContentPadding,
        text = text,
        leadingIcon = leadingIcon,
        trailingIcon = trailingIcon,
        enabled = enabled,
        isLoading = isLoading,
        onClick = onClick,
        containerColorDefault = DSTokens.colors.button.brand,
        containerColorDisabled = DSTokens.colors.button.disabled,
        containerColorPressed = DSTokens.colors.button.brandPressed,
        textColorDefault = DSTokens.colors.text.inverseAccent,
        textColorDisabled = DSTokens.colors.text.onColorDisabled,
        iconColorDefault = DSTokens.colors.icon.inverseAccent,
        iconColorDisabled = DSTokens.colors.icon.onColorDisabled,
        loaderIconColor = DSTokens.colors.icon.inverseAccent,
    )
}

private val xSmallCornerSize = 12.dp
private val xSmallContentPadding =
    PaddingValues(horizontal = 12.dp, vertical = 0.dp)

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
    height: Dp = buttonDefaultHeight,
    cornerSize: Dp = 8.dp,
    contentPadding: PaddingValues = ButtonDefaults.ContentPadding,
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isButtonPressed by interactionSource.collectIsPressedAsState()
    val containerColor = when {
        isButtonPressed -> containerColorPressed
        else -> containerColorDefault
    }

    Button(
        modifier = modifier.heightIn(height),
        interactionSource = interactionSource,
        shape = RoundedCornerShape(cornerSize),
        onClick = { if (!isLoading) onClick() },
        colors = ButtonDefaults.buttonColors(
            containerColor = containerColor,
            disabledContainerColor = containerColorDisabled
        ),
        contentPadding = contentPadding,
        enabled = enabled
    ) {
        val textColor = if (enabled) textColorDefault else textColorDisabled
        val iconColor = if (enabled) iconColorDefault else iconColorDisabled

        if (isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.size(height / 2),
                color = loaderIconColor,
            )
        } else {
            leadingIcon?.let {
                Icon(
                    modifier = Modifier
                        .padding(end = 8.dp)
                        .size(height / 2),
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
                        .padding(start = 8.dp)
                        .size(height / 2),
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
private fun PrimaryFilledButtonPreview() {
    AndroidThemeForPreviews {
        PrimaryFilledButton(
            modifier = Modifier.wrapContentWidth(),
            text = "Filled Button",
            onClick = { })
    }
}

@CombinedThemePreviews
@Composable
private fun PrimaryFilledButtonLoadingPreview() {
    AndroidThemeForPreviews {
        PrimaryFilledButton(
            modifier = Modifier.wrapContentWidth(),
            text = "Filled Button",
            isLoading = true,
            onClick = { })
    }
}

@CombinedThemePreviews
@Composable
private fun PrimaryFilledButtonWithIconsPreview() {
    AndroidThemeForPreviews {
        PrimaryFilledButton(
            modifier = Modifier.wrapContentWidth(),
            text = "Add Item",
            leadingIcon = painterResource(id = R.drawable.ic_search_large_medium_thin_outline),
            trailingIcon = painterResource(id = R.drawable.ic_arrow_left_medium_thin_outline),
            onClick = { })
    }
}

// preview for button in disabled state
@CombinedThemePreviews
@Composable
private fun PrimaryFilledButtonDisabledPreview() {
    AndroidThemeForPreviews {
        Box(
            modifier = Modifier
                .padding(20.dp)
                .background(DSTokens.colors.background.pageBackground)
        ) {
            PrimaryFilledButton(
                modifier = Modifier.wrapContentWidth(),
                text = "Filled Button",
                enabled = false,
                onClick = { })
        }
    }
}

@CombinedThemePreviews
@Composable
private fun PrimaryFilledButtonM3XSmallPreview() {
    AndroidThemeForPreviews {
        PrimaryFilledButtonM3XSmall(
            modifier = Modifier.wrapContentWidth(),
            text = "Create a free account",
            onClick = { },
        )
    }
}

@CombinedThemePreviews
@Composable
private fun SecondaryFilledButtonM3XSmallPreview() {
    AndroidThemeForPreviews {
        SecondaryFilledButtonM3XSmall(
            modifier = Modifier.wrapContentWidth(),
            text = "Log in",
            onClick = { },
        )
    }
}

@CombinedThemePreviews
@Composable
private fun BrandFilledButtonM3XSmallPreview() {
    AndroidThemeForPreviews {
        BrandFilledButtonM3XSmall(
            modifier = Modifier.wrapContentWidth(),
            text = "Create a free account",
            onClick = { },
        )
    }
}

@CombinedThemePreviews
@Composable
private fun PrimaryFilledButtonM3XSmallDisabledPreview() {
    AndroidThemeForPreviews {
        Box(
            modifier = Modifier
                .padding(20.dp)
                .background(DSTokens.colors.background.pageBackground)
        ) {
            PrimaryFilledButtonM3XSmall(
                modifier = Modifier.wrapContentWidth(),
                text = "Disabled",
                enabled = false,
                onClick = { },
            )
        }
    }
}