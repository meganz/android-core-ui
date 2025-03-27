package mega.android.core.ui.components.tooltip.model

import androidx.compose.runtime.Composable
import androidx.compose.ui.layout.LayoutCoordinates
import androidx.compose.ui.window.PopupPositionProvider
import mega.android.core.ui.components.tooltip.defaults.MegaTooltipDefaults.rememberBottomTooltipPositionProvider
import mega.android.core.ui.components.tooltip.defaults.MegaTooltipDefaults.rememberLeftTooltipPositionProvider
import mega.android.core.ui.components.tooltip.defaults.MegaTooltipDefaults.rememberRightTooltipPositionProvider
import mega.android.core.ui.components.tooltip.defaults.MegaTooltipDefaults.rememberTopTooltipPositionProvider
import mega.android.core.ui.components.tooltip.direction.TooltipDirection

enum class NoneTooltipPosition {
    TopLeft,
    TopCentre,
    TopRight,

    BottomLeft,
    BottomCentre,
    BottomRight,

    Left,
    Right
}

@Composable
internal fun getNoneTooltipPopupPositionProvider(
    position: NoneTooltipPosition,
    anchorViewCoordinates: LayoutCoordinates
): PopupPositionProvider {
    val positionProvider = when (position) {
        NoneTooltipPosition.TopLeft -> rememberBottomTooltipPositionProvider(
            direction = TooltipDirection.Bottom.Left,
            anchorViewCoordinates = anchorViewCoordinates
        )

        NoneTooltipPosition.TopCentre -> rememberBottomTooltipPositionProvider(
            direction = TooltipDirection.Bottom.Centre,
            anchorViewCoordinates = anchorViewCoordinates
        )

        NoneTooltipPosition.TopRight -> rememberBottomTooltipPositionProvider(
            direction = TooltipDirection.Bottom.Right,
            anchorViewCoordinates = anchorViewCoordinates
        )

        NoneTooltipPosition.BottomLeft -> rememberTopTooltipPositionProvider(
            direction = TooltipDirection.Top.Left,
            anchorViewCoordinates = anchorViewCoordinates
        )

        NoneTooltipPosition.BottomCentre -> rememberTopTooltipPositionProvider(
            direction = TooltipDirection.Top.Centre,
            anchorViewCoordinates = anchorViewCoordinates
        )

        NoneTooltipPosition.BottomRight -> rememberTopTooltipPositionProvider(
            direction = TooltipDirection.Top.Right,
            anchorViewCoordinates = anchorViewCoordinates
        )

        NoneTooltipPosition.Left -> rememberRightTooltipPositionProvider(
            anchorViewCoordinates = anchorViewCoordinates
        )

        NoneTooltipPosition.Right -> rememberLeftTooltipPositionProvider(
            anchorViewCoordinates = anchorViewCoordinates
        )
    }
    return positionProvider
}
