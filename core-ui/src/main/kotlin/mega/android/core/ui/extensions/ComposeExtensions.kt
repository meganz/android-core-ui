package mega.android.core.ui.extensions

import androidx.compose.foundation.layout.BoxScope
import androidx.compose.runtime.Composable

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