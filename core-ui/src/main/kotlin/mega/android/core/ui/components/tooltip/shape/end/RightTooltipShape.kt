package mega.android.core.ui.components.tooltip.shape.end

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Path
import mega.android.core.ui.components.tooltip.model.TooltipPointerSizeInPx

internal fun Path.drawRightTooltipShape(
    size: Size,
    radius: Float,
    pointerSize: TooltipPointerSizeInPx
) {
    moveTo(radius, 0f)

    // Draw top line
    lineTo(size.width - radius - pointerSize.height, 0f)

    // Draw arc for top right
    arcTo(
        rect = Rect(
            offset = Offset(
                x = size.width - radius - pointerSize.height,
                y = 0f
            ),
            size = Size(radius, radius)
        ),
        startAngleDegrees = 270f,
        sweepAngleDegrees = 90f,
        forceMoveTo = false
    )

    // Draw right line - #1
    lineTo(size.width - pointerSize.height, (size.height - pointerSize.width) / 2f)

    // Draw pointer
    lineTo(size.width, size.height / 2f)
    lineTo(size.width - pointerSize.height, (size.height / 2f) + (pointerSize.width / 2f))

    // Draw right line - #2
    lineTo(size.width - pointerSize.height, size.height - radius)

    // Draw arc for bottom right
    arcTo(
        rect = Rect(
            offset = Offset(
                x = size.width - pointerSize.height - radius,
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
