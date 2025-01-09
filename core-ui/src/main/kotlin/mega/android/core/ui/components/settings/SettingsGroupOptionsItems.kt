package mega.android.core.ui.components.settings

import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import mega.android.core.ui.R
import mega.android.core.ui.components.button.MegaRadioButton
import mega.android.core.ui.components.image.MegaIcon
import mega.android.core.ui.components.list.OneLineListItem
import mega.android.core.ui.components.settings.SettingsItemConst.listOptionItemTag
import mega.android.core.ui.components.settings.SettingsItemConst.radioButtonTag
import mega.android.core.ui.preview.CombinedThemePreviews
import mega.android.core.ui.theme.AndroidThemeForPreviews
import mega.android.core.ui.theme.values.IconColor
import mega.android.core.ui.theme.values.SupportColor

/**
 * Settings Group Options Items. List of items where only one can be chosen.
 *
 * @param T type of selectable values
 * @param key the key of this setting. It will be also used for action callbacks and for test tags
 * @param values list of all possible values for this setting
 * @param selectedValue current selected value
 * @param modifier
 * @param valueToString lambda function to get the name of each value. Default will use `toString` of each value.
 * @param leadingElement optional leading element
 * @param enabled
 * @param onValueSelected callback for clicks on this component
 */
@Composable
fun <T : Any> ColumnScope.SettingsGroupOptionsItems(
    key: String,
    values: List<T>,
    selectedValue: T?,
    modifier: Modifier = Modifier,
    valueToString: (T) -> String = { it.toString() },
    leadingElement: (T) -> (@Composable BoxScope.() -> Unit)? = { null },
    enabled: Boolean = true,
    onValueSelected: (key: String, value: T) -> Unit,
) {
    values.forEachIndexed { i, value ->
        OneLineListItem(
            modifier = Modifier.testTag(listOptionItemTag(key, i)),
            text = valueToString(value),
            leadingElement = leadingElement(value),
            onClickListener = { onValueSelected(key, value) },
            enableClick = enabled,
            trailingElement = {
                MegaRadioButton(
                    modifier = Modifier.testTag(radioButtonTag(key, i)),
                    identifier = value,
                    selected = value == selectedValue,
                    enabled = enabled,
                    onOptionSelected = { onValueSelected(key, value) }
                )
            }
        )
    }
}

@CombinedThemePreviews
@Composable
private fun SettingsGroupOptionsItemsPreview(
    @PreviewParameter(GroupItemForPreviewProvider::class) values: List<GroupItemForPreview>,
) {
    AndroidThemeForPreviews {
        var selectedValue by remember { mutableStateOf(values.first()) }
        Column {
            SettingsGroupOptionsItems(
                key = "key",
                valueToString = GroupItemForPreview::title,
                leadingElement = GroupItemForPreview::icon,
                values = values,
                selectedValue = selectedValue,
            ) { _, value ->
                selectedValue = value
            }
        }
    }
}

private class GroupItemForPreviewProvider : PreviewParameterProvider<List<GroupItemForPreview>> {
    override val values: Sequence<List<GroupItemForPreview>> =
        sequenceOf(
            (1..5).map {
                GroupItemForPreview(it, "Item $it")
            },
            listOf(
                GroupItemForPreview("Online") {
                    MegaIcon(
                        modifier = Modifier.align(Alignment.Center),
                        painter = painterResource(id = R.drawable.ic_dot_medium_thin_solid),
                        supportTint = SupportColor.Success
                    )
                },
                GroupItemForPreview("Away") {
                    MegaIcon(
                        modifier = Modifier.align(Alignment.Center),
                        painter = painterResource(id = R.drawable.ic_dot_medium_thin_solid),
                        supportTint = SupportColor.Warning
                    )
                },
                GroupItemForPreview("Busy") {
                    MegaIcon(
                        modifier = Modifier.align(Alignment.Center),
                        painter = painterResource(id = R.drawable.ic_dot_medium_thin_solid),
                        supportTint = SupportColor.Error
                    )
                },
                GroupItemForPreview("Offline")
                {
                    MegaIcon(
                        modifier = Modifier.align(Alignment.Center),
                        painter = painterResource(id = R.drawable.ic_dot_medium_thin_solid),
                        tint = IconColor.Disabled
                    )
                },
            )
        )

}

private data class GroupItemForPreview(
    val key: Any,
    val title: String = key.toString(),
    val icon: (@Composable BoxScope.() -> Unit)? = null
)