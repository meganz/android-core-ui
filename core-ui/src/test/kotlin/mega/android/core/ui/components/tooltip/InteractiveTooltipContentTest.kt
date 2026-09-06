package mega.android.core.ui.components.tooltip

import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.assertHeightIsAtLeast
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertWidthIsAtLeast
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.unit.dp
import androidx.test.ext.junit.runners.AndroidJUnit4
import mega.android.core.ui.components.tooltip.popup.interactive.InteractiveTooltipButtonProperties
import mega.android.core.ui.components.tooltip.popup.interactive.InteractiveTooltipContent
import mega.android.core.ui.theme.AndroidThemeForPreviews
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class InteractiveTooltipContentTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun `test that close icon exposes a content description and a 48dp touch target`() {
        val expected = "Dismiss"

        composeTestRule.setContent {
            AndroidThemeForPreviews {
                InteractiveTooltipContent(
                    title = "Title",
                    needCloseIcon = true,
                    onCloseClick = {},
                    closeIconDescription = expected
                )
            }
        }

        composeTestRule.onNodeWithContentDescription(expected)
            .assertIsDisplayed()
            .assertHasClickAction()
            .assertWidthIsAtLeast(48.dp)
            .assertHeightIsAtLeast(48.dp)
    }

    @Test
    fun `test that primary and secondary buttons have a minimum 48dp touch target`() {
        composeTestRule.setContent {
            AndroidThemeForPreviews {
                InteractiveTooltipContent(
                    title = "Title",
                    primaryButton = InteractiveTooltipButtonProperties(
                        text = "Primary",
                        onClick = {}
                    ),
                    secondaryButton = InteractiveTooltipButtonProperties(
                        text = "Secondary",
                        onClick = {}
                    )
                )
            }
        }

        composeTestRule.onNodeWithText("Primary")
            .assertHasClickAction()
            .assertHeightIsAtLeast(48.dp)
        composeTestRule.onNodeWithText("Secondary")
            .assertHasClickAction()
            .assertHeightIsAtLeast(48.dp)
    }
}
