package mega.android.core.ui.tokens.theme.colors

import androidx.compose.runtime.Immutable
import mega.android.core.ui.tokens.theme.tokens.SemanticTokens

/**
 * Design system colors
 */
@Immutable
data class DSColors(
    private val semanticTokens: SemanticTokens,
    val isLight: Boolean,
) : SemanticTokens by semanticTokens