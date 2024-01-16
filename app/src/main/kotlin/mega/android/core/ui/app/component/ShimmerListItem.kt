package mega.android.core.ui.app.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import mega.android.core.ui.app.util.Section
import mega.android.core.ui.components.util.shimmerEffect
import mega.android.core.ui.theme.spacing.LocalSpacing

@Composable
fun ShimmerListItem() {
    val spacing = LocalSpacing.current
    Spacer(modifier = Modifier.height(spacing.x16))

    Section(header = "Sample list item with shimmer") {
        Row {
            Row(modifier = Modifier.padding(spacing.x16)) {
                Box(
                    modifier = Modifier
                        .size(spacing.x64)
                        .shimmerEffect()
                )
                Spacer(modifier = Modifier.width(spacing.x16))
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(spacing.x16)
                            .shimmerEffect()
                    )
                    Spacer(modifier = Modifier.height(spacing.x16))
                    Box(
                        modifier = Modifier
                            .fillMaxWidth(0.7f)
                            .height(spacing.x16)
                            .shimmerEffect()
                    )
                }
            }
        }
    }
}