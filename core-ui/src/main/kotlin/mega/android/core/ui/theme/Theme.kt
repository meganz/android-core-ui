package mega.android.core.ui.theme

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowHeightSizeClass
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import mega.android.core.ui.theme.AppTheme.typography
import mega.android.core.ui.theme.activity.LocalActivity
import mega.android.core.ui.theme.devicetype.DeviceType
import mega.android.core.ui.theme.devicetype.LocalDeviceType
import mega.android.core.ui.tokens.theme.shape.shapes
import mega.android.core.ui.theme.spacing.Dimensions
import mega.android.core.ui.theme.spacing.LocalSpacing
import mega.android.core.ui.theme.values.BackgroundColor
import mega.android.core.ui.theme.values.ComponentsColor
import mega.android.core.ui.theme.values.IconColor
import mega.android.core.ui.theme.values.LinkColor
import mega.android.core.ui.theme.values.SupportColor
import mega.android.core.ui.theme.values.TextColor
import mega.android.core.ui.tokens.theme.DSTokens
import mega.android.core.ui.tokens.theme.LocalColorPalette
import mega.android.core.ui.tokens.theme.colors.DSColors
import mega.android.core.ui.tokens.theme.tokens.AndroidNewSemanticTokensDark
import mega.android.core.ui.tokens.theme.tokens.AndroidNewSemanticTokensLight

/**
 * Only to be used for Previews within core-ui and any library module that depends on core-ui
 *
 * @param content
 */
@Composable
fun AndroidThemeForPreviews(
    content: @Composable () -> Unit,
) = AndroidTheme(
    isDark = isSystemInDarkTheme(),
    content = content,
)

/**
 * Default theme to be used in fragments or activities using components in this library
 * @param isDark
 * @param fromAutofill
 * @param useLegacyStatusBarColor statusBarColor is deprecated and it should not be used. This parameter is to avoid using it in screens already migrated to edge-to-edge. It will be removed once all screens support edge-to-edge.
 */
@Composable
fun AndroidTheme(
    isDark: Boolean,
    fromAutofill: Boolean = false,
    useLegacyStatusBarColor: Boolean = true,
    content: @Composable () -> Unit,
) {

    val semanticTokens = if (isDark) {
        AndroidNewSemanticTokensDark
    } else {
        AndroidNewSemanticTokensLight
    }
    val colors = DSColors(semanticTokens, !isDark)
    val colorPalette by remember(colors) {
        mutableStateOf(colors)
    }

    val view = LocalView.current
    val activity = view.context.findActivity()
    if (!view.isInEditMode && useLegacyStatusBarColor) {
        SideEffect {
            activity?.window?.let { window ->
                window.statusBarColor =
                    (if (fromAutofill) Color.Transparent else colors.background.pageBackground).toArgb()
                window.navigationBarColor = colors.background.pageBackground.toArgb()
                WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !isDark
                WindowCompat.getInsetsController(window, view).isAppearanceLightNavigationBars =
                    !isDark
                window.decorView.setBackgroundColor(if (fromAutofill) Color.Transparent.toArgb() else colors.background.pageBackground.toArgb()) //Added to fix keyboard backdrop issue in screen
            }
        }
    }

    val windowSize = getWindowSize()
    val deviceType = if (
        windowSize.widthSizeClass >= WindowWidthSizeClass.Medium &&
        windowSize.heightSizeClass >= WindowHeightSizeClass.Medium
    ) {
        DeviceType.Tablet
    } else {
        DeviceType.Phone
    }
    CompositionLocalProvider(
        LocalColorPalette provides colorPalette,
        LocalSpacing provides Dimensions(),
        LocalDeviceType provides deviceType,
        LocalActivity provides activity
    ) {
        MaterialTheme(
            shapes = shapes,
            typography = typography,
            content = content
        )
    }
}

@Composable
internal fun DSTokens.textColor(textColor: TextColor) =
    textColor.getTextColor(colors.text)

@Composable
internal fun DSTokens.iconColor(iconColor: IconColor) =
    iconColor.getIconColor(colors.icon)

@Composable
internal fun DSTokens.supportColor(supportColor: SupportColor) =
    supportColor.getSupportColor(colors.support)

@Composable
internal fun DSTokens.linkColor(linkColor: LinkColor) =
    linkColor.getLinkColor(colors.link)

@Composable
internal fun DSTokens.componentsColor(componentsColor: ComponentsColor) =
    componentsColor.getComponentsColor(colors.components)

@Composable
internal fun DSTokens.backgroundColor(backgroundColor: BackgroundColor) =
    backgroundColor.getBackgroundColor(colors.background)


private fun Context.findActivity(): Activity? = when (this) {
    is Activity -> this
    is ContextWrapper -> baseContext.findActivity()
    else -> null
}

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@Composable
private fun getWindowSize(): WindowSizeClass {
    val configuration = LocalConfiguration.current
    val size = DpSize(configuration.screenWidthDp.dp, configuration.screenHeightDp.dp)
    return WindowSizeClass.calculateFromSize(size)
}

object AppTheme {
    val typography: Typography
        @Composable
        get() = megaTypographyToken(MaterialTheme.typography)
}

@Composable
internal fun megaTypographyToken(typography: Typography = MaterialTheme.typography) =
    typography.copy(
        headlineLarge = typography.headlineLarge.copy(fontWeight = FontWeight.SemiBold),
        headlineMedium = typography.headlineMedium.copy(fontWeight = FontWeight.SemiBold),
        headlineSmall = typography.headlineSmall.copy(fontWeight = FontWeight.SemiBold),
        titleLarge = typography.titleLarge.copy(fontWeight = FontWeight.Medium),
    )
