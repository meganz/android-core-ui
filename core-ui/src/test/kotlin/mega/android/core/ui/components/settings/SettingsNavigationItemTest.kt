package mega.android.core.ui.components.settings

import androidx.compose.ui.Modifier
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
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
class SettingsNavigationItemTest {
    @get:Rule
    val composeRule = createComposeRule()

    @Test
    fun `test that title is shown`() {
        val title = "title to show"
        initComposeRuleContent(
            title = title,
        )
        composeRule.onNodeWithText(title)
            .assertIsDisplayed()
    }

    @Test
    fun `test that subtitle is shown if is not null`() {
        val subtitle = "subtitle to show"
        initComposeRuleContent(
            subtitle = subtitle,
        )
        composeRule.onNodeWithText(subtitle)
            .assertIsDisplayed()
    }

    @Test
    fun `test that onActionClicked is invoked when item is clicked`() {
        val onClicked = mock<(String) -> Unit>()
        initComposeRuleContent(
            onClicked = onClicked,
        )
        val tag = SettingsItemConst.listItemTag(defaultKey)
        composeRule.onNodeWithTag(tag).performClick()
        verify(onClicked).invoke(defaultKey)
    }

    @Test
    fun `test that onActionClicked is not invoked when item is not enabled`() {
        val onClicked = mock<(String) -> Unit>()
        initComposeRuleContent(
            onClicked = onClicked,
            enabled = false,
        )
        val tag = SettingsItemConst.listItemTag(defaultKey)
        composeRule.onNodeWithTag(tag).performClick()
        verifyNoInteractions(onClicked)
    }

    private fun initComposeRuleContent(
        key: String = defaultKey,
        title: String = "title",
        subtitle: String? = "subtitle",
        enabled: Boolean = true,
        onClicked: (key: String) -> Unit = { },
    ) {
        composeRule.setContent {
            SettingsNavigationItem(
                key = key,
                title = title,
                subtitle = subtitle,
                enabled = enabled,
                modifier = Modifier,
                onClicked = onClicked,
            )
        }
    }
}

private const val defaultKey = "key"
