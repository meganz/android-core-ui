@file:OptIn(ExperimentalMaterial3Api::class)

package mega.android.core.ui.modifiers

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.layout.Measurable
import androidx.compose.ui.layout.MeasureResult
import androidx.compose.ui.layout.MeasureScope
import androidx.compose.ui.node.CompositionLocalConsumerModifierNode
import androidx.compose.ui.node.LayoutModifierNode
import androidx.compose.ui.node.ModifierNodeElement
import androidx.compose.ui.node.currentValueOf
import androidx.compose.ui.platform.InspectorInfo
import androidx.compose.ui.unit.Constraints
import mega.android.core.ui.components.LocalScrollToHideBehavior
import mega.android.core.ui.components.LocalTopAppBarScrollBehavior

/**
 * Creates and remembers a [ScrollToHideBehavior] instance to translate UI components
 * based on scroll offset, similar to TopAppBarDefaults.enterAlwaysScrollBehavior.
 *
 * The component will translate up proportionally to scroll offset, making it feel
 * like it's part of the content being pushed up.
 *
 * @return A remembered [ScrollToHideBehavior] instance
 */
@Composable
fun rememberScrollToHideBehavior(): ScrollToHideBehavior {
    return rememberSaveable(saver = ScrollToHideBehavior.Saver) {
        ScrollToHideBehavior()
    }
}

/**
 * Modifier extension to make a composable collapsible based on [ScrollToHideBehavior].
 * Apply this to the content that should collapse/expand on scroll (e.g. tabs, chips).
 *
 * This handles:
 * - Measuring the content height and setting [ScrollToHideBehavior.heightOffsetLimit]
 * - Clipping the content as it collapses
 * - Reducing the measured height proportionally to the scroll offset
 * - Translating the content upward as it collapses
 *
 * @param behavior The [ScrollToHideBehavior] that drives the collapse behavior
 * @return A [Modifier] with collapsible behavior applied
 */
@Composable
fun Modifier.applyScrollToHideBehavior(
    behavior: ScrollToHideBehavior? = LocalScrollToHideBehavior.current,
): Modifier =
    if (behavior == null) this
    else this
        .clipToBounds()
        .then(ScrollOffsetContentElement(behavior))

private data class ScrollOffsetContentElement(
    private val state: ScrollToHideBehavior,
) : ModifierNodeElement<ScrollOffsetContentNode>() {
    override fun create() = ScrollOffsetContentNode(state)

    override fun update(node: ScrollOffsetContentNode) {
        if (node.state !== state) {
            node.state.clearInternalHooks()
            node.state = state
            node.wireHooks()
        }
    }

    override fun InspectorInfo.inspectableProperties() {
        name = "scrollOffsetContent"
        properties["state"] = state
    }
}

private class ScrollOffsetContentNode(
    var state: ScrollToHideBehavior,
) : Modifier.Node(), LayoutModifierNode, CompositionLocalConsumerModifierNode {

    override fun onAttach() {
        wireHooks()
    }

    fun wireHooks() {
        val scrollBehavior = currentValueOf(LocalTopAppBarScrollBehavior)
        state.shouldConsumePreScroll = scrollBehavior?.let { behavior ->
            { availableY ->
                behavior.isPinned
                    || availableY > 0f  // expanding direction — always allow
                    || behavior.state.heightOffset <= behavior.state.heightOffsetLimit
            }
        }
        state.onPreScrollConsumed = scrollBehavior?.takeIf { it.isPinned }?.let { behavior ->
            { consumedY -> behavior.state.contentOffset += consumedY }
        }
    }

    override fun onDetach() {
        state.clearInternalHooks()
    }

    override fun MeasureScope.measure(
        measurable: Measurable,
        constraints: Constraints,
    ): MeasureResult {
        val placeable = measurable.measure(constraints)
        state.heightOffsetLimit = -placeable.height.toFloat()
        val offset = state.offset
        val height = (placeable.height + offset).toInt().coerceAtLeast(0)
        return layout(placeable.width, height) {
            placeable.placeWithLayer(0, 0) {
                translationY = offset
            }
        }
    }
}

/**
 * State holder for scroll offset functionality.
 *
 * This state tracks vertical scroll gestures and provides a height offset
 * that can be used to adjust container height and translate UI components.
 * Similar to Material3's TopAppBarState, it uses a [heightOffsetLimit] property
 * that can be set dynamically to define the maximum collapse distance.
 *
 * Usage:
 * - Apply [nestedScroll] modifier to the scrollable container
 * - Apply [applyScrollToHideBehavior] modifier to the collapsible content
 */
@Stable
class ScrollToHideBehavior {
    internal var onPreScrollConsumed: ((consumedY: Float) -> Unit)? = null
    internal var shouldConsumePreScroll: ((availableY: Float) -> Boolean)? = null

    internal fun clearInternalHooks() {
        onPreScrollConsumed = null
        shouldConsumePreScroll = null
    }

    /**
     * The limit for the height offset. Should be a negative value representing
     * the maximum distance the component can collapse. Similar to
     * Material3's TopAppBarState.heightOffsetLimit.
     */
    var heightOffsetLimit by mutableFloatStateOf(0f)

    var heightOffset by mutableFloatStateOf(0f)
        private set

    /**
     * Accumulation of scroll in the current direction (px).
     * Negative = scrolling down, positive = scrolling up.
     * Resets to 0 whenever the scroll direction reverses.
     */
    var scrollDirectionOffset by mutableFloatStateOf(0f)
        private set

    /**
     * The current height offset in pixels, clamped between [heightOffsetLimit] and 0.
     * Negative values indicate the container should shrink (hide).
     *
     * Uses [derivedStateOf] to avoid unnecessary relayout/redraw when [heightOffset]
     * changes but the clamped value remains the same (e.g. when chips are fully
     * collapsed and the user keeps scrolling down).
     */
    val offset: Float by derivedStateOf {
        heightOffset.coerceIn(heightOffsetLimit, 0f)
    }

    val nestedScrollConnection: NestedScrollConnection = object : NestedScrollConnection {
        override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
            val delta = available.y
            if (delta != 0f) {
                if (scrollDirectionOffset != 0f && (delta > 0f) != (scrollDirectionOffset > 0f)) {
                    scrollDirectionOffset = 0f
                }
                scrollDirectionOffset += delta
            }

            if (shouldConsumePreScroll?.invoke(available.y) == false) {
                return Offset.Zero
            }

            val prevHeightOffset = heightOffset
            heightOffset = (heightOffset + available.y).coerceIn(heightOffsetLimit, 0f)

            val consumedY = heightOffset - prevHeightOffset
            return if (consumedY != 0f) {
                onPreScrollConsumed?.invoke(consumedY)
                Offset(x = 0f, y = consumedY)
            } else {
                Offset.Zero
            }
        }

        override fun onPostScroll(
            consumed: Offset,
            available: Offset,
            source: NestedScrollSource,
        ): Offset {
            heightOffset = (heightOffset + consumed.y).coerceIn(heightOffsetLimit, 0f)
            return Offset.Zero
        }
    }

    /**
     * Resets scroll offsets to their initial state.
     */
    fun reset() {
        heightOffset = 0f
        scrollDirectionOffset = 0f
    }

    companion object {
        val Saver: Saver<ScrollToHideBehavior, Float> = Saver(
            save = { it.heightOffset },
            restore = {
                ScrollToHideBehavior().apply {
                    heightOffset = it
                }
            }
        )
    }
}
