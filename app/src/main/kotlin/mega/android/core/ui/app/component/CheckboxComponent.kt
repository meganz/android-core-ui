package mega.android.core.ui.app.component

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import mega.android.core.ui.app.util.Section
import mega.android.core.ui.components.MegaText
import mega.android.core.ui.components.checkbox.Checkbox
import mega.android.core.ui.theme.spacing.LocalSpacing
import mega.android.core.ui.theme.values.TextColor

@Composable
fun CheckboxCatalog() {
    Spacer(modifier = Modifier.height(LocalSpacing.current.x16))

    Section(header = "Checkbox") {
        Row(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = false,
                onCheckStateChanged = {}
            )

            MegaText(
                modifier = Modifier.padding(start = 12.dp),
                text = "This is a checkbox example",
                textColor = TextColor.Primary
            )
        }
    }
}