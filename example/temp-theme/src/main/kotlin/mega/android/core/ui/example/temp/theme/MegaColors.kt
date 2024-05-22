package mega.android.core.ui.example.temp.theme

import androidx.compose.runtime.Immutable
import mega.android.core.ui.tokens.theme.tokens.SemanticTokens

/**
 * Theme colors to be used in all core-ui components.
 */
@Immutable
internal data class MegaColors(
    private val semanticTokens: SemanticTokens,
    val isLight: Boolean,
) : SemanticTokens by semanticTokens {
    val isDark = !isLight
}