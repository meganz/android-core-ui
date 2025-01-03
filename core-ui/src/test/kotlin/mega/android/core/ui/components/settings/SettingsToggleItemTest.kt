package mega.android.core.ui.components.settings

import androidx.compose.ui.Modifier
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.assertIsOff
import androidx.compose.ui.test.assertIsOn
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

@RunWith(AndroidJUnit4::class)
class SettingsToggleItemTest {

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
    fun `test that subtitle is shown if isnot null`() {
        val subtitle = "subtitle to show"
        initComposeRuleContent(
            subtitle = subtitle,
        )
        composeRule.onNodeWithText(subtitle)
            .assertIsDisplayed()
    }

    @Test
    fun `test that footer is shown if is not null`() {
        val footer = "subtitle to show"
        initComposeRuleContent(
            footerText = footer,
        )
        composeRule.onNodeWithText(footer)
            .assertIsDisplayed()
    }

    @Test
    fun `test that toggle is not enabled and checked when enabled is false and checked is true`() {
        initComposeRuleContent(
            enabled = false,
        )
        composeRule.onNodeWithTag(SettingsToggleItem.toggleTag(defaultKey))
            .assertIsNotEnabled()
            .assertIsOn()
    }

    @Test
    fun `test that toggle is not enabled when enabled and checked are false`() {
        initComposeRuleContent(
            enabled = false,
            checked = false,
        )
        composeRule.onNodeWithTag(SettingsToggleItem.toggleTag(defaultKey))
            .assertIsNotEnabled()
            .assertIsOff()
    }

    @Test
    fun `test that toggle is enabled and checked when enabled and checked are true`() {
        initComposeRuleContent(
            enabled = true,
            checked = true,
        )
        composeRule.onNodeWithTag(SettingsToggleItem.toggleTag(defaultKey))
            .assertIsEnabled()
            .assertIsOn()
    }

    @Test
    fun `test that toggle is enabled and unchecked when enabled is true and checked is false`() {
        initComposeRuleContent(
            enabled = true,
            checked = false,
        )
        composeRule.onNodeWithTag(SettingsToggleItem.toggleTag(defaultKey))
            .assertIsEnabled()
            .assertIsOff()
    }

    @Test
    fun `test that onSettingsChanged is invoked with false as new value when item is clicked and current value is true`() {
        val onSettingsChanged = mock<(String, Boolean) -> Unit>()
        initComposeRuleContent(
            onSettingsChanged = onSettingsChanged,
        )
        val tag = SettingsToggleItem.listItemTag(defaultKey)
        composeRule.onNodeWithTag(tag).performClick()
        verify(onSettingsChanged).invoke(defaultKey, false)
    }

    @Test
    fun `test that onSettingsChanged is invoked with true as new value when item is clicked and current value is false`() {
        val onSettingsChanged = mock<(String, Boolean) -> Unit>()
        initComposeRuleContent(
            onSettingsChanged = onSettingsChanged,
            checked = false,
        )
        val tag = SettingsToggleItem.listItemTag(defaultKey)
        composeRule.onNodeWithTag(tag).performClick()
        verify(onSettingsChanged).invoke(defaultKey, true)
    }

    @Test
    fun `test that onSettingsChanged is invoked with false as new value when toggle is clicked and current value is true`() {
        val onSettingsChanged = mock<(String, Boolean) -> Unit>()
        initComposeRuleContent(
            onSettingsChanged = onSettingsChanged,
        )
        val tag = SettingsToggleItem.toggleTag(defaultKey)
        composeRule.onNodeWithTag(tag).performClick()
        verify(onSettingsChanged).invoke(defaultKey, false)
    }

    @Test
    fun `test that onSettingsChanged is invoked with true as new value when toggle is clicked and current value is false`() {
        val onSettingsChanged = mock<(String, Boolean) -> Unit>()
        initComposeRuleContent(
            onSettingsChanged = onSettingsChanged,
            checked = false,
        )
        val tag = SettingsToggleItem.toggleTag(defaultKey)
        composeRule.onNodeWithTag(tag).performClick()
        verify(onSettingsChanged).invoke(defaultKey, true)
    }

    private fun initComposeRuleContent(
        key: String = defaultKey,
        title: String = "title",
        subtitle: String? = "subtitle",
        checked: Boolean = true,
        enabled: Boolean = true,
        footerText: String? = null,
        onSettingsChanged: (key: String, newValue: Boolean) -> Unit = { key, newValue -> },
    ) {
        composeRule.setContent {
            SettingsToggleItem(
                key = key,
                title = title,
                subtitle = subtitle,
                checked = checked,
                enabled = enabled,
                modifier = Modifier,
                footerText = footerText,
                onSettingsChanged = onSettingsChanged,
            )
        }
    }
}

private const val defaultKey = "key"

