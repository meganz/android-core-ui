package mega.android.core.ui.components.datepicker

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import mega.android.core.ui.theme.AndroidTheme
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.util.Calendar
import java.util.TimeZone

@RunWith(AndroidJUnit4::class)
class MegaDatePickerDialogTest {

    @get:Rule
    val composeRule = createComposeRule()

    @Test
    fun `test that the dialog is displayed`() {
        setContent()

        composeRule.onNodeWithTag(MEGA_DATE_PICKER_DIALOG_TAG).assertIsDisplayed()
    }

    @Test
    fun `test that confirm is disabled when no date is selected`() {
        setContent(initialSelectedTimeMillis = null)

        composeRule.onNodeWithTag(MEGA_DATE_PICKER_CONFIRM_TAG).assertIsNotEnabled()
    }

    @Test
    fun `test that confirm is enabled when a date is pre-selected`() {
        setContent(initialSelectedTimeMillis = FUTURE_UTC_MIDNIGHT)

        composeRule.onNodeWithTag(MEGA_DATE_PICKER_CONFIRM_TAG).assertIsEnabled()
    }

    @Test
    fun `test that confirming a pre-selected date reports its millis`() {
        var selected: Long? = null
        setContent(initialSelectedTimeMillis = FUTURE_UTC_MIDNIGHT, onDateSelected = { selected = it })

        composeRule.onNodeWithTag(MEGA_DATE_PICKER_CONFIRM_TAG).performClick()

        assertEquals(FUTURE_UTC_MIDNIGHT, selected)
    }

    @Test
    fun `test that tapping dismiss invokes onDismiss`() {
        var dismissed = false
        setContent(onDismiss = { dismissed = true })

        composeRule.onNodeWithTag(MEGA_DATE_PICKER_DISMISS_TAG).performClick()

        assertTrue(dismissed)
    }

    private fun setContent(
        initialSelectedTimeMillis: Long? = null,
        onDateSelected: (Long) -> Unit = {},
        onDismiss: () -> Unit = {},
    ) {
        composeRule.setContent {
            AndroidTheme(isDark = false) {
                MegaDatePickerDialog(
                    confirmText = "OK",
                    dismissText = "Cancel",
                    onDateSelected = onDateSelected,
                    onDismiss = onDismiss,
                    initialSelectedTimeMillis = initialSelectedTimeMillis,
                )
            }
        }
    }

    private companion object {
        val FUTURE_UTC_MIDNIGHT: Long =
            Calendar.getInstance(TimeZone.getTimeZone("UTC")).apply {
                clear()
                set(2035, Calendar.JANUARY, 1)
            }.timeInMillis
    }
}
