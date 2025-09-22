package mega.android.core.ui.components.scrollbar.fastscroll

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.gestures.ScrollableState
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.detectVerticalDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import mega.android.core.ui.R
import mega.android.core.ui.components.image.MegaIcon
import mega.android.core.ui.theme.values.IconColor
import mega.android.core.ui.tokens.theme.DSTokens

@Composable
internal fun TooltipVerticalScrollbar(
    state: LazyListState,
    itemCount: Int,
    reverseLayout: Boolean,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(),
    tooltipText: ((currentIndex: Int) -> String)? = null,
) = TooltipVerticalScrollbar(
    tooltipText = tooltipText,
    state = state,
    firstVisibleItemIndex = remember(state) { derivedStateOf { state.firstVisibleItemIndex } },
    // Fractional progress within the first visible item for smooth thumb movement
    fractionalOffsetInItem = remember(state) {
        derivedStateOf {
            val itemSizePx = state.layoutInfo.visibleItemsInfo.firstOrNull()?.size ?: 1
            if (itemSizePx <= 0) 0f else state.firstVisibleItemScrollOffset.toFloat() / itemSizePx.toFloat()
        }
    },
    // List advances one item per row
    fractionalItemScale = remember(state) { derivedStateOf { 1f } },
    scrollToItem = { state.scrollToItem(it) },
    itemCount = itemCount,
    reverseLayout = reverseLayout,
    modifier = modifier,
    contentPadding = contentPadding
)

@Composable
internal fun TooltipVerticalScrollbar(
    state: LazyGridState,
    itemCount: Int,
    reverseLayout: Boolean,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(),
    tooltipText: ((currentIndex: Int) -> String)? = null,
) = TooltipVerticalScrollbar(
    tooltipText = tooltipText,
    state = state,
    firstVisibleItemIndex = remember(state) { derivedStateOf { state.firstVisibleItemIndex } },
    // Fractional progress within the first visible grid item (row height) for smooth movement
    fractionalOffsetInItem = remember(state) {
        derivedStateOf {
            val itemHeightPx = state.layoutInfo.visibleItemsInfo.firstOrNull()?.size?.height ?: 1
            if (itemHeightPx <= 0) 0f else state.firstVisibleItemScrollOffset.toFloat() / itemHeightPx.toFloat()
        }
    },
    // Scale the fractional progress by the number of items per row to avoid stepping by full rows
    fractionalItemScale = remember(state) {
        derivedStateOf {
            val infos = state.layoutInfo.visibleItemsInfo
            if (infos.isEmpty()) 1f else run {
                val firstRowY = infos.minOf { it.offset.y }
                val colsInFirstRow = infos.count { it.offset.y == firstRowY }.coerceAtLeast(1)
                colsInFirstRow.toFloat()
            }
        }
    },
    scrollToItem = { state.scrollToItem(it) },
    itemCount = itemCount,
    reverseLayout = reverseLayout,
    modifier = modifier,
    contentPadding = contentPadding
)

private val thumbHeight = 40.dp
private const val HIDE_DELAY_MILLIS = 900

/**
 * Specifies the minimum item count needed to enable the fast scrollbar thumb.
 * Ensures the scrollbar is hidden when the list contains too few items.
 */
internal object MinimumItemThreshold {
    const val SINGLE_COLUMN = 50
    const val TWO_COLUMN_GRID = 50
    const val MULTI_COLUMN_GRID = 100
}

internal const val THUMB_TAG = "fast_scroll_lazy_column:icon_thumb"

@Composable
private fun TooltipVerticalScrollbar(
    state: ScrollableState,
    firstVisibleItemIndex: State<Int>,
    fractionalOffsetInItem: State<Float>,
    fractionalItemScale: State<Float>,
    scrollToItem: suspend (targetIndex: Int) -> Unit,
    itemCount: Int,
    reverseLayout: Boolean,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues,
    tooltipText: ((currentIndex: Int) -> String)? = null,
) {
    val density = LocalDensity.current
    val coroutineScope = rememberCoroutineScope()
    val thumbHeightPixels = with(density) { thumbHeight.toPx() }
    var scrollableHeightPixels by remember { mutableFloatStateOf(0f) }
    var scrollableHeight by remember { mutableStateOf(0.dp) }
    var thumbPressed by remember { mutableStateOf(false) }

    // Calculate content padding values once and memoize
    val (topPaddingPx, bottomPaddingPx) = remember(contentPadding, density) {
        val topPx = with(density) { contentPadding.calculateTopPadding().toPx() }
        val bottomPx = with(density) { contentPadding.calculateBottomPadding().toPx() }
        topPx to bottomPx
    }

    // Thumb offset from top of the scrollbar area
    val thumbOffset by remember(itemCount, state, thumbPressed) {
        derivedStateOf {
            // Always follow the actual scroll position for consistent behavior
            val maxIndex = (itemCount - 1).coerceAtLeast(1)
            val continuousIndex = firstVisibleItemIndex.value.toFloat() +
                    fractionalOffsetInItem.value.coerceIn(0f, 1f) *
                    fractionalItemScale.value.coerceAtLeast(1f)
            val clampedIndex = continuousIndex.coerceIn(0f, maxIndex.toFloat())
            val rawProportion =
                if (reverseLayout) 1 - (clampedIndex / maxIndex) else clampedIndex / maxIndex
            val scrollProportion = rawProportion.coerceIn(0f, 1f)
            scrollableHeight * scrollProportion
        }
    }

    val thumbVisible by remember(itemCount, state) {
        derivedStateOf {
            itemCount > 0 && state.isScrollInProgress && (state.canScrollForward || state.canScrollBackward) || thumbPressed
        }
    }

    val tooltipString by remember(thumbVisible) {
        derivedStateOf {
            tooltipText?.let { it(firstVisibleItemIndex.value) }
        }
    }

    // Full height box to capture drags
    Box(
        modifier = modifier
            .onGloballyPositioned { coordinates ->
                with(density) {
                    // Account for content padding in scrollable height calculation
                    val totalHeight = coordinates.size.height.toFloat()
                    val availableHeight = totalHeight - topPaddingPx - bottomPaddingPx
                    scrollableHeightPixels = availableHeight
                    scrollableHeight = scrollableHeightPixels.toDp()
                }
            }
            .pointerInput(itemCount, reverseLayout) {
                detectVerticalDragGestures(
                    onDragEnd = {
                        thumbPressed = false
                    }
                ) { change, _ ->
                    if (thumbPressed && scrollableHeightPixels > 0 && itemCount > 0) {
                        change.consume()

                        val dragPositionY = change.position.y
                        val adjustedY = dragPositionY - thumbHeightPixels / 2

                        // Use total available height for drag calculations
                        val totalAvailableHeight = scrollableHeightPixels + thumbHeightPixels

                        val dragProportion = if (reverseLayout) {
                            1 - (adjustedY / totalAvailableHeight)
                        } else {
                            adjustedY / totalAvailableHeight
                        }

                        val clampedProportion = dragProportion.coerceIn(0f, 1f)

                        val targetIndex = (clampedProportion * (itemCount - 1))
                            .toInt()
                            .coerceIn(0, itemCount - 1)

                        coroutineScope.launch {
                            scrollToItem(targetIndex)
                        }
                    }
                }
            },
    ) {
        // The actual thumb and tooltip
        Box(
            modifier = Modifier
                .height(thumbHeight)
                .offset(y = thumbOffset),
            contentAlignment = Alignment.CenterEnd
        ) {
            val enterAnimation = fadeIn() + scaleIn(
                transformOrigin = TransformOrigin(1f, 0.5f),
                initialScale = 0.5f
            )
            val exitAnimation = scaleOut(
                animationSpec = tween(delayMillis = HIDE_DELAY_MILLIS),
                targetScale = 0.5f,
                transformOrigin = TransformOrigin(1f, 0.5f),
            ) + fadeOut(
                animationSpec = tween(delayMillis = HIDE_DELAY_MILLIS),
            )
            AnimatedVisibility(
                visible = thumbVisible,
                enter = enterAnimation,
                exit = exitAnimation,
            ) {
                Thumb(
                    Modifier
                        .align(Alignment.TopEnd)
                        .pointerInput(Unit) {
                            detectTapGestures(
                                onPress = {
                                    thumbPressed = true
                                    if (tryAwaitRelease()) thumbPressed = false
                                }
                            )
                        })
            }
            tooltipString?.let {
                AnimatedVisibility(
                    visible = thumbPressed,
                    enter = enterAnimation,
                    exit = exitAnimation
                ) {
                    Counter(it, modifier = Modifier.offset(x = (-40).dp))
                }
            }
        }
    }
}

@Composable
private fun Thumb(modifier: Modifier = Modifier) =
    Surface(
        modifier = modifier
            .offset(x = 8.dp)
            .size(thumbHeight),
        shape = CircleShape,
        color = DSTokens.colors.background.surface1,
        shadowElevation = 8.dp
    ) {
        MegaIcon(
            tint = IconColor.Secondary,
            modifier = Modifier
                .padding(8.dp)
                .testTag(THUMB_TAG),
            imageVector = ImageVector.vectorResource(R.drawable.ic_chevron_up_down_small_regular),
            contentDescription = "Chevron up down"
        )
    }