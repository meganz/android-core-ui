package mega.android.core.ui.components.chip

import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.SelectableChipColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
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

/**
 * Transparent style for chips
 */
data object TransparentChipStyle : ChipStyle {

    @Composable
    override fun selectableChipColors(): SelectableChipColors = FilterChipDefaults.filterChipColors(
        containerColor = Color.Transparent,
        labelColor = DSTokens.colors.text.primary,
        iconColor = DSTokens.colors.icon.primary,
        disabledContainerColor = Color.Transparent,
        disabledLabelColor = DSTokens.colors.text.primary,
        disabledLeadingIconColor = DSTokens.colors.icon.primary,
        selectedContainerColor = Color.Transparent,
        selectedLabelColor = DSTokens.colors.text.primary,
        selectedLeadingIconColor = DSTokens.colors.icon.primary,
        disabledSelectedContainerColor = Color.Transparent
    )
}

/**
 * Transparent style for chips
 */
data object SelectionChipStyle : ChipStyle {

    @Composable
    override fun selectableChipColors(): SelectableChipColors = FilterChipDefaults.filterChipColors(
        selectedContainerColor = DSTokens.colors.components.selectionControl,
        selectedLabelColor = DSTokens.colors.text.inverseAccent,
        selectedLeadingIconColor = DSTokens.colors.icon.inverseAccent,
        containerColor = DSTokens.colors.background.surface1,
        labelColor = DSTokens.colors.text.primary,
        iconColor = DSTokens.colors.icon.primary
    )
}
