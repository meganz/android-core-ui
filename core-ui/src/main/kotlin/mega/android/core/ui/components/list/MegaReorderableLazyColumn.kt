package mega.android.core.ui.components.list


import android.widget.Toast
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.lazy.LazyListItemInfo
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.core.view.ViewCompat
import kotlinx.coroutines.CoroutineScope
import mega.android.core.ui.components.list.DragStartType.StartAfterLongPressWithHapticFeedback
import mega.android.core.ui.components.list.DragStartType.StartImmediately
import mega.android.core.ui.preview.CombinedThemePreviews
import mega.android.core.ui.theme.AndroidThemeForPreviews
import okhttp3.internal.toImmutableList
import sh.calvin.reorderable.ReorderableItem
import sh.calvin.reorderable.rememberReorderableLazyListState

/**
 * An implementation of a LazyColumn that handles drag and drop to re-order the elements in the list.
 * @param T the type of the items
 * @param K the type of the key of the items
 * @param items The items to be shown in the lazy column
 * @param key A lambda to get the key of each item
 * @param onMove The function that is called when an item is moved. Make sure this function returns only after the items are moved in the UI state.
 * @param modifier
 * @param lazyListState
 * @param onDragStarted The function that is called when the item starts being dragged
 * @param onDragStopped The function that is called when the item stops being dragged
 * @param dragEnabled The function to specify if an item is draggable. Default is true for all items
 * @param dragStartType Defines how the drag will be started. Default is [StartAfterLongPressWithHapticFeedback]
 * @param draggingElevation The elevation of the dragged item. Default 6.dp
 * @param itemContent The composable for each item
 */
@Composable
fun <T, K : Any> MegaReorderableLazyColumn(
    items: List<T>,
    key: (item: T) -> K,
    onMove: suspend CoroutineScope.(from: LazyListItemInfo, to: LazyListItemInfo) -> Unit,
    modifier: Modifier = Modifier,
    lazyListState: LazyListState = rememberLazyListState(),
    onDragStarted: (item: T, startedPosition: Offset) -> Unit = { _, _ -> },
    onDragStopped: (item: T) -> Unit = {},
    dragEnabled: (item: T) -> Boolean = { true },
    dragStartType: DragStartType = StartAfterLongPressWithHapticFeedback,
    draggingElevation: Dp = 6.dp,
    itemContent: @Composable LazyItemScope.(item: T) -> Unit,
) {
    val state = rememberReorderableLazyListState(lazyListState, onMove = onMove)
    LazyColumn(
        state = lazyListState,
        modifier = modifier
    ) {
        items(items, key) { item ->
            ReorderableItem(state, key = key(item)) { isDragging ->
                val elevation by animateDpAsState(if (isDragging) draggingElevation else 0.dp)
                val view = LocalView.current
                Surface(
                    shadowElevation = elevation,
                    color = Color.Unspecified,
                    modifier = Modifier
                        .then(
                            if (dragStartType == StartImmediately) {
                                Modifier.draggableHandle(
                                    enabled = dragEnabled(item),
                                    onDragStarted = { onDragStarted(item, it) },
                                    onDragStopped = { onDragStopped(item) }
                                )
                            } else {
                                Modifier.longPressDraggableHandle(
                                    enabled = dragEnabled(item),
                                    onDragStarted = {
                                        if (dragStartType == StartAfterLongPressWithHapticFeedback) {
                                            ViewCompat.performHapticFeedback(
                                                view,
                                                androidx.core.view.HapticFeedbackConstantsCompat.LONG_PRESS
                                            )
                                        }
                                        onDragStarted(item, it)
                                    },
                                    onDragStopped = { onDragStopped(item) }
                                )
                            }
                        )
                ) {
                    itemContent(item)
                }
            }
        }
    }
}

@CombinedThemePreviews
@Composable
fun MegaReorderableLazyColumnPreview() {
    data class Item(val id: Int, val title: String, val locked: Boolean = false)

    val itemsList = (1..50).map { Item(it, "Item $it") }

    AndroidThemeForPreviews {
        var items by remember { mutableStateOf(itemsList) }
        val context = LocalContext.current
        MegaReorderableLazyColumn(
            items = items,
            key = { it.id },
            onMove = { from, to ->
                items = with(items.toMutableList()) {
                    removeAt(from.index).also { element ->
                        add(to.index, element)
                    }
                    this.toImmutableList()
                }
            },
            dragEnabled = { !it.locked }
        ) { item ->
            OneLineListItem(
                item.title + if (item.locked) " (locked)" else "",
                onClickListener = {
                    items = items.map { if (it.id == item.id) it.copy(locked = !it.locked) else it }
                },
                onLongClickListener = if (!item.locked) null else {
                    { Toast.makeText(context, "Item locked", Toast.LENGTH_SHORT).show() }
                }
            )
        }
    }
}


/**
 * Defines the different ways a drag operation can be initiated.
 *
 */
enum class DragStartType {
    /**
     * The drag operation starts as soon as a touch down event is detected.
     */
    StartImmediately,

    /**
     * The drag operation starts only after a long press gesture is detected.
     */
    StartAfterLongPress,

    /**
     * The drag operation starts after a long press gesture is detected,
     * accompanied by haptic feedback to indicate the drag initiation.
     */
    StartAfterLongPressWithHapticFeedback,
}