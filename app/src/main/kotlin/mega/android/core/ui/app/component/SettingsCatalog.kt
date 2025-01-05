package mega.android.core.ui.app.component

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import mega.android.core.ui.app.util.Section
import mega.android.core.ui.components.settings.SettingsActionItem
import mega.android.core.ui.components.settings.SettingsNavigationItem
import mega.android.core.ui.components.settings.SettingsToggleItem
import mega.android.core.ui.theme.spacing.LocalSpacing
import kotlin.time.Duration.Companion.seconds

@Composable
fun SettingsCatalog() {
    Spacer(modifier = Modifier.height(LocalSpacing.current.x16))
    Section(header = "Settings Toggle Item") {
        var checked by remember<MutableState<Boolean>> { mutableStateOf(false) }
        SettingsToggleItem(
            key = "key",
            title = "Title of Settings Toggle Item without footer",
            subtitle = "Sub title",
            checked = checked,
            enabled = true,
        ) { key, value ->
            checked = value
        }
        var checkedFooter by remember<MutableState<Boolean>> { mutableStateOf(false) }
        SettingsToggleItem(
            key = "key",
            title = "Title of Settings Toggle Item with footer",
            subtitle = "Sub title",
            checked = checkedFooter,
            enabled = true,
            footerText = "Footer text",
        ) { key, value ->
            checkedFooter = value
        }
    }
    Section(header = "Settings Action Item") {
        SettingsActionItem(
            key = "key",
            title = "Title of Action Item",
            destructive = false,
            loading = false,
            enabled = true,
        ) { key -> }
        var loading by remember { mutableStateOf(false) }
        var enabled by remember { mutableStateOf(true) }
        val coroutineScope = rememberCoroutineScope()
        SettingsActionItem(
            key = "key",
            title = "Delete something important",
            destructive = true,
            loading = loading,
            enabled = enabled,
            footerText = "Deleting this important thing will free 100 kB"
        ) { key ->
            loading = true
            enabled = false
            coroutineScope.launch {
                delay(2.seconds)
                loading = false
                enabled = true
            }
        }
    }
    Section(header = "Settings Navigation Item") {
        SettingsNavigationItem(
            key = "key",
            title = "Title of Navigation Item without Subtitle",
            enabled = true,
        ) { key -> }
        SettingsNavigationItem(
            key = "key",
            title = "Title of Navigation Item",
            subtitle = "Subtitle of Navigation Item",
            enabled = true,
        ) { key -> }
    }
}