//
// Generated automatically by KotlinTokensGenerator.
// Do not modify this file manually.
//
package mega.android.core.ui.theme.values

import androidx.compose.ui.graphics.Color
import mega.android.core.ui.tokens.theme.tokens.Notifications

public enum class NotificationsColor {
    NotificationSuccess,
    NotificationWarning,
    NotificationError,
    NotificationInfo,
    ;

    public fun getNotificationsColor(notifications: Notifications): Color = when (this) {
        NotificationSuccess -> notifications.notificationSuccess
        NotificationWarning -> notifications.notificationWarning
        NotificationError -> notifications.notificationError
        NotificationInfo -> notifications.notificationInfo
    }
}
