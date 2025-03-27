package mega.android.core.ui.components.tooltip.component

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import mega.android.core.ui.components.MegaText
import mega.android.core.ui.theme.AppTheme
import mega.android.core.ui.theme.values.TextColor

@Composable
internal fun TooltipTitle(
    value: String,
    modifier: Modifier = Modifier
) {
    MegaText(
        modifier = modifier,
        text = value,
        style = AppTheme.typography.titleMedium,
        textColor = TextColor.Inverse
    )
}

@Composable
internal fun TooltipBody(
    value: String,
    modifier: Modifier = Modifier
) {
    MegaText(
        modifier = modifier,
        text = value,
        style = AppTheme.typography.bodyMedium,
        textColor = TextColor.InverseSecondary
    )
}
