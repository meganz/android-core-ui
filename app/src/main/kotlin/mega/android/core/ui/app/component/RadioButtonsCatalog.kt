package mega.android.core.ui.app.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import mega.android.core.ui.app.util.Section
import mega.android.core.ui.components.MegaText
import mega.android.core.ui.components.button.MegaRadioButton
import mega.android.core.ui.theme.spacing.LocalSpacing
import mega.android.core.ui.theme.values.TextColor

@Composable
fun RadioButtonCatalog() {
    Spacer(modifier = Modifier.height(LocalSpacing.current.x16))

    Section(
        header = "Radio Buttons"
    ) {
        val options = listOf("Option1", "Option2", "Option3")
        val selectedOption = remember { mutableStateOf(options[0]) }

        options.forEach { option ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                MegaRadioButton(
                    modifier = Modifier.wrapContentWidth(),
                    identifier = option,
                    onOptionSelected = {
                        selectedOption.value = it as String
                    },
                    selected = selectedOption.value == option
                )
                MegaText(text = option, textColor = TextColor.Primary)
            }
        }
    }

    Section(
        header = "Radio Buttons (Disabled)"
    ) {
        val options = listOf("Option1", "Option2", "Option3")
        val selectedOption = remember { mutableStateOf(options[0]) }

        options.forEach { option ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                MegaRadioButton(
                    modifier = Modifier.wrapContentWidth(),
                    identifier = option,
                    onOptionSelected = {
                        selectedOption.value = it as String
                    },
                    selected = selectedOption.value == option,
                    enabled = false
                )
                MegaText(text = option, textColor = TextColor.Primary)
            }
        }
    }
}