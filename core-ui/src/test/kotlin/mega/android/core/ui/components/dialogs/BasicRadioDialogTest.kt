package mega.android.core.ui.components.dialogs

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.test.ext.junit.runners.AndroidJUnit4
import kotlinx.collections.immutable.persistentListOf
import mega.android.core.ui.components.text.SpannableText
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class BasicRadioDialogTest {

    @get:Rule
    val composeRule = createComposeRule()

    @Test
    fun `test that the description is displayed when it is provided`() {
        setContent(description = DESCRIPTION)

        composeRule.onNodeWithTag(BASIC_RADIO_DIALOG_DESCRIPTION_TAG).assertIsDisplayed()
        composeRule.onNodeWithText(DESCRIPTION).assertIsDisplayed()
    }

    @Test
    fun `test that the description is not displayed when it is not provided`() {
        setContent(description = null)

        composeRule.onNodeWithTag(BASIC_RADIO_DIALOG_DESCRIPTION_TAG).assertDoesNotExist()
    }

    @Test
    fun `test that the description is not displayed when it is empty`() {
        setContent(description = "")

        composeRule.onNodeWithTag(BASIC_RADIO_DIALOG_DESCRIPTION_TAG).assertDoesNotExist()
    }

    @Test
    fun `test that the title and options are displayed when a description is provided`() {
        setContent(description = DESCRIPTION)

        composeRule.onNodeWithText(TITLE).assertIsDisplayed()
        composeRule.onNodeWithText(FIRST_OPTION).assertIsDisplayed()
        composeRule.onNodeWithText(SECOND_OPTION).assertIsDisplayed()
    }

    private fun setContent(description: String?) {
        composeRule.setContent {
            BasicRadioDialog(
                title = SpannableText(TITLE),
                description = description?.let { SpannableText(it) },
                options = persistentListOf(
                    BasicDialogRadioOption(0, FIRST_OPTION),
                    BasicDialogRadioOption(1, SECOND_OPTION),
                ),
                selectedOption = BasicDialogRadioOption(0, FIRST_OPTION),
                onOptionSelected = {},
                buttons = persistentListOf(
                    BasicDialogButton(text = "Cancel", onClick = {})
                ),
                onDismissRequest = {},
            )
        }
    }

    private companion object {
        const val TITLE = "Battery usage"
        const val DESCRIPTION = "Syncs and backups pause below 20% battery when not charging."
        const val FIRST_OPTION = "Sync always"
        const val SECOND_OPTION = "Sync only when charging"
    }
}
