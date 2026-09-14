package mega.android.core.ui.app.component

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import mega.android.core.ui.app.util.Section
import mega.android.core.ui.components.dropdown.MegaDropDownSelector
import mega.android.core.ui.theme.spacing.LocalSpacing

@Composable
fun MegaDropDownSelectorComponent() {
    val spacing = LocalSpacing.current
    var selectedUnit by remember { mutableStateOf("MB") }
    var emptyUnit by remember { mutableStateOf("") }
    var isPickerOpen by remember { mutableStateOf(false) }

    Spacer(modifier = Modifier.height(spacing.x16))

    Section(header = "Drop Down Selector - With Value") {
        MegaDropDownSelector(
            modifier = Modifier.padding(horizontal = spacing.x16),
            value = selectedUnit,
            onClick = {
                selectedUnit = when (selectedUnit) {
                    "B" -> "KB"
                    "KB" -> "MB"
                    "MB" -> "GB"
                    else -> "B"
                }
            },
            label = "File size unit",
            optionalLabelText = "Optional",
        )
    }

    Section(header = "Drop Down Selector - Placeholder") {
        MegaDropDownSelector(
            modifier = Modifier.padding(horizontal = spacing.x16),
            value = emptyUnit,
            placeholder = "Select unit",
            onClick = {
                emptyUnit = "MB"
            },
            label = "File size unit",
        )
    }

    Section(header = "Drop Down Selector - Error State") {
        MegaDropDownSelector(
            modifier = Modifier.padding(horizontal = spacing.x16),
            value = "TB",
            onClick = {},
            label = "File size unit",
            isError = true,
            errorText = "Unit not supported",
        )
    }

    Section(header = "Drop Down Selector - Open State") {
        MegaDropDownSelector(
            modifier = Modifier.padding(horizontal = spacing.x16),
            value = "MB",
            onClick = { isPickerOpen = !isPickerOpen },
            label = "File size unit",
            expanded = isPickerOpen,
        )
    }

    Section(header = "Drop Down Selector - Disabled State") {
        MegaDropDownSelector(
            modifier = Modifier.padding(horizontal = spacing.x16),
            value = "GB",
            onClick = {},
            label = "File size unit",
            enabled = false,
        )
    }
}
