package mega.android.core.ui.components.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsOff
import androidx.compose.ui.test.assertIsOn
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import mega.android.core.ui.components.settings.SettingsItemConst.listOptionItemTag
import mega.android.core.ui.components.settings.SettingsItemConst.radioButtonTag
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.verifyNoInteractions

@RunWith(AndroidJUnit4::class)
class SettingsGroupOptionsItemsTest {

    @get:Rule
    val composeRule = createComposeRule()

    @Test
    fun `test that all options are shown`() {
        val values = (0..5).map{"test $it"}
        initComposeRuleContent(
            values = values
        )
        values.forEach {
            composeRule.onNodeWithText(it)
                .assertIsDisplayed()
        }
    }

    @Test
    fun `test that only selected option is actually selected`() {
        val values = (0..5).map{"test $it"}
        val selectedIndex = 3
        val selected = values[selectedIndex]
        initComposeRuleContent(
            values = values,
            selectedValue = selected,
        )
        values.forEachIndexed {i,value ->
            if (value == selected) {
                composeRule.onNodeWithTag(radioButtonTag(defaultKey, i)).assertIsOn()
            } else {
                composeRule.onNodeWithTag(radioButtonTag(defaultKey, i)).assertIsOff()
            }
        }
    }

    @Test
    fun `test that onValueSelected is invoked when item is clicked`() {
        val values = (0..5).map{"test $it"}
        val selectedIndex = 3
        val selected = values[selectedIndex]
        val onValueSelected = mock<(String, String) -> Unit>()
        initComposeRuleContent(
            values = values,
            onValueSelected = onValueSelected
        )
        composeRule.onNodeWithTag(listOptionItemTag(defaultKey,selectedIndex)).performClick()
        verify(onValueSelected).invoke(defaultKey, selected)
    }

    @Test
    fun `test that onValueSelected is invoked when radio button is clicked`() {
        val values = (0..5).map{"test $it"}
        val selectedIndex = 3
        val selected = values[selectedIndex]
        val onValueSelected = mock<(String, String) -> Unit>()
        initComposeRuleContent(
            values = values,
            onValueSelected = onValueSelected
        )
        composeRule.onNodeWithTag(radioButtonTag(defaultKey,selectedIndex)).performClick()
        verify(onValueSelected).invoke(defaultKey, selected)
    }

    @Test
    fun `test that onValueSelected is not invoked when item is clicked but not enabled`() {
        val values = (0..5).map{"test $it"}
        val onValueSelected = mock<(String, String) -> Unit>()
        initComposeRuleContent(
            values = values,
            onValueSelected = onValueSelected,
            enabled = false,
        )
        composeRule.onNodeWithTag(listOptionItemTag(defaultKey,3)).performClick()
        verifyNoInteractions(onValueSelected)
    }

    private fun initComposeRuleContent(
        key: String = defaultKey,
        values: List<String> = (1..5).map { "item $it" },
        selectedValue: String = values.first(),
        enabled: Boolean = true,
        onValueSelected: (key: String, newValue: String) -> Unit = { _, _ -> },
    ) {
        composeRule.setContent {
            Column {
                SettingsGroupOptionsItems(
                    key = key,
                    values = values,
                    selectedValue = selectedValue,
                    enabled = enabled,
                    onValueSelected = onValueSelected
                )
            }
        }
    }
}

private const val defaultKey = "key"
