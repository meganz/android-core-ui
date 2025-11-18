package mega.android.core.ui.app.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import mega.android.core.ui.R
import mega.android.core.ui.app.util.Section
import mega.android.core.ui.components.button.AccessoryBarButton
import mega.android.core.ui.components.button.AccessoryBarButtonGroup
import mega.android.core.ui.components.button.AnchoredButtonGroup
import mega.android.core.ui.components.button.MegaOutlinedButton
import mega.android.core.ui.components.button.PrimaryFilledButton
import mega.android.core.ui.components.button.SecondaryFilledButton
import mega.android.core.ui.components.button.TextOnlyButton
import mega.android.core.ui.model.AccessoryBarButtonContent
import mega.android.core.ui.model.Button
import mega.android.core.ui.theme.spacing.LocalSpacing

@Composable
fun ButtonComponentCatalog(onClick: (String) -> Unit = {}) {
    Spacer(modifier = Modifier.height(LocalSpacing.current.x16))

    Section(header = "Filled Buttons") {
        PrimaryFilledButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(all = 16.dp),
            text = "Primary",
            onClick = {onClick("Primary button clicked")},
        )

        SecondaryFilledButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(all = 16.dp),
            text = "Secondary",
            onClick = {onClick("Secondary button clicked")},
        )
    }

    Section(header = "Text Only Button") {
        TextOnlyButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(all = 16.dp),
            text = "Text Only",
            onClick = {onClick("Text Only button clicked")},)
    }

    Section(header = "Outlined Button") {
        MegaOutlinedButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(all = 16.dp),
            text = "Outlined Button",
            onClick = {onClick("Outlined button clicked")},
        )
    }

    Section(header = "Anchored Button Group") {
        AnchoredButtonGroup(
            modifier = Modifier.padding(vertical = 16.dp),
            title = "Anchored button group",
            buttonGroup = listOf(
                {
                    Button.PrimaryButton(
                        text = "Primary",
                        modifier = Modifier.fillMaxWidth(),
                        onClick = {onClick("Anchored primary button clicked")},
                    )
                },
                {
                    Button.SecondaryButton(
                        text = "Secondary",
                        modifier = Modifier.fillMaxWidth(),
                        onClick = {onClick("Anchored secondary button clicked")},
                    )
                },
                {
                    Button.TextOnlyButton(
                        text = "Text Only",
                        modifier = Modifier.fillMaxWidth(),
                        onClick = {onClick("Anchored text only button clicked")},
                    )
                }
            ),
            withDivider = true
        )
    }
    Section(header = "Accessory Bar Buttons") {
        Column {
            AccessoryBarButton(
                button = AccessoryBarButtonContent(
                    icon = painterResource(id = R.drawable.ic_search_large_medium_thin_outline),
                    text = "Button",
                    onClick = {}
                ),
            )
            AccessoryBarButtonGroup(
                firstButton = AccessoryBarButtonContent(
                    icon = painterResource(id = R.drawable.ic_close_medium_thin_outline),
                    text = "Cancel",
                    onClick = {}
                ),
                secondButton = AccessoryBarButtonContent(
                    icon = painterResource(id = R.drawable.ic_search_large_medium_thin_outline),
                    text = "Search",
                    onClick = {}
                ),
            )
        }
    }
}