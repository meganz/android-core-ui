package mega.android.core.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import mega.android.core.ui.preview.CombinedThemePreviews
import mega.android.core.ui.theme.AndroidThemeForPreviews
import mega.android.core.ui.theme.AppTheme

@Composable
fun LoadingView(modifier: Modifier) {
    Box(modifier = modifier) {
        ClippedShadow(
            elevation = 20.dp,
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier
                .size(108.dp)
                .background(
                    color = AppTheme.colors.background.surface1.copy(alpha = 0.85f),
                    shape = RoundedCornerShape(8.dp),
                ),
        )

        CircularProgressIndicator(
            modifier = Modifier
                .size(48.dp)
                .align(Alignment.Center),
            color = AppTheme.colors.icon.primary,
        )
    }
}

@CombinedThemePreviews
@Composable
fun LoadingViewPreview() {
    AndroidThemeForPreviews {
        LoadingView(modifier = Modifier)
    }
}