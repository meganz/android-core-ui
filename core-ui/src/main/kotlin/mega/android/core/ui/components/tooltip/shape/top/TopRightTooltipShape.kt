package mega.android.core.ui.components.tooltip.shape.top

import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Path
import mega.android.core.ui.components.tooltip.model.TooltipPointerSizeInPx

internal fun Path.drawTopRightTooltipShape(
    size: Size,
    radius: Float,
    pointerSize: TooltipPointerSizeInPx
) {
    moveTo(radius, pointerSize.height)

    // Draw top line
    val topLineWidth = size.width - radius - pointerSize.width
    lineTo(topLineWidth, pointerSize.height)

    // Draw pointer
    lineTo(topLineWidth + pointerSize.width / 2f, 0f)
    lineTo(size.width - radius, pointerSize.height)

    // Draw remaining box
    drawRemainingTopDirectionBox(
        size = size,
        radius = radius,
        pointerSize = pointerSize
    )
}
