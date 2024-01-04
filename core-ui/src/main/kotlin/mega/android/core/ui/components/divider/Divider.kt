package mega.android.core.ui.components.divider

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import mega.android.core.ui.preview.CombinedThemePreviews
import mega.android.core.ui.theme.AppTheme
import androidx.compose.material3.Divider as MaterialDivider

@Composable
fun Divider(modifier: Modifier = Modifier) {
    MaterialDivider(
        modifier = modifier,
        color = AppTheme.colors.border.subtle,
        thickness = 1.dp
    )
}

@Composable
@CombinedThemePreviews
private fun DividerPreview() {
    Divider(modifier = Modifier.fillMaxWidth())
}