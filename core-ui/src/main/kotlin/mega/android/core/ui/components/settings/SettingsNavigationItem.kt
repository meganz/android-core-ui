package mega.android.core.ui.components.settings

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
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

@Composable
fun SettingsNavigationItem(
    key: String,
    title: String,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    subtitle: String? = null,
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