package mega.android.core.ui.app.component

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import kotlinx.coroutines.delay
import mega.android.core.ui.components.indicators.TimerRadialProgressBar

@Composable
fun TimerRadialProgressBarComponent() {
    val timer = 30000L
    var isRunning by remember { mutableStateOf(true) }
    var timeLeft by remember { mutableLongStateOf(timer) }
    LaunchedEffect(isRunning) {
        if (isRunning) {
            while (timeLeft > 0) {
                delay(100L)
                timeLeft -= 100L
            }
            isRunning = false
        } else {
            isRunning = true
            timeLeft = timer
        }
    }
    TimerRadialProgressBar(
        totalTime = timer,
        size = 36,
        textSize = 18,
        strokeWidth = 2f,
        remainingTime = { timeLeft }
    )
}