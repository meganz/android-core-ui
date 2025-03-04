package mega.android.core.ui.components.inputfields

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import mega.android.core.ui.model.MegaSpanStyle
import mega.android.core.ui.model.SpanIndicator
import mega.android.core.ui.model.SpanStyleWithAnnotation
import mega.android.core.ui.theme.AppTheme
import mega.android.core.ui.theme.values.LinkColor
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.mock
import org.mockito.kotlin.any
import org.mockito.kotlin.verify

@RunWith(AndroidJUnit4::class)
class HelpTextTest {

    @get:Rule
    val composeRule = createComposeRule()

    @Test
    fun `test that help text success displays icon and text as expected`() {
        // Given
        val text = "Success text"
        // When
        composeRule.setContent {
            HelpTextSuccess(text = text)
        }
        // Then
        composeRule.onNodeWithText(text)
            .assertIsDisplayed()
        composeRule.onNodeWithContentDescription("Success text")
            .assertIsDisplayed()
    }

    @Test
    fun `test that help text error displays text as expected`() {
        // Given
        val text = "Error text"
        // When
        composeRule.setContent {
            HelpTextError(text = text)
        }
        // Then
        composeRule.onNodeWithText(text)
            .assertIsDisplayed()
        composeRule.onNodeWithContentDescription("Error text")
            .assertIsDisplayed()
    }

    @Test
    fun `test that help text warning displays text as expected`() {
        // Given
        val text = "Warning text"
        // When
        composeRule.setContent {
            HelpTextWarning(text = text)
        }
        // Then
        composeRule.onNodeWithText(text)
            .assertIsDisplayed()
        composeRule.onNodeWithContentDescription("Warning text")
            .assertIsDisplayed()
    }

    @Test
    fun `test that help text info displays text as expected`() {
        // Given
        val text = "Info text"
        // When
        composeRule.setContent {
            HelpTextInfo(text = text)
        }
        // Then
        composeRule.onNodeWithText(text)
            .assertIsDisplayed()
        composeRule.onNodeWithContentDescription("Info text")
            .assertIsDisplayed()
    }

    @Test
    fun `test that help text link displays text as expected`() {
        // Given
        val text = "[A]Click here to learn more[/A]"
        val onClick = mock<(String) -> Unit>()
        // When
        composeRule.setContent {
            HelpTextLink(
                text = text,
                textStyle = AppTheme.typography.bodyLarge,
                spanStyles = hashMapOf(
                    SpanIndicator('A') to SpanStyleWithAnnotation(
                        megaSpanStyle = MegaSpanStyle.LinkColorStyle(
                            spanStyle = AppTheme.typography.bodyLarge.toSpanStyle(),
                            linkColor = LinkColor.Primary,
                        ),
                        annotation = text
                            .substringAfter("[A]")
                            .substringBefore("[/A]")
                    )
                ),
                onAnnotationClick = onClick
            )
        }
        // Then
        composeRule.onNodeWithText("Click here to learn more")
            .assertIsDisplayed()
        composeRule.onNodeWithTag(HELP_TEXT_TEXT_TEST_TAG)
            .performClick()
        verify(onClick).invoke(any())
    }


}