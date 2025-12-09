package mega.android.core.ui.components.toolbar

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import mega.android.core.ui.components.LocalForceRaiseTopAppBar
import mega.android.core.ui.components.LocalTopAppBarScrollBehavior

/**
 * When this composable effect is added to a composition it forces TopAppBar to be raised, the same as when it's scrolled.
 * It automatically removes the effect when it leaves the composition
 * It's usually used in banners and prompts when they are fixed below the Mega-TopAppBars or Mega-TabRows
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ForceRiceTopAppBarEffect(scrollBehavior: TopAppBarScrollBehavior? = LocalTopAppBarScrollBehavior.current) {
    val forceRaised = LocalForceRaiseTopAppBar.current
    if (forceRaised != null) {
        DisposableEffect(scrollBehavior) {
            forceRaised.intValue += 1

            onDispose {
                forceRaised.intValue -= 1
            }
        }
    }
}