package mega.android.core.ui.app.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import mega.android.core.ui.app.util.Section
import mega.android.core.ui.components.indicators.InfiniteProgressBarIndicator
import mega.android.core.ui.components.indicators.LargeHUD
import mega.android.core.ui.components.indicators.LargeInfiniteSpinnerIndicator
import mega.android.core.ui.components.indicators.LargeSpinnerIndicator
import mega.android.core.ui.components.indicators.ProgressBarIndicator
import mega.android.core.ui.components.indicators.SmallHUD
import mega.android.core.ui.components.indicators.SmallInfiniteSpinnerIndicator
import mega.android.core.ui.components.indicators.SmallSpinnerIndicator
import mega.android.core.ui.theme.spacing.LocalSpacing

@Composable
fun ProgressIndicatorCatalog() {
    Spacer(modifier = Modifier.height(LocalSpacing.current.x16))

    Section(header = "Progress Bar Indicator") {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            ProgressBarIndicator(modifier = Modifier.padding(16.dp), progressPercentage = 50f)
            InfiniteProgressBarIndicator(modifier = Modifier.padding(16.dp))
            TimerRadialProgressBarComponent()
        }
    }

    Section(header = "Spinner Indicator") {
        Row {
            SmallSpinnerIndicator(
                modifier = Modifier.padding(16.dp),
                progressPercentage = 50f
            )

            LargeSpinnerIndicator(
                modifier = Modifier.padding(16.dp),
                progressPercentage = 50f
            )
        }
    }

    Section(header = "Indeterminate Spinner Indicator") {
        Row {
            SmallInfiniteSpinnerIndicator(modifier = Modifier.padding(16.dp))
            LargeInfiniteSpinnerIndicator(modifier = Modifier.padding(16.dp))
        }
    }

    Section(header = "HUD") {
        Row {
            SmallHUD(modifier = Modifier.padding(16.dp))
            LargeHUD(modifier = Modifier.padding(16.dp))
        }
    }
}