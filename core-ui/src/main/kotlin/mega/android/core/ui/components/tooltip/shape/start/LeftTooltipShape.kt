package mega.android.core.ui.components.tooltip.shape.start

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Path
import mega.android.core.ui.components.tooltip.model.TooltipPointerSizeInPx

internal fun Path.drawLeftTooltipShape(
    size: Size,
    radius: Float,
    pointerSize: TooltipPointerSizeInPx
) {
    val startXCoordinate = radius + pointerSize.height
    moveTo(startXCoordinate, 0f)

    // Draw top line
    lineTo(size.width - radius, 0f)

    // Draw arc for top right
    arcTo(
        rect = Rect(
            offset = Offset(
                x = size.width - radius,
                y = 0f
            ),
            size = Size(radius, radius)
        ),
        startAngleDegrees = 270f,
        sweepAngleDegrees = 90f,
        forceMoveTo = false
    )

    // Draw right line
    lineTo(size.width, size.height - radius)

    // Draw arc for bottom right
    arcTo(
        rect = Rect(
            offset = Offset(
                x = size.width - radius,
                y = size.height - radius
            ),
            size = Size(radius, radius)
        ),
        startAngleDegrees = 0f,
        sweepAngleDegrees = 90f,
        forceMoveTo = false
    )

    // Draw bottom line
    lineTo(startXCoordinate, size.height)

    // Draw arc for bottom left
    arcTo(
        rect = Rect(
            offset = Offset(
                x = pointerSize.height,
                y = size.height - radius
            ),
            size = Size(radius, radius)
        ),
        startAngleDegrees = 90f,
        sweepAngleDegrees = 90f,
        forceMoveTo = false
    )

    // Draw left line - #1
    lineTo(pointerSize.height, (size.height / 2f) + (pointerSize.width / 2f))

    // Draw pointer
    lineTo(0f, size.height / 2f)
    lineTo(pointerSize.height, (size.height / 2f) - (pointerSize.width / 2f))

    // Draw left line - #2
    lineTo(pointerSize.height, radius)

    // Draw arc for top left
    arcTo(
        rect = Rect(
            offset = Offset(
                x = pointerSize.height,
                y = 0f
            ),
            size = Size(radius, radius)
        ),
        startAngleDegrees = 180f,
        sweepAngleDegrees = 90f,
        forceMoveTo = false
    )
}
