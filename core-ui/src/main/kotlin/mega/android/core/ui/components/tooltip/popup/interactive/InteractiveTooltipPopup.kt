package mega.android.core.ui.components.tooltip.popup.interactive

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.LayoutCoordinates
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
import mega.android.core.ui.components.tooltip.state.MegaTooltipState
import mega.android.core.ui.components.tooltip.state.rememberMegaTooltipState

@Composable
fun InteractiveLeftDirectionTooltipPopup(
    title: String,
    anchorViewCoordinates: LayoutCoordinates,
    modifier: Modifier = Modifier,
    properties: PopupProperties = PopupProperties(),
    tooltipState: MegaTooltipState = rememberMegaTooltipState(),
    onDismissRequest: (() -> Unit)? = null,
    body: String? = null,
    primaryButton: InteractiveTooltipButtonProperties? = null,
    secondaryButton: InteractiveTooltipButtonProperties? = null,
    stepCounter: InteractiveTooltipStepCounterProperties? = null,
    needCloseIcon: Boolean = false,
    needDivider: Boolean = false
) {
    BasicTooltipPopup(
        properties = properties,
        tooltipState = tooltipState,
        popupPositionProvider = rememberLeftTooltipPositionProvider(
            anchorViewCoordinates = anchorViewCoordinates
        ),
        onDismissRequest = onDismissRequest
    ) {
        BasicLeftDirectionTooltip(
            modifier = modifier,
            type = MegaTooltipType.Interactive,
            content = {
                InteractiveTooltipContent(
                    title = title,
                    body = body,
                    primaryButton = primaryButton,
                    secondaryButton = secondaryButton,
                    stepCounter = stepCounter,
                    needCloseIcon = needCloseIcon,
                    onCloseClick = tooltipState::dismiss,
                    needDivider = needDivider
                )
            }
        )
    }
}

@Composable
fun InteractiveTopDirectionTooltipPopup(
    title: String,
    direction: TooltipDirection.Top,
    anchorViewCoordinates: LayoutCoordinates,
    modifier: Modifier = Modifier,
    properties: PopupProperties = PopupProperties(),
    tooltipState: MegaTooltipState = rememberMegaTooltipState(),
    onDismissRequest: (() -> Unit)? = null,
    body: String? = null,
    primaryButton: InteractiveTooltipButtonProperties? = null,
    secondaryButton: InteractiveTooltipButtonProperties? = null,
    stepCounter: InteractiveTooltipStepCounterProperties? = null,
    needCloseIcon: Boolean = false,
    needDivider: Boolean = false
) {
    BasicTooltipPopup(
        properties = properties,
        tooltipState = tooltipState,
        popupPositionProvider = rememberTopTooltipPositionProvider(
            direction = direction,
            anchorViewCoordinates = anchorViewCoordinates
        ),
        onDismissRequest = onDismissRequest
    ) {
        BasicTopDirectionTooltip(
            modifier = modifier,
            type = MegaTooltipType.Interactive,
            direction = direction,
            content = {
                InteractiveTooltipContent(
                    title = title,
                    body = body,
                    primaryButton = primaryButton,
                    secondaryButton = secondaryButton,
                    stepCounter = stepCounter,
                    needCloseIcon = needCloseIcon,
                    onCloseClick = tooltipState::dismiss,
                    needDivider = needDivider
                )
            }
        )
    }
}

@Composable
fun InteractiveRightDirectionTooltipPopup(
    title: String,
    anchorViewCoordinates: LayoutCoordinates,
    modifier: Modifier = Modifier,
    properties: PopupProperties = PopupProperties(),
    tooltipState: MegaTooltipState = rememberMegaTooltipState(),
    onDismissRequest: (() -> Unit)? = null,
    body: String? = null,
    primaryButton: InteractiveTooltipButtonProperties? = null,
    secondaryButton: InteractiveTooltipButtonProperties? = null,
    stepCounter: InteractiveTooltipStepCounterProperties? = null,
    needCloseIcon: Boolean = false,
    needDivider: Boolean = false
) {
    BasicTooltipPopup(
        properties = properties,
        tooltipState = tooltipState,
        popupPositionProvider = rememberRightTooltipPositionProvider(
            anchorViewCoordinates = anchorViewCoordinates
        ),
        onDismissRequest = onDismissRequest
    ) {
        BasicRightDirectionTooltip(
            modifier = modifier,
            type = MegaTooltipType.Interactive,
            content = {
                InteractiveTooltipContent(
                    title = title,
                    body = body,
                    primaryButton = primaryButton,
                    secondaryButton = secondaryButton,
                    stepCounter = stepCounter,
                    needCloseIcon = needCloseIcon,
                    onCloseClick = tooltipState::dismiss,
                    needDivider = needDivider
                )
            }
        )
    }
}

@Composable
fun InteractiveBottomDirectionTooltipPopup(
    title: String,
    direction: TooltipDirection.Bottom,
    anchorViewCoordinates: LayoutCoordinates,
    modifier: Modifier = Modifier,
    properties: PopupProperties = PopupProperties(),
    tooltipState: MegaTooltipState = rememberMegaTooltipState(),
    onDismissRequest: (() -> Unit)? = null,
    body: String? = null,
    primaryButton: InteractiveTooltipButtonProperties? = null,
    secondaryButton: InteractiveTooltipButtonProperties? = null,
    stepCounter: InteractiveTooltipStepCounterProperties? = null,
    needCloseIcon: Boolean = false,
    needDivider: Boolean = false
) {
    BasicTooltipPopup(
        properties = properties,
        tooltipState = tooltipState,
        popupPositionProvider = rememberBottomTooltipPositionProvider(
            direction = direction,
            anchorViewCoordinates = anchorViewCoordinates
        ),
        onDismissRequest = onDismissRequest
    ) {
        BasicBottomDirectionTooltip(
            modifier = modifier,
            type = MegaTooltipType.Interactive,
            direction = direction,
            content = {
                InteractiveTooltipContent(
                    title = title,
                    body = body,
                    primaryButton = primaryButton,
                    secondaryButton = secondaryButton,
                    stepCounter = stepCounter,
                    needCloseIcon = needCloseIcon,
                    onCloseClick = tooltipState::dismiss,
                    needDivider = needDivider
                )
            }
        )
    }
}

@Composable
fun InteractiveNoneTooltipPopup(
    title: String,
    position: NoneTooltipPosition,
    anchorViewCoordinates: LayoutCoordinates,
    modifier: Modifier = Modifier,
    properties: PopupProperties = PopupProperties(),
    tooltipState: MegaTooltipState = rememberMegaTooltipState(),
    onDismissRequest: (() -> Unit)? = null,
    body: String? = null,
    primaryButton: InteractiveTooltipButtonProperties? = null,
    secondaryButton: InteractiveTooltipButtonProperties? = null,
    stepCounter: InteractiveTooltipStepCounterProperties? = null,
    needCloseIcon: Boolean = false,
    needDivider: Boolean = false
) {
    val positionProvider = getNoneTooltipPopupPositionProvider(
        position = position,
        anchorViewCoordinates = anchorViewCoordinates
    )
    BasicTooltipPopup(
        properties = properties,
        tooltipState = tooltipState,
        popupPositionProvider = positionProvider,
        onDismissRequest = onDismissRequest
    ) {
        BasicNoneTooltip(
            modifier = modifier,
            type = MegaTooltipType.Interactive,
            content = {
                InteractiveTooltipContent(
                    title = title,
                    body = body,
                    primaryButton = primaryButton,
                    secondaryButton = secondaryButton,
                    stepCounter = stepCounter,
                    needCloseIcon = needCloseIcon,
                    onCloseClick = tooltipState::dismiss,
                    needDivider = needDivider
                )
            }
        )
    }
}
