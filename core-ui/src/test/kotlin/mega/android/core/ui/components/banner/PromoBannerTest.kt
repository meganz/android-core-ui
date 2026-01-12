package mega.android.core.ui.components.banner

import androidx.compose.ui.Modifier
import androidx.compose.ui.test.assert
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotDisplayed
import androidx.compose.ui.test.hasClickAction
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.verifyNoInteractions

@RunWith(AndroidJUnit4::class)
class PromoBannerTest {

    @get:Rule
    val composeRule = createComposeRule()

    @Test
    fun `test that title is displayed`() {
        val title = "Test Banner Title"
        initComposeRuleContent(
            title = title,
        )
        composeRule.onNodeWithText(title)
            .assertIsDisplayed()
    }

    @Test
    fun `test that button text is displayed`() {
        val buttonText = "Learn More"
        initComposeRuleContent(
            buttonText = buttonText,
        )
        composeRule.onNodeWithText(buttonText)
            .assertIsDisplayed()
    }

    @Test
    fun `test that dismiss button is displayed when showDismissButton is true`() {
        initComposeRuleContent(
            showDismissButton = true,
        )
        composeRule.onNodeWithContentDescription("Dismiss")
            .assertIsDisplayed()
    }

    @Test
    fun `test that dismiss button is not displayed when showDismissButton is false`() {
        initComposeRuleContent(
            showDismissButton = false,
        )
        composeRule.onNodeWithContentDescription("Dismiss")
            .assertIsNotDisplayed()
    }

    @Test
    fun `test that dismiss button is clickable`() {
        initComposeRuleContent(
            showDismissButton = true,
        )
        composeRule.onNodeWithContentDescription("Dismiss")
            .assert(hasClickAction())
    }

    @Test
    fun `test that onDismissClick is invoked when dismiss button is clicked`() {
        val onDismissClick = mock<() -> Unit>()
        initComposeRuleContent(
            showDismissButton = true,
            onDismissClick = onDismissClick,
        )
        composeRule.onNodeWithContentDescription("Dismiss")
            .performClick()
        verify(onDismissClick).invoke()
    }

    @Test
    fun `test that onClick is invoked when dismiss button is clicked`() {
        val onClick = mock<() -> Unit>()
        initComposeRuleContent(
            buttonText = "Learn More",
            onClick = onClick,
        )
        composeRule.onNodeWithText("Learn More")
            .performClick()
        verify(onClick).invoke()
    }

    @Test
    fun `test that onDismissClick is not invoked when dismiss button is not shown`() {
        val onDismissClick = mock<() -> Unit>()
        initComposeRuleContent(
            showDismissButton = false,
            onDismissClick = onDismissClick,
        )
        // Try to find dismiss button - should not exist
        composeRule.onNodeWithContentDescription("Dismiss")
            .assertDoesNotExist()
        verifyNoInteractions(onDismissClick)
    }

    private fun initComposeRuleContent(
        title: String = "Default Title",
        buttonText: String = "Default description",
        showDismissButton: Boolean = true,
        onDismissClick: () -> Unit = {},
        onClick: () -> Unit = {}

    ) {
        composeRule.setContent {
            PromoBanner(
                modifier = Modifier,
                backgroundImageUrl = null,
                imageUrl = null,
                title = title,
                buttonText = buttonText,
                showDismissButton = showDismissButton,
                onDismissClick = onDismissClick,
                onClick = onClick
            )
        }
    }
}
