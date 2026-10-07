package mega.android.core.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTopPositionInRootIsEqualTo
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.unit.dp
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MegaScaffoldTopBannerTest {

    @get:Rule
    val composeRule = createComposeRule()

    @Test
    fun `test that banner is displayed below the top bar when provided`() {
        setContent()

        composeRule.onNodeWithTag(BANNER_TAG)
            .assertIsDisplayed()
            .assertTopPositionInRootIsEqualTo(TOP_BAR_HEIGHT)
    }

    @Test
    fun `test that content is padded below the banner when banner is shown`() {
        setContent()

        composeRule.onNodeWithTag(CONTENT_TAG)
            .assertTopPositionInRootIsEqualTo(TOP_BAR_HEIGHT + BANNER_HEIGHT)
    }

    @Test
    fun `test that banner is not displayed when showTopBanner is false`() {
        setContent(showTopBanner = false)

        composeRule.onNodeWithTag(BANNER_TAG).assertDoesNotExist()
        composeRule.onNodeWithTag(CONTENT_TAG).assertTopPositionInRootIsEqualTo(TOP_BAR_HEIGHT)
    }

    @Test
    fun `test that banner is not displayed when scaffold has no top bar`() {
        setContent(topBar = {})

        composeRule.onNodeWithTag(BANNER_TAG).assertDoesNotExist()
        composeRule.onNodeWithTag(CONTENT_TAG).assertTopPositionInRootIsEqualTo(0.dp)
    }

    @Test
    fun `test that banner is not displayed when no banner is provided`() {
        setContent(banner = null)

        composeRule.onNodeWithTag(BANNER_TAG).assertDoesNotExist()
        composeRule.onNodeWithTag(CONTENT_TAG).assertTopPositionInRootIsEqualTo(TOP_BAR_HEIGHT)
    }

    @Test
    fun `test that banner is displayed once when scaffolds are nested`() {
        setContent(content = { NestedScaffold(it) })

        composeRule.onAllNodesWithTag(BANNER_TAG).assertCountEquals(1)
    }

    @Test
    fun `test that banner is not displayed in nested scaffolds when outer scaffold opts out`() {
        setContent(showTopBanner = false, content = { NestedScaffold(it) })

        composeRule.onNodeWithTag(BANNER_TAG).assertDoesNotExist()
    }

    private fun setContent(
        banner: (@Composable () -> Unit)? = { TestBanner() },
        showTopBanner: Boolean = true,
        topBar: @Composable () -> Unit = { TestTopBar() },
        content: @Composable (PaddingValues) -> Unit = { TestContent(it) },
    ) {
        composeRule.setContent {
            CompositionLocalProvider(LocalMegaScaffoldTopBanner provides banner) {
                MegaScaffold(
                    topBar = topBar,
                    showTopBanner = showTopBanner,
                    contentWindowInsets = WindowInsets(0.dp),
                    content = content,
                )
            }
        }
    }

    @Composable
    private fun NestedScaffold(paddingValues: PaddingValues) = MegaScaffold(
        modifier = Modifier.padding(paddingValues),
        topBar = { TestTopBar() },
        contentWindowInsets = WindowInsets(0.dp),
    ) {
        TestContent(it)
    }

    @Composable
    private fun TestTopBar() = Box(
        Modifier
            .fillMaxWidth()
            .height(TOP_BAR_HEIGHT)
    )

    @Composable
    private fun TestBanner() = Box(
        Modifier
            .fillMaxWidth()
            .height(BANNER_HEIGHT)
            .testTag(BANNER_TAG)
    )

    @Composable
    private fun TestContent(paddingValues: PaddingValues) = Box(
        Modifier
            .padding(paddingValues)
            .testTag(CONTENT_TAG)
    )

    private companion object {
        val TOP_BAR_HEIGHT = 56.dp
        val BANNER_HEIGHT = 40.dp
        const val BANNER_TAG = "test:banner"
        const val CONTENT_TAG = "test:content"
    }
}
