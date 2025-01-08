package mega.android.core.ui.components.settings

import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import mega.android.core.ui.R
import mega.android.core.ui.components.image.MegaIcon
import mega.android.core.ui.components.list.HeaderTextStyle
import mega.android.core.ui.components.list.OneLineListItem
import mega.android.core.ui.components.list.PrimaryHeaderListItem
import mega.android.core.ui.components.sheets.MegaBottomSheetDragHandler
import mega.android.core.ui.components.sheets.MegaModalBottomSheet
import mega.android.core.ui.components.sheets.MegaModalBottomSheetBackground
import mega.android.core.ui.extensions.composeLet
import mega.android.core.ui.preview.CombinedThemePreviews
import mega.android.core.ui.theme.AndroidThemeForPreviews
import mega.android.core.ui.theme.values.IconColor


/**
 * Settings Options Item. Item to allow to choose and option from a small set of option
 *
 * @param T type of selectable values
 * @param key the key of this setting. It will be also used for action callbacks and for test tags
 * @param title the title of this setting.
 * @param values list of all possible values for this setting
 * @param selectedValue current selected value
 * @param modifier
 * @param valueToString lambda function to get the name of each value. Default will use `toString` of each value.
 * @param enabled
 * @param onValueSelected callback for clicks on this component
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun <T> SettingsOptionsItem(
    key: String,
    title: String,
    values: List<T>,
    selectedValue: T?,
    modifier: Modifier = Modifier,
    valueToString: (T) -> String = { it.toString() },
    enabled: Boolean = true,
    onValueSelected: (key: String, value: T) -> Unit,
) {
    var showOptions by remember { mutableStateOf(false) }
    SettingsNavigationItem(
        key = key,
        title = title,
        modifier = modifier,
        enabled = enabled,
        subtitle = selectedValue?.let { valueToString(it) },
    ) {
        showOptions = true
    }
    if (showOptions) {
        SettingsOptionsModal(
            key = key,
            title = title,
            values = values,
            valueToString = valueToString,
            selectedValue = selectedValue,
            onDismiss = { showOptions = false },
            modifier = Modifier.testTag(SettingsItemConst.bottomSheetTag(key))) {
            onValueSelected(key, it)
        }
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
internal fun <T> SettingsOptionsModal(
    key: String,
    title: String,
    values: List<T>,
    valueToString: (T) -> String,
    selectedValue: T?,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier,
    onValueSelected: (T) -> Unit,
) {
    val modalBottomSheetState = rememberModalBottomSheetState()
    MegaModalBottomSheet(
        modifier = modifier,
        sheetState = modalBottomSheetState,
        bottomSheetBackground = MegaModalBottomSheetBackground.Surface1,
        onDismissRequest = {
            onDismiss()
        },
        dragHandle = { MegaBottomSheetDragHandler() }
    ) {
        PrimaryHeaderListItem(
            text = title,
            headerTextStyle = HeaderTextStyle.Medium,
            modifier = Modifier
                .testTag(SettingsItemConst.headerTag(key))
                .heightIn(min = SettingsItemConst.minHeight)
        )
        LazyColumn {
            items(items = values) { value ->
                OneLineListItem(
                    text = valueToString(value),
                    trailingElement = takeIf { value == selectedValue }?.composeLet {
                        MegaIcon(
                            painter = painterResource(R.drawable.ic_check_medium_thin_outline),
                            modifier = Modifier.testTag(SettingsItemConst.checkTag(key)),
                            tint = IconColor.Secondary
                        )
                    },
                    onClickListener = {
                        onDismiss()
                        onValueSelected(value)
                    }
                )
            }
        }
    }
}

@CombinedThemePreviews
@Composable
private fun SettingsOptionsItemPreview() {
    AndroidThemeForPreviews {
        val values = (1..50).map { "Item $it" }
        var selectedValue by remember { mutableStateOf(values.first()) }
        SettingsOptionsItem(
            key = "key",
            title = "Options Settings",
            values = values,
            selectedValue = selectedValue,
        ) { _, value ->
            selectedValue = value
        }
    }
}