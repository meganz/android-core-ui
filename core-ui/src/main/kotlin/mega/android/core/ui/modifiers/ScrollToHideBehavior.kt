@file:OptIn(ExperimentalMaterial3Api::class)

package mega.android.core.ui.modifiers

import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.animate
import androidx.compose.animation.core.tween
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.layout.LayoutCoordinates
import androidx.compose.ui.layout.Measurable
import androidx.compose.ui.layout.MeasureResult
import androidx.compose.ui.layout.MeasureScope
import androidx.compose.ui.layout.positionInRoot
import androidx.compose.ui.node.CompositionLocalConsumerModifierNode
import androidx.compose.ui.node.LayoutAwareModifierNode
import androidx.compose.ui.node.LayoutModifierNode
import androidx.compose.ui.node.ModifierNodeElement
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.node.currentValueOf
import androidx.compose.ui.node.invalidateMeasurement
import androidx.compose.ui.platform.InspectorInfo
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.Velocity
import androidx.compose.ui.unit.dp
import mega.android.core.ui.components.LocalScrollToHideBehavior
import mega.android.core.ui.components.LocalTopAppBarScrollBehavior
import kotlin.math.roundToInt

/**
 * Creates and remembers a [ScrollToHideBehavior] instance to translate UI components
 * based on scroll offset, similar to TopAppBarDefaults.enterAlwaysScrollBehavior.
 *
 * The component will translate up proportionally to scroll offset, making it feel
 * like it's part of the content being pushed up.
 *
 * @param snapAnimationSpec When set, a scroll ending with the component partially collapsed
 * settles it to the nearest edge (fully shown or fully hidden) with this animation; null
 * (the default) leaves it wherever the scroll stopped.
 * @return A remembered [ScrollToHideBehavior] instance
 */
@Composable
fun rememberScrollToHideBehavior(
    snapAnimationSpec: AnimationSpec<Float>? = null,
): ScrollToHideBehavior {
    return rememberSaveable(saver = ScrollToHideBehavior.Saver) {
        ScrollToHideBehavior()
    }.apply { this.snapAnimationSpec = snapAnimationSpec }
}

/**
 * Modifier extension to make a composable collapsible based on [ScrollToHideBehavior].
 * Apply this to the content that should collapse/expand on scroll (e.g. tabs, chips).
 *
 * This handles:
 * - Measuring the content height and setting [ScrollToHideBehavior.heightOffsetLimit]
 * - Clipping the content as it collapses
 * - Reducing the measured height as the scroll offset moves past the content
 * - Translating the content upward as it collapses
 *
 * Several composables may share one [behavior]: their heights add up to the distance the
 * behavior collapses over, and they move up as one block with the scrolling content, the
 * topmost disappearing first, like a header scrolled out of a list.
 *
 * @param behavior The [ScrollToHideBehavior] that drives the collapse behavior
 * @param contributesHeight Whether this content's height counts towards the collapse distance.
 * Pass false for content that is laid out but not shown, such as a header on an off-screen
 * pager page, so it moves with the block without stretching the scroll.
 * @return A [Modifier] with collapsible behavior applied
 */
@Composable
fun Modifier.applyScrollToHideBehavior(
    behavior: ScrollToHideBehavior? = LocalScrollToHideBehavior.current,
    contributesHeight: Boolean = true,
): Modifier =
    if (behavior == null) this
    else this
        .clipToBounds()
        .then(ScrollOffsetContentElement(behavior, contributesHeight))

/**
 * Slides a bottom-anchored component (e.g. a floating toolbar) down and out of view in step
 * with the collapse of the component driven by [behavior], so both retreat together as the
 * user scrolls into the content. The translation is proportional to the collapse fraction,
 * up to [slideDistance].
 *
 * @param behavior The [ScrollToHideBehavior] that drives the collapse
 * @param slideDistance How far the component travels when the collapse completes
 */
@Composable
fun Modifier.applyScrollToHideBottomBarBehavior(
    behavior: ScrollToHideBehavior? = LocalScrollToHideBehavior.current,
    slideDistance: Dp = DefaultBottomBarSlideDistance,
): Modifier {
    if (behavior == null) return this
    val slideDistancePx = with(LocalDensity.current) { slideDistance.toPx() }
    return graphicsLayer {
        translationY = behavior.hiddenFraction * slideDistancePx
    }
}

private val DefaultBottomBarSlideDistance = 100.dp

