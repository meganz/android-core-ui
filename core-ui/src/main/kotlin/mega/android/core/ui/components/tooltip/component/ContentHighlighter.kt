package mega.android.core.ui.components.tooltip.component

import androidx.compose.animation.core.animateOffsetAsState
import androidx.compose.animation.core.animateSizeAsState
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.RoundRect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.ClipOp
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.clipPath
import androidx.compose.ui.layout.LayoutCoordinates
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInRoot
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import mega.android.core.ui.theme.AppTheme
import mega.android.core.ui.theme.spacing.LocalSpacing

@Immutable
data class ContentHighlighterOffsetPadding(
    val start: Dp = 0.dp,
    val top: Dp = 0.dp
)

@Composable
fun ContentHighlighter(
    anchorViewCoordinates: LayoutCoordinates,
    modifier: Modifier = Modifier,
    offsetPadding: ContentHighlighterOffsetPadding = ContentHighlighterOffsetPadding(),
    cornerRadius: Dp = LocalSpacing.current.x8,
    needAnimation: Boolean = true
) {
    val configuration = LocalConfiguration.current
    val density = LocalDensity.current
    val isDarkTheme = isSystemInDarkTheme()
    var currentSize by remember {
        mutableStateOf(
            Size(
                width = with(density) {
                    if (needAnimation) {
                        configuration.screenWidthDp.dp.toPx()
                    } else {
                        anchorViewCoordinates.size.width.toFloat()
                    }
                },
                height = with(density) {
                    if (needAnimation) {
                        configuration.screenHeightDp.dp.toPx()
                    } else {
                        anchorViewCoordinates.size.height.toFloat()
                    }
                }
            )
        )
    }
    val animatedSize by animateSizeAsState(targetValue = currentSize)
    var currentOffset by remember(anchorViewCoordinates.positionInRoot()) {
        mutableStateOf(
            if (needAnimation) {
                Offset.Zero
            } else {
                val anchorViewPosition = anchorViewCoordinates.positionInRoot()
                val startPadding = with(density) { offsetPadding.start.toPx() }
                val topPadding = with(density) { offsetPadding.top.toPx() }
                Offset(
                    x = anchorViewPosition.x + startPadding,
                    y = anchorViewPosition.y + topPadding
                )
            }
        )
    }
    val animatedOffset by animateOffsetAsState(targetValue = currentOffset)
    val overlayColor = AppTheme.colors.background.blur

    Canvas(
        modifier = modifier.onGloballyPositioned {
            if (needAnimation) {
                val anchorViewPosition = anchorViewCoordinates.positionInRoot()
                val startPadding = with(density) { offsetPadding.start.toPx() }
                val topPadding = with(density) { offsetPadding.top.toPx() }
                currentSize = Size(
                    width = anchorViewCoordinates.size.width.toFloat(),
                    height = anchorViewCoordinates.size.height.toFloat()
                )
                currentOffset = Offset(
                    x = anchorViewPosition.x + startPadding,
                    y = anchorViewPosition.y + topPadding
                )
            }
        }
    ) {
        val highlightPath = Path().apply {
            val cornerRadiusInPx = with(density) { cornerRadius.toPx() }
            addRoundRect(
                roundRect = RoundRect(
                    rect = Rect(
                        offset = animatedOffset,
                        size = animatedSize
                    ),
                    cornerRadius = CornerRadius(
                        x = cornerRadiusInPx,
                        y = cornerRadiusInPx
                    )
                )
            )
        }

        clipPath(
            path = highlightPath,
            clipOp = ClipOp.Difference
        ) {
            drawRect(
                color = overlayColor.copy(
                    alpha = if (isDarkTheme) 0.5F else 0.2F
                )
            )
        }
    }
}
