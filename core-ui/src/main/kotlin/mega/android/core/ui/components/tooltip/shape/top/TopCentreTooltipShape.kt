package mega.android.core.ui.components.tooltip.shape.top

import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Path
import mega.android.core.ui.components.tooltip.model.TooltipPointerSizeInPx

internal fun Path.drawTopCentreTooltipShape(
    size: Size,
    radius: Float,
    pointerSize: TooltipPointerSizeInPx
) {
    moveTo(radius, pointerSize.height)

    // Draw top line - #1
    lineTo((size.width / 2f) - (pointerSize.width / 2f), pointerSize.height)

    // Draw pointer
    lineTo(size.width / 2f, 0f)
    lineTo((size.width / 2f) + (pointerSize.width / 2f), pointerSize.height)

    // Draw top line - #2
    lineTo(size.width - radius, pointerSize.height)

    // Draw remaining box
    drawRemainingTopDirectionBox(
        size = size,
        radius = radius,
        pointerSize = pointerSize
    )
}
