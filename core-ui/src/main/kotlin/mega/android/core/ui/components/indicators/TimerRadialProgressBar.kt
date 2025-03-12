package mega.android.core.ui.components.indicators

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import mega.android.core.ui.components.MegaText
import mega.android.core.ui.preview.CombinedThemePreviews
import mega.android.core.ui.theme.AndroidThemeForPreviews
import mega.android.core.ui.theme.AppTheme
import mega.android.core.ui.theme.values.TextColor

/**
 * TimerRadialProgressBar is a circular progress bar that shows the remaining time of a timer.
 * It has a text in the center that shows the remaining time in seconds.
 * The color of the progress bar changes according to the remaining time.
 * The progress bar is animated.
 *
 * @param totalTimeInSeconds the total time of the timer
 * @param remainingTimeInMilliSeconds a lambda that returns the remaining time of the timer
 * @param modifier the modifier to apply to this layout
 * @param size the size of the progress bar
 * @param textSize the size of the text in the center
 */
@Composable
fun TimerRadialProgressBar(
    totalTimeInSeconds: Long,
    remainingTimeInMilliSeconds: () -> Long,
    modifier: Modifier = Modifier,
    size: Int = 24,
    textSize: Int = 12,
    strokeWidth: Float = 2f,
) {

    val totalTimeText by remember(remainingTimeInMilliSeconds()) {
        derivedStateOf {
            "${remainingTimeInMilliSeconds() / 1000}"
        }
    }
    val progress by remember(remainingTimeInMilliSeconds()) { derivedStateOf { remainingTimeInMilliSeconds().toFloat() / (totalTimeInSeconds * 1000) } }

    val color = when (remainingTimeInMilliSeconds() / 1000) {
        in 0..totalTimeInSeconds / 3 -> AppTheme.colors.support.error
        in (totalTimeInSeconds / 3)..(2 * totalTimeInSeconds / 3) -> AppTheme.colors.support.warning
        else -> AppTheme.colors.support.success
    }
    Box(modifier = modifier, contentAlignment = Alignment.Center) {
        CircularProgressIndicator(
            progress = { progress },
            color = color,
            trackColor = AppTheme.colors.border.strong,
            strokeWidth = strokeWidth.dp,
            strokeCap = StrokeCap.Butt,
            gapSize = 0.dp,
            modifier = Modifier.size(size.dp),
        )
        MegaText(
            text = totalTimeText,
            style = AppTheme.typography.bodySmall.copy(fontSize = textSize.sp),
            textColor = TextColor.Secondary
        )
    }
}


@CombinedThemePreviews
@Composable
fun TimerRadialProgressBarInteractablePreview() {
    AndroidThemeForPreviews {
        val timer = 30L
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            var timeLeft by remember { mutableLongStateOf(timer * 1000) }
            var textFieldValue by remember { mutableStateOf("$timer") }

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


            Column(
                modifier = Modifier,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                TextField(
                    value = textFieldValue,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    onValueChange = {
                        textFieldValue = it
                        timeLeft = it.toLong()
                    }
                )
                TimerRadialProgressBar(
                    totalTimeInSeconds = timer,
                    size = 100,
                    textSize = 50,
                    strokeWidth = 5f,
                    remainingTimeInMilliSeconds = { timeLeft }
                )
            }
        }
    }
}


