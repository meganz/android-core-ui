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
import mega.android.core.ui.components.dialogs.BasicDialog
import mega.android.core.ui.components.indicators.SmallInfiniteSpinnerIndicator
import mega.android.core.ui.components.list.FlexibleLineListItem
import mega.android.core.ui.components.text.SpannableText
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
 * @param subtitle optional subtitle of this action
 * @param enabled
 * @param destructive destructive color will be used if this parameter is true to emphasize destructive actions
 * @param loading if it's true a indeterminate progress indicator will be shown
 * @param footerText optional footer text for this action
 * @param onClicked callback for clicks on this component, either the toggle or the main component, but not the footer if exists
 */
@Composable
fun SettingsActionItem(
    key: String,
    title: String,
    modifier: Modifier = Modifier,
    subtitle: String? = null,
    enabled: Boolean = true,
    destructive: Boolean = false,
    loading: Boolean = false,
    footerText: String? = null,
    confirmAction: ConfirmActionParameters? = null,
    onClicked: (key: String) -> Unit,
) {
    SettingsWithFooter(
        footerText = footerText,
        modifier = modifier
    ) {
        var showConfirmation by remember { mutableStateOf(false) }
        FlexibleLineListItem(
            modifier = Modifier.testTag(SettingsItemConst.listItemTag(key)),
            minHeight = SettingsItemConst.minHeight,
            enableClick = enabled,
            onClickListener = {
                confirmAction?.let {
                    showConfirmation = true
                }?:run {
                    onClicked(key)
                }
            },
            title = title,
            subtitle = subtitle,
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
        if (showConfirmation) {
            SettingsActionConfirmationDialog(
                confirmAction = confirmAction,
                onConfirmed = {
                    showConfirmation = false
                    onClicked(key)
                },
                onDismiss = {
                    showConfirmation = false
                }
            )
        }
    }
}

@Composable
internal fun SettingsActionConfirmationDialog(
    confirmAction: ConfirmActionParameters?,
    onConfirmed: () -> Unit,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier,
) {
    confirmAction?.apply {
        BasicDialog(
            modifier = modifier,
            title = SpannableText(title),
            description = SpannableText(description),
            positiveButtonText = confirmTitle,
            negativeButtonText = cancelTitle,
            onNegativeButtonClicked = onDismiss,
            onPositiveButtonClicked = onConfirmed,
            onDismiss = onDismiss,
        )
    }
}

data class ConfirmActionParameters(
    val title: String,
    val description: String?,
    val confirmTitle: String,
    val cancelTitle: String,
)


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
            subtitle = parameters.subtitle,
            destructive = parameters.destructive,
            loading = loading,
            enabled = enabled,
            footerText = parameters.footerText,
            confirmAction = parameters.confirmAction
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
    val subtitle: String? = null,
    val footerText: String? = null,
    val confirmAction: ConfirmActionParameters? = null,
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
            confirmAction = ConfirmActionParameters(
                title = "Empty Rubbish bin?",
                description = "All items in the rubbish bin will be deleted.",
                confirmTitle = "Empty",
                cancelTitle = "Cancel"
            )
        ),
        SettingsActionItemPreviewParameters(
            "Clear cache",
            subtitle = "193.8MB",
        ),
        SettingsActionItemPreviewParameters(
            "Delete all older version of files",
            footerText = "All current files will remain. Only historic versions of your files will be deleted.",
            destructive = true,
        ),
    )

}