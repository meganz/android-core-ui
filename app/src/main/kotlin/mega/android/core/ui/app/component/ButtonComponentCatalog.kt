package mega.android.core.ui.app.component

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import mega.android.core.ui.app.util.Section
import mega.android.core.ui.components.button.AnchoredButtonGroup
import mega.android.core.ui.components.button.PrimaryFilledButton
import mega.android.core.ui.components.button.SecondaryFilledButton
import mega.android.core.ui.components.button.TextOnlyButton
import mega.android.core.ui.model.Button
import mega.android.core.ui.theme.spacing.LocalSpacing

@Composable
fun ButtonComponentCatalog() {
    Spacer(modifier = Modifier.height(LocalSpacing.current.x16))

    Section(header = "Filled Buttons") {
        PrimaryFilledButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(all = 16.dp),
            text = "Primary",
            onClick = {}
        )

        SecondaryFilledButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(all = 16.dp),
            text = "Secondary",
            onClick = {}
        )
    }

    Section(header = "Text Only Button") {
        TextOnlyButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(all = 16.dp),
            text = "Text Only",
            onClick = {})
    }

    Section(header = "Anchored Button Group") {
        AnchoredButtonGroup(
            modifier = Modifier.padding(vertical = 16.dp),
            title = "Anchored button group",
            buttonGroup = listOf(
                {
                    Button.PrimaryButton(
                        text = "Primary",
                        modifier = Modifier.fillMaxWidth()
                    )
                },
                {
                    Button.SecondaryButton(
                        text = "Secondary",
                        modifier = Modifier.fillMaxWidth()
                    )
                },
                {
                    Button.TextOnlyButton(
                        text = "Text Only",
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            ),
            withDivider = true
        )
    }
}