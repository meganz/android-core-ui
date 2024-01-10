package mega.android.core.ui.components.util

import android.graphics.BlurMaskFilter
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * Adds a blur shadow outline to any shape
 * @param color Color of the shadow
 * @param modifier Modifier to apply to the shadow
 * @param cornerRadius Corner radius of the shape
 * @param blurRadius the radius of the blur to apply to the shadow
 * @param offsetX the horizontal offset of the shadow. This can be useful for elevation purposes
 * @param offsetY the vertical offset of the shadow. This can be useful for elevation purposes
 */
fun Modifier.blurShadow(
    color: Color,
    modifier: Modifier = Modifier,
    cornerRadius: Dp = 0.dp,
    blurRadius: Dp = 0.dp,
    offsetX: Dp = 0.dp,
    offsetY: Dp = 0.dp,
) = this.then(
    modifier.drawBehind {
        this.drawIntoCanvas {
            val paint = Paint().also { paint ->
                paint.asFrameworkPaint().apply {
                    this.color = color.toArgb()
                    if (blurRadius > 0.dp) {
                        maskFilter =
                            (BlurMaskFilter(blurRadius.toPx(), BlurMaskFilter.Blur.NORMAL))
                    }
                }
            }

            it.drawRoundRect(
                left = -offsetX.toPx(),
                top = offsetY.toPx(),
                right = this.size.width + offsetX.toPx(),
                bottom = this.size.height + offsetY.toPx(),
                radiusX = cornerRadius.toPx(),
                radiusY = cornerRadius.toPx(),
                paint
            )
        }
    }
)