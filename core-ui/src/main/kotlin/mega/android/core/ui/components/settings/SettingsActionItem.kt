package mega.android.core.ui.components.settings

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import mega.android.core.ui.components.indicators.SmallInfiniteSpinnerIndicator
import mega.android.core.ui.components.list.FlexibleLineListItem
import mega.android.core.ui.preview.CombinedThemePreviews
import mega.android.core.ui.theme.AndroidThemeForPreviews
import mega.android.core.ui.theme.values.TextColor
import kotlin.time.Duration.Companion.seconds

/**
 * Settings action item. Component to allow actions on settings.
 *
 * @param key the key for this action, it will be used for action callbacks and for test tags
 * @param title title for this action
 * @param modifier
 * @param enabled
 * @param destructive destructive color will be used if this parameter is true to emphasize destructive actions
 * @param loading if it's true a indeterminate progress indicator will be shown
 * @param footerText optional footer text for this setting
 * @param onClicked callback for clicks on this component, either the toggle or the main component, but not the footer if exists
 */
@Composable
fun SettingsActionItem(
    key: String,
    title: String,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    destructive: Boolean = false,
    loading: Boolean = false,
    footerText: String? = null,
    onClicked: (key: String) -> Unit,
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
                onClicked(key)
            },
            title = title,
            titleTextColor = when {
                !enabled -> TextColor.Disabled
                destructive -> TextColor.Error
                else -> TextColor.Primary
            },
            trailingElement = {
                if (loading) {
                    SmallInfiniteSpinnerIndicator(
                        modifier = Modifier
                            .testTag(SettingsItemConst.progressIndicatorTag(key))
                    )
                }
            }
        )
    }
}


@CombinedThemePreviews
@Composable
private fun SettingActionItemPreview(
    @PreviewParameter(SettingsActionItemPreviewProvider::class) parameters: SettingsActionItemPreviewParameters
) {
    AndroidThemeForPreviews {
        var loading by remember { mutableStateOf(false) }
        var enabled by remember { mutableStateOf(true) }
        val coroutineScope = rememberCoroutineScope()
        SettingsActionItem(
            key = "key",
            title = parameters.title,
            destructive = parameters.destructive,
            loading = loading,
            enabled = enabled,
            footerText = parameters.footerText,
        ) { key ->
            loading = true
            enabled = false
            coroutineScope.launch {
                delay(2.seconds)
                loading = false
                enabled = true
            }
        }
    }
}

private data class SettingsActionItemPreviewParameters(
    val title: String,
    val destructive: Boolean = false,
    val footerText: String? = null,
)

private class SettingsActionItemPreviewProvider :
    PreviewParameterProvider<SettingsActionItemPreviewParameters> {
    override val values: Sequence<SettingsActionItemPreviewParameters> = sequenceOf(
        SettingsActionItemPreviewParameters(
            "Simple title",
        ),
        SettingsActionItemPreviewParameters(
            "Empty Rubbish Bin",
            destructive = true,
        ),
        SettingsActionItemPreviewParameters(
            "Delete all older version of files",
            footerText = "All current files will remain. Only historic versions of your files will be deleted.",
            destructive = true,
        ),
    )

}