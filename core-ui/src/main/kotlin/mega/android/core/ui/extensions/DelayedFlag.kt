package mega.android.core.ui.extensions

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.produceState
import kotlinx.coroutines.delay
import kotlin.time.Duration

/**
 * Returns a [State] whose value changes from [initialValue] to [finalValue] after the given [delayDuration].
 *
 * @param delayDuration The time to wait before updating the state to [finalValue].
 * @param initialValue The initial value of the state.
 * @param finalValue The value to set after the delay.
 */
@Composable
fun <T> delayedState(
    delayDuration: Duration,
    initialValue: T,
    finalValue: T,
): State<T> = produceState(
    initialValue = initialValue,
    delayDuration,
    finalValue,
) {
    if (delayDuration != Duration.ZERO) {
        delay(delayDuration)
    }
    value = finalValue
}

/**
 * Returns a [State] that becomes `true` after the given [delay].
 *
 * This is typically used to avoid showing UI elements (such as loading indicators)
 * for very short operations.
 *
 * @param delay The time to wait before the state becomes `true`.
 */
@Composable
fun delayedTrue(delayDuration: Duration) = delayedState(
    delayDuration = delayDuration,
    initialValue = false,
    finalValue = true
)