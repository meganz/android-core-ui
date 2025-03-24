package mega.android.core.ui.app.component

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import mega.android.core.ui.components.indicators.TimerRadialProgressBar

@Composable
fun TimerRadialProgressBarComponent() {
    val timer = 30L
    var timeLeft by remember { mutableLongStateOf(timer * 1000) }

    LaunchedEffect(Unit) {
        while (true) {
            if (timeLeft > 0) {
                delay(100L)
                timeLeft -= 100L
            } else {
                timeLeft = timer * 1000
            }
        }
    }

    TimerRadialProgressBar(
        totalTimeInSeconds = timer,
        size = 36.dp,
        strokeWidth = 2f,
        remainingTimeInMilliSeconds = { timeLeft }
    )
}