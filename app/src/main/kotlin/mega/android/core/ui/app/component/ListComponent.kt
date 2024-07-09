package mega.android.core.ui.app.component

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
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
import mega.android.core.ui.components.MegaText
import mega.android.core.ui.components.image.MegaIcon
import mega.android.core.ui.components.list.FlexibleLineListItem
import mega.android.core.ui.components.list.OneLineListItem
import mega.android.core.ui.components.list.PrimaryHeaderListItem
import mega.android.core.ui.components.list.SecondaryHeaderListItem
import mega.android.core.ui.components.list.VpnSelectedCountryListItem
import mega.android.core.ui.components.slider.MegaSlider
import mega.android.core.ui.theme.AppTheme
import mega.android.core.ui.theme.spacing.LocalSpacing
import mega.android.core.ui.theme.values.IconColor
import mega.android.core.ui.theme.values.TextColor

private const val DUMMY_TEXT =
    "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Laoreet sit amet cursus sit amet dictum. Massa tincidunt dui ut ornare. Arcu non sodales neque sodales ut etiam sit amet nisl. Malesuada fames ac turpis egestas maecenas pharetra. Mauris nunc congue nisi vitae suscipit tellus mauris. Sed sed risus pretium quam vulputate. Semper risus in hendrerit gravida rutrum. Lectus urna duis convallis convallis. Nunc consequat interdum varius sit amet mattis vulputate enim nulla. Tellus in metus vulputate eu. Vitae tortor condimentum lacinia quis vel eros. Consectetur adipiscing elit ut aliquam purus. Amet nisl suscipit adipiscing bibendum est. Et leo duis ut diam quam. Faucibus interdum posuere lorem ipsum. Non nisi est sit amet facilisis. Facilisis volutpat est velit egestas dui id ornare arcu. Id consectetur purus ut faucibus pulvinar elementum integer. Ligula ullamcorper malesuada proin libero nunc consequat interdum varius. Pellentesque dignissim enim sit amet venenatis urna cursus. Donec pretium vulputate sapien nec sagittis. Velit ut tortor pretium viverra suspendisse potenti nullam. Enim facilisis gravida neque convallis. Tristique magna sit amet purus gravida quis blandit turpis cursus. Turpis nunc eget lorem dolor sed viverra ipsum. Ultrices eros in cursus turpis massa tincidunt dui ut. Senectus et netus et malesuada. Amet aliquam id diam maecenas ultricies mi eget mauris. Porta nibh venenatis cras sed felis eget velit aliquet sagittis. Orci dapibus ultrices in iaculis nunc. Sit amet facilisis magna etiam. Nunc pulvinar sapien et ligula ullamcorper."

@Composable
fun ListComponentCatalog() {
    val context = LocalContext.current
    var isSwitchChecked by remember { mutableStateOf(false) }

    Spacer(modifier = Modifier.height(LocalSpacing.current.x16))

    Section(header = "One Line List Item") {
        OneLineListItem(text = "List item")
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
                        .size(32.dp)
                        .align(Alignment.Center)
                )
            },
            trailingElement = {
                MegaIcon(
                    painter = painterResource(id = R.drawable.ic_launcher_foreground),
                    contentDescription = null,
                    tint = IconColor.Primary,
                    modifier = Modifier.size(32.dp)
                )
            },
        )
    }
    Section(header = "Flexible Line List Item") {
        var sliderValue by remember { mutableFloatStateOf(1f) }

        MegaText(
            modifier = Modifier.padding(
                horizontal = LocalSpacing.current.x16,
                vertical = LocalSpacing.current.x8
            ),
            text = "Number of Lines = ${sliderValue.toInt()}",
            textColor = TextColor.Primary,
            style = AppTheme.typography.bodyMedium
        )
        MegaSlider(
            modifier = Modifier.padding(
                vertical = LocalSpacing.current.x4,
                horizontal = LocalSpacing.current.x16
            ),
            value = sliderValue,
            onValueChange = {
                sliderValue = it
            },
            valueRange = 1f..20f,
        )
        FlexibleLineListItem(
            modifier = Modifier.padding(top = LocalSpacing.current.x8),
            title = DUMMY_TEXT,
            subtitle = DUMMY_TEXT,
            titleMaxLines = sliderValue.toInt(),
            subtitleMaxLines = sliderValue.toInt(),
            leadingElement = {
                MegaIcon(
                    painter = painterResource(id = R.drawable.ic_launcher_foreground),
                    contentDescription = null,
                    tint = IconColor.Primary,
                    modifier = Modifier
                        .size(32.dp)
                        .align(Alignment.Center),
                )
            },
            trailingElement = {
                MegaIcon(
                    painter = painterResource(id = R.drawable.ic_launcher_foreground),
                    contentDescription = null,
                    tint = IconColor.Primary,
                    modifier = Modifier
                        .size(32.dp),
                )
            },
        )
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
            modifier = Modifier,
            title = "Selected Country",
            subtitle = "Australia",
            countryFlag = painterResource(id = R.drawable.australia),
            rightIcon = painterResource(id = R.drawable.ic_chevron_right)
        )
    }

    Section(header = "VPN Country List Item - Shimmer") {
        VpnSelectedCountryListItem(
            modifier = Modifier,
            title = "Selected Country",
            subtitle = null,
            countryFlag = null,
            rightIcon = painterResource(id = R.drawable.ic_chevron_right)
        )
    }
}