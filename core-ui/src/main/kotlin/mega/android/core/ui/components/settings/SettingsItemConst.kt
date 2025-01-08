package mega.android.core.ui.components.settings

import androidx.compose.ui.unit.dp

internal object SettingsItemConst {
    fun listItemTag(key: String) = "settings_$key:list_item"
    fun progressIndicatorTag(key: String) = "settings_$key:progress_indicator"
    fun toggleTag(key: String) = "settings_$key:toggle"
    fun chevronTag(key: String) = "settings_$key:chevron"
    fun checkTag(key: String) = "settings_$key:check"
    fun bottomSheetTag(key: String) = "settings_$key:bottom_sheet"
    fun headerTag(key: String) = "settings_$key:header"

    val minHeight = 58.dp
}