package mega.android.core.ui.theme.colors

import androidx.compose.runtime.Immutable
import mega.android.core.ui.theme.tokens.SemanticTokens

@Immutable
internal data class AppColors(
    private val semanticTokens: SemanticTokens,
    val isLight: Boolean,
) : SemanticTokens by semanticTokens