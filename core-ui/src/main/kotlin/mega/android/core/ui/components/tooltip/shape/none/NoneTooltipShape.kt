package mega.android.core.ui.components.tooltip.shape.none

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Path

internal fun Path.drawNoneTooltipShape(size: Size, radius: Float) {
    moveTo(radius, 0f)

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
    lineTo(radius, size.height)

    // Draw arc for bottom left
    arcTo(
        rect = Rect(
            offset = Offset(
                x = 0f,
                y = size.height - radius
            ),
            size = Size(radius, radius)
        ),
        startAngleDegrees = 90f,
        sweepAngleDegrees = 90f,
        forceMoveTo = false
    )

    // Draw left line
    lineTo(0f, size.height - radius)

    // Draw arc for top left
    arcTo(
        rect = Rect(
            offset = Offset(
                x = 0f,
                y = 0f
            ),
            size = Size(radius, radius)
        ),
        startAngleDegrees = 180f,
        sweepAngleDegrees = 90f,
        forceMoveTo = false
    )
}
