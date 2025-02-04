package mega.android.core.ui.theme.activity

import android.app.Activity
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.platform.LocalContext

/**
 * Provides the [android.app.Activity] belonging to the current [LocalContext].
 *
 * Note, when possible you should always prefer using the finer grained composition locals where
 * available. This API should be used as a fallback when the required API is only available via
 * [android.app.Activity].
 *
 * See [androidx.compose.ui.platform.LocalConfiguration]
 * [androidx.compose.ui.platform.LocalLifecycleOwner] [androidx.compose.ui.platform.LocalView]
 */
val LocalActivity = staticCompositionLocalOf<Activity?> { null }
