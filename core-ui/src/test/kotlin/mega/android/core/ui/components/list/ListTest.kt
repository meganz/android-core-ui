package mega.android.core.ui.components.list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.test.assert
import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.assertHasNoClickAction
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import mega.android.core.ui.components.MegaText
import mega.android.core.ui.theme.AndroidThemeForPreviews
import mega.android.core.ui.theme.values.TextColor
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.verifyNoInteractions

@RunWith(AndroidJUnit4::class)
class ListTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun `test that generic list item is clickable when click is enabled`() {
        composeTestRule.setContent {
            AndroidThemeForPreviews {
                GenericListItem(
                    modifier = Modifier.testTag(TEST_TAG_ITEM),
                    title = { MegaText(text = "Title", textColor = TextColor.Primary) },
                    subtitle = { MegaText(text = "Subtitle", textColor = TextColor.Secondary) },
                    enableClick = true,
                )
            }
        }

        composeTestRule.onNodeWithTag(TEST_TAG_ITEM).assertHasClickAction()
    }

    @Test
    fun `test that generic list item exposes no click action when click is disabled`() {
        composeTestRule.setContent {
            AndroidThemeForPreviews {
                GenericListItem(
                    modifier = Modifier.testTag(TEST_TAG_ITEM),
                    title = { MegaText(text = "Title", textColor = TextColor.Primary) },
                    subtitle = { MegaText(text = "Subtitle", textColor = TextColor.Secondary) },
                    enableClick = false,
                )
            }
        }

        composeTestRule.onNodeWithTag(TEST_TAG_ITEM).assertHasNoClickAction()
    }

    @Test
    fun `test that a click on a generic list item with click disabled reaches the parent`() {
        val onParentClick = mock<() -> Unit>()

        composeTestRule.setContent {
            AndroidThemeForPreviews {
                Box(modifier = Modifier.clickable(onClick = onParentClick)) {
                    GenericListItem(
                        modifier = Modifier.testTag(TEST_TAG_ITEM),
                        title = { MegaText(text = "Title", textColor = TextColor.Primary) },
                        subtitle = { MegaText(text = "Subtitle", textColor = TextColor.Secondary) },
                        enableClick = false,
                    )
                }
            }
        }

        composeTestRule.onNodeWithTag(TEST_TAG_ITEM).performClick()

        verify(onParentClick).invoke()
    }

    @Test
    fun `test that a click on a generic list item with click enabled does not reach the parent`() {
        val onParentClick = mock<() -> Unit>()
        val onItemClick = mock<() -> Unit>()

        composeTestRule.setContent {
            AndroidThemeForPreviews {
                Box(modifier = Modifier.clickable(onClick = onParentClick)) {
                    GenericListItem(
                        modifier = Modifier.testTag(TEST_TAG_ITEM),
                        title = { MegaText(text = "Title", textColor = TextColor.Primary) },
                        subtitle = { MegaText(text = "Subtitle", textColor = TextColor.Secondary) },
                        enableClick = true,
                        onClickListener = onItemClick,
                    )
                }
            }
        }

        composeTestRule.onNodeWithTag(TEST_TAG_ITEM).performClick()

        verify(onItemClick).invoke()
        verifyNoInteractions(onParentClick)
    }

    @Test
    fun `test that generic list item with click disabled still groups its content in one node`() {
        composeTestRule.setContent {
            AndroidThemeForPreviews {
                GenericListItem(
                    modifier = Modifier.testTag(TEST_TAG_ITEM),
                    title = { MegaText(text = "Title", textColor = TextColor.Primary) },
                    subtitle = { MegaText(text = "Subtitle", textColor = TextColor.Secondary) },
                    enableClick = false,
                )
            }
        }

        composeTestRule.onNodeWithTag(TEST_TAG_ITEM)
            .assert(hasText("Title"))
            .assert(hasText("Subtitle"))
    }

    @Test
    fun `test that flexible line list item exposes no click action when click is disabled`() {
        composeTestRule.setContent {
            AndroidThemeForPreviews {
                FlexibleLineListItem(
                    modifier = Modifier.testTag(TEST_TAG_ITEM),
                    title = "Title",
                    subtitle = "Subtitle",
                    enableClick = false,
                )
            }
        }

        composeTestRule.onNodeWithTag(TEST_TAG_ITEM).assertHasNoClickAction()
    }

    @Test
    fun `test that header list item is clickable when click is enabled`() {
        composeTestRule.setContent {
            AndroidThemeForPreviews {
                PrimaryHeaderListItem(
                    modifier = Modifier.testTag(TEST_TAG_ITEM),
                    text = "Header",
                    enableClick = true,
                )
            }
        }

        composeTestRule.onNodeWithTag(TEST_TAG_ITEM).assertHasClickAction()
    }

    @Test
    fun `test that header list item exposes no click action when click is disabled`() {
        composeTestRule.setContent {
            AndroidThemeForPreviews {
                PrimaryHeaderListItem(
                    modifier = Modifier.testTag(TEST_TAG_ITEM),
                    text = "Header",
                    enableClick = false,
                )
            }
        }

        composeTestRule.onNodeWithTag(TEST_TAG_ITEM).assertHasNoClickAction()
    }

    private companion object {
        const val TEST_TAG_ITEM = "list_item"
    }
}
