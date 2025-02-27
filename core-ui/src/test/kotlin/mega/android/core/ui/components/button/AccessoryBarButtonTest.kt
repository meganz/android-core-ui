package mega.android.core.ui.components.button

import androidx.compose.ui.res.painterResource
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.test.ext.junit.runners.AndroidJUnit4
import mega.android.core.ui.R
import mega.android.core.ui.model.AccessoryBarButtonContent
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class AccessoryBarButtonTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun `test that AccessoryBarButton is displayed correctly for single item`() {
        with(composeTestRule) {
            setContent {
                AccessoryBarButton(
                    button = AccessoryBarButtonContent(
                        text = "Button 1",
                        onClick = {}
                    )
                )
            }
            onNodeWithText("Button 1").assertExists()
            onNodeWithText("Button 2").assertDoesNotExist()
            onNodeWithTag(ACCESSORY_BAR_BUTTON_ICON_TEST_TAG).assertDoesNotExist()
            onNodeWithTag(ACCESSORY_BAR_BUTTON_TEST_TAG).assertExists()
            onNodeWithTag(ACCESSORY_BAR_BUTTON_VERTICAL_DIVIDER_TEST_TAG).assertDoesNotExist()
        }
    }

    @Test
    fun `test that AccessoryBarButton is displayed correctly for multiple items`() {
        with(composeTestRule) {
            setContent {
                AccessoryBarButtonGroup(
                    firstButton = AccessoryBarButtonContent(
                        icon = painterResource(id = R.drawable.ic_eye),
                        text = "Button 1",
                        onClick = {}
                    ),
                    secondButton = AccessoryBarButtonContent(
                        icon = painterResource(id = R.drawable.ic_eye),
                        text = "Button 2",
                        onClick = {}
                    )
                )
            }
            onNodeWithText("Button 1").assertExists()
            onNodeWithText("Button 2").assertExists()
            onNodeWithTag(ACCESSORY_BAR_BUTTON_VERTICAL_DIVIDER_TEST_TAG).assertExists()
        }
    }
}