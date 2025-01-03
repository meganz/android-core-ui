package mega.android.core.ui.app.component

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import mega.android.core.ui.app.util.Section
import mega.android.core.ui.components.settings.SettingsToggleItem
import mega.android.core.ui.theme.spacing.LocalSpacing

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

}