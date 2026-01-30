package mega.android.core.ui.components.banner

import androidx.compose.ui.Modifier
import androidx.compose.ui.test.assert
import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotDisplayed
import androidx.compose.ui.test.hasClickAction
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
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
class HomeBannerTest {

    @get:Rule
    val composeRule = createComposeRule()

    @Test
    fun `test that banner is displayed`() {
        initComposeRuleContent()
        composeRule.onNodeWithTag(HOME_BANNER_TEST_TAG)
            .assertIsDisplayed()
    }

    @Test
    fun `test that title is displayed`() {
        val title = "Test Banner Title"
        initComposeRuleContent(
            title = title,
        )
        composeRule.onNodeWithTag(HOME_BANNER_TITLE_TEST_TAG)
            .assertIsDisplayed()
            .assert(hasText(title))
    }

    @Test
    fun `test that button is displayed`() {
        initComposeRuleContent()
        composeRule.onNodeWithTag(HOME_BANNER_BUTTON_TEST_TAG)
            .assertIsDisplayed()
    }

    @Test
    fun `test that button text is displayed`() {
        val buttonText = "Learn More"
        initComposeRuleContent(
            buttonText = buttonText,
        )
        composeRule.onNodeWithTag(HOME_BANNER_BUTTON_TEST_TAG)
            .assertIsDisplayed()
            .assert(hasText(buttonText))
    }

    @Test
    fun `test that button is clickable`() {
        initComposeRuleContent()
        composeRule.onNodeWithTag(HOME_BANNER_BUTTON_TEST_TAG)
            .assertHasClickAction()
    }

    @Test
    fun `test that onClick is invoked when button is clicked`() {
        val onClick = mock<() -> Unit>()
        initComposeRuleContent(
            onClick = onClick,
        )
        composeRule.onNodeWithTag(HOME_BANNER_BUTTON_TEST_TAG)
            .performClick()
        verify(onClick).invoke()
    }

    @Test
    fun `test that background image is displayed`() {
        initComposeRuleContent()
        composeRule.onNodeWithTag(HOME_BANNER_BACKGROUND_IMAGE_TEST_TAG)
            .assertExists()
    }

    @Test
    fun `test that main image is displayed`() {
        initComposeRuleContent()
        composeRule.onNodeWithTag(HOME_BANNER_MAIN_IMAGE_TEST_TAG)
            .assertExists()
    }

    @Test
    fun `test that dismiss button is displayed when showDismissButton is true`() {
        initComposeRuleContent(
            showDismissButton = true,
        )
        composeRule.onNodeWithTag(HOME_BANNER_DISMISS_BUTTON_TEST_TAG)
            .assertIsDisplayed()
    }

    @Test
    fun `test that dismiss button is not displayed when showDismissButton is false`() {
        initComposeRuleContent(
            showDismissButton = false,
        )
        composeRule.onNodeWithTag(HOME_BANNER_DISMISS_BUTTON_TEST_TAG)
            .assertDoesNotExist()
    }

    @Test
    fun `test that dismiss button is clickable`() {
        initComposeRuleContent(
            showDismissButton = true,
        )
        composeRule.onNodeWithTag(HOME_BANNER_DISMISS_BUTTON_TEST_TAG)
            .assertHasClickAction()
    }

    @Test
    fun `test that onDismissClick is invoked when dismiss button is clicked`() {
        val onDismissClick = mock<() -> Unit>()
        initComposeRuleContent(
            showDismissButton = true,
            onDismissClick = onDismissClick,
        )
        composeRule.onNodeWithTag(HOME_BANNER_DISMISS_BUTTON_TEST_TAG)
            .performClick()
        verify(onDismissClick).invoke()
    }

    @Test
    fun `test that onDismissClick is not invoked when dismiss button is not shown`() {
        val onDismissClick = mock<() -> Unit>()
        initComposeRuleContent(
            showDismissButton = false,
            onDismissClick = onDismissClick,
        )
        composeRule.onNodeWithTag(HOME_BANNER_DISMISS_BUTTON_TEST_TAG)
            .assertDoesNotExist()
        verifyNoInteractions(onDismissClick)
    }

    @Test
    fun `test that banner displays custom title correctly`() {
        val customTitle = "Custom Banner Title with Special Characters !@#"
        initComposeRuleContent(
            title = customTitle,
        )
        composeRule.onNodeWithTag(HOME_BANNER_TITLE_TEST_TAG)
            .assert(hasText(customTitle))
    }

    @Test
    fun `test that banner displays custom button text correctly`() {
        val customButtonText = "Get Started Now"
        initComposeRuleContent(
            buttonText = customButtonText,
        )
        composeRule.onNodeWithTag(HOME_BANNER_BUTTON_TEST_TAG)
            .assert(hasText(customButtonText))
    }

    @Test
    fun `test that all banner components are displayed together`() {
        initComposeRuleContent(
            showDismissButton = true,
        )
        composeRule.onNodeWithTag(HOME_BANNER_TEST_TAG).assertExists()
        composeRule.onNodeWithTag(HOME_BANNER_TITLE_TEST_TAG).assertExists()
        composeRule.onNodeWithTag(HOME_BANNER_BUTTON_TEST_TAG).assertExists()
        composeRule.onNodeWithTag(HOME_BANNER_DISMISS_BUTTON_TEST_TAG).assertExists()
        composeRule.onNodeWithTag(HOME_BANNER_BACKGROUND_IMAGE_TEST_TAG).assertExists()
        composeRule.onNodeWithTag(HOME_BANNER_MAIN_IMAGE_TEST_TAG).assertExists()
    }

    @Test
    fun `test that onClick is not invoked when button is not clicked`() {
        val onClick = mock<() -> Unit>()
        initComposeRuleContent(
            onClick = onClick,
        )
        verifyNoInteractions(onClick)
    }

    @Test
    fun `test that onDismissClick is not invoked when dismiss button is not clicked`() {
        val onDismissClick = mock<() -> Unit>()
        initComposeRuleContent(
            showDismissButton = true,
            onDismissClick = onDismissClick,
        )
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
            HomeBanner(
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
