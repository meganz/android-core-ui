package mega.android.core.ui.components.chip

import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.SelectableChipColors
import androidx.compose.runtime.Composable
import mega.android.core.ui.theme.AppTheme
import mega.android.core.ui.tokens.theme.DSTokens


/**
 * Definition of chips
 */
sealed interface ChipStyle {

    /**
     * Colors of selectable chip
     */
    @Composable
    fun selectableChipColors(): SelectableChipColors
}

/**
 * Default style for chips
 */
data object DefaultChipStyle : ChipStyle {

    @Composable
    override fun selectableChipColors(): SelectableChipColors = FilterChipDefaults.filterChipColors(
        selectedContainerColor = DSTokens.colors.brand.containerDefault,
        selectedLabelColor = DSTokens.colors.brand.onContainer,
        selectedLeadingIconColor = DSTokens.colors.brand.onContainer,
        selectedTrailingIconColor = DSTokens.colors.brand.onContainer,
        containerColor = DSTokens.colors.button.secondary,
        labelColor = DSTokens.colors.text.primary,
        iconColor = DSTokens.colors.icon.primary,
    )
}