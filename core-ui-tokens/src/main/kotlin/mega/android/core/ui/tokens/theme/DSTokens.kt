package mega.android.core.ui.tokens.theme

import androidx.compose.material3.Shapes
import androidx.compose.runtime.Composable
import androidx.compose.runtime.staticCompositionLocalOf
import mega.android.core.ui.tokens.theme.colors.DSColors
import mega.android.core.ui.tokens.theme.tokens.Background
import mega.android.core.ui.tokens.theme.tokens.Border
import mega.android.core.ui.tokens.theme.tokens.Brand
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
 * Design System Tokens
 */
object DSTokens {
    val colors: DSColors
        @Composable
        get() = LocalColorPalette.current

    val shapes: Shapes
        @Composable
        get() = mega.android.core.ui.tokens.theme.shape.shapes
}

val LocalColorPalette = staticCompositionLocalOf {
    testColorPalette
}

/**
 * [DSColors] default palette for testing purposes, all magenta to easily detect it.
 */
private val testColorPalette = DSColors(
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
        override val brand = Brand()
    },
    isLight = false,
)