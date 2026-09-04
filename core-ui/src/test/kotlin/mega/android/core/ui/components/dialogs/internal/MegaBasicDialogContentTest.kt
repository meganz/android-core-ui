package mega.android.core.ui.components.dialogs.internal

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.runtime.Composable
import androidx.compose.ui.test.assertHeightIsAtLeast
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.getUnclippedBoundsInRoot
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performScrollTo
import androidx.compose.ui.unit.dp
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config

@RunWith(AndroidJUnit4::class)
@Config(qualifiers = "w480dp-h800dp")
class MegaBasicDialogContentTest {

    @get:Rule
    val composeRule = createComposeRule()

    @Test
    fun `test that the buttons keep their height when the body is taller than the available height`() {
        setContent()

        val unconstrained = composeRule.onNodeWithTag(UNCONSTRAINED_BUTTONS_TAG)
            .getUnclippedBoundsInRoot()

        composeRule.onNodeWithTag(BUTTONS_TAG)
            .assertIsDisplayed()
            .assertHeightIsAtLeast(unconstrained.bottom - unconstrained.top)
    }

    @Test
    fun `test that the description is displayed when the body is taller than the available height`() {
        setContent()

        composeRule.onNodeWithTag(DESCRIPTION_TAG).assertIsDisplayed()
    }

    @Test
    fun `test that the content can be scrolled into view when the body is taller than the available height`() {
        setContent()

        composeRule.onNodeWithTag(CONTENT_TAG).performScrollTo().assertIsDisplayed()
    }

    /**
     * Renders the dialog into less height than its title, body and buttons need together,
     * alongside an unconstrained copy of the buttons to compare against.
     */
    private fun setContent() {
        composeRule.setContent {
            Column {
                Box(modifier = Modifier.height(AVAILABLE_HEIGHT)) {
                    MegaBasicDialogContent(
                        buttons = { Buttons(BUTTONS_TAG) },
                        title = { Text(text = TITLE) },
                        text = {
                            Text(text = BODY, modifier = Modifier.testTag(DESCRIPTION_TAG))
                        },
                        content = {
                            Text(
                                text = BODY.repeat(3),
                                modifier = Modifier.testTag(CONTENT_TAG),
                            )
                        },
                    )
                }
                Buttons(UNCONSTRAINED_BUTTONS_TAG)
            }
        }
    }

    /**
     * Single-word labels so the copy inside the dialog and the unconstrained one below it wrap
     * identically despite their different widths.
     */
    @Composable
    private fun Buttons(tag: String) {
        Column(modifier = Modifier.testTag(tag)) {
            Text(text = "Move")
            Text(text = "Delete")
            Text(text = "Cancel")
        }
    }

    private companion object {
        val AVAILABLE_HEIGHT = 200.dp
        const val TITLE = "Stop backup"
        const val BODY =
            "Move this folder to your Cloud drive to get full access to your data. " +
                    "It will no longer be backed up to. Or delete the folder from MEGA. " +
                    "It won't be deleted from your device. This action can't be undone."
        const val DESCRIPTION_TAG = "mega_basic_dialog_content_test:description"
        const val CONTENT_TAG = "mega_basic_dialog_content_test:content"
        const val BUTTONS_TAG = "mega_basic_dialog_content_test:buttons"
        const val UNCONSTRAINED_BUTTONS_TAG =
            "mega_basic_dialog_content_test:unconstrained_buttons"
    }
}
