package mega.android.core.ui.components.dialogs

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.test.ext.junit.runners.AndroidJUnit4
import kotlinx.collections.immutable.persistentListOf
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class BasicDialogContentTest {

    @get:Rule
    val composeRule = createComposeRule()

    @Test
    fun `test that the content is displayed when it is provided`() {
        setContent(content = { Text(CONTENT, modifier = Modifier.testTag(CONTENT_TAG)) })

        composeRule.onNodeWithTag(CONTENT_TAG).assertIsDisplayed()
        composeRule.onNodeWithText(CONTENT).assertIsDisplayed()
    }

    @Test
    fun `test that no content is displayed when it is not provided`() {
        setContent(content = null)

        composeRule.onNodeWithTag(CONTENT_TAG).assertDoesNotExist()
    }

    @Test
    fun `test that the title description and buttons are displayed alongside the content`() {
        setContent(content = { Text(CONTENT, modifier = Modifier.testTag(CONTENT_TAG)) })

        composeRule.onNodeWithTag(BASIC_DIALOG_TITLE_TAG).assertIsDisplayed()
        composeRule.onNodeWithTag(BASIC_DIALOG_DESCRIPTION_TAG).assertIsDisplayed()
        composeRule.onNodeWithText(POSITIVE_BUTTON).assertIsDisplayed()
        composeRule.onNodeWithText(NEGATIVE_BUTTON).assertIsDisplayed()
        composeRule.onNodeWithTag(CONTENT_TAG).assertIsDisplayed()
    }

    private fun setContent(content: (@Composable () -> Unit)?) {
        composeRule.setContent {
            BasicDialog(
                title = TITLE,
                description = DESCRIPTION,
                buttons = persistentListOf(
                    BasicDialogButton(text = NEGATIVE_BUTTON, onClick = {}),
                    BasicDialogButton(text = POSITIVE_BUTTON, onClick = {}),
                ),
                onDismissRequest = {},
                content = content,
            )
        }
    }

    private companion object {
        const val TITLE = "Basic dialog title"
        const val DESCRIPTION = "Basic dialog description"
        const val POSITIVE_BUTTON = "Confirm"
        const val NEGATIVE_BUTTON = "Cancel"
        const val CONTENT = "Apply to all"
        const val CONTENT_TAG = "basic_dialog_content_test:content"
    }
}
