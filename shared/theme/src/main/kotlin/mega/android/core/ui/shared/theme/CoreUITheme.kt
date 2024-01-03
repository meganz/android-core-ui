package mega.android.core.ui.shared.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import mega.android.core.ui.shared.theme.tokens.CoreUISemanticTokensDark
import mega.android.core.ui.shared.theme.tokens.CoreUISemanticTokensLight
import mega.android.core.ui.theme.AndroidTheme

/**
 * Android theme with Core-UI app specific color tokens
 *
 * @param isDark
 * @param content
 */
@Composable
fun CoreUITheme(
    isDark: Boolean,
    content: @Composable () -> Unit,
) = AndroidTheme(
    isDark = isDark,
    content = content,
    darkColorTokens = CoreUISemanticTokensDark,
    lightColorTokens = CoreUISemanticTokensLight
)

/**
 * Core-UI app theme to be used for Previews in modules that depend on shared:theme
 *
 * @param content
 */
@Composable
fun CoreUIThemeForPreviews(
    content: @Composable () -> Unit,
) = CoreUITheme(
    isDark = isSystemInDarkTheme(),
    content = content,
)
