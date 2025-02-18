package mega.android.core.ui.components.scrollbar

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectVerticalDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.pointer.PointerInputChange
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import mega.android.core.ui.theme.AppTheme
import mega.android.core.ui.theme.values.IconColor
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min
import kotlin.math.roundToInt

@Composable
fun VerticalScrollbar(
    lazyListState: LazyListState,
    minThumbHeightInDp: Dp,
    modifier: Modifier = Modifier,
    thumb: (@Composable () -> Unit)? = null
) {
    // Using Offset.Unspecified and Float.NaN instead of null
    // to prevent unnecessary boxing of primitives
    var draggedOffset by remember { mutableStateOf(Offset.Unspecified) }

    var trackSize by remember { mutableIntStateOf(0) }

    val totalItemsCount by remember { derivedStateOf { lazyListState.layoutInfo.totalItemsCount } }

    // Represent the thumb offset in percentage to make the scroll smoother
    val thumbOffset by remember {
        derivedStateOf {
            val visibleItemsInfo = lazyListState.layoutInfo.visibleItemsInfo
            val firstItemIndex = lazyListState.firstVisibleItemIndex
            val firstItemSize = visibleItemsInfo.first().size
            val itemOffsetInFloat =
                lazyListState.firstVisibleItemScrollOffset.toFloat() / firstItemSize
            val offsetInPercentage =
                (itemOffsetInFloat + firstItemIndex) / totalItemsCount
            // Make sure the value doesn't exceed 100%
            min(offsetInPercentage, 1F)
        }
    }

    // The percentage of the list that has been scrolled in pixels.
    val listScrolledPercentage by remember {
        derivedStateOf {
            val itemsVisible =
                lazyListState.layoutInfo.visibleItemsInfo.fold(initial = 0F) { accumulator, itemInfo ->
                    accumulator + itemVisibilityPercentage(
                        itemSize = itemInfo.size,
                        itemStartOffset = itemInfo.offset,
                        viewportStartOffset = lazyListState.layoutInfo.viewportStartOffset,
                        viewportEndOffset = lazyListState.layoutInfo.viewportEndOffset,
                    )
                }
            // Make sure the value doesn't exceed 100%
            min(itemsVisible / totalItemsCount, 1F)
        }
    }

    val isScrollInProgress by remember { derivedStateOf { lazyListState.isScrollInProgress } }

    val defaultThumb: @Composable () -> Unit = {
        Box(
            modifier = Modifier
                .width(6.dp)
                .fillMaxHeight()
                .background(
                    shape = RoundedCornerShape(3.dp),
                    color = AppTheme.iconColor(IconColor.Secondary)
                ),
        )
    }

    Box(
        modifier = modifier
            .onGloballyPositioned { coordinates ->
                trackSize = coordinates.size.height
            }
            .pointerInput(Unit) {
                val onDrag: (change: PointerInputChange, dragAmount: Float) -> Unit =
                    onDrag@{ _, delta ->
                        if (draggedOffset == Offset.Unspecified) return@onDrag
                        draggedOffset = draggedOffset.copy(
                            y = draggedOffset.y + delta,
                        )
                    }

                detectVerticalDragGestures(onVerticalDrag = onDrag)
            }
    ) {
        // Only display the thumb if the scroll is in progress.
        if (isScrollInProgress) {
            Layout(content = { thumb?.invoke() ?: defaultThumb() }) { measurables, constraints ->
                val measurable = measurables.first()

                val thumbHeightInPx = max(
                    a = listScrolledPercentage * trackSize.toFloat(),
                    b = minThumbHeightInDp.toPx(),
                )

                // Update the placeable constraints.
                val updatedConstraints = constraints.copy(
                    minHeight = thumbHeightInPx.roundToInt(),
                    maxHeight = thumbHeightInPx.roundToInt(),
                )
                val placeable = measurable.measure(updatedConstraints)

                // The max distance the thumb can travel as a percentage of total track size after being scrolled
                // The track size in pixels based on the maximum available distance for the thumb.
                val trackSizePx = when (val thumbTrackSizePercent = 1F - listScrolledPercentage) {
                    0f -> trackSize.toFloat()
                    else -> (trackSize.toFloat() - thumbHeightInPx) / thumbTrackSizePercent
                }
                // The total number of pixels the thumb has moved.
                val thumbMovedPx = trackSizePx * thumbOffset

                layout(placeable.width, placeable.height) {
                    placeable.place(0, thumbMovedPx.roundToInt())
                }
            }
        }
    }
}

/**
 * Returns the percentage of an item that is currently visible in the view port.
 *
 * @param itemSize the size of the item
 * @param itemStartOffset the start offset of the item relative to the view port start
 * @param viewportStartOffset the start offset of the view port
 * @param viewportEndOffset the end offset of the view port
 * @return The visibility percentage in [Float].
 */
private fun itemVisibilityPercentage(
    itemSize: Int,
    itemStartOffset: Int,
    viewportStartOffset: Int,
    viewportEndOffset: Int,
): Float {
    if (itemSize == 0) return 0f
    val itemEnd = itemStartOffset + itemSize
    val startOffset = when {
        itemStartOffset > viewportStartOffset -> 0
        else -> abs(abs(viewportStartOffset) - abs(itemStartOffset))
    }
    val endOffset = when {
        itemEnd < viewportEndOffset -> 0
        else -> abs(abs(itemEnd) - abs(viewportEndOffset))
    }

    val size = itemSize.toFloat()
    return (size - startOffset - endOffset) / size
}
