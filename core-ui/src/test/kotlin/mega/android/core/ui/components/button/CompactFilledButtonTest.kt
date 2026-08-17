package mega.android.core.ui.components.button

import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.test.assertHeightIsEqualTo
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.unit.dp
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * The compact sizes exist to fit banners that only have 90-112dp to spare, so the height they
 * occupy in a layout — not just the height they paint — has to match the nominal size. Material's
 * [androidx.compose.material3.Surface] otherwise reserves a 48dp touch target as layout space.
 */
@RunWith(AndroidJUnit4::class)
class CompactFilledButtonTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun `test that PrimaryFilledButtonXSmall occupies 30dp of layout height`() {
        with(composeTestRule) {
            setContent {
                PrimaryFilledButtonXSmall(
                    modifier = Modifier.testTag(TEST_TAG),
                    text = "Grab deal",
                    onClick = {},
                )
            }
            onNodeWithTag(TEST_TAG).assertHeightIsEqualTo(30.dp)
        }
    }

    @Test
    fun `test that PrimaryFilledButtonXXSmall occupies 24dp of layout height`() {
        with(composeTestRule) {
            setContent {
                PrimaryFilledButtonXXSmall(
                    modifier = Modifier.testTag(TEST_TAG),
                    text = "Grab deal",
                    onClick = {},
                )
            }
            onNodeWithTag(TEST_TAG).assertHeightIsEqualTo(24.dp)
        }
    }

    @Test
    fun `test that PrimaryFilledButtonM3XSmall occupies 32dp of layout height`() {
        with(composeTestRule) {
            setContent {
                PrimaryFilledButtonM3XSmall(
                    modifier = Modifier.testTag(TEST_TAG),
                    text = "Grab deal",
                    onClick = {},
                )
            }
            onNodeWithTag(TEST_TAG).assertHeightIsEqualTo(32.dp)
        }
    }
}

private const val TEST_TAG = "compact_filled_button"
