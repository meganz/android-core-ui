package mega.android.core.ui.components.tooltip.defaults

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.layout.LayoutCoordinates
import androidx.compose.ui.layout.positionInRoot
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntRect
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.window.PopupPositionProvider
import mega.android.core.ui.components.tooltip.direction.TooltipDirection

object MegaTooltipDefaults {

    @Composable
    fun rememberLeftTooltipPositionProvider(anchorViewCoordinates: LayoutCoordinates): PopupPositionProvider {
        val tooltipAnchorSpacing =
            with(LocalDensity.current) { TooltipSizeDefaults.SpacingBetweenTooltipAndAnchor.roundToPx() }
        return remember(anchorViewCoordinates.positionInRoot()) {
            object : PopupPositionProvider {
                override fun calculatePosition(
                    anchorBounds: IntRect,
                    windowSize: IntSize,
                    layoutDirection: LayoutDirection,
                    popupContentSize: IntSize
                ): IntOffset {
                    val anchorViewCoordinatesInRoot = anchorViewCoordinates.positionInRoot()
                    val x =
                        anchorViewCoordinatesInRoot.x.toInt() + anchorViewCoordinates.size.width + tooltipAnchorSpacing
                    val anchorViewCoordinatesHalfHeight = anchorViewCoordinates.size.height / 2
                    val popupContentHalfHeight = popupContentSize.height / 2
                    val y =
                        anchorViewCoordinatesInRoot.y.toInt() + anchorViewCoordinatesHalfHeight - popupContentHalfHeight
                    return IntOffset(x, y)
                }
            }
        }
    }

    @Composable
    fun rememberTopTooltipPositionProvider(
        direction: TooltipDirection.Top,
        anchorViewCoordinates: LayoutCoordinates
    ): PopupPositionProvider {
        val tooltipAnchorSpacing =
            with(LocalDensity.current) { TooltipSizeDefaults.SpacingBetweenTooltipAndAnchor.roundToPx() }
        return remember(anchorViewCoordinates.positionInRoot()) {
            object : PopupPositionProvider {
                override fun calculatePosition(
                    anchorBounds: IntRect,
                    windowSize: IntSize,
                    layoutDirection: LayoutDirection,
                    popupContentSize: IntSize
                ): IntOffset {
                    var x = 0
                    var y = 0
                    val anchorViewCoordinatesInRoot = anchorViewCoordinates.positionInRoot()
                    when (direction) {
                        TooltipDirection.Top.Left -> {
                            x = anchorViewCoordinatesInRoot.x.toInt()
                            y =
                                anchorViewCoordinatesInRoot.y.toInt() + anchorViewCoordinates.size.height + tooltipAnchorSpacing
                        }

                        TooltipDirection.Top.Centre -> {
                            val anchorViewCoordinatesHalfWidth =
                                anchorViewCoordinates.size.width / 2
                            val popupContentHalfWidth = popupContentSize.width / 2
                            x =
                                anchorViewCoordinatesInRoot.x.toInt() + anchorViewCoordinatesHalfWidth - popupContentHalfWidth
                            y =
                                anchorViewCoordinatesInRoot.y.toInt() + anchorViewCoordinates.size.height + tooltipAnchorSpacing
                        }

                        TooltipDirection.Top.Right -> {
                            x =
                                anchorViewCoordinatesInRoot.x.toInt() - (popupContentSize.width - anchorViewCoordinates.size.width)
                            y =
                                anchorViewCoordinatesInRoot.y.toInt() + anchorViewCoordinates.size.height + tooltipAnchorSpacing
                        }
                    }
                    return IntOffset(x, y)
                }
            }
        }
    }

    @Composable
    fun rememberRightTooltipPositionProvider(anchorViewCoordinates: LayoutCoordinates): PopupPositionProvider {
        val tooltipAnchorSpacing =
            with(LocalDensity.current) { TooltipSizeDefaults.SpacingBetweenTooltipAndAnchor.roundToPx() }
        return remember(anchorViewCoordinates.positionInRoot()) {
            object : PopupPositionProvider {
                override fun calculatePosition(
                    anchorBounds: IntRect,
                    windowSize: IntSize,
                    layoutDirection: LayoutDirection,
                    popupContentSize: IntSize
                ): IntOffset {
                    val anchorViewCoordinatesInRoot = anchorViewCoordinates.positionInRoot()
                    val x =
                        anchorViewCoordinatesInRoot.x.toInt() - popupContentSize.width - tooltipAnchorSpacing
                    val anchorViewCoordinatesHalfHeight = anchorViewCoordinates.size.height / 2
                    val popupContentHalfHeight = popupContentSize.height / 2
                    val y =
                        anchorViewCoordinatesInRoot.y.toInt() + anchorViewCoordinatesHalfHeight - popupContentHalfHeight
                    return IntOffset(x, y)
                }
            }
        }
    }

    @Composable
    fun rememberBottomTooltipPositionProvider(
        direction: TooltipDirection.Bottom,
        anchorViewCoordinates: LayoutCoordinates
    ): PopupPositionProvider {
        val tooltipAnchorSpacing =
            with(LocalDensity.current) { TooltipSizeDefaults.SpacingBetweenTooltipAndAnchor.roundToPx() }
        return remember(anchorViewCoordinates.positionInRoot()) {
            object : PopupPositionProvider {
                override fun calculatePosition(
                    anchorBounds: IntRect,
                    windowSize: IntSize,
                    layoutDirection: LayoutDirection,
                    popupContentSize: IntSize
                ): IntOffset {
                    var x = 0
                    var y = 0
                    val anchorViewCoordinatesInRoot = anchorViewCoordinates.positionInRoot()
                    when (direction) {
                        TooltipDirection.Bottom.Left -> {
                            x = anchorViewCoordinatesInRoot.x.toInt()
                            y =
                                anchorViewCoordinatesInRoot.y.toInt() - popupContentSize.height - tooltipAnchorSpacing
                        }

                        TooltipDirection.Bottom.Centre -> {
                            val anchorViewCoordinatesHalfWidth =
                                anchorViewCoordinates.size.width / 2
                            val popupContentHalfWidth = popupContentSize.width / 2
                            x =
                                anchorViewCoordinatesInRoot.x.toInt() + anchorViewCoordinatesHalfWidth - popupContentHalfWidth
                            y =
                                anchorViewCoordinatesInRoot.y.toInt() - popupContentSize.height - tooltipAnchorSpacing
                        }

                        TooltipDirection.Bottom.Right -> {
                            x =
                                anchorViewCoordinatesInRoot.x.toInt() - (popupContentSize.width - anchorViewCoordinates.size.width)
                            y =
                                anchorViewCoordinatesInRoot.y.toInt() - popupContentSize.height - tooltipAnchorSpacing
                        }
                    }
                    return IntOffset(x, y)
                }
            }
        }
    }
}
