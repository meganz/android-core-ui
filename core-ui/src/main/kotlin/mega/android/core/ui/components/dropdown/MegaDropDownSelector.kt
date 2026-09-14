package mega.android.core.ui.components.dropdown

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.error
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import mega.android.core.ui.R
import mega.android.core.ui.components.inputFieldHeight
import mega.android.core.ui.components.inputfields.HelpTextError
import mega.android.core.ui.preview.CombinedThemePreviews
import mega.android.core.ui.theme.AndroidThemeForPreviews
import mega.android.core.ui.theme.AppTheme
import mega.android.core.ui.theme.spacing.LocalSpacing
import mega.android.core.ui.tokens.theme.DSTokens

internal const val DROP_DOWN_SELECTOR_LABEL_TAG = "drop_down_selector:text_label"
internal const val DROP_DOWN_SELECTOR_OPTIONAL_LABEL_TAG = "drop_down_selector:text_optional_label"
internal const val DROP_DOWN_SELECTOR_BOX_TAG = "drop_down_selector:box"
internal const val DROP_DOWN_SELECTOR_VALUE_TAG = "drop_down_selector:text_value"
internal const val DROP_DOWN_SELECTOR_PLACEHOLDER_TAG = "drop_down_selector:text_placeholder"
internal const val DROP_DOWN_SELECTOR_TRAILING_ICON_TAG = "drop_down_selector:icon_trailing"
internal const val DROP_DOWN_SELECTOR_ERROR_HELP_TEXT_TAG = "drop_down_selector:help_text_error"

/**
 * Drop down selector
 *
 * The closed, tappable half of a drop down: it shows the current selection and opens a picker
 * such as [MegaDropDownMenu] or a bottom sheet. It is not a text field - the value can only be
 * changed through whatever the click opens.
 *
 * @param value The currently selected value.
 * @param onClick Callback invoked when the user taps the selector.
 * @param modifier Modifier for the selector box. It wraps its content by default; pass
 * `Modifier.fillMaxWidth()` or a weight to stretch it.
 * @param placeholder Text shown while [value] is empty.
 * @param label Optional label displayed above the selector.
 * @param optionalLabelText Optional supporting text displayed next to [label].
 * @param enabled When false the selector cannot be tapped.
 * @param expanded Whether the picker this selector opens is currently showing.
 * @param isError Whether the selector is in an error state.
 * @param errorText Error message displayed below the selector.
 * @param trailingIcon Trailing icon. Defaults to a 24dp down chevron.
 */
@Composable
fun MegaDropDownSelector(
    value: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    placeholder: String? = null,
    label: String? = null,
    optionalLabelText: String? = null,
    enabled: Boolean = true,
    expanded: Boolean = false,
    isError: Boolean = false,
    errorText: String? = null,
    trailingIcon: @Composable (() -> Unit)? = {
        Icon(
            modifier = Modifier
                .size(24.dp)
                .testTag(DROP_DOWN_SELECTOR_TRAILING_ICON_TAG),
            painter = painterResource(id = R.drawable.ic_chevron_down_medium_thin_outline),
            tint = if (enabled) DSTokens.colors.icon.primary else DSTokens.colors.icon.disabled,
            contentDescription = null,
        )
    },
) {
    val spacing = LocalSpacing.current
    val shape = DSTokens.shapes.small
    val containerColor = if (enabled) {
        DSTokens.colors.background.pageBackground
    } else {
        DSTokens.colors.button.disabled
    }
    val borderColor = when {
        !enabled -> DSTokens.colors.border.disabled
        isError || !errorText.isNullOrBlank() -> DSTokens.colors.support.error
        expanded -> DSTokens.colors.border.strongSelected
        else -> DSTokens.colors.border.strong
    }

    Column {
        label?.let {
            Row(
                modifier = Modifier
                    .wrapContentWidth()
                    .padding(bottom = spacing.x4)
            ) {
                Text(
                    modifier = Modifier.testTag(DROP_DOWN_SELECTOR_LABEL_TAG),
                    text = label,
                    style = AppTheme.typography.titleSmall,
                    color = DSTokens.colors.text.primary
                )
                optionalLabelText?.let {
                    Text(
                        modifier = Modifier
                            .padding(start = spacing.x8)
                            .testTag(DROP_DOWN_SELECTOR_OPTIONAL_LABEL_TAG),
                        text = optionalLabelText,
                        style = AppTheme.typography.bodyMedium,
                        color = DSTokens.colors.text.secondary
                    )
                }
            }
        }

        Box(
            modifier = modifier
                .heightIn(min = inputFieldHeight)
                .testTag(DROP_DOWN_SELECTOR_BOX_TAG)
                .background(color = containerColor, shape = shape)
                .border(width = 1.dp, color = borderColor, shape = shape)
                .clip(shape)
                .clickable(
                    enabled = enabled,
                    role = Role.DropdownList,
                    onClick = onClick
                )
                .semantics {
                    if (isError || !errorText.isNullOrBlank()) {
                        error(errorText.orEmpty())
                    }
                },
            contentAlignment = Alignment.CenterStart
        ) {
            Row(
                modifier = Modifier.padding(horizontal = spacing.x12, vertical = spacing.x12),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                if (value.isNotEmpty()) {
                    Text(
                        modifier = Modifier.testTag(DROP_DOWN_SELECTOR_VALUE_TAG),
                        text = value,
                        style = AppTheme.typography.bodyLarge,
                        color = if (enabled) DSTokens.colors.text.primary else DSTokens.colors.text.disabled,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                } else if (!placeholder.isNullOrBlank()) {
                    Text(
                        modifier = Modifier.testTag(DROP_DOWN_SELECTOR_PLACEHOLDER_TAG),
                        text = placeholder,
                        style = AppTheme.typography.bodyLarge,
                        color = if (enabled) DSTokens.colors.text.placeholder else DSTokens.colors.text.disabled,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
                Box(modifier = Modifier.padding(start = spacing.x16)) {
                    trailingIcon?.invoke()
                }
            }
        }

        if (!errorText.isNullOrBlank()) {
            HelpTextError(
                modifier = Modifier
                    .padding(top = spacing.x4)
                    .testTag(DROP_DOWN_SELECTOR_ERROR_HELP_TEXT_TAG),
                text = errorText
            )
        }
    }
}

@CombinedThemePreviews
@Composable
private fun MegaDropDownSelectorPreview() {
    AndroidThemeForPreviews {
        MegaDropDownSelector(
            value = "MB",
            onClick = {},
        )
    }
}

@CombinedThemePreviews
@Composable
private fun MegaDropDownSelectorExpandedPreview() {
    AndroidThemeForPreviews {
        MegaDropDownSelector(
            value = "MB",
            onClick = {},
            expanded = true,
        )
    }
}

@CombinedThemePreviews
@Composable
private fun MegaDropDownSelectorPlaceholderPreview() {
    AndroidThemeForPreviews {
        MegaDropDownSelector(
            value = "",
            placeholder = "Select unit",
            onClick = {},
        )
    }
}

@CombinedThemePreviews
@Composable
private fun MegaDropDownSelectorErrorPreview() {
    AndroidThemeForPreviews {
        MegaDropDownSelector(
            value = "KB",
            onClick = {},
            errorText = "Invalid unit",
        )
    }
}

@CombinedThemePreviews
@Composable
private fun MegaDropDownSelectorDisabledPreview() {
    AndroidThemeForPreviews {
        MegaDropDownSelector(
            value = "GB",
            onClick = {},
            enabled = false,
        )
    }
}
