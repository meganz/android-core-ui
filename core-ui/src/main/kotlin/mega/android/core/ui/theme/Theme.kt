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
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import mega.android.core.ui.theme.AppTheme.typography
import mega.android.core.ui.theme.colors.AppColors
import mega.android.core.ui.theme.shape.shapes
import mega.android.core.ui.theme.spacing.Dimensions
import mega.android.core.ui.theme.spacing.LocalSpacing
import mega.android.core.ui.theme.tokens.Background
import mega.android.core.ui.theme.tokens.Border
import mega.android.core.ui.theme.tokens.Button
import mega.android.core.ui.theme.tokens.Components
import mega.android.core.ui.theme.tokens.DefaultSemanticTokensDark
import mega.android.core.ui.theme.tokens.DefaultSemanticTokensLight
import mega.android.core.ui.theme.tokens.Focus
import mega.android.core.ui.theme.tokens.Icon
import mega.android.core.ui.theme.tokens.IconColor
import mega.android.core.ui.theme.tokens.Indicator
import mega.android.core.ui.theme.tokens.Link
import mega.android.core.ui.theme.tokens.LinkColor
import mega.android.core.ui.theme.tokens.Notifications
import mega.android.core.ui.theme.tokens.SemanticTokens
import mega.android.core.ui.theme.tokens.Support
import mega.android.core.ui.theme.tokens.SupportColor
import mega.android.core.ui.theme.tokens.Text
import mega.android.core.ui.theme.tokens.TextColor

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
    darkColorTokens = DefaultSemanticTokensDark,
    lightColorTokens = DefaultSemanticTokensLight,
    content = content,
)

@Composable
fun AndroidTheme(
    isDark: Boolean,
    darkColorTokens: SemanticTokens,
    lightColorTokens: SemanticTokens,
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
            window.statusBarColor = colors.background.pageBackground.toArgb()
            window.navigationBarColor = colors.background.pageBackground.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !isDark
            WindowCompat.getInsetsController(window, view).isAppearanceLightNavigationBars = !isDark
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
        LocalColorPalette.current.text.getTextColor(textColor)

    @Composable
    internal fun iconColor(iconColor: IconColor) =
        LocalColorPalette.current.icon.getIconColor(iconColor)

    @Composable
    internal fun supportColor(supportColor: SupportColor) =
        LocalColorPalette.current.support.getSupportColor(supportColor)

    @Composable
    internal fun linkColor(linkColor: LinkColor) =
        LocalColorPalette.current.link.getLinkColor(linkColor)

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
    false,
)

