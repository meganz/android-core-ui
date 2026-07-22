package mega.android.core.ui.components.settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import mega.android.core.ui.modifiers.shimmerEffect
import mega.android.core.ui.preview.BooleanProvider
import mega.android.core.ui.preview.CombinedThemePreviews
import mega.android.core.ui.theme.AndroidThemeForPreviews

/**
 * Skeleton setting item showing the loading status before a setting item is fully available.
 *
 * @param showFooter whether to show the footer
 */
@Composable
fun SkeletonPreferenceItem(
    showFooter: Boolean = false
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth(0.5f)
                .height(16.dp)
                .shimmerEffect(),
        )
        if (showFooter) {
            Box(
                modifier = Modifier
                    .fillMaxWidth(0.7f)
                    .height(12.dp)
                    .shimmerEffect(),
            )
        }
    }
}


@CombinedThemePreviews
@Composable
private fun SkeletonPreferenceItemPreview(
    @PreviewParameter(BooleanProvider::class) showFooter: Boolean,
) {
    AndroidThemeForPreviews {
        SkeletonPreferenceItem(showFooter = showFooter)
    }
}
