package mega.android.core.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import mega.android.core.ui.theme.AppTheme.typography
import mega.android.core.ui.theme.colors.AppColors
import mega.android.core.ui.theme.shape.shapes
import mega.android.core.ui.theme.spacing.Dimensions
import mega.android.core.ui.theme.spacing.LocalSpacing
import mega.android.core.ui.theme.values.IconColor
import mega.android.core.ui.theme.values.LinkColor
import mega.android.core.ui.theme.values.SupportColor
import mega.android.core.ui.theme.values.TextColor
import mega.android.core.ui.tokens.theme.tokens.AndroidNewSemanticTokensDark
import mega.android.core.ui.tokens.theme.tokens.AndroidNewSemanticTokensLight
import mega.android.core.ui.tokens.theme.tokens.Background
import mega.android.core.ui.tokens.theme.tokens.Border
import mega.android.core.ui.tokens.theme.tokens.Button
import mega.android.core.ui.tokens.theme.tokens.Components
import mega.android.core.ui.tokens.theme.tokens.Focus
import mega.android.core.ui.tokens.theme.tokens.Icon
import mega.android.core.ui.tokens.theme.tokens.Indicator
import mega.android.core.ui.tokens.theme.tokens.Link
import mega.android.core.ui.tokens.theme.tokens.Notifications
import mega.android.core.ui.tokens.theme.tokens.SemanticTokens
import mega.android.core.ui.tokens.theme.tokens.Support
import mega.android.core.ui.tokens.theme.tokens.Text

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
    darkColorTokens = AndroidNewSemanticTokensDark,
    lightColorTokens = AndroidNewSemanticTokensLight,
    content = content,
)

/**
 * Default theme to be used in fragments or activities using components in this library
 */
@Composable
fun AndroidTheme(
    isDark: Boolean,
    fromAutofill: Boolean = false,
    content: @Composable () -> Unit,
) = AndroidTheme(
    isDark = isDark,
    darkColorTokens = AndroidNewSemanticTokensDark,
    lightColorTokens = AndroidNewSemanticTokensLight,
    fromAutofill = fromAutofill,
    content = content,
)

/**
 * This theme is just to add some flexibility, the version with default tokens should be preferred.
 * This can be used to inject other semantic tokens, for instance to use the components in this library with TEMP-tokens.
 */
@Composable
fun AndroidTheme(
    isDark: Boolean,
    darkColorTokens: SemanticTokens,
    lightColorTokens: SemanticTokens,
    fromAutofill: Boolean = false,
    content: @Composable () -> Unit,
) {

    val semanticTokens = if (isDark) {
        darkColorTokens
    } else {
        lightColorTokens
    }
    val colors = AppColors(semanticTokens, !isDark)
    val colorPalette by remember(colors) {
        mutableStateOf(colors)
    }

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor =
                (if (fromAutofill) Color.Transparent else colors.background.pageBackground).toArgb()
            window.navigationBarColor = colors.background.pageBackground.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !isDark
            WindowCompat.getInsetsController(window, view).isAppearanceLightNavigationBars = !isDark
            window.decorView.setBackgroundColor(colors.background.pageBackground.toArgb()) //Added to fix keyboard backdrop issue in screen
        }
    }

    CompositionLocalProvider(
        LocalColorPalette provides colorPalette,
        LocalSpacing provides Dimensions()
    ) {
        MaterialTheme(
            shapes = shapes,
            typography = typography,
            content = content
        )
    }
}

object AppTheme {
    internal val colors: AppColors
        @Composable
        get() = LocalColorPalette.current

    internal val shapes: Shapes
        @Composable
        get() = mega.android.core.ui.theme.shape.shapes

    @Composable
    internal fun textColor(textColor: TextColor) =
        textColor.getTextColor(LocalColorPalette.current.text)

    @Composable
    internal fun iconColor(iconColor: IconColor) =
        iconColor.getIconColor(LocalColorPalette.current.icon)

    @Composable
    internal fun supportColor(supportColor: SupportColor) =
        supportColor.getSupportColor(LocalColorPalette.current.support)

    @Composable
    internal fun linkColor(linkColor: LinkColor) =
        linkColor.getLinkColor(LocalColorPalette.current.link)

    val typography: Typography
        @Composable
        get() = MaterialTheme.typography
}

internal val LocalColorPalette = staticCompositionLocalOf {
    testColorPalette
}

/**
 * [AppColors] default palette for testing purposes, all magenta to easily detect it.
 */
private val testColorPalette = AppColors(
    object : SemanticTokens {
        override val focus = Focus()
        override val indicator = Indicator()
        override val support = Support()
        override val button = Button()
        override val text = Text()
        override val background = Background()
        override val icon = Icon()
        override val components = Components()
        override val link = Link()
        override val notifications = Notifications()
        override val border = Border()
    },
    isLight = false,
)

