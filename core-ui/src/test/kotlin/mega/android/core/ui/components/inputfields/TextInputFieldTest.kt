package mega.android.core.ui.components.inputfields

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.hasSetTextAction
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.compose.ui.test.performTextInputSelection
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.test.ext.junit.runners.AndroidJUnit4
import mega.android.core.ui.theme.AndroidTheme
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class TextInputFieldTest {

    @get:Rule
    val composeRule = createComposeRule()

    @Test
    fun `test that input up to the character limit is accepted`() {
        var lastValue: String? = null
        composeRule.setContent {
            AndroidTheme(isDark = false) {
                TextInputField(
                    modifier = Modifier,
                    keyboardType = KeyboardType.Text,
                    maxCharLimit = 5,
                    onValueChanged = { lastValue = it },
                )
            }
        }

        composeRule.onNode(hasSetTextAction()).performTextInput("abcde")

        assertEquals("abcde", lastValue)
    }

    @Test
    fun `test that pasting text longer than the limit keeps only the portion that fits`() {
        var lastValue: String? = null
        composeRule.setContent {
            AndroidTheme(isDark = false) {
                var text by remember { mutableStateOf("") }
                TextInputField(
                    modifier = Modifier,
                    keyboardType = KeyboardType.Text,
                    text = text,
                    maxCharLimit = 5,
                    onValueChanged = {
                        text = it
                        lastValue = it
                    },
                )
            }
        }

        composeRule.onNode(hasSetTextAction()).performTextInput("abcdefgh")

        assertEquals("abcde", lastValue)
    }

    @Test
    fun `test that input exceeding the character limit is rejected and previous value is kept`() {
        var rejectedValue: String? = null
        composeRule.setContent {
            AndroidTheme(isDark = false) {
                var text by remember { mutableStateOf("abcde") }
                TextInputField(
                    modifier = Modifier,
                    keyboardType = KeyboardType.Text,
                    text = text,
                    maxCharLimit = 5,
                    onValueChanged = {
                        text = it
                        rejectedValue = it
                    },
                )
            }
        }

        composeRule.onNode(hasSetTextAction()).performTextInput("f")

        // The overflowing keystroke is rejected: no callback, value stays at the limit.
        assertNull(rejectedValue)
        composeRule.onNode(hasSetTextAction()).assertTextEquals("abcde")
    }

    @Test
    fun `test that the TextFieldValue overload rejects input exceeding the character limit`() {
        var invocations = 0
        composeRule.setContent {
            AndroidTheme(isDark = false) {
                var value by remember { mutableStateOf(TextFieldValue("abcde")) }
                TextInputField(
                    modifier = Modifier,
                    keyboardType = KeyboardType.Text,
                    textFieldValue = value,
                    maxCharLimit = 5,
                    onValueChanged = {
                        value = it
                        invocations++
                    },
                )
            }
        }

        composeRule.onNode(hasSetTextAction()).performTextInput("f")

        assertEquals(0, invocations)
        composeRule.onNode(hasSetTextAction()).assertTextEquals("abcde")
    }

    @Test
    fun `test that moving the cursor in a full field is propagated on the TextFieldValue overload`() {
        var latest: TextFieldValue? = null
        composeRule.setContent {
            AndroidTheme(isDark = false) {
                var value by remember {
                    mutableStateOf(TextFieldValue("abcde", TextRange(5)))
                }
                TextInputField(
                    modifier = Modifier,
                    keyboardType = KeyboardType.Text,
                    textFieldValue = value,
                    maxCharLimit = 5,
                    onValueChanged = {
                        value = it
                        latest = it
                    },
                )
            }
        }

        // Field is already at the limit; move the caret into the middle without changing text.
        composeRule.onNode(hasSetTextAction()).performTextInputSelection(TextRange(2))

        // The selection-only change must round-trip to the caller; previously it was swallowed,
        // which made the caret snap back to the end and blocked mid-string editing.
        assertEquals("abcde", latest?.text)
        assertEquals(TextRange(2), latest?.selection)
    }

    @Test
    fun `test that editing in the middle of a full field replaces text at the caret on the TextFieldValue overload`() {
        var latest: TextFieldValue? = null
        composeRule.setContent {
            AndroidTheme(isDark = false) {
                var value by remember {
                    mutableStateOf(TextFieldValue("abcde", TextRange(5)))
                }
                TextInputField(
                    modifier = Modifier,
                    keyboardType = KeyboardType.Text,
                    textFieldValue = value,
                    maxCharLimit = 5,
                    onValueChanged = {
                        value = it
                        latest = it
                    },
                )
            }
        }

        val node = composeRule.onNode(hasSetTextAction())
        // Select the middle character of the full field, then type over it.
        node.performTextInputSelection(TextRange(2, 3))
        node.performTextInput("X")

        // The replacement lands in the middle (kept at the limit), not appended at the end.
        assertEquals("abXde", latest?.text)
    }

    @Test
    fun `test that the password field shows the placeholder while empty`() {
        composeRule.setContent {
            AndroidTheme(isDark = false) {
                PasswordTextInputField(
                    modifier = Modifier,
                    label = null,
                    placeholder = "Enter password",
                )
            }
        }

        composeRule.onNodeWithTag(BASE_TEXT_FIELD_OUTLINED_TEXT_FIELD_PLACEHOLDER_TAG, useUnmergedTree = true)
            .assertExists()
    }

    @Test
    fun `test that the password field shows the visibility toggle when it has text`() {
        composeRule.setContent {
            AndroidTheme(isDark = false) {
                PasswordTextInputField(
                    modifier = Modifier,
                    label = null,
                    text = "secret",
                )
            }
        }

        composeRule.onNodeWithTag(BASE_TEXT_FIELD_PASSWORD_VISIBILITY_ICON_TAG)
            .assertIsDisplayed()
    }

    @Test
    fun `test that the clear icon appears once the password field is focused and clears the text`() {
        var latest: String? = null
        composeRule.setContent {
            AndroidTheme(isDark = false) {
                var text by remember { mutableStateOf("secret") }
                PasswordTextInputField(
                    modifier = Modifier,
                    label = null,
                    text = text,
                    showClearIcon = true,
                    onValueChanged = {
                        text = it
                        latest = it
                    },
                )
            }
        }

        composeRule.onNode(hasSetTextAction()).performClick()
        composeRule.onNodeWithTag(BASE_TEXT_FIELD_CLEAR_TEXT_ICON_TAG).performClick()

        assertEquals("", latest)
    }

    @Test
    fun `test that the clear icon is hidden when showClearIcon is false`() {
        composeRule.setContent {
            AndroidTheme(isDark = false) {
                PasswordTextInputField(
                    modifier = Modifier,
                    label = null,
                    text = "secret",
                    showClearIcon = false,
                )
            }
        }

        composeRule.onNode(hasSetTextAction()).performClick()

        composeRule.onNodeWithTag(BASE_TEXT_FIELD_CLEAR_TEXT_ICON_TAG).assertDoesNotExist()
        composeRule.onNodeWithTag(BASE_TEXT_FIELD_PASSWORD_VISIBILITY_ICON_TAG).assertIsDisplayed()
    }
}
