package mega.android.core.ui.components.datepicker

import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerColors
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SelectableDates
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import mega.android.core.ui.components.button.TextOnlyButton
import mega.android.core.ui.preview.CombinedThemePreviews
import mega.android.core.ui.theme.AndroidThemeForPreviews
import mega.android.core.ui.tokens.theme.DSTokens

/**
 * A Material3 date-picker dialog themed with the MEGA design system.
 *
 * The design system has no native date picker, so this wraps Material3's [DatePickerDialog] and
 * maps its palette onto [DSTokens] (the ambient Material3 `colorScheme` is intentionally not
 * populated by [mega.android.core.ui.theme.AndroidTheme], so the platform picker would otherwise
 * fall back to the stock baseline colors and ignore dark mode).
 *
 * The confirm action is disabled until a date is selected and reports the selection in UTC
 * milliseconds.
 *
 * @param confirmText Label for the confirm button (e.g. "OK").
 * @param dismissText Label for the dismiss button (e.g. "Cancel").
 * @param onDateSelected Invoked with the selected date, in UTC milliseconds, when the user confirms.
 * @param onDismiss Invoked when the user cancels or dismisses the dialog.
 * @param modifier Modifier for the dialog.
 * @param initialSelectedTimeMillis The date to pre-select, in UTC milliseconds, or null for none.
 * @param selectableDates Constrains which dates can be selected. Defaults to all dates.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MegaDatePickerDialog(
    confirmText: String,
    dismissText: String,
    onDateSelected: (Long) -> Unit,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier,
    initialSelectedTimeMillis: Long? = null,
    selectableDates: SelectableDates = DatePickerDefaults.AllDates,
) {
    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = initialSelectedTimeMillis,
        selectableDates = selectableDates,
    )
    val confirmEnabled by remember {
        derivedStateOf { datePickerState.selectedDateMillis != null }
    }
    val colors = megaDatePickerColors()

    DatePickerDialog(
        modifier = modifier.testTag(MEGA_DATE_PICKER_DIALOG_TAG),
        onDismissRequest = onDismiss,
        colors = colors,
        confirmButton = {
            TextOnlyButton(
                modifier = Modifier.testTag(MEGA_DATE_PICKER_CONFIRM_TAG),
                text = confirmText,
                enabled = confirmEnabled,
                onClick = { datePickerState.selectedDateMillis?.let(onDateSelected) },
            )
        },
        dismissButton = {
            TextOnlyButton(
                modifier = Modifier.testTag(MEGA_DATE_PICKER_DISMISS_TAG),
                text = dismissText,
                onClick = onDismiss,
            )
        },
    ) {
        DatePicker(state = datePickerState, colors = colors)
    }
}

/**
 * Maps the Material3 date-picker palette onto MEGA design tokens so the picker matches the design
 * in light and dark. Every slot is set explicitly; any left unspecified would re-derive from the
 * unthemed Material3 baseline scheme.
 *
 * The result is remembered against the (theme-stable) token set so it is rebuilt only when the
 * theme changes, not on every recomposition.
 */
@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun megaDatePickerColors(): DatePickerColors {
    val colors = DSTokens.colors
    val defaults = DatePickerDefaults.colors()
    return remember(colors) {
        defaults.copy(
            containerColor = colors.background.surface1,
            titleContentColor = colors.text.secondary,
            headlineContentColor = colors.text.primary,
            weekdayContentColor = colors.text.secondary,
            subheadContentColor = colors.text.secondary,
            navigationContentColor = colors.icon.primary,
            yearContentColor = colors.text.primary,
            disabledYearContentColor = colors.text.disabled,
            currentYearContentColor = colors.text.primary,
            selectedYearContentColor = colors.text.inverseAccent,
            disabledSelectedYearContentColor = colors.text.onColorDisabled,
            selectedYearContainerColor = colors.components.selectionControl,
            disabledSelectedYearContainerColor = colors.button.disabled,
            dayContentColor = colors.text.primary,
            disabledDayContentColor = colors.text.disabled,
            selectedDayContentColor = colors.text.inverseAccent,
            disabledSelectedDayContentColor = colors.text.onColorDisabled,
            selectedDayContainerColor = colors.components.selectionControl,
            disabledSelectedDayContainerColor = colors.button.disabled,
            todayContentColor = colors.text.primary,
            todayDateBorderColor = colors.border.strong,
            dayInSelectionRangeContentColor = colors.text.primary,
            dayInSelectionRangeContainerColor = colors.background.surface2,
            dividerColor = colors.border.subtle,
        )
    }
}

@CombinedThemePreviews
@Composable
private fun MegaDatePickerDialogPreview() {
    AndroidThemeForPreviews {
        MegaDatePickerDialog(
            confirmText = "OK",
            dismissText = "Cancel",
            onDateSelected = {},
            onDismiss = {},
        )
    }
}

internal const val MEGA_DATE_PICKER_DIALOG_TAG = "mega_date_picker_dialog:dialog"
internal const val MEGA_DATE_PICKER_CONFIRM_TAG = "mega_date_picker_dialog:button_confirm"
internal const val MEGA_DATE_PICKER_DISMISS_TAG = "mega_date_picker_dialog:button_dismiss"
