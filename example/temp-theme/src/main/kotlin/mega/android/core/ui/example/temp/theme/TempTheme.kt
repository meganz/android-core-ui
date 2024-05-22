package mega.android.core.ui.example.temp.theme

import android.annotation.SuppressLint
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.staticCompositionLocalOf
import mega.android.core.ui.example.temp.theme.tokens.TempSemanticTokensDark
import mega.android.core.ui.example.temp.theme.tokens.TempSemanticTokensLight
import mega.android.core.ui.example.temp.theme.tokens.TextColor
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
 * Android theme to be used only for internal previews
 *
 * @param isDark
 * @param content
 */
@SuppressLint("IsSystemInDarkTheme")
@Composable
fun AndroidTempTheme(
    isDark: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) = AndroidTempTheme(
    isDark = isDark,
    darkColorTokens = TempSemanticTokensDark,
    lightColorTokens = TempSemanticTokensLight,
    content = content,
)

/**
 * Android theme
 *
 * @param isDark
 * @param darkColorTokens [SemanticTokens] for dark mode
 * @param lightColorTokens [SemanticTokens] for light mode
 * @param content
 */
@Composable
private fun AndroidTempTheme(
    isDark: Boolean,
    darkColorTokens: SemanticTokens,
    lightColorTokens: SemanticTokens,
    content: @Composable () -> Unit,
) {
    val legacyColors = if (isDark) {
        TempSemanticTokensDark
    } else {
        TempSemanticTokensLight
    }

    val semanticTokens = if (isDark) {
        darkColorTokens
    } else {
        lightColorTokens
    }
    val colors = MegaColors(semanticTokens, !isDark)

    val colorPalette by remember(colors) {
        mutableStateOf(colors)
    }
    CompositionLocalProvider(
        LocalMegaColors provides colorPalette,
    ) {
        //in temp theme Material2 is used, here we are using Material3 to simplify the example
        MaterialTheme(
            content = content
        )
    }
}

internal object MegaTheme {
    val colors: MegaColors
        @Composable
        get() = LocalMegaColors.current

    @Composable
    fun textColor(textColor: TextColor) = textColor.getTextColor(LocalMegaColors.current.text)
}

private val LocalMegaColors = staticCompositionLocalOf {
    testColorPalette
}

/**
 * [MegaColors] default palette for testing purposes, all magenta to easily detect it.
 */
private val testColorPalette = MegaColors(
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