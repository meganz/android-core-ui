package mega.android.core.ui.components.settings

import androidx.compose.ui.Modifier
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotDisplayed
import androidx.compose.ui.test.hasClickAction
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import kotlinx.coroutines.delay
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.verifyNoInteractions

@RunWith(AndroidJUnit4::class)
class SettingsActionItemTest {

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
    fun `test that footer is shown if is not null`() {
        val footer = "subtitle to show"
        initComposeRuleContent(
            footerText = footer,
        )
        composeRule.onNodeWithText(footer)
            .assertIsDisplayed()
    }

    @Test
    fun `test that progress indicator is shown when loading is true`() {
        initComposeRuleContent(
            loading = true,
        )
        composeRule.onNodeWithTag(SettingsItemConst.progressIndicatorTag(defaultKey))
            .assertIsDisplayed()
    }

    @Test
    fun `test that progress indicator is not shown when loading is false`() {
        initComposeRuleContent(
            enabled = false,
        )
        composeRule.onNodeWithTag(SettingsItemConst.progressIndicatorTag(defaultKey))
            .assertIsNotDisplayed()
    }

    @Test
    fun `test that onActionClicked is invoked when item is clicked and confirm parameters are null`() {
        val onClicked = mock<(String) -> Unit>()
        initComposeRuleContent(
            onClicked = onClicked,
        )
        val tag = SettingsItemConst.listItemTag(defaultKey)
        composeRule.onNodeWithTag(tag).performClick()
        verify(onClicked).invoke(defaultKey)
    }

    @Test
    fun `test that onActionClicked is not invoked when item is clicked and confirm parameters are not null`() {
        val onClicked = mock<(String) -> Unit>()
        initComposeRuleContent(
            onClicked = onClicked,
            confirmAction = confirmActionParameters()
        )
        val tag = SettingsItemConst.listItemTag(defaultKey)
        composeRule.onNodeWithTag(tag).performClick()
        verifyNoInteractions(onClicked)
    }

    @Test
    fun `test that onActionClicked is invoked and dialog dismissed when item is clicked and confirmed`() {
        val onClicked = mock<(String) -> Unit>()
        val confirmActionParameters = confirmActionParameters()
        initComposeRuleContent(
            onClicked = onClicked,
            confirmAction = confirmActionParameters
        )
        val tag = SettingsItemConst.listItemTag(defaultKey)
        composeRule.onNodeWithTag(tag).performClick()
        composeRule.onNodeWithText(confirmActionParameters.confirmTitle).performClick()
        verify(onClicked).invoke(defaultKey)
        composeRule.onNodeWithText(confirmActionParameters.cancelTitle).assertIsNotDisplayed()
    }

    @Test
    fun `test that action is not invoked and dialog dismissed if clicked but declined`() {
        val onClicked = mock<(String) -> Unit>()
        val confirmActionParameters = confirmActionParameters()
        initComposeRuleContent(
            onClicked = onClicked,
            confirmAction = confirmActionParameters
        )
        val tag = SettingsItemConst.listItemTag(defaultKey)
        composeRule.onNodeWithTag(tag).performClick()
        composeRule.onNodeWithText(confirmActionParameters.cancelTitle).performClick()
        verifyNoInteractions(onClicked)
        composeRule.onNodeWithText(confirmActionParameters.cancelTitle).assertIsNotDisplayed()
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
        subtitle: String? = null,
        enabled: Boolean = true,
        loading: Boolean = false,
        footerText: String? = null,
        confirmAction: ConfirmActionParameters? = null,
        onClicked: (key: String) -> Unit = { },
    ) {
        composeRule.setContent {
            SettingsActionItem(
                key = key,
                title = title,
                enabled = enabled,
                loading = loading,
                modifier = Modifier.Companion,
                footerText = footerText,
                onClicked = onClicked,
                subtitle = subtitle,
                confirmAction = confirmAction,
            )
        }
    }
}

private fun confirmActionParameters() = ConfirmActionParameters(
    title = "title",
    description = null,
    confirmTitle = "Confirm",
    cancelTitle = "Cancel",
)


private const val defaultKey = "key"