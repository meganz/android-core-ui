package mega.android.core.ui.components.divider

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import mega.android.core.ui.preview.CombinedThemePreviews
import mega.android.core.ui.theme.AppTheme

@Composable
fun SubtleDivider(modifier: Modifier = Modifier) {
    HorizontalDivider(
        modifier = modifier,
        thickness = 1.dp,
        color = AppTheme.colors.border.subtle
    )
}

@Composable
fun StrongDivider(modifier: Modifier = Modifier) {
    HorizontalDivider(
        modifier = modifier,
        thickness = 1.dp,
        color = AppTheme.colors.border.strong
    )
}

@Composable
@CombinedThemePreviews
private fun SubtleDividerPreview() {
    SubtleDivider(modifier = Modifier.fillMaxWidth())
}

@Composable
@CombinedThemePreviews
private fun StrongDividerPreview() {
    StrongDivider(modifier = Modifier.fillMaxWidth())
}