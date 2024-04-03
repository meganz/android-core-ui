package mega.android.core.ui.app.component

import android.widget.Toast
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Switch
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import mega.android.core.ui.app.R
import mega.android.core.ui.app.util.Section
import mega.android.core.ui.components.image.MegaIcon
import mega.android.core.ui.components.list.MultiLineListItem
import mega.android.core.ui.components.list.OneLineListItem
import mega.android.core.ui.components.list.PrimaryHeaderListItem
import mega.android.core.ui.components.list.SecondaryHeaderListItem
import mega.android.core.ui.components.list.VpnSelectedCountryListItem
import mega.android.core.ui.theme.spacing.LocalSpacing
import mega.android.core.ui.theme.tokens.IconColor

@Composable
fun ListComponentCatalog() {
    val context = LocalContext.current
    var isSwitchChecked by remember { mutableStateOf(false) }

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
        MultiLineListItem(
            title = "List item with Switch",
            subtitle = "Supporting line text lorem ipsum lorem ipsum",
            trailingElement = {
                Switch(
                    checked = isSwitchChecked,
                    onCheckedChange = {
                        isSwitchChecked = it
                        Toast.makeText(context, "Switch is checked: $it", Toast.LENGTH_SHORT).show()
                    }
                )
            }
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
            })
    }
    Section(header = "Primary Header") {
        PrimaryHeaderListItem(text = "Header Text")
    }
    Section(header = "Primary Header With Icon") {
        PrimaryHeaderListItem(text = "Header Text", rightIconRes = R.drawable.ic_chevron_down)
    }
    Section(header = "Secondary Header") {
        SecondaryHeaderListItem(text = "Header Text")
    }
    Section(header = "Secondary Header With Icon") {
        SecondaryHeaderListItem(
            text = "Header Text", rightIconRes = R.drawable.ic_chevron_down
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

    Section(header = "VPN Country List Item - Shimmer") {
        VpnSelectedCountryListItem(
            modifier = Modifier.padding(all = LocalSpacing.current.x16),
            title = "Selected Country",
            subtitle = null,
            countryFlag = null,
            rightIcon = painterResource(id = R.drawable.ic_chevron_right)
        )
    }
}