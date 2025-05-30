package mega.android.core.ui.extensions

import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.ime
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalDensity
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.drop
import mega.android.core.ui.theme.activity.LocalActivity

/**
 * A scope function similar to "let" to be used with compose blocks.
 * Useful to create composable functions if [this] is not null or null (instead of empty composable) if it is null
 */
inline fun <T> T.composeLet(crossinline block: @Composable (T) -> Unit): @Composable () -> Unit {
    return {
        block(this)
    }
}

/**
 * A scope function similar to "let" to be used with compose blocks.
 * Useful to create composable functions if [this] is not null or null (instead of empty composable) if it is null
 */
inline fun <T> T.composeLetBoxScope(crossinline block: @Composable BoxScope.(T) -> Unit): @Composable BoxScope.() -> Unit {
    return {
        block(this@composeLetBoxScope)
    }
}

/**
 * Source: https://github.com/android/nowinandroid/pull/1034/files.
 * Add this modifier to a [TextField] to retain focus and keyboard visibility
 * after configuration is changed. This also works with multiple [TextField]s.
 */
@Composable
fun Modifier.focusRestorer(focusRequester: FocusRequester, defaultFocus: Boolean = true): Modifier {
    var hasFocus by rememberSaveable { mutableStateOf(defaultFocus) }
    // Cache the hasFocus value during initialization because `onFocusChanged` is called
    // when not focused during the initial composition.
    var shouldRequestFocus by remember { mutableStateOf(hasFocus) }
    // Save whether the focus is lost by the user's keyboard operation or not.
    // This is required because IME will be hidden during configuration changes
    // but [Activity.isChangingConfigurations] is always false
    // in IME visibility event collector.
    var lostFocusByIme by rememberSaveable { mutableStateOf(defaultFocus) }
    val activity = LocalActivity.current
    val modifier = onFocusChanged {
        when {
            it.isFocused -> hasFocus = true
            // Ignore focus changes during configuration changes,
            // because focus is lost before the activity is killed.
            activity?.isChangingConfigurations?.not() == true -> hasFocus = false
        }
        lostFocusByIme = false
    }
        .focusRequester(focusRequester)

    val imeVisibilityState by imeVisibilityAsState()
    LaunchedEffect(Unit) {
        if (shouldRequestFocus) {
            focusRequester.requestFocus()
            shouldRequestFocus = false
        }
        snapshotFlow { imeVisibilityState }
            .drop(2) // Drop the first two initial values when launched
            .collectLatest { isVisible ->
                when {
                    hasFocus && !isVisible -> {
                        hasFocus = false
                        lostFocusByIme = true
                    }

                    lostFocusByIme && isVisible -> {
                        hasFocus = true
                        lostFocusByIme = false
                    }
                }
            }
    }
    return modifier
}

/**
 * Obtain IME visibility as a State which can trigger recomposition.
 * Note that this may not work if edge-to-edge layout is not enabled.
 */
@Composable
private fun imeVisibilityAsState(): State<Boolean> {
    val isImeVisible = WindowInsets.ime.getBottom(LocalDensity.current) > 0
    return rememberUpdatedState(isImeVisible)
}
