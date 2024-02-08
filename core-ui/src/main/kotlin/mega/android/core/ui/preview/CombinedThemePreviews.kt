package mega.android.core.ui.preview

import android.content.res.Configuration
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview

/**
 * Annotation to generate previews with night and day themes
 */
@Preview(
    showBackground = true,
    backgroundColor = 0xFF121212,
    name = "1-Dark theme",
    group = "themes",
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    apiLevel = 33
)
@Preview(
    showBackground = true,
    name = "2-Light theme",
    group = "themes",
    uiMode = Configuration.UI_MODE_NIGHT_NO,
    apiLevel = 33
)
annotation class CombinedThemePreviews

@Preview(
    showBackground = true,
    backgroundColor = 0xFF121212,
    name = "1-Dark theme",
    group = "themes",
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    apiLevel = 33,
    device = Devices.TABLET
)
@Preview(
    showBackground = true,
    name = "2-Light theme",
    group = "themes",
    uiMode = Configuration.UI_MODE_NIGHT_NO,
    apiLevel = 33,
    device = Devices.TABLET
)
annotation class CombinedThemePreviewsTablet