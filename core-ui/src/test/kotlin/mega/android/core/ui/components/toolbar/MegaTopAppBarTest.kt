package mega.android.core.ui.components.toolbar

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.unit.dp
import androidx.test.ext.junit.runners.AndroidJUnit4
import mega.android.core.ui.theme.AndroidThemeForPreviews
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MegaTopAppBarTest {

    @get:Rule
    val composeRule = createComposeRule()

    private val trailingContentTag = "subtitle_trailing_content"

    private val trailingContent: @Composable RowScope.() -> Unit = {
        Box(modifier = Modifier.size(16.dp).testTag(trailingContentTag))
    }

    @Test
    fun `test that subtitle trailing content is shown when subtitle is set`() {
        setTopAppBar(subtitle = "Subtitle", subtitleTrailingContent = trailingContent)

        composeRule.onNodeWithTag(DEFAULT_TOP_APP_BAR_SUBTITLE_TAG, useUnmergedTree = true)
            .assertIsDisplayed()
        composeRule.onNodeWithTag(trailingContentTag, useUnmergedTree = true)
            .assertIsDisplayed()
    }

    @Test
    fun `test that subtitle trailing content is not shown when subtitle is null`() {
        setTopAppBar(subtitle = null, subtitleTrailingContent = trailingContent)

        composeRule.onNodeWithTag(trailingContentTag, useUnmergedTree = true)
            .assertDoesNotExist()
    }

    @Test
    fun `test that subtitle is shown when subtitle trailing content is null`() {
        setTopAppBar(subtitle = "Subtitle", subtitleTrailingContent = null)

        composeRule.onNodeWithTag(DEFAULT_TOP_APP_BAR_SUBTITLE_TAG, useUnmergedTree = true)
            .assertIsDisplayed()
    }

    @Test
    fun `test that subtitle chevron is shown when it is collapsed`() {
        setTopAppBar(
            subtitle = "Subtitle",
            subtitleTrailingContent = { MegaTopAppBarSubtitleChevron(isExpanded = false) },
        )

        composeRule.onNodeWithTag(MEGA_TOP_APP_BAR_SUBTITLE_CHEVRON_TAG, useUnmergedTree = true)
            .assertIsDisplayed()
    }

    @Test
    fun `test that subtitle chevron is shown when it is expanded`() {
        setTopAppBar(
            subtitle = "Subtitle",
            subtitleTrailingContent = { MegaTopAppBarSubtitleChevron(isExpanded = true) },
        )

        composeRule.onNodeWithTag(MEGA_TOP_APP_BAR_SUBTITLE_CHEVRON_TAG, useUnmergedTree = true)
            .assertIsDisplayed()
    }

    private fun setTopAppBar(
        subtitle: String?,
        subtitleTrailingContent: (@Composable RowScope.() -> Unit)?,
    ) {
        composeRule.setContent {
            AndroidThemeForPreviews {
                MegaTopAppBar(
                    title = "Title",
                    subtitle = subtitle,
                    navigationType = AppBarNavigationType.None,
                    subtitleTrailingContent = subtitleTrailingContent,
                )
            }
        }
    }
}
