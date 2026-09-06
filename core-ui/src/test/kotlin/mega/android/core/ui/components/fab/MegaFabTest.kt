package mega.android.core.ui.components.fab

import androidx.compose.ui.res.painterResource
import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.test.ext.junit.runners.AndroidJUnit4
import mega.android.core.ui.R
import mega.android.core.ui.theme.AndroidThemeForPreviews
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MegaFabTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun `test that fab exposes its content description to accessibility services`() {
        val expected = "Create new folder"

        composeTestRule.setContent {
            AndroidThemeForPreviews {
                MegaFab(
                    onClick = {},
                    painter = painterResource(R.drawable.ic_arrow_left_medium_thin_outline),
                    contentDescription = expected
                )
            }
        }

        composeTestRule.onNodeWithContentDescription(expected)
            .assertIsDisplayed()
            .assertHasClickAction()
    }
}
