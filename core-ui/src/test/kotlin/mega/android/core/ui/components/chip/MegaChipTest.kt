package mega.android.core.ui.components.chip

import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.assertHeightIsAtLeast
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsSelectable
import androidx.compose.ui.test.assertWidthIsAtLeast
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.unit.dp
import androidx.test.ext.junit.runners.AndroidJUnit4
import mega.android.core.ui.R
import mega.android.core.ui.theme.AndroidTheme
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.kotlin.mock
import org.mockito.kotlin.never
import org.mockito.kotlin.verify

@RunWith(AndroidJUnit4::class)
class MegaChipTest {

    @get:Rule
    val composeRule = createComposeRule()

    @Test
    fun `test that onClick is invoked when a chip without a trailing action is clicked`() {
        val onClick = mock<() -> Unit>()
        initComposeRuleContent(onClick = onClick)

        composeRule.onNodeWithContentDescription(CHIP_TEXT).performClick()

        verify(onClick).invoke()
    }

    @Test
    fun `test that onClick is invoked when a chip without a trailing icon is clicked`() {
        val onClick = mock<() -> Unit>()
        initComposeRuleContent(trailingIcon = null, onClick = onClick)

        composeRule.onNodeWithContentDescription(CHIP_TEXT).performClick()

        verify(onClick).invoke()
    }

    @Test
    fun `test that the trailing icon is not a separate node without a trailing action`() {
        initComposeRuleContent()

        composeRule.onNodeWithContentDescription(DISMISS_LABEL).assertDoesNotExist()
    }

    @Test
    fun `test that onClick is invoked when the chip label is clicked`() {
        val onClick = mock<() -> Unit>()
        initComposeRuleContent(onClick = onClick, trailingAction = trailingAction())

        composeRule.onNodeWithText(CHIP_TEXT, useUnmergedTree = true).performClick()

        verify(onClick).invoke()
    }

    @Test
    fun `test that the trailing action is invoked when the trailing icon is clicked`() {
        val onTrailingClick = mock<() -> Unit>()
        initComposeRuleContent(trailingAction = trailingAction(onTrailingClick))

        composeRule.onNodeWithContentDescription(DISMISS_LABEL).performClick()

        verify(onTrailingClick).invoke()
    }

    @Test
    fun `test that onClick is not invoked when the trailing icon is clicked`() {
        val onClick = mock<() -> Unit>()
        initComposeRuleContent(onClick = onClick, trailingAction = trailingAction())

        composeRule.onNodeWithContentDescription(DISMISS_LABEL).performClick()

        verify(onClick, never()).invoke()
    }

    @Test
    fun `test that the trailing action is not invoked when the chip label is clicked`() {
        val onTrailingClick = mock<() -> Unit>()
        initComposeRuleContent(trailingAction = trailingAction(onTrailingClick))

        composeRule.onNodeWithText(CHIP_TEXT, useUnmergedTree = true).performClick()

        verify(onTrailingClick, never()).invoke()
    }

    @Test
    fun `test that the trailing action is not invoked when the chip is disabled`() {
        val onTrailingClick = mock<() -> Unit>()
        initComposeRuleContent(
            enabled = false,
            trailingAction = trailingAction(onTrailingClick),
        )

        composeRule.onNodeWithContentDescription(DISMISS_LABEL).performClick()

        verify(onTrailingClick, never()).invoke()
    }

    @Test
    fun `test that onClick is not invoked when the chip is disabled`() {
        val onClick = mock<() -> Unit>()
        initComposeRuleContent(
            enabled = false,
            onClick = onClick,
            trailingAction = trailingAction(),
        )

        composeRule.onNodeWithText(CHIP_TEXT, useUnmergedTree = true).performClick()

        verify(onClick, never()).invoke()
    }

    @Test
    fun `test that the trailing icon hit target is larger than the icon itself`() {
        initComposeRuleContent(trailingAction = trailingAction())

        composeRule.onNodeWithContentDescription(DISMISS_LABEL)
            .assertHeightIsAtLeast(32.dp)
            .assertWidthIsAtLeast(24.dp)
    }

    @Test
    fun `test that the trailing icon touch target meets the minimum interactive size`() {
        initComposeRuleContent(trailingAction = trailingAction())

        val touchBounds = composeRule.onNodeWithContentDescription(DISMISS_LABEL)
            .fetchSemanticsNode().touchBoundsInRoot
        with(composeRule.density) {
            val width = touchBounds.width.toDp()
            val height = touchBounds.height.toDp()
            assertTrue(
                "touch target was $width x $height",
                width >= 48.dp && height >= 48.dp,
            )
        }
    }

    @Test
    fun `test that the chip keeps its own click action and selected state alongside a trailing action`() {
        initComposeRuleContent(trailingAction = trailingAction())

        composeRule.onNodeWithContentDescription(CHIP_TEXT)
            .assertHasClickAction()
            .assertIsSelectable()
    }

    @Test
    fun `test that chip text is reachable by a text matcher when a trailing action is set`() {
        initComposeRuleContent(trailingAction = trailingAction())

        composeRule.onNodeWithText(CHIP_TEXT).assertIsDisplayed()
    }

    private fun trailingAction(onClick: () -> Unit = {}) = ChipTrailingAction(
        contentDescription = DISMISS_LABEL,
        onClick = onClick,
    )

    private fun initComposeRuleContent(
        text: String = CHIP_TEXT,
        enabled: Boolean = true,
        trailingIcon: Int? = R.drawable.ic_x_medium_thin_outline,
        onClick: () -> Unit = {},
        trailingAction: ChipTrailingAction? = null,
    ) {
        composeRule.setContent {
            AndroidTheme(isDark = false) {
                MegaChip(
                    selected = false,
                    text = text,
                    enabled = enabled,
                    trailingIcon = trailingIcon,
                    onClick = onClick,
                    trailingAction = trailingAction,
                )
            }
        }
    }

    private companion object {
        const val CHIP_TEXT = "pdf"
        const val DISMISS_LABEL = "Remove pdf exclusion"
    }
}
