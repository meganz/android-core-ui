package mega.android.core.ui.components.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import mega.android.core.ui.components.MegaText
import mega.android.core.ui.theme.AppTheme
import mega.android.core.ui.theme.values.TextColor

/**
 * Helper component to add footer texts to settings components
 */
@Composable
internal fun SettingsWithFooter(
    footerText: String?,
    modifier: Modifier = Modifier,
    settingsItem: @Composable () -> Unit,
) = Column(
    modifier = modifier
) {
    settingsItem()
    footerText?.let { footer ->
        MegaText(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .padding(top = 5.dp, bottom = 16.dp),
            text = footer,
            textColor = TextColor.Secondary,
            style = AppTheme.typography.bodyMedium,
        )
    }
}