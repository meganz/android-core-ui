package mega.android.core.ui.components.settings

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import kotlinx.coroutines.test.runTest
import org.junit.Ignore
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class SettingsOptionsItemTest {
    @get:Rule
    val composeRule = createComposeRule()

    @Test
    fun `test that title is shown`() {
        val title = "title to show"
        initComposeRuleContent(title = title)
        composeRule.onNodeWithText(title).assertIsDisplayed()
    }

    @Test
    fun `test that selected option text is shown`() {
        val values = (1..5).map { "itemTest $it" }
        val selectedValue = values[3]
        initComposeRuleContent(
            values = values,
            selectedValue = selectedValue
        )
        composeRule.onNodeWithText(selectedValue).assertIsDisplayed()
    }

    @Test
    fun `test that bottom sheet is shown when item is clicked`() {
        initComposeRuleContent()
        val itemTag = SettingsItemConst.listItemTag(defaultKey)
        val bottomSheetTag = SettingsItemConst.bottomSheetTag(defaultKey)
        composeRule.onNodeWithTag(itemTag).performClick()
        composeRule.onNodeWithTag(bottomSheetTag).assertIsDisplayed()
    }

    @Test
    fun `test that bottom sheet shows setting title`() {
        val title = "titleTest"
        initComposeRuleBottomSheet(title = title)
        composeRule.onNodeWithText(title).assertIsDisplayed()
    }

    @Test
    fun `test that bottom sheet shows all values`() {
        val values = (1..5).map { "itemTest $it" }
        initComposeRuleBottomSheet(values = values)
        values.forEach {
            composeRule.onNodeWithText(it, useUnmergedTree = true).assertExists()
        }
    }

    @Test
    @Ignore(
        value = "Ignore until it's fixed by the Compose team. This is a known issue form bottom sheet" +
                "https://issuetracker.google.com/issues/215231631 " +
                "https://issuetracker.google.com/issues/366255137 " +
                "https://github.com/robolectric/robolectric/issues/9595"
    )
    fun `test that onValueSelected callback is invoked when item is clicked in the bottom sheet`() =
        runTest {
            val values = (1..5).map { "itemTest $it" }
            val selectedValue = values[3]
            val onValueSelected = mock<(String) -> Unit>()
            initComposeRuleBottomSheet(
                values = values,
                onValueSelected = onValueSelected
            )
            composeRule.onNodeWithText(selectedValue, useUnmergedTree = true).performClick()
            verify(onValueSelected).invoke(selectedValue)
        }

    private fun initComposeRuleContent(
        key: String = defaultKey,
        title: String = "title",
        values: List<String> = (1..5).map { "item $it" },
        selectedValue: String = values.first(),
        onValueSelected: (key: String, value: String) -> Unit = { _, _ -> },
    ) {
        composeRule.setContent {
            SettingsOptionsItem(
                key = key,
                title = title,
                values = values,
                selectedValue = selectedValue,
                onValueSelected = onValueSelected
            )
        }
    }

    private fun initComposeRuleBottomSheet(
        title: String = "title",
        values: List<String> = (1..5).map { "item $it" },
        selectedValue: String = values.first(),
        onDismiss: () -> Unit = {},
        onValueSelected: (String) -> Unit = {},
    ) {
        composeRule.setContent {
            SettingsOptionsModal(
                key = defaultKey,
                title = title,
                values = values,
                valueToString = { it.toString() },
                selectedValue = selectedValue,
                onDismiss = onDismiss,
                onValueSelected = onValueSelected,
            )
        }
    }
}

private const val defaultKey = "key"