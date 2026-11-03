package mega.android.core.ui.components.settings

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.PreviewParameter
import mega.android.core.ui.components.image.MegaIcon
import mega.android.core.ui.components.list.FlexibleLineListItem
import mega.android.core.ui.preview.BooleanProvider
import mega.android.core.ui.preview.CombinedThemePreviews
import mega.android.core.ui.theme.AndroidThemeForPreviews
import mega.android.core.ui.theme.values.IconColor
import mega.android.core.ui.R

/**
 * Settings Navigation Item. Item to allow to navigate trough Settings screens hierarchy
 *
 * @param key the key of the section or screen to navigate to. It will be also used for action callbacks and for test tags
 * @param title the title of the section or screen to navigate to
 * @param modifier
 * @param enabled
 * @param subtitle optional subtitle
 * @param leadingIcon optional icon shown before the title
 * @param onClicked callback for option selected
 */
@Composable
fun SettingsNavigationItem(
    key: String,
    title: String,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    subtitle: String? = null,
    leadingIcon: Painter? = null,
    onClicked: (key: String) -> Unit,
) {
    FlexibleLineListItem(
        modifier = modifier.testTag(SettingsItemConst.listItemTag(key)),
        minHeight = SettingsItemConst.minHeight,
        enableClick = enabled,
        onClickListener = {
            onClicked(key)
        },
        title = title,
        subtitle = subtitle,
        leadingElement = settingsLeadingElement(leadingIcon, key),
        trailingElement = {
            MegaIcon(
                painter = painterResource(R.drawable.ic_chevron_right_medium_thin_outline),
                modifier = Modifier.testTag(SettingsItemConst.chevronTag(key)),
                tint = IconColor.Secondary
            )
        }
    )
}

@CombinedThemePreviews
@Composable
private fun SettingActionItemPreview(
    @PreviewParameter(BooleanProvider::class) hasSubtitle: Boolean
) {
    AndroidThemeForPreviews {
        SettingsNavigationItem(
            key = "key",
            title = "Notification Settings",
            subtitle = if (hasSubtitle) "Offline" else null,
        ) { }
    }
}

@CombinedThemePreviews
@Composable
private fun SettingsNavigationItemWithLeadingIconPreview() {
    AndroidThemeForPreviews {
        SettingsNavigationItem(
            key = "key",
            title = "About",
            subtitle = "Version and legal information",
            leadingIcon = painterResource(R.drawable.ic_info_medium_thin_outline),
        ) { }
    }
}