private data class ScrollOffsetContentElement(
    private val state: ScrollToHideBehavior,
    private val contributesHeight: Boolean,
) : ModifierNodeElement<ScrollOffsetContentNode>() {
    override fun create() = ScrollOffsetContentNode(state, contributesHeight)

    override fun update(node: ScrollOffsetContentNode) {
        if (node.state !== state) {
            node.state.detachNode(node)
            node.state = state
            node.state.attachNode(node)
            node.wireHooks()
        }
        node.contributesHeight = contributesHeight
        node.invalidateMeasurement()
    }

    override fun InspectorInfo.inspectableProperties() {
        name = "scrollOffsetContent"
        properties["state"] = state
        properties["contributesHeight"] = contributesHeight
    }
}

private class ScrollOffsetContentNode(
    var state: ScrollToHideBehavior,
    var contributesHeight: Boolean,
) : Modifier.Node(), LayoutModifierNode, LayoutAwareModifierNode,
    CompositionLocalConsumerModifierNode {

    override fun onAttach() {
        state.attachNode(this)
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
        state.detachNode(this)
    }

    override fun onPlaced(coordinates: LayoutCoordinates) {
        val top = coordinates.positionInRoot().y
        state.updateNodePosition(this, top = top, bottom = top + coordinates.size.height)
    }

    override fun MeasureScope.measure(
        measurable: Measurable,
        constraints: Constraints,
    ): MeasureResult {
        val node = this@ScrollOffsetContentNode
        val placeable = measurable.measure(constraints)
        state.setContribution(node, placeable.height, contributesHeight)
        val hidden = (state.collapseDistance - state.collapsibleHeightAbove(node))
            .coerceIn(0f, placeable.height.toFloat())
        return layout(placeable.width, (placeable.height - hidden).roundToInt()) {
            placeable.placeWithLayer(0, 0) {
                translationY = -hidden
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

    private class Entry {
        var height = 0
        var contributes = false
        var placed = false
        var top = 0f
        var bottom = 0f
        var heightAbove = 0f

        val isCollapsed get() = bottom == top
    }

    private val entries = mutableMapOf<Any, Entry>()

    /**
     * Attached nodes sorted by their placed top edge. Ties keep their previous order, so a
     * fully collapsed component stays above the one it used to sit on, and a node that has not
     * been placed yet goes first.
     */
    private var order: List<Any> = emptyList()

    /**
     * Bumped whenever the stack a measure depends on changes (attached nodes, their heights or
     * what sits above them), so the affected measures re-run.
     */
    private var layoutVersion by mutableIntStateOf(0)

    internal fun attachNode(node: Any) {
        entries[node] = Entry()
        order = listOf(node) + order
        layoutVersion++
    }

    /**
     * The top app bar hooks are shared by every attached node, so they only go away with the
     * last one.
     */
    internal fun detachNode(node: Any) {
        if (entries.remove(node) == null) return
        order = order - node
        updateHeightOffsetLimit()
        recomputeStack()
        if (entries.isEmpty()) clearInternalHooks()
    }

    internal fun setContribution(node: Any, height: Int, contributes: Boolean) {
        val entry = entries[node] ?: return
        if (entry.height == height && entry.contributes == contributes) return
        entry.height = height
        entry.contributes = contributes
        updateHeightOffsetLimit()
        recomputeStack()
    }

    internal fun updateNodePosition(node: Any, top: Float, bottom: Float) {
        val entry = entries[node] ?: return
        if (entry.placed && entry.top == top && entry.bottom == bottom) return
        entry.placed = true
        entry.top = top
        entry.bottom = bottom
        order = order.sortedBy { entries[it]?.takeIf { entry -> entry.placed }?.top ?: Float.NEGATIVE_INFINITY }
        recomputeStack()
    }

    /**
     * Height of the collapsible content stacked above [node], which is how far the block has
     * to move before this content starts to disappear.
     */
    internal fun collapsibleHeightAbove(node: Any): Float {
        layoutVersion
        return entries[node]?.heightAbove ?: 0f
    }

    /**
     * Only contributing content defines the stack: content above is content whose top edge is
     * higher, or which is fully collapsed onto the same edge and was above before. Content
     * that only follows the block, such as a header on a neighbouring pager page, takes the
     * place of the contributing content it shares a top edge with, the lowest one in the stack
     * when collapsed content has folded onto that edge too.
     */
    private fun recomputeStack() {
        fun heightAbove(node: Any): Float {
            val entry = entries.getValue(node)
            var sum = 0
            for (other in order) {
                if (other === node) break
                val otherEntry = entries.getValue(other)
                if (!otherEntry.contributes || !otherEntry.placed) continue
                if (otherEntry.top < entry.top || otherEntry.isCollapsed) sum += otherEntry.height
            }
            return sum.toFloat()
        }

        val contributorHeights = order
            .filter { entries.getValue(it).let { entry -> entry.contributes && entry.placed } }
            .associateWith { heightAbove(it) }

        var changed = false
        for ((node, entry) in entries) {
            if (!entry.placed) continue
            val heightAbove = contributorHeights[node]
                ?: contributorHeights.entries
                    .filter { entries.getValue(it.key).top == entry.top }
                    .maxOfOrNull { it.value }
                ?: heightAbove(node)
            if (entry.heightAbove != heightAbove) {
                entry.heightAbove = heightAbove
                changed = true
            }
        }
        if (changed) layoutVersion++
    }

    /**
     * The limit follows the attached content while [heightOffset] keeps its pixel value, so
     * content that appears or disappears (a pager page becoming active, tabs hidden for
     * selection mode) moves in below or above the rest without making it jump.
     */
    private fun updateHeightOffsetLimit() {
        heightOffsetLimit = -entries.values.filter { it.contributes }.sumOf { it.height }.toFloat()
        layoutVersion++
    }

    private fun clearInternalHooks() {
        onPreScrollConsumed = null
        shouldConsumePreScroll = null
    }

    /**
     * When set, a scroll ending with the component partially collapsed settles it to the
     * nearest edge with this animation. Null disables snapping.
     */
    var snapAnimationSpec: AnimationSpec<Float>? = null

    /**
     * The limit for the height offset. Should be a negative value representing
     * the maximum distance the component can collapse. Similar to
     * Material3's TopAppBarState.heightOffsetLimit.
     *
     * Content using [applyScrollToHideBehavior] keeps this in sync with the sum of its
     * measured heights, so only set it directly when no such content is attached.
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

    /**
     * How far the collapse has progressed, from 0 (fully shown) to 1 (fully hidden).
     * Zero while nothing collapsible is attached.
     */
    val hiddenFraction: Float by derivedStateOf {
        val limit = heightOffsetLimit
        if (limit < 0f) (offset / limit).coerceIn(0f, 1f) else 0f
    }

    /**
     * How far the collapsible content has moved up, in pixels. Unlike [offset] it is not
     * clamped to [heightOffsetLimit] but to the height of everything attached, so content that
     * only follows the collapse stays hidden while the contributing content is gone.
     */
    internal val collapseDistance: Float by derivedStateOf {
        layoutVersion
        val total = entries.values.sumOf { it.height }.toFloat()
        (-heightOffset).coerceIn(0f, total)
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

            // Starting from the clamped offset keeps a stale offset beyond a shrunken limit
            // from being reported as consumed scroll.
            val prevOffset = offset
            heightOffset = (prevOffset + available.y).coerceIn(heightOffsetLimit, 0f)

            val consumedY = heightOffset - prevOffset
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
            heightOffset = (offset + consumed.y).coerceIn(heightOffsetLimit, 0f)
            return Offset.Zero
        }

        override suspend fun onPostFling(consumed: Velocity, available: Velocity): Velocity {
            snapToNearestEdge()
            return Velocity.Zero
        }
    }

    private suspend fun snapToNearestEdge() {
        val spec = snapAnimationSpec ?: return
        val limit = heightOffsetLimit
        val current = heightOffset
        if (limit >= 0f || current <= limit || current >= 0f) return
        val target = if (current < limit / 2) limit else 0f
        animate(current, target, animationSpec = spec) { value, _ ->
            heightOffset = value
        }
    }

    /**
     * Resets scroll offsets to their initial state.
     */
    fun reset() {
        heightOffset = 0f
        scrollDirectionOffset = 0f
    }

    /**
     * Animates the hidden component back to fully expanded — the animated counterpart of
     * [reset], for tap-to-reveal and back-press flows. A scroll arriving while the animation
     * runs writes [heightOffset] directly and wins; callers who need exclusivity should
     * cancel and relaunch.
     */
    suspend fun expand(animationSpec: AnimationSpec<Float> = tween(EXPAND_ANIMATION_MS)) {
        val start = heightOffset
        if (start == 0f) return
        animate(start, 0f, animationSpec = animationSpec) { value, _ ->
            heightOffset = value
        }
        scrollDirectionOffset = 0f
    }

    companion object {
        private const val EXPAND_ANIMATION_MS = 200

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
