package mega.android.core.ui.components.tooltip.state

import android.os.Parcelable
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import kotlinx.coroutines.delay
import kotlinx.parcelize.Parcelize
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

@Composable
fun rememberMegaTooltipState(): MegaTooltipState {
    return rememberSaveable(saver = MegaTooltipState.Saver()) {
        MegaTooltipState()
    }
}

@Stable
class MegaTooltipState {

    internal var visibilityState by mutableStateOf(true)
        private set

    internal var dismissDuration: Duration? = null
        private set

    internal var dismissCountDownEnd by mutableStateOf(false)
        private set

    internal var onDismissFinish: (() -> Unit)? = null
        private set

    private var isCountingDown = false

    fun dismiss(onFinish: (() -> Unit)? = null) {
        onDismissFinish = onFinish
        visibilityState = false
    }

    internal fun setDismissDuration(dismissDuration: Duration) {
        if (dismissDuration > 0.seconds && !isCountingDown) {
            this.dismissDuration = dismissDuration
        }
    }

    internal suspend fun tryToStartDismissCountDown() {
        var duration = dismissDuration ?: return
        if (duration <= 0.seconds) return

        isCountingDown = true
        while (duration > 0.seconds) {
            delay(1.seconds)
            duration = duration.minus(1.seconds)
            dismissDuration = duration
        }
        isCountingDown = false
        dismissDuration = null
        dismissCountDownEnd = true
    }

    @Parcelize
    data class MegaTooltipStateData(
        val visibilityState: Boolean,
        val dismissDuration: String,
        val onDismissFinish: (() -> Unit)?
    ) : Parcelable

    companion object {
        fun Saver() = Saver<MegaTooltipState, MegaTooltipStateData>(
            save = {
                MegaTooltipStateData(
                    visibilityState = it.visibilityState,
                    dismissDuration = it.dismissDuration.toString(),
                    onDismissFinish = it.onDismissFinish
                )
            },
            restore = {
                MegaTooltipState().apply {
                    // The dismissDuration is nullable, hence Duration.toString() can return
                    // the string "null" if the dismissDuration is null.
                    if (it.dismissDuration != "null") {
                        val duration = Duration.parse(it.dismissDuration)
                        dismissDuration = duration
                        if (duration > 0.seconds) isCountingDown = true
                    }

                    visibilityState = it.visibilityState
                    onDismissFinish = it.onDismissFinish
                }
            }
        )
    }
}
