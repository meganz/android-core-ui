package mega.android.core.ui.components.dialogs

import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import mega.android.core.ui.theme.AndroidThemeForPreviews
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.verifyNoInteractions

@RunWith(AndroidJUnit4::class)
class DialogButtonTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun `test that the button is reported as enabled when it is enabled`() {
        setContent(enabled = true)

        composeTestRule.onNodeWithTag(TEST_TAG_BUTTON).assertIsEnabled()
    }

    @Test
    fun `test that the button is reported as disabled when it is not enabled`() {
        setContent(enabled = false)

        composeTestRule.onNodeWithTag(TEST_TAG_BUTTON).assertIsNotEnabled()
    }

    @Test
    fun `test that clicking the button notifies the listener when it is enabled`() {
        val onButtonClicked = mock<() -> Unit>()
        setContent(enabled = true, onButtonClicked = onButtonClicked)

        composeTestRule.onNodeWithTag(TEST_TAG_BUTTON).performClick()

        verify(onButtonClicked).invoke()
    }

    @Test
    fun `test that clicking the button does not notify the listener when it is not enabled`() {
        val onButtonClicked = mock<() -> Unit>()
        setContent(enabled = false, onButtonClicked = onButtonClicked)

        composeTestRule.onNodeWithTag(TEST_TAG_BUTTON).performClick()

        verifyNoInteractions(onButtonClicked)
    }

    private fun setContent(enabled: Boolean, onButtonClicked: () -> Unit = {}) {
        composeTestRule.setContent {
            AndroidThemeForPreviews {
                DialogButton(
                    modifier = Modifier.testTag(TEST_TAG_BUTTON),
                    buttonText = "Confirm",
                    onButtonClicked = onButtonClicked,
                    enabled = enabled,
                )
            }
        }
    }

    private companion object {
        const val TEST_TAG_BUTTON = "dialog_button_test:button"
    }
}
