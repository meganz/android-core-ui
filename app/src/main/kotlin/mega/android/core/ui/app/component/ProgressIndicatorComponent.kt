package mega.android.core.ui.app.component

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import mega.android.core.ui.app.util.Section
import mega.android.core.ui.components.indicators.ProgressBarIndicator
import mega.android.core.ui.theme.spacing.LocalSpacing

@Composable
fun ProgressIndicatorCatalog() {
    Spacer(modifier = Modifier.height(LocalSpacing.current.x16))

    Section(header = "Progress Bar Indicator") {
        ProgressBarIndicator(modifier = Modifier.padding(16.dp), progressPercentage = 50f)
    }
}