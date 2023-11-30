package mega.android.core.ui.components

import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import mega.android.core.ui.preview.CombinedThemePreviews
import mega.android.core.ui.theme.AppTheme

/**
 * Default divider
 */
@Composable
fun DefaultDivider() {
    Divider(
        color = AppTheme.colors.border.disabled, thickness = 0.5.dp
    )
}

@CombinedThemePreviews
@Composable
private fun PreviewDefaultDivider() {
    DefaultDivider()
}
