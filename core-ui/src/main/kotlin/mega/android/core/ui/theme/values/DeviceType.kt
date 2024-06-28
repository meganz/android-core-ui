package mega.android.core.ui.theme.values

import androidx.compose.runtime.staticCompositionLocalOf

enum class DeviceType {
    Tablet,
    Phone
}

val LocalDeviceType = staticCompositionLocalOf { DeviceType.Phone }
