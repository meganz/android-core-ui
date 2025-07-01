package mega.android.core.ui.components.settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import mega.android.core.ui.R
import mega.android.core.ui.components.MegaText
import mega.android.core.ui.components.image.MegaIcon
import mega.android.core.ui.components.list.HeaderTextStyle
import mega.android.core.ui.components.list.OneLineListItem
import mega.android.core.ui.components.list.PrimaryHeaderListItem
import mega.android.core.ui.components.sheets.MegaModalBottomSheet
import mega.android.core.ui.components.sheets.MegaModalBottomSheetBackground
import mega.android.core.ui.extensions.composeLet
import mega.android.core.ui.preview.CombinedThemePreviews
import mega.android.core.ui.theme.AndroidThemeForPreviews
import mega.android.core.ui.theme.AppTheme
import mega.android.core.ui.theme.spacing.LocalSpacing
import mega.android.core.ui.theme.values.IconColor
import mega.android.core.ui.theme.values.TextColor


/**
 * Settings Options Item. Item to allow choosing an option from a list of options using a bottom sheet selector.
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
            modifier = Modifier.testTag(SettingsItemConst.bottomSheetTag(key))
        ) {
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

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun <T> SettingsOptionsModal(
    key: String,
    content: @Composable SettingOptionsModalScope<T>.() -> Unit,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier,
    onValueSelected: (T) -> Unit,
) {
    val modalBottomSheetState = rememberModalBottomSheetState()
    val scope = SettingOptionsModalScope<T>().apply { content() }
    MegaModalBottomSheet(
        modifier = modifier,
        sheetState = modalBottomSheetState,
        bottomSheetBackground = MegaModalBottomSheetBackground.Surface1,
        onDismissRequest = {
            onDismiss()
        },
    ) {
        scope.buildHeader()?.Header(
            modifier = Modifier.testTag(SettingsItemConst.headerTag(key))
        )
        LazyColumn {
            items(items = scope.buildItems(), key = { "${it.value}" }) { item ->
                item.Item(
                    modifier = Modifier.testTag(SettingsItemConst.checkTag(key)),
                    onValueSelected = {
                        onDismiss()
                        onValueSelected(it)
                    }
                )
            }
        }
    }
}

@Stable
internal data class SettingsSheetHeaderContent(
    val title: String,
    val subtitle: String? = null,
) {
    @Composable
    fun Header(
        modifier: Modifier,
    ) {
        Column(
            modifier = modifier
                .padding(horizontal = LocalSpacing.current.x16, vertical = LocalSpacing.current.x8)
                .heightIn(min = SettingsItemConst.minHeight),
            verticalArrangement = Arrangement.Center
        ) {
            MegaText(
                text = title,
                style = AppTheme.typography.titleMedium,
                textColor = TextColor.Primary,
            )

            subtitle?.let {
                MegaText(
                    text = it,
                    style = AppTheme.typography.bodyMedium,
                    textColor = TextColor.Secondary,
                )
            }
        }
    }
}

@Stable
internal data class SettingsSheetItemContent<T>(
    val isSelected: Boolean,
    val value: T,
    val valueToString: (T) -> String,
) {
    @Composable
    fun Item(
        modifier: Modifier,
        onValueSelected: (T) -> Unit,
    ) {
        OneLineListItem(
            text = valueToString(value),
            trailingElement = takeIf { isSelected }?.composeLet {
                MegaIcon(
                    painter = painterResource(R.drawable.ic_check_medium_thin_outline),
                    modifier = modifier,
                    tint = IconColor.Secondary
                )
            },
            onClickListener = {
                onValueSelected(value)
            }
        )
    }
}

class SettingOptionsModalScope<T> {
    private var headerCell: SettingsSheetHeaderContent? = null
    private val itemCells = mutableListOf<SettingsSheetItemContent<T>>()

    fun addHeader(
        title: String,
        subtitle: String? = null,
    ) {
        headerCell = SettingsSheetHeaderContent(
            title = title,
            subtitle = subtitle
        )
    }

    fun addItem(
        isSelected: Boolean,
        value: T,
        valueToString: (T) -> String,
    ) {
        itemCells.add(
            SettingsSheetItemContent(
                isSelected = isSelected,
                value = value,
                valueToString = valueToString
            )
        )
    }

    internal fun buildHeader(): SettingsSheetHeaderContent? = headerCell

    internal fun buildItems(): ImmutableList<SettingsSheetItemContent<T>> =
        persistentListOf(*itemCells.toTypedArray())
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