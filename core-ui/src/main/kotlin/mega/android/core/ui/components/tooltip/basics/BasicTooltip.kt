package mega.android.core.ui.components.tooltip.basics

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredWidthIn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import mega.android.core.ui.components.tooltip.defaults.TooltipSizeDefaults
import mega.android.core.ui.components.tooltip.defaults.TooltipSizeDefaults.interactiveTooltipMinWidth
import mega.android.core.ui.components.tooltip.direction.TooltipDirection
import mega.android.core.ui.components.tooltip.model.MegaTooltipType
import mega.android.core.ui.components.tooltip.model.TooltipPointerSizeInPx
import mega.android.core.ui.components.tooltip.shape.MegaTooltipShape
import mega.android.core.ui.theme.AppTheme

@Composable
internal fun BasicTopDirectionTooltip(
    type: MegaTooltipType,
    direction: TooltipDirection.Top,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    BasicMegaTooltip(
        modifier = modifier,
        type = type,
        direction = direction,
        content = {
            Box(
                modifier = Modifier.padding(top = TooltipSizeDefaults.pointerHeightInDp),
            ) {
                content()
            }
        }
    )
}

@Composable
internal fun BasicBottomDirectionTooltip(
    type: MegaTooltipType,
    direction: TooltipDirection.Bottom,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    BasicMegaTooltip(
        modifier = modifier,
        type = type,
        direction = direction,
        content = {
            Box(
                modifier = Modifier.padding(bottom = TooltipSizeDefaults.pointerHeightInDp),
            ) {
                content()
            }
        }
    )
}

@Composable
internal fun BasicLeftDirectionTooltip(
    type: MegaTooltipType,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    BasicMegaTooltip(
        modifier = modifier,
        type = type,
        direction = TooltipDirection.Left,
        content = {
            Box(
                modifier = Modifier.padding(start = TooltipSizeDefaults.pointerHeightInDp),
            ) {
                content()
            }
        }
    )
}

@Composable
internal fun BasicRightDirectionTooltip(
    type: MegaTooltipType,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    BasicMegaTooltip(
        modifier = modifier,
        type = type,
        direction = TooltipDirection.Right,
        content = {
            Box(
                modifier = Modifier.padding(end = TooltipSizeDefaults.pointerHeightInDp),
            ) {
                content()
            }
        }
    )
}

@Composable
internal fun BasicNoneTooltip(
    type: MegaTooltipType,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    BasicMegaTooltip(
        modifier = modifier,
        type = type,
        direction = TooltipDirection.None,
        content = content
    )
}

@Composable
private fun BasicMegaTooltip(
    type: MegaTooltipType,
    direction: TooltipDirection,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    val radiusInPx = with(LocalDensity.current) {
        TooltipSizeDefaults.radiusInDp.toPx()
    }
    val pointerWidthInPx = with(LocalDensity.current) {
        TooltipSizeDefaults.pointerWidthInDp.toPx()
    }
    val pointerHeightInPx = with(LocalDensity.current) {
        TooltipSizeDefaults.pointerHeightInDp.toPx()
    }
    val pointerSize = TooltipPointerSizeInPx(
        width = pointerWidthInPx,
        height = pointerHeightInPx
    )

    Box(
        modifier = if (type == MegaTooltipType.Interactive) {
            Modifier.requiredWidthIn(min = interactiveTooltipMinWidth) then modifier
        } else {
            modifier
        }
            .background(
                color = AppTheme.colors.background.inverse,
                shape = MegaTooltipShape(
                    radius = radiusInPx,
                    pointerSize = pointerSize,
                    direction = direction
                )
            )
    ) {
        content()
    }
}

@Preview
@Composable
private fun BasicTopTooltipPreview(
    @PreviewParameter(BasicTopTooltipPreviewParameterProvider::class) direction: TooltipDirection.Top
) {
    BasicTopDirectionTooltip(
        type = MegaTooltipType.Simple,
        direction = direction
    ) {
        Text(
            text = "Titlen ajsndjand unjk as sdfkj sd fjsdf sdkj sdl " +
                    "k sjkdf sdjk dunajskd jioasd  kja sdkjn jknjkasdouj akdsu kn asdjnaskjd sa"
        )
    }
}

private class BasicTopTooltipPreviewParameterProvider :
    PreviewParameterProvider<TooltipDirection.Top> {
    override val values = sequenceOf(
        TooltipDirection.Top.Left,
        TooltipDirection.Top.Centre,
        TooltipDirection.Top.Right
    )
}

@Preview
@Composable
private fun BasicBottomTooltipPreview(
    @PreviewParameter(BasicBottomTooltipPreviewParameterProvider::class) direction: TooltipDirection.Bottom
) {
    BasicBottomDirectionTooltip(
        type = MegaTooltipType.Simple,
        direction = direction
    ) {
        Text(
            text = "Titlen ajsndjand unjk as sdfkj sd fjsdf sdkj sdl " +
                    "k sjkdf sdjk dunajskd jioasd  kja sdkjn jknjkasdouj akdsu kn asdjnaskjd sa"
        )
    }
}

private class BasicBottomTooltipPreviewParameterProvider :
    PreviewParameterProvider<TooltipDirection.Bottom> {
    override val values = sequenceOf(
        TooltipDirection.Bottom.Left,
        TooltipDirection.Bottom.Centre,
        TooltipDirection.Bottom.Right
    )
}

@Preview
@Composable
private fun BasicLeftTooltipPreview() {
    BasicLeftDirectionTooltip(type = MegaTooltipType.Simple) {
        Text(
            text = "Titlen ajsndjand unjk as sdfkj sd fjsdf sdkj sdl " +
                    "k sjkdf sdjk dunajskd jioasd  kja sdkjn jknjkasdouj akdsu kn asdjnaskjd sa"
        )
    }
}

@Preview
@Composable
private fun BasicRightTooltipPreview() {
    BasicRightDirectionTooltip(type = MegaTooltipType.Simple) {
        Text(
            text = "Titlen ajsndjand unjk as sdfkj sd fjsdf sdkj sdl " +
                    "k sjkdf sdjk dunajskd jioasd  kja sdkjn jknjkasdouj akdsu kn asdjnaskjd sa"
        )
    }
}

@Preview
@Composable
private fun BasicNoneTooltipPreview() {
    BasicNoneTooltip(type = MegaTooltipType.Simple) {
        Text(
            text = "Titlen ajsndjand unjk as sdfkj sd fjsdf sdkj sdl " +
                    "k sjkdf sdjk dunajskd jioasd  kja sdkjn jknjkasdouj akdsu kn asdjnaskjd sa"
        )
    }
}
