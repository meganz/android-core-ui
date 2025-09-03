package mega.android.core.ui.components.dialogs.internal

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.runtime.Composable
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.Placeable
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.fastForEach
import androidx.compose.ui.util.fastForEachIndexed
import kotlin.math.max

/**
 * Simple clone of FlowRow that arranges its children in a horizontal flow with limited
 * customization.
 */
@Composable
internal fun MegaBasicDialogFlowRow(content: @Composable () -> Unit) {
    Layout(content) { measurables, constraints ->
        val sequences = mutableListOf<List<Placeable>>()
        val crossAxisSizes = mutableListOf<Int>()
        val crossAxisPositions = mutableListOf<Int>()

        var mainAxisSpace = 0
        var crossAxisSpace = 0

        val currentSequence = mutableListOf<Placeable>()
        var currentMainAxisSize = 0
        var currentCrossAxisSize = 0

        // Return whether the placeable can be added to the current sequence.
        fun canAddToCurrentSequence(placeable: Placeable) =
            currentSequence.isEmpty() ||
                    currentMainAxisSize + ButtonsMainAxisSpacing.roundToPx() + placeable.width <=
                    constraints.maxWidth

        // Store current sequence information and start a new sequence.
        fun startNewSequence() {
            if (sequences.isNotEmpty()) {
                crossAxisSpace += ButtonsCrossAxisSpacing.roundToPx()
            }
            // Ensures that confirming actions appear above dismissive actions.
            @Suppress("ListIterator") sequences.add(0, currentSequence.toList())
            crossAxisSizes += currentCrossAxisSize
            crossAxisPositions += crossAxisSpace

            crossAxisSpace += currentCrossAxisSize
            mainAxisSpace = max(mainAxisSpace, currentMainAxisSize)

            currentSequence.clear()
            currentMainAxisSize = 0
            currentCrossAxisSize = 0
        }

        measurables.fastForEach { measurable ->
            // Ask the child for its preferred size.
            val placeable = measurable.measure(constraints)

            // Start a new sequence if there is not enough space.
            if (!canAddToCurrentSequence(placeable)) startNewSequence()

            // Add the child to the current sequence.
            if (currentSequence.isNotEmpty()) {
                currentMainAxisSize += ButtonsMainAxisSpacing.roundToPx()
            }
            currentSequence.add(placeable)
            currentMainAxisSize += placeable.width
            currentCrossAxisSize = max(currentCrossAxisSize, placeable.height)
        }

        if (currentSequence.isNotEmpty()) startNewSequence()

        val mainAxisLayoutSize = max(mainAxisSpace, constraints.minWidth)

        val crossAxisLayoutSize = max(crossAxisSpace, constraints.minHeight)

        layout(mainAxisLayoutSize, crossAxisLayoutSize) {
            sequences.fastForEachIndexed { i, placeables ->
                val childrenMainAxisSizes =
                    IntArray(placeables.size) { j ->
                        placeables[j].width +
                                if (j < placeables.lastIndex) ButtonsMainAxisSpacing.roundToPx() else 0
                    }
                val arrangement = Arrangement.End
                val mainAxisPositions = IntArray(childrenMainAxisSizes.size) { 0 }
                with(arrangement) {
                    arrange(
                        mainAxisLayoutSize,
                        childrenMainAxisSizes,
                        layoutDirection,
                        mainAxisPositions
                    )
                }
                placeables.fastForEachIndexed { j, placeable ->
                    placeable.place(x = mainAxisPositions[j], y = crossAxisPositions[i])
                }
            }
        }
    }
}

private val ButtonsMainAxisSpacing = 8.dp
private val ButtonsCrossAxisSpacing = 12.dp
