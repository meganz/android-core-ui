package mega.android.core.ui.modifiers

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed

/**
 * Conditional execution wrapper for the Modifier class
 * it's the equivalent of `if (condition)` on the modifier
 * @param condition the condition of which the block will executes
 * @param modifier block to invoke and returns [Modifier] as self
 */
@SuppressLint("UnnecessaryComposedModifier")
fun Modifier.conditional(
    condition: Boolean,
    modifier: @Composable Modifier.() -> Modifier,
): Modifier = composed {
    if (condition) then(modifier(Modifier)) else this
}