package mega.android.core.ui.components.divider

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import mega.android.core.ui.preview.CombinedThemePreviews
import mega.android.core.ui.theme.AndroidThemeForPreviews
import mega.android.core.ui.tokens.theme.DSTokens

@Composable
fun SubtleDivider(modifier: Modifier = Modifier) {
    HorizontalDivider(
        modifier = modifier,
        thickness = 1.dp,
        color = DSTokens.colors.border.subtle
    )
}

@Composable
fun SubtleVerticalDivider(thickness: Dp, modifier: Modifier = Modifier) {
    VerticalDivider(
        modifier = modifier,
        thickness = thickness,
        color = DSTokens.colors.border.subtle
    )
}

@Composable
fun StrongDivider(modifier: Modifier = Modifier) {
    HorizontalDivider(
        modifier = modifier,
        thickness = 1.dp,
        color = DSTokens.colors.border.strong
    )
}

@Composable
fun StrongVerticalDivider(thickness: Dp, modifier: Modifier = Modifier) {
    VerticalDivider(
        modifier = modifier,
        thickness = thickness,
        color = DSTokens.colors.border.strong
    )
}

@Composable
@CombinedThemePreviews
private fun SubtleDividerPreview() {
    AndroidThemeForPreviews {
        SubtleDivider(modifier = Modifier.fillMaxWidth())
    }
}

@Composable
@CombinedThemePreviews
private fun StrongDividerPreview() {
    AndroidThemeForPreviews {
        StrongDivider(modifier = Modifier.fillMaxWidth())
    }
}

@Composable
@CombinedThemePreviews
private fun SubtleVerticalDividerPreview() {
    AndroidThemeForPreviews {
        SubtleVerticalDivider(thickness = 2.dp, modifier = Modifier.height(30.dp))
    }
}

@Composable
@CombinedThemePreviews
private fun StrongVerticalDividerPreview() {
    AndroidThemeForPreviews {
        StrongVerticalDivider(thickness = 2.dp, modifier = Modifier.height(30.dp))
    }
}