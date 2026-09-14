package mega.android.core.ui.components.dropdown

import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.SemanticsProperties
import androidx.compose.ui.test.SemanticsMatcher
import androidx.compose.ui.test.assert
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
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
class MegaDropDownSelectorTest {

    @get:Rule
    val composeRule = createComposeRule()

    @Test
    fun `test that value is displayed`() {
        val value = "MB"
        composeRule.setContent {
            AndroidThemeForPreviews {
                MegaDropDownSelector(
                    value = value,
                    onClick = {}
                )
            }
        }

        composeRule.onNodeWithTag(DROP_DOWN_SELECTOR_VALUE_TAG, useUnmergedTree = true)
            .assertIsDisplayed()
        composeRule.onNodeWithText(value)
            .assertIsDisplayed()
    }


    @Test
    fun `test that placeholder is displayed when value is empty`() {
        val placeholder = "Select unit"
        composeRule.setContent {
            AndroidThemeForPreviews {
                MegaDropDownSelector(
                    value = "",
                    placeholder = placeholder,
                    onClick = {}
                )
            }
        }

        composeRule.onNodeWithTag(DROP_DOWN_SELECTOR_PLACEHOLDER_TAG, useUnmergedTree = true)
            .assertIsDisplayed()
        composeRule.onNodeWithText(placeholder)
            .assertIsDisplayed()
        composeRule.onNodeWithTag(DROP_DOWN_SELECTOR_VALUE_TAG, useUnmergedTree = true)
            .assertDoesNotExist()
    }


    @Test
    fun `test that label and optional label are displayed`() {
        val label = "Unit"
        val optionalLabel = "Optional"
        composeRule.setContent {
            AndroidThemeForPreviews {
                MegaDropDownSelector(
                    value = "GB",
                    onClick = {},
                    label = label,
                    optionalLabelText = optionalLabel
                )
            }
        }

        composeRule.onNodeWithTag(DROP_DOWN_SELECTOR_LABEL_TAG)
            .assertIsDisplayed()
        composeRule.onNodeWithText(label)
            .assertIsDisplayed()
        composeRule.onNodeWithTag(DROP_DOWN_SELECTOR_OPTIONAL_LABEL_TAG)
            .assertIsDisplayed()
        composeRule.onNodeWithText(optionalLabel)
            .assertIsDisplayed()
    }


    @Test
    fun `test that onClick callback is triggered when clicked`() {
        val onClick: () -> Unit = mock()
        composeRule.setContent {
            AndroidThemeForPreviews {
                MegaDropDownSelector(
                    value = "KB",
                    onClick = onClick
                )
            }
        }

        composeRule.onNodeWithTag(DROP_DOWN_SELECTOR_BOX_TAG)
            .performClick()

        verify(onClick).invoke()
    }


    @Test
    fun `test that onClick callback is not triggered when disabled`() {
        val onClick: () -> Unit = mock()
        composeRule.setContent {
            AndroidThemeForPreviews {
                MegaDropDownSelector(
                    value = "KB",
                    onClick = onClick,
                    enabled = false
                )
            }
        }

        composeRule.onNodeWithTag(DROP_DOWN_SELECTOR_BOX_TAG)
            .performClick()

        verifyNoInteractions(onClick)
    }


    @Test
    fun `test that errorText is displayed`() {
        val errorText = "Invalid unit"
        composeRule.setContent {
            AndroidThemeForPreviews {
                MegaDropDownSelector(
                    value = "KB",
                    onClick = {},
                    errorText = errorText
                )
            }
        }

        composeRule.onNodeWithTag(DROP_DOWN_SELECTOR_ERROR_HELP_TEXT_TAG)
            .assertIsDisplayed()
        composeRule.onNodeWithText(errorText)
            .assertIsDisplayed()
    }


    @Test
    fun `test that trailing icon is displayed by default`() {
        composeRule.setContent {
            AndroidThemeForPreviews {
                MegaDropDownSelector(
                    value = "MB",
                    onClick = {}
                )
            }
        }

        composeRule.onNodeWithTag(DROP_DOWN_SELECTOR_TRAILING_ICON_TAG, useUnmergedTree = true)
            .assertIsDisplayed()
    }


    @Test
    fun `test that custom trailing icon is displayed when provided`() {
        val customTag = "custom_trailing_icon"
        composeRule.setContent {
            AndroidThemeForPreviews {
                MegaDropDownSelector(
                    value = "MB",
                    onClick = {},
                    trailingIcon = {
                        Text(
                            modifier = Modifier.testTag(customTag),
                            text = "Custom"
                        )
                    }
                )
            }
        }

        composeRule.onNodeWithTag(customTag, useUnmergedTree = true)
            .assertIsDisplayed()
        composeRule.onNodeWithTag(DROP_DOWN_SELECTOR_TRAILING_ICON_TAG, useUnmergedTree = true)
            .assertDoesNotExist()
    }


    @Test
    fun `test that error semantics have no description when isError is true and errorText is null`() {
        composeRule.setContent {
            AndroidThemeForPreviews {
                MegaDropDownSelector(
                    value = "KB",
                    onClick = {},
                    isError = true
                )
            }
        }

        composeRule.onNodeWithTag(DROP_DOWN_SELECTOR_BOX_TAG)
            .assert(SemanticsMatcher.expectValue(SemanticsProperties.Error, ""))
    }


    @Test
    fun `test that error semantics use errorText when errorText is provided`() {
        val errorText = "Invalid unit"
        composeRule.setContent {
            AndroidThemeForPreviews {
                MegaDropDownSelector(
                    value = "KB",
                    onClick = {},
                    errorText = errorText
                )
            }
        }

        composeRule.onNodeWithTag(DROP_DOWN_SELECTOR_BOX_TAG)
            .assert(SemanticsMatcher.expectValue(SemanticsProperties.Error, errorText))
    }
}
