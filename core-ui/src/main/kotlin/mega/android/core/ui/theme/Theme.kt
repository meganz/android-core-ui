package mega.android.core.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.staticCompositionLocalOf
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
import mega.android.core.ui.theme.tokens.Indicator
import mega.android.core.ui.theme.tokens.Link
import mega.android.core.ui.theme.tokens.Notifications
import mega.android.core.ui.theme.tokens.SemanticTokens
import mega.android.core.ui.theme.tokens.Support
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

    @Composable
    internal fun textColor(textColor: TextColor) =
        LocalColorPalette.current.text.getTextColor(textColor)

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

