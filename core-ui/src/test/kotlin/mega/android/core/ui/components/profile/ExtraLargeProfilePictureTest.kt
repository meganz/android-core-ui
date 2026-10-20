package mega.android.core.ui.components.profile

import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.test.assertHeightIsEqualTo
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertWidthIsEqualTo
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.unit.dp
import androidx.test.ext.junit.runners.AndroidJUnit4
import mega.android.core.ui.theme.AndroidThemeForPreviews
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ExtraLargeProfilePictureTest {

    @get:Rule
    val composeRule = createComposeRule()

    @Test
    fun `test that extra large profile picture is 88 dp`() {
        setContent(name = "Mega")

        composeRule.onNodeWithTag(TAG)
            .assertWidthIsEqualTo(88.dp)
            .assertHeightIsEqualTo(88.dp)
    }

    @Test
    fun `test that extra large profile picture shows the upper case initial when there is no image`() {
        setContent(name = "mega")

        composeRule.onNodeWithText("M").assertIsDisplayed()
    }

    private fun setContent(name: String?) {
        composeRule.setContent {
            AndroidThemeForPreviews {
                ExtraLargeProfilePicture(
                    imageFile = null,
                    name = name,
                    contentDescription = null,
                    modifier = Modifier.testTag(TAG),
                )
            }
        }
    }

    private companion object {
        const val TAG = "extra_large_profile_picture"
    }
}
