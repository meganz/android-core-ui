package mega.android.core.ui.components.tooltip.popup.simple

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.layout.LayoutCoordinates
import androidx.compose.ui.window.PopupPositionProvider
import androidx.compose.ui.window.PopupProperties
import mega.android.core.ui.components.tooltip.basics.BasicBottomDirectionTooltip
import mega.android.core.ui.components.tooltip.basics.BasicLeftDirectionTooltip
import mega.android.core.ui.components.tooltip.basics.BasicNoneTooltip
import mega.android.core.ui.components.tooltip.basics.BasicRightDirectionTooltip
import mega.android.core.ui.components.tooltip.basics.BasicTopDirectionTooltip
import mega.android.core.ui.components.tooltip.defaults.MegaTooltipDefaults.rememberBottomTooltipPositionProvider
import mega.android.core.ui.components.tooltip.defaults.MegaTooltipDefaults.rememberLeftTooltipPositionProvider
import mega.android.core.ui.components.tooltip.defaults.MegaTooltipDefaults.rememberRightTooltipPositionProvider
import mega.android.core.ui.components.tooltip.defaults.MegaTooltipDefaults.rememberTopTooltipPositionProvider
import mega.android.core.ui.components.tooltip.direction.TooltipDirection
import mega.android.core.ui.components.tooltip.model.MegaTooltipType
import mega.android.core.ui.components.tooltip.model.NoneTooltipPosition
import mega.android.core.ui.components.tooltip.model.getNoneTooltipPopupPositionProvider
import mega.android.core.ui.components.tooltip.popup.BasicTooltipPopup
import mega.android.core.ui.components.tooltip.state.rememberMegaTooltipState
import kotlin.time.Duration.Companion.seconds

@Composable
fun SimpleLeftDirectionTooltipPopup(
    anchorViewCoordinates: LayoutCoordinates,
    content: SimpleTooltipContent,
    onDismissRequest: (() -> Unit)? = null
) {
    BasicSimpleTooltipPopup(
        popupPositionProvider = rememberLeftTooltipPositionProvider(
            anchorViewCoordinates = anchorViewCoordinates
        ),
        onDismissRequest = onDismissRequest
    ) {
        BasicLeftDirectionTooltip(
            type = MegaTooltipType.Simple,
            content = { content.Content() }
        )
    }
}

@Composable
fun SimpleTopDirectionTooltipPopup(
    direction: TooltipDirection.Top,
    anchorViewCoordinates: LayoutCoordinates,
    content: SimpleTooltipContent,
    onDismissRequest: (() -> Unit)? = null
) {
    BasicSimpleTooltipPopup(
        popupPositionProvider = rememberTopTooltipPositionProvider(
            direction = direction,
            anchorViewCoordinates = anchorViewCoordinates
        ),
        onDismissRequest = onDismissRequest
    ) {
        BasicTopDirectionTooltip(
            type = MegaTooltipType.Simple,
            direction = direction,
            content = { content.Content() }
        )
    }
}

@Composable
fun SimpleRightDirectionTooltipPopup(
    anchorViewCoordinates: LayoutCoordinates,
    content: SimpleTooltipContent,
    onDismissRequest: (() -> Unit)? = null
) {
    BasicSimpleTooltipPopup(
        popupPositionProvider = rememberRightTooltipPositionProvider(
            anchorViewCoordinates = anchorViewCoordinates
        ),
        onDismissRequest = onDismissRequest
    ) {
        BasicRightDirectionTooltip(
            type = MegaTooltipType.Simple,
            content = { content.Content() }
        )
    }
}

@Composable
fun SimpleBottomDirectionTooltipPopup(
    direction: TooltipDirection.Bottom,
    anchorViewCoordinates: LayoutCoordinates,
    content: SimpleTooltipContent,
    onDismissRequest: (() -> Unit)? = null
) {
    BasicSimpleTooltipPopup(
        popupPositionProvider = rememberBottomTooltipPositionProvider(
            direction = direction,
            anchorViewCoordinates = anchorViewCoordinates
        ),
        onDismissRequest = onDismissRequest
    ) {
        BasicBottomDirectionTooltip(
            type = MegaTooltipType.Simple,
            direction = direction,
            content = { content.Content() }
        )
    }
}

@Composable
fun SimpleNoneTooltipPopup(
    position: NoneTooltipPosition,
    anchorViewCoordinates: LayoutCoordinates,
    content: SimpleTooltipContent,
    onDismissRequest: (() -> Unit)? = null
) {
    val positionProvider = getNoneTooltipPopupPositionProvider(
        position = position,
        anchorViewCoordinates = anchorViewCoordinates
    )
    BasicSimpleTooltipPopup(
        popupPositionProvider = positionProvider,
        onDismissRequest = onDismissRequest
    ) {
        BasicNoneTooltip(
            type = MegaTooltipType.Simple,
            content = { content.Content() }
        )
    }
}

@Composable
private fun BasicSimpleTooltipPopup(
    popupPositionProvider: PopupPositionProvider,
    onDismissRequest: (() -> Unit)? = null,
    content: @Composable () -> Unit
) {
    val tooltipState = rememberMegaTooltipState()

    LaunchedEffect(tooltipState) {
        tooltipState.setDismissDuration(6.seconds)
    }

    BasicTooltipPopup(
        tooltipState = tooltipState,
        popupPositionProvider = popupPositionProvider,
        properties = PopupProperties(
            dismissOnBackPress = false,
            dismissOnClickOutside = false
        ),
        onDismissRequest = onDismissRequest
    ) {
        content()
    }
}
