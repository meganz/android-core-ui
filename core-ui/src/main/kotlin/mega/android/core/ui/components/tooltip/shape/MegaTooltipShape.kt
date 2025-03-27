package mega.android.core.ui.components.tooltip.shape

import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import mega.android.core.ui.components.tooltip.direction.TooltipDirection
import mega.android.core.ui.components.tooltip.model.TooltipPointerSizeInPx
import mega.android.core.ui.components.tooltip.shape.bottom.drawBottomCentreTooltipShape
import mega.android.core.ui.components.tooltip.shape.bottom.drawBottomLeftTooltipShape
import mega.android.core.ui.components.tooltip.shape.bottom.drawBottomRightTooltipShape
import mega.android.core.ui.components.tooltip.shape.end.drawRightTooltipShape
import mega.android.core.ui.components.tooltip.shape.none.drawNoneTooltipShape
import mega.android.core.ui.components.tooltip.shape.start.drawLeftTooltipShape
import mega.android.core.ui.components.tooltip.shape.top.drawTopCentreTooltipShape
import mega.android.core.ui.components.tooltip.shape.top.drawTopLeftTooltipShape
import mega.android.core.ui.components.tooltip.shape.top.drawTopRightTooltipShape

class MegaTooltipShape(
    private val radius: Float,
    private val pointerSize: TooltipPointerSizeInPx,
    private val direction: TooltipDirection
) : Shape {

    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        val path = Path().apply {
            reset()

            when (direction) {
                TooltipDirection.Top.Left -> drawTopLeftTooltipShape(
                    size = size,
                    radius = radius,
                    pointerSize = pointerSize
                )

                TooltipDirection.Top.Centre -> drawTopCentreTooltipShape(
                    size = size,
                    radius = radius,
                    pointerSize = pointerSize
                )

                TooltipDirection.Top.Right -> drawTopRightTooltipShape(
                    size = size,
                    radius = radius,
                    pointerSize = pointerSize
                )

                TooltipDirection.Bottom.Left -> drawBottomLeftTooltipShape(
                    size = size,
                    radius = radius,
                    pointerSize = pointerSize
                )

                TooltipDirection.Bottom.Centre -> drawBottomCentreTooltipShape(
                    size = size,
                    radius = radius,
                    pointerSize = pointerSize
                )

                TooltipDirection.Bottom.Right -> drawBottomRightTooltipShape(
                    size = size,
                    radius = radius,
                    pointerSize = pointerSize
                )

                TooltipDirection.Left -> drawLeftTooltipShape(
                    size = size,
                    radius = radius,
                    pointerSize = pointerSize
                )

                TooltipDirection.Right -> drawRightTooltipShape(
                    size = size,
                    radius = radius,
                    pointerSize = pointerSize
                )

                TooltipDirection.None -> drawNoneTooltipShape(
                    size = size,
                    radius = radius,
                )
            }

            close()
        }
        return Outline.Generic(path)
    }
}
