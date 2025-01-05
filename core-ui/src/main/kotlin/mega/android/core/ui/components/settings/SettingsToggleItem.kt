package mega.android.core.ui.components.settings

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import mega.android.core.ui.components.list.FlexibleLineListItem
import mega.android.core.ui.components.toggle.Toggle
import mega.android.core.ui.preview.CombinedThemePreviews
import mega.android.core.ui.theme.AndroidThemeForPreviews

/**
 * Settings toggle item. Component to show and change boolean settings.
 *
 * @param key the key of this setting. It will be also used for action callbacks and for test tags
 * @param title title of this setting
 * @param subtitle optional subtitle of this setting
 * @param checked
 * @param modifier
 * @param enabled
 * @param footerText optional footer text for this setting
 * @param onSettingsChanged callback for clicks on this component, either the toggle or the main component, but not the footer if exists
 */
@Composable
fun SettingsToggleItem(
    key: String,
    title: String,
    subtitle: String?,
    checked: Boolean,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    footerText: String? = null,
    onSettingsChanged: (key: String, newValue: Boolean) -> Unit,
) {
    SettingsWithFooter(
        footerText = footerText,
        modifier = modifier
    ) {
        FlexibleLineListItem(
            modifier = Modifier.testTag(SettingsItemConst.listItemTag(key)),
            minHeight = SettingsItemConst.minHeight,
            enableClick = enabled,
            onClickListener = {
                onSettingsChanged(key, !checked)
            },
            title = title,
            subtitle = subtitle,
            trailingElement = {
                Toggle(
                    modifier = Modifier.testTag(SettingsItemConst.toggleTag(key)),
                    isChecked = checked,
                    onCheckedChange = {
                        onSettingsChanged(key, it)
                    },
                    isEnabled = enabled,
                )
            }
        )
    }
}


@CombinedThemePreviews
@Composable
private fun SettingToggleItemPreview(
    @PreviewParameter(SettingsToggleItemPreviewProvider::class) parameters: SettingsToggleItemPreviewParameters
) {
    AndroidThemeForPreviews {
        var checked by remember { mutableStateOf(parameters.checked) }
        SettingsToggleItem(
            key = "key",
            title = parameters.title,
            subtitle = parameters.subtitle,
            checked = checked,
            enabled = parameters.enabled,
            footerText = parameters.footerText,
        ) { key, value ->
            checked = value
        }
    }
}

private data class SettingsToggleItemPreviewParameters(
    val title: String,
    val subtitle: String? = null,
    val checked: Boolean = true,
    val enabled: Boolean = true,
    val footerText: String? = null,
)

private class SettingsToggleItemPreviewProvider :
    PreviewParameterProvider<SettingsToggleItemPreviewParameters> {
    override val values: Sequence<SettingsToggleItemPreviewParameters> = sequenceOf(
        SettingsToggleItemPreviewParameters(
            "Simple title",
        ),
        SettingsToggleItemPreviewParameters(
            "Sound notifications",
            subtitle = "Hear a sound when someone joins or leaves a call",
        ),
        SettingsToggleItemPreviewParameters(
            "Use mobile data to load high resolution images",
            subtitle = "If disabled, the high resolution image will only be loaded when you zoom in.",
            checked = false,
        ),
        SettingsToggleItemPreviewParameters(
            "File versioning",
            subtitle = "Create multiple versions of your files. Disabling this setting does not stop your contacts from creating new versions.",
            footerText = "15 File versions. Total size taken by file versions: 107 kB",
        ),
    )

}