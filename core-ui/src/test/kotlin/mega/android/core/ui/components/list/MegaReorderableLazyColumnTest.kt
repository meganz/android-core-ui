package mega.android.core.ui.components.list

import androidx.activity.ComponentActivity
import androidx.compose.foundation.lazy.LazyListItemInfo
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performTouchInput
import androidx.test.ext.junit.runners.AndroidJUnit4
import kotlinx.coroutines.CoroutineScope
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.kotlin.any
import org.mockito.kotlin.argThat
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.verifyNoInteractions

@RunWith(AndroidJUnit4::class)
class MegaReorderableLazyColumnTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun `test that onMove is called when an item is dragged after long click`() {
        val onMove = mock<(CoroutineScope, from: LazyListItemInfo, to: LazyListItemInfo) -> Unit>()
        initComposeTestRule(onMove)
        composeTestRule.onNodeWithTag("3")
            .performTouchInput {
                down(center)
                advanceEventTime(viewConfiguration.longPressTimeoutMillis + 300)
                moveBy(Offset(0f, height * 2f))
            }

        verify(onMove).invoke(any(), argThat { this.key == 3 }, argThat { this.key == 5 })
    }

    @Test
    fun `test that onMove is not called when an item is dragged without previous long click`() {
        val onMove = mock<(CoroutineScope, from: LazyListItemInfo, to: LazyListItemInfo) -> Unit>()
        initComposeTestRule(onMove)
        composeTestRule.onNodeWithTag("3")
            .performTouchInput {
                down(center)
                moveBy(Offset(0f, height * 2f))
            }

        verifyNoInteractions(onMove)
    }

    @Test
    fun `test that onMove is called when an item is dragged immediately if start type is StartImmediately`() {
        val onMove = mock<(CoroutineScope, from: LazyListItemInfo, to: LazyListItemInfo) -> Unit>()
        initComposeTestRule(onMove, dragStartType = DragStartType.StartImmediately)
        composeTestRule.onNodeWithTag("3")
            .performTouchInput {
                down(center)
                moveBy(Offset(0f, height * 2f))
            }

        verify(onMove).invoke(any(), any(), any())
    }

    @Test
    fun `test that onMove is called multiple times when an item is dragged trough multiple items`() {
        val onMove = mock<(CoroutineScope, from: LazyListItemInfo, to: LazyListItemInfo) -> Unit>()
        initComposeTestRule(onMove, dragStartType = DragStartType.StartImmediately)
        composeTestRule.onNodeWithTag("3")
            .performTouchInput {
                down(center)
                moveBy(Offset(0f, height.toFloat()))
                moveBy(Offset(0f, height.toFloat()))
                up()
            }

        verify(onMove).invoke(any(), argThat { this.key == 3 }, argThat { this.key == 4 })
        verify(onMove).invoke(any(), argThat { this.key == 3 }, argThat { this.key == 5 })
    }

    @Test
    fun `test that onMove is called only for enabled items`() {
        val onMove = mock<(CoroutineScope, from: LazyListItemInfo, to: LazyListItemInfo) -> Unit>()
        initComposeTestRule(
            onMove = onMove,
            dragStartType = DragStartType.StartImmediately,
            dragEnabled = { it.id != 2 })
        composeTestRule.onNodeWithTag("2")
            .performTouchInput {
                down(center)
                moveBy(Offset(0f, height * 2f))
            }

        verifyNoInteractions(onMove)

        composeTestRule.onNodeWithTag("3")
            .performTouchInput {
                up()
                down(center)
                moveBy(Offset(0f, height * 2f))
            }

        verify(onMove).invoke(any(), argThat { this.key == 3 }, any())
    }

    private data class Item(val id: Int, val title: String)

    private fun initComposeTestRule(
        onMove: (CoroutineScope, from: LazyListItemInfo, to: LazyListItemInfo) -> Unit,
        dragStartType: DragStartType = DragStartType.StartAfterLongPress,
        dragEnabled: (item: Item) -> Boolean = { true },
    ) {
        composeTestRule.setContent {
            val itemsList = (1..50).map { Item(it, "Item $it") }

            val items by remember { mutableStateOf(itemsList) }
            MegaReorderableLazyColumn(
                items = items,
                key = { it.id },
                onMove = onMove,
                dragEnabled = dragEnabled,
                dragStartType = dragStartType,
            ) { item ->
                OneLineListItem(item.title, modifier = Modifier.testTag(item.id.toString()))
            }
        }
    }
}