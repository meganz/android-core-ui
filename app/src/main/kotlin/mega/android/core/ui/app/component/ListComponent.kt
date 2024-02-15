package mega.android.core.ui.app.component

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import mega.android.core.ui.app.R
import mega.android.core.ui.app.util.Section
import mega.android.core.ui.components.image.MegaIcon
import mega.android.core.ui.components.list.MultiLineListItem
import mega.android.core.ui.components.list.OneLineListItem
import mega.android.core.ui.components.list.VpnSelectedCountryListItem
import mega.android.core.ui.theme.spacing.LocalSpacing
import mega.android.core.ui.theme.tokens.IconColor

@Composable
fun ListComponentCatalog() {
    Spacer(modifier = Modifier.height(LocalSpacing.current.x16))

    Section(header = "One Line List Item") {
        OneLineListItem(
            text = "List item",
        )
    }
    Section(header = "Two Line List Item") {
        MultiLineListItem(
            title = "List item",
            subtitle = "Supporting line text lorem ipsum lorem ipsum"
        )
    }
    Section(header = "Three Line List Item") {
        MultiLineListItem(
            title = "List item",
            subtitle = "Supporting line text lorem ipsum lorem ipsum lorem ipsum lorem ipsum"
        )
    }
    Section(header = "One Line List Item with Elements") {
        OneLineListItem(
            text = "List item",
            leadingElement = {
                MegaIcon(
                    painter = painterResource(id = R.drawable.ic_launcher_foreground),
                    tint = IconColor.Primary,
                    contentDescription = null,
                    modifier = Modifier
                        .size(24.dp)
                        .align(Alignment.Center)
                )
            },
            trailingElement = {
                MegaIcon(
                    painter = painterResource(id = R.drawable.ic_launcher_foreground),
                    contentDescription = null,
                    tint = IconColor.Primary,
                    modifier = Modifier.size(24.dp)
                )
            },
        )
    }
    Section(header = "Multi Line List Item with Elements") {
        MultiLineListItem(
            title = "List item",
            subtitle = "Supporting line text lorem ipsum lorem ipsum",
            leadingElement = {
                MegaIcon(
                    painter = painterResource(id = R.drawable.ic_launcher_foreground),
                    contentDescription = null,
                    tint = IconColor.Primary,
                    modifier = Modifier
                        .size(24.dp)
                        .align(Alignment.Center),
                )
            },
            trailingElement = {
                MegaIcon(
                    painter = painterResource(id = R.drawable.ic_launcher_foreground),
                    contentDescription = null,
                    tint = IconColor.Primary,
                    modifier = Modifier
                        .size(24.dp),
                )
            }
        )
    }
    Section(header = "VPN Country List Item") {
        VpnSelectedCountryListItem(
            modifier = Modifier.padding(all = LocalSpacing.current.x16),
            title = "Selected Country",
            subtitle = "Australia",
            countryFlag = painterResource(id = R.drawable.australia),
            rightIcon = painterResource(id = R.drawable.ic_chevron_right)
        )
    }
}