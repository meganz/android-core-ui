package mega.android.core.ui.theme.devicetype

import androidx.compose.runtime.staticCompositionLocalOf

enum class DeviceType {
    Tablet,
    Phone
}

val LocalDeviceType = staticCompositionLocalOf { DeviceType.Phone }
