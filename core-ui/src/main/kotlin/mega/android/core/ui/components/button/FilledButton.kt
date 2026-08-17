package mega.android.core.ui.components.button

import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalMinimumInteractiveComponentSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import mega.android.core.ui.R
import mega.android.core.ui.components.buttonDefaultHeight
import mega.android.core.ui.components.buttonDefaultHeightM3
import mega.android.core.ui.components.buttonXSmallHeight
import mega.android.core.ui.components.buttonXSmallHeightM3
import mega.android.core.ui.components.buttonXXSmallHeight
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
 * Full-size Filled button using MEGA brand colours, for a high-emphasis brand CTA.
 */
@Composable
fun BrandFilledButton(
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

/**
 * Filled button at the XSmall size (30dp height, 8dp corner).
 *
 * Sits below [PrimaryFilledButton] for compact surfaces — promotional banners, cards and inline
 * prompts — where the 48dp default is too tall but the call to action still needs full emphasis.
 * Use [PrimaryFilledButtonXXSmall] when the surface is shorter still.
 */
@Composable
fun PrimaryFilledButtonXSmall(
    modifier: Modifier,
    text: String,
    onClick: () -> Unit,
    leadingIcon: Painter? = null,
    trailingIcon: Painter? = null,
    enabled: Boolean = true,
    isLoading: Boolean = false,
) {
    CompactPrimaryFilledButton(
        modifier = modifier,
        text = text,
        onClick = onClick,
        height = buttonXSmallHeight,
        cornerSize = 8.dp,
        contentPadding = PaddingValues(horizontal = 12.dp, vertical = 7.dp),
        leadingIcon = leadingIcon,
        trailingIcon = trailingIcon,
        enabled = enabled,
        isLoading = isLoading,
    )
}

/**
 * Filled button at the XXSmall size (24dp height, 4dp corner).
 *
 * The smallest call to action in the system, for surfaces that leave almost no vertical room —
 * for example the cards in the Home carousel. Prefer [PrimaryFilledButtonXSmall] whenever the
 * surface can spare the extra 6dp.
 */
@Composable
fun PrimaryFilledButtonXXSmall(
    modifier: Modifier,
    text: String,
    onClick: () -> Unit,
    leadingIcon: Painter? = null,
    trailingIcon: Painter? = null,
    enabled: Boolean = true,
    isLoading: Boolean = false,
) {
    CompactPrimaryFilledButton(
        modifier = modifier,
        text = text,
        onClick = onClick,
        height = buttonXXSmallHeight,
        cornerSize = 4.dp,
        contentPadding = PaddingValues(horizontal = 12.dp, vertical = 4.dp),
        leadingIcon = leadingIcon,
        trailingIcon = trailingIcon,
        enabled = enabled,
        isLoading = isLoading,
    )
}

/**
 * [PrimaryFilledButtonXXSmall] in the on-colour palette — a light pill with dark text, for the
 * compact call to action on a banner whose artwork is dark.
 */
@Composable
fun OnColorFilledButtonXXSmall(
    modifier: Modifier,
    text: String,
    onClick: () -> Unit,
    enabled: Boolean = true,
    isLoading: Boolean = false,
) {
    CompactPrimaryFilledButton(
        modifier = modifier,
        text = text,
        onClick = onClick,
        height = buttonXXSmallHeight,
        cornerSize = 4.dp,
        contentPadding = PaddingValues(horizontal = 12.dp, vertical = 4.dp),
        leadingIcon = null,
        trailingIcon = null,
        enabled = enabled,
        isLoading = isLoading,
        containerColor = DSTokens.colors.button.onColor,
        containerColorPressed = DSTokens.colors.button.onColorPressed,
        textColor = DSTokens.colors.text.onColorInverse,
        iconColor = DSTokens.colors.icon.accent,
    )
}

/**
 * Shared body of the compact primary sizes. They differ only in height, corner and content
 * padding; both label their content with `labelMedium`, which is what lets them stay under the
 * 32dp of [PrimaryFilledButtonM3XSmall].
 *
 * Material's [Surface] reserves a 48dp square around any clickable so touch targets cannot
 * collide, which for these sizes means an 18-24dp band of empty layout above and below the pill —
 * enough to push a 90dp banner past 110dp. The button is therefore pinned to [height] and its
 * content measured unbounded, so it occupies exactly its drawn height while the 48dp touch target
 * simply overflows the bounds. The trade-off is that the label cannot grow the button at large
 * font scales.
 */
@Composable
private fun CompactPrimaryFilledButton(
    modifier: Modifier,
    text: String,
    onClick: () -> Unit,
    height: Dp,
    cornerSize: Dp,
    contentPadding: PaddingValues,
    leadingIcon: Painter?,
    trailingIcon: Painter?,
    enabled: Boolean,
    isLoading: Boolean,
    containerColor: Color = DSTokens.colors.button.primary,
    containerColorPressed: Color = DSTokens.colors.button.primaryPressed,
    textColor: Color = DSTokens.colors.text.inverseAccent,
    iconColor: Color = DSTokens.colors.icon.inverseAccent,
) {
    // Material reserves a 48dp touch target as layout around the pill, which at these sizes is
    // twice the button and pushes the surface hosting it out of shape. Dropping the reserved space
    // keeps the layout honest; `heightIn` still lets the label grow with the font scale.
    CompositionLocalProvider(LocalMinimumInteractiveComponentSize provides Dp.Unspecified) {
        FilledButton(
            modifier = modifier,
            cornerSize = cornerSize,
            height = height,
            contentPadding = contentPadding,
            textStyle = AppTheme.typography.labelMedium,
            text = text,
            leadingIcon = leadingIcon,
            trailingIcon = trailingIcon,
            enabled = enabled,
            isLoading = isLoading,
            onClick = onClick,
            containerColorDefault = containerColor,
            containerColorDisabled = DSTokens.colors.button.disabled,
            containerColorPressed = containerColorPressed,
            textColorDefault = textColor,
            textColorDisabled = DSTokens.colors.text.onColorDisabled,
            iconColorDefault = iconColor,
            iconColorDisabled = DSTokens.colors.icon.onColorDisabled,
            loaderIconColor = iconColor,
        )
    }
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
    height: Dp = buttonDefaultHeight,
    cornerSize: Dp = 8.dp,
    contentPadding: PaddingValues = ButtonDefaults.ContentPadding,
    textStyle: TextStyle = AppTheme.typography.labelLarge,
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
                text = text,
                style = textStyle,
                color = textColor,
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
private fun PrimaryFilledButtonXSmallPreview() {
    AndroidThemeForPreviews {
        PrimaryFilledButtonXSmall(
            modifier = Modifier.wrapContentWidth(),
            text = "Grab deal",
            onClick = { },
        )
    }
}

@CombinedThemePreviews
@Composable
private fun PrimaryFilledButtonXXSmallPreview() {
    AndroidThemeForPreviews {
        PrimaryFilledButtonXXSmall(
            modifier = Modifier.wrapContentWidth(),
            text = "Grab deal",
            onClick = { },
        )
    }
}

@CombinedThemePreviews
@Composable
private fun OnColorFilledButtonXXSmallPreview() {
    AndroidThemeForPreviews {
        OnColorFilledButtonXXSmall(
            modifier = Modifier.wrapContentWidth(),
            text = "Learn more",
            onClick = { },
        )
    }
}

@CombinedThemePreviews
@Composable
private fun PrimaryFilledButtonXXSmallDisabledPreview() {
    AndroidThemeForPreviews {
        PrimaryFilledButtonXXSmall(
            modifier = Modifier.wrapContentWidth(),
            text = "Grab deal",
            enabled = false,
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
private fun BrandFilledButtonPreview() {
    AndroidThemeForPreviews {
        BrandFilledButton(
            modifier = Modifier.wrapContentWidth(),
            text = "Get Pro I for €4.99/month",
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