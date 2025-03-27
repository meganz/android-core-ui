package mega.android.core.ui.components.tooltip.shape.top

import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Path
import mega.android.core.ui.components.tooltip.model.TooltipPointerSizeInPx

internal fun Path.drawTopLeftTooltipShape(
    size: Size,
    radius: Float,
    pointerSize: TooltipPointerSizeInPx
) {
    moveTo(radius, pointerSize.height)

    // Draw pointer
    lineTo(radius + (pointerSize.width / 2f), 0f)
    lineTo(radius + pointerSize.width, pointerSize.height)

    // Draw top line
    lineTo(size.width - radius, pointerSize.height)

    // Draw remaining box
    drawRemainingTopDirectionBox(
        size = size,
        radius = radius,
        pointerSize = pointerSize
    )
}
