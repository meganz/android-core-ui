import androidx.compose.ui.test.assert
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.test.ext.junit.runners.AndroidJUnit4
import mega.android.core.ui.R
import mega.android.core.ui.components.card.PlanFeature
import mega.android.core.ui.components.card.PlansCard
import mega.android.core.ui.components.card.PlansCardTestTags
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class PlansCardViewTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun `test that title should be displayed`() {
        val expected = "Title"

        composeTestRule.setContent {
            PlansCard(title = expected)
        }

        composeTestRule.onNodeWithTag(PlansCardTestTags.TITLE)
            .assertIsDisplayed()
            .assert(hasText(expected))
    }

    @Test
    fun `test that subtitle should be displayed when not null`() {
        val expected = "Subtitle"

        composeTestRule.setContent {
            PlansCard(title = "Title", subtitle = expected)
        }
        composeTestRule.onNodeWithTag(PlansCardTestTags.SUBTITLE)
            .assertIsDisplayed()
            .assert(hasText(expected))
    }

    @Test
    fun `test that subtitle should not be displayed when null`() {
        composeTestRule.setContent {
            PlansCard(title = "Title", subtitle = null)
        }
        composeTestRule.onNodeWithTag(PlansCardTestTags.SUBTITLE)
            .assertDoesNotExist()
    }

    @Test
    fun `test that price should be displayed when not null`() {
        val expected = "Price"

        composeTestRule.setContent {
            PlansCard(title = "Title", price = expected)
        }
        composeTestRule.onNodeWithTag(PlansCardTestTags.PRICING)
            .assertIsDisplayed()
            .assert(hasText(expected))
    }

    @Test
    fun `test that price should not be displayed when null`() {
        composeTestRule.setContent {
            PlansCard(title = "Title", price = null)
        }
        composeTestRule.onNodeWithTag(PlansCardTestTags.PRICING)
            .assertDoesNotExist()
    }

    @Test
    fun `test that features should be displayed when not null`() {
        val expected = listOf("Feature 1", "Feature 2")
        val expectedSectionTitle = "Features"

        composeTestRule.setContent {
            PlansCard(
                title = "Title",
                featuresSectionTitle = expectedSectionTitle,
                features = expected.map { PlanFeature(R.drawable.ic_close_medium_thin_outline, it) }
            )
        }

        composeTestRule.onNodeWithTag(PlansCardTestTags.FEATURES_SECTION_TITLE)
            .assertIsDisplayed()
            .assert(hasText(expectedSectionTitle))
        composeTestRule.onNodeWithTag(PlansCardTestTags.FEATURES)
            .assertIsDisplayed()
        expected.forEachIndexed { index, value ->
            composeTestRule.onNodeWithTag("${PlansCardTestTags.FEATURE_ITEM}_$index")
                .assertIsDisplayed()
                .assert(hasText(value))
        }
    }

    @Test
    fun `test that features should not be displayed when empty`() {
        composeTestRule.setContent {
            PlansCard(title = "Title", features = emptyList())
        }
        composeTestRule.onNodeWithTag(PlansCardTestTags.FEATURES)
            .assertDoesNotExist()
        composeTestRule.onNodeWithTag(PlansCardTestTags.FEATURES_SECTION_TITLE)
            .assertDoesNotExist()
    }

    @Test
    fun `test that link text should be displayed when not null`() {
        val expected = "Link Text"

        composeTestRule.setContent {
            PlansCard(title = "Title", linkText = expected)
        }
        composeTestRule.onNodeWithTag(PlansCardTestTags.LINK)
            .assertIsDisplayed()
            .assert(hasText(expected))
    }

    @Test
    fun `test that link text should not be displayed when null`() {
        composeTestRule.setContent {
            PlansCard(title = "Title", linkText = null)
        }
        composeTestRule.onNodeWithTag(PlansCardTestTags.LINK)
            .assertDoesNotExist()
    }

    @Test
    fun `test that primary button should be displayed when not null`() {
        val expected = "Primary Button Text"

        composeTestRule.setContent {
            PlansCard(title = "Title", primaryButtonText = expected)
        }
        composeTestRule.onNodeWithTag(PlansCardTestTags.PRIMARY_BUTTON)
            .assertIsDisplayed()
            .assert(hasText(expected))
    }

    @Test
    fun `test that primary button should not be displayed when null`() {
        composeTestRule.setContent {
            PlansCard(title = "Title", primaryButtonText = null)
        }
        composeTestRule.onNodeWithTag(PlansCardTestTags.PRIMARY_BUTTON)
            .assertDoesNotExist()
    }

    @Test
    fun `test that secondary button should be displayed when not null`() {
        val expected = "Secondary Button Text"

        composeTestRule.setContent {
            PlansCard(title = "Title", secondaryButtonText = expected)
        }
        composeTestRule.onNodeWithTag(PlansCardTestTags.SECONDARY_BUTTON)
            .assertIsDisplayed()
            .assert(hasText(expected))
    }

    @Test
    fun `test that secondary button should not be displayed when null`() {
        composeTestRule.setContent {
            PlansCard(title = "Title", secondaryButtonText = null)
        }
        composeTestRule.onNodeWithTag(PlansCardTestTags.SECONDARY_BUTTON)
            .assertDoesNotExist()
    }

    @Test
    fun `test that footer text should be displayed when not null`() {
        val expected = "Footer Text"

        composeTestRule.setContent {
            PlansCard(title = "Title", footerText = expected)
        }
        composeTestRule.onNodeWithTag(PlansCardTestTags.FOOTER)
            .assertIsDisplayed()
            .assert(hasText(expected))
    }

    @Test
    fun `test that footer text should not be displayed when null`() {
        composeTestRule.setContent {
            PlansCard(title = "Title", footerText = null)
        }
        composeTestRule.onNodeWithTag(PlansCardTestTags.FOOTER)
            .assertDoesNotExist()
    }
}