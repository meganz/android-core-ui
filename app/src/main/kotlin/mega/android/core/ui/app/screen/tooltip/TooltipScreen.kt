package mega.android.core.ui.app.screen.tooltip

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material3.Button
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.LayoutCoordinates
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import mega.android.core.ui.components.MegaText
import mega.android.core.ui.components.tooltip.direction.TooltipDirection
import mega.android.core.ui.components.tooltip.model.MegaTooltipType
import mega.android.core.ui.components.tooltip.model.NoneTooltipPosition
import mega.android.core.ui.components.tooltip.popup.interactive.InteractiveBottomDirectionTooltipPopup
import mega.android.core.ui.components.tooltip.popup.interactive.InteractiveLeftDirectionTooltipPopup
import mega.android.core.ui.components.tooltip.popup.interactive.InteractiveNoneTooltipPopup
import mega.android.core.ui.components.tooltip.popup.interactive.InteractiveRightDirectionTooltipPopup
import mega.android.core.ui.components.tooltip.popup.interactive.InteractiveTooltipButtonProperties
import mega.android.core.ui.components.tooltip.popup.interactive.InteractiveTooltipStepCounterProperties
import mega.android.core.ui.components.tooltip.popup.interactive.InteractiveTopDirectionTooltipPopup
import mega.android.core.ui.components.tooltip.popup.simple.SimpleBottomDirectionTooltipPopup
import mega.android.core.ui.components.tooltip.popup.simple.SimpleLeftDirectionTooltipPopup
import mega.android.core.ui.components.tooltip.popup.simple.SimpleNoneTooltipPopup
import mega.android.core.ui.components.tooltip.popup.simple.SimpleRightDirectionTooltipPopup
import mega.android.core.ui.components.tooltip.popup.simple.SimpleTooltipContentBodyOnly
import mega.android.core.ui.components.tooltip.popup.simple.SimpleTooltipContentTitleAndBody
import mega.android.core.ui.components.tooltip.popup.simple.SimpleTooltipContentTitleOnly
import mega.android.core.ui.components.tooltip.popup.simple.SimpleTopDirectionTooltipPopup
import mega.android.core.ui.components.tooltip.state.rememberMegaTooltipState
import mega.android.core.ui.preview.CombinedThemePreviews
import mega.android.core.ui.theme.AndroidThemeForPreviews
import mega.android.core.ui.theme.AppTheme
import mega.android.core.ui.theme.spacing.LocalSpacing
import mega.android.core.ui.theme.values.TextColor

@Composable
internal fun TooltipRoute(modifier: Modifier = Modifier) {
    TooltipScreen(modifier = modifier)
}

@Composable
internal fun TooltipScreen(modifier: Modifier = Modifier) {
    val spacing = LocalSpacing.current
    var shouldShowTooltip by rememberSaveable { mutableStateOf(false) }
    var anchorViewCoordinates by remember { mutableStateOf<LayoutCoordinates?>(null) }
    var selectedTooltipType by rememberSaveable { mutableStateOf(MegaTooltipType.Simple) }
    var selectedDirection by rememberSaveable(
        saver = Saver(
            save = { it.toString() },
            restore = { mutableStateOf(it.toTooltipDirection()) }
        )
    ) { mutableStateOf<TooltipDirection>(TooltipDirection.None) }
    var shouldShowSimpleTooltipRunningIndication by remember { mutableStateOf(false) }

    Column(modifier = modifier) {
        MegaText(
            modifier = Modifier
                .fillMaxWidth()
                .padding(spacing.x4),
            text = "Select type:",
            textColor = TextColor.Primary
        )
        MegaTooltipTypeOption(
            selectedTooltipType = selectedTooltipType,
            onItemSelected = {
                if (!shouldShowTooltip) {
                    selectedTooltipType = it
                }
            }
        )

        MegaText(
            modifier = Modifier
                .fillMaxWidth()
                .padding(spacing.x4),
            text = "Select direction:",
            textColor = TextColor.Primary
        )
        MegaTooltipDirectionOption(
            selectedDirection = selectedDirection,
            onItemSelected = {
                if (!shouldShowTooltip) {
                    selectedDirection = it
                }
            }
        )

        if (shouldShowSimpleTooltipRunningIndication) {
            MegaText(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = spacing.x8),
                text = "Simple tooltip will be dismissed in 6 seconds..",
                textColor = TextColor.Secondary,
                style = AppTheme.typography.bodyMedium,
                textAlign = TextAlign.Center
            )
        }

        Box(modifier = Modifier.fillMaxSize()) {
            Button(
                modifier = Modifier
                    .align(Alignment.Center)
                    .onGloballyPositioned { anchorViewCoordinates = it },
                onClick = { shouldShowTooltip = true }
            ) {
                MegaText(
                    text = "Show",
                    textColor = TextColor.OnColor
                )
            }

            if (shouldShowTooltip && anchorViewCoordinates != null) {
                when (selectedTooltipType) {
                    MegaTooltipType.Simple -> {
                        shouldShowSimpleTooltipRunningIndication = true
                        SimpleTooltip(
                            direction = selectedDirection,
                            anchorViewCoordinates = anchorViewCoordinates!!,
                            onDismissRequest = {
                                shouldShowTooltip = false
                                shouldShowSimpleTooltipRunningIndication = false
                            }
                        )
                    }

                    MegaTooltipType.Interactive -> {
                        InteractiveTooltip(
                            direction = selectedDirection,
                            anchorViewCoordinates = anchorViewCoordinates!!,
                            onDismissRequest = { shouldShowTooltip = false }
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun MegaTooltipTypeOption(
    selectedTooltipType: MegaTooltipType,
    onItemSelected: (type: MegaTooltipType) -> Unit
) {
    val spacing = LocalSpacing.current
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .selectableGroup()
            .background(Color.Black),
        horizontalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier
                .selectable(
                    onClick = { onItemSelected(MegaTooltipType.Simple) },
                    selected = selectedTooltipType == MegaTooltipType.Simple,
                    role = Role.RadioButton
                )
                .background(
                    color = if (selectedTooltipType == MegaTooltipType.Simple) {
                        Color.Gray
                    } else Color.Black
                ),
        ) {
            MegaText(
                modifier = Modifier.padding(spacing.x16),
                text = "Simple",
                textColor = TextColor.OnColor
            )
        }

        Spacer(modifier = Modifier.width(spacing.x8))

        Box(
            modifier = Modifier
                .selectable(
                    onClick = { onItemSelected(MegaTooltipType.Interactive) },
                    selected = selectedTooltipType == MegaTooltipType.Interactive,
                    role = Role.RadioButton
                )
                .background(
                    color = if (selectedTooltipType == MegaTooltipType.Interactive) {
                        Color.Gray
                    } else Color.Black
                ),
        ) {
            MegaText(
                modifier = Modifier.padding(spacing.x16),
                text = "Interactive",
                textColor = TextColor.OnColor
            )
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun MegaTooltipDirectionOption(
    selectedDirection: TooltipDirection,
    onItemSelected: (duration: TooltipDirection) -> Unit
) {
    val spacing = LocalSpacing.current
    FlowRow(
        modifier = Modifier
            .fillMaxWidth()
            .selectableGroup()
            .background(Color.Black),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        // None
        Box(
            modifier = Modifier
                .selectable(
                    onClick = { onItemSelected(TooltipDirection.None) },
                    selected = selectedDirection == TooltipDirection.None,
                    role = Role.RadioButton
                )
                .background(
                    color = if (selectedDirection == TooltipDirection.None) {
                        Color.Gray
                    } else Color.Black
                ),
        ) {
            MegaText(
                modifier = Modifier.padding(spacing.x4),
                text = "None",
                textColor = TextColor.OnColor
            )
        }

        VerticalDivider(modifier = Modifier.height(spacing.x36))

        // Top-Left
        Box(
            modifier = Modifier
                .selectable(
                    onClick = { onItemSelected(TooltipDirection.Top.Left) },
                    selected = selectedDirection == TooltipDirection.Top.Left,
                    role = Role.RadioButton
                )
                .background(
                    color = if (selectedDirection == TooltipDirection.Top.Left) {
                        Color.Gray
                    } else Color.Black
                ),
        ) {
            MegaText(
                modifier = Modifier.padding(spacing.x4),
                text = "Top-Left",
                textColor = TextColor.OnColor
            )
        }

        VerticalDivider(modifier = Modifier.height(spacing.x36))

        // Top-Centre
        Box(
            modifier = Modifier
                .selectable(
                    onClick = { onItemSelected(TooltipDirection.Top.Centre) },
                    selected = selectedDirection == TooltipDirection.Top.Centre,
                    role = Role.RadioButton
                )
                .background(
                    color = if (selectedDirection == TooltipDirection.Top.Centre) {
                        Color.Gray
                    } else Color.Black
                ),
        ) {
            MegaText(
                modifier = Modifier.padding(spacing.x4),
                text = "Top-Centre",
                textColor = TextColor.OnColor
            )
        }

        VerticalDivider(modifier = Modifier.height(spacing.x36))

        // Top-Right
        Box(
            modifier = Modifier
                .selectable(
                    onClick = { onItemSelected(TooltipDirection.Top.Right) },
                    selected = selectedDirection == TooltipDirection.Top.Right,
                    role = Role.RadioButton
                )
                .background(
                    color = if (selectedDirection == TooltipDirection.Top.Right) {
                        Color.Gray
                    } else Color.Black
                ),
        ) {
            MegaText(
                modifier = Modifier.padding(spacing.x4),
                text = "Top-Right",
                textColor = TextColor.OnColor
            )
        }

        VerticalDivider(modifier = Modifier.height(spacing.x36))

        // Bottom-Left
        Box(
            modifier = Modifier
                .selectable(
                    onClick = { onItemSelected(TooltipDirection.Bottom.Left) },
                    selected = selectedDirection == TooltipDirection.Bottom.Left,
                    role = Role.RadioButton
                )
                .background(
                    color = if (selectedDirection == TooltipDirection.Bottom.Left) {
                        Color.Gray
                    } else Color.Black
                ),
        ) {
            MegaText(
                modifier = Modifier.padding(spacing.x4),
                text = "Bottom-Left",
                textColor = TextColor.OnColor
            )
        }

        VerticalDivider(modifier = Modifier.height(spacing.x36))

        // Bottom-Centre
        Box(
            modifier = Modifier
                .selectable(
                    onClick = { onItemSelected(TooltipDirection.Bottom.Centre) },
                    selected = selectedDirection == TooltipDirection.Bottom.Centre,
                    role = Role.RadioButton
                )
                .background(
                    color = if (selectedDirection == TooltipDirection.Bottom.Centre) {
                        Color.Gray
                    } else Color.Black
                ),
        ) {
            MegaText(
                modifier = Modifier.padding(spacing.x4),
                text = "Bottom-Centre",
                textColor = TextColor.OnColor
            )
        }

        VerticalDivider(modifier = Modifier.height(spacing.x36))

        // Bottom-Right
        Box(
            modifier = Modifier
                .selectable(
                    onClick = { onItemSelected(TooltipDirection.Bottom.Right) },
                    selected = selectedDirection == TooltipDirection.Bottom.Right,
                    role = Role.RadioButton
                )
                .background(
                    color = if (selectedDirection == TooltipDirection.Bottom.Right) {
                        Color.Gray
                    } else Color.Black
                ),
        ) {
            MegaText(
                modifier = Modifier.padding(spacing.x4),
                text = "Bottom-Right",
                textColor = TextColor.OnColor
            )
        }

        VerticalDivider(modifier = Modifier.height(spacing.x36))

        // Left
        Box(
            modifier = Modifier
                .selectable(
                    onClick = { onItemSelected(TooltipDirection.Left) },
                    selected = selectedDirection == TooltipDirection.Left,
                    role = Role.RadioButton
                )
                .background(
                    color = if (selectedDirection == TooltipDirection.Left) {
                        Color.Gray
                    } else Color.Black
                ),
        ) {
            MegaText(
                modifier = Modifier.padding(spacing.x4),
                text = "Left",
                textColor = TextColor.OnColor
            )
        }

        VerticalDivider(modifier = Modifier.height(spacing.x36))

        // Right
        Box(
            modifier = Modifier
                .selectable(
                    onClick = { onItemSelected(TooltipDirection.Right) },
                    selected = selectedDirection == TooltipDirection.Right,
                    role = Role.RadioButton
                )
                .background(
                    color = if (selectedDirection == TooltipDirection.Right) {
                        Color.Gray
                    } else Color.Black
                ),
        ) {
            MegaText(
                modifier = Modifier.padding(spacing.x4),
                text = "Right",
                textColor = TextColor.OnColor
            )
        }
    }
}

@Composable
private fun SimpleTooltip(
    direction: TooltipDirection,
    anchorViewCoordinates: LayoutCoordinates,
    onDismissRequest: () -> Unit
) {
    when (direction) {
        TooltipDirection.Top.Left -> {
            SimpleTopDirectionTooltipPopup(
                direction = TooltipDirection.Top.Left,
                anchorViewCoordinates = anchorViewCoordinates,
                content = SimpleTooltipContentTitleAndBody(
                    title = "Simple text",
                    body = "Simple text"
                ),
                onDismissRequest = onDismissRequest
            )
        }

        TooltipDirection.Top.Centre -> {
            SimpleTopDirectionTooltipPopup(
                direction = TooltipDirection.Top.Centre,
                anchorViewCoordinates = anchorViewCoordinates,
                content = SimpleTooltipContentTitleOnly(value = "Simple text"),
                onDismissRequest = onDismissRequest
            )
        }

        TooltipDirection.Top.Right -> {
            SimpleTopDirectionTooltipPopup(
                direction = TooltipDirection.Top.Right,
                anchorViewCoordinates = anchorViewCoordinates,
                content = SimpleTooltipContentBodyOnly(value = "Simple text"),
                onDismissRequest = onDismissRequest
            )
        }

        TooltipDirection.Bottom.Left -> {
            SimpleBottomDirectionTooltipPopup(
                direction = TooltipDirection.Bottom.Left,
                anchorViewCoordinates = anchorViewCoordinates,
                content = SimpleTooltipContentTitleAndBody(
                    title = "Simple text",
                    body = "Simple text"
                ),
                onDismissRequest = onDismissRequest
            )
        }

        TooltipDirection.Bottom.Centre -> {
            SimpleBottomDirectionTooltipPopup(
                direction = TooltipDirection.Bottom.Centre,
                anchorViewCoordinates = anchorViewCoordinates,
                content = SimpleTooltipContentTitleOnly(value = "Simple text"),
                onDismissRequest = onDismissRequest
            )
        }

        TooltipDirection.Bottom.Right -> {
            SimpleBottomDirectionTooltipPopup(
                direction = TooltipDirection.Bottom.Right,
                anchorViewCoordinates = anchorViewCoordinates,
                content = SimpleTooltipContentBodyOnly(value = "Simple text"),
                onDismissRequest = onDismissRequest
            )
        }

        TooltipDirection.Left -> {
            SimpleLeftDirectionTooltipPopup(
                anchorViewCoordinates = anchorViewCoordinates,
                content = SimpleTooltipContentTitleAndBody(
                    title = "Simple text",
                    body = "Simple text"
                ),
                onDismissRequest = onDismissRequest
            )
        }

        TooltipDirection.Right -> {
            SimpleRightDirectionTooltipPopup(
                anchorViewCoordinates = anchorViewCoordinates,
                content = SimpleTooltipContentTitleAndBody(
                    title = "Simple text",
                    body = "Simple text"
                ),
                onDismissRequest = onDismissRequest
            )
        }

        TooltipDirection.None -> {
            SimpleNoneTooltipPopup(
                position = NoneTooltipPosition.BottomCentre,
                anchorViewCoordinates = anchorViewCoordinates,
                content = SimpleTooltipContentTitleAndBody(
                    title = "Simple text",
                    body = "Simple text"
                ),
                onDismissRequest = onDismissRequest
            )
        }
    }
}

@Composable
private fun InteractiveTooltip(
    direction: TooltipDirection,
    anchorViewCoordinates: LayoutCoordinates,
    onDismissRequest: () -> Unit
) {
    val context = LocalContext.current

    val tooltipState = rememberMegaTooltipState()

    fun showToast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    when (direction) {
        TooltipDirection.Top.Left -> {
            InteractiveTopDirectionTooltipPopup(
                modifier = Modifier.width(280.dp),
                tooltipState = tooltipState,
                title = "Title",
                direction = TooltipDirection.Top.Left,
                anchorViewCoordinates = anchorViewCoordinates,
                onDismissRequest = onDismissRequest,
                primaryButton = InteractiveTooltipButtonProperties(
                    text = "Primary button",
                    onClick = {
                        showToast(message = "Primary button clicked")
                        tooltipState.dismiss()
                    }
                ),
                needDivider = true
            )
        }

        TooltipDirection.Top.Centre -> {
            InteractiveTopDirectionTooltipPopup(
                modifier = Modifier.width(280.dp),
                tooltipState = tooltipState,
                title = "Title",
                direction = TooltipDirection.Top.Centre,
                anchorViewCoordinates = anchorViewCoordinates,
                onDismissRequest = onDismissRequest,
                secondaryButton = InteractiveTooltipButtonProperties(
                    text = "Secondary button",
                    onClick = {
                        showToast(message = "Secondary button clicked")
                        tooltipState.dismiss()
                    }
                ),
                needDivider = true
            )
        }

        TooltipDirection.Top.Right -> {
            InteractiveTopDirectionTooltipPopup(
                modifier = Modifier.width(180.dp),
                title = "Title",
                direction = TooltipDirection.Top.Right,
                anchorViewCoordinates = anchorViewCoordinates,
                onDismissRequest = onDismissRequest,
                needCloseIcon = true
            )
        }

        TooltipDirection.Bottom.Left -> {
            InteractiveBottomDirectionTooltipPopup(
                modifier = Modifier.width(280.dp),
                tooltipState = tooltipState,
                title = "Title",
                direction = TooltipDirection.Bottom.Left,
                anchorViewCoordinates = anchorViewCoordinates,
                onDismissRequest = onDismissRequest,
                body = "Body",
                primaryButton = InteractiveTooltipButtonProperties(
                    text = "Primary button",
                    onClick = {
                        showToast(message = "Primary button clicked")
                        tooltipState.dismiss()
                    }
                ),
                secondaryButton = InteractiveTooltipButtonProperties(
                    text = "Secondary button",
                    onClick = {
                        showToast(message = "Secondary button clicked")
                        tooltipState.dismiss()
                    }
                ),
                stepCounter = InteractiveTooltipStepCounterProperties(
                    currentPage = 1,
                    totalPage = 4
                ),
                needCloseIcon = true,
                needDivider = true
            )
        }

        TooltipDirection.Bottom.Centre -> {
            InteractiveBottomDirectionTooltipPopup(
                modifier = Modifier.width(280.dp),
                title = "Title",
                direction = TooltipDirection.Bottom.Centre,
                anchorViewCoordinates = anchorViewCoordinates,
                onDismissRequest = onDismissRequest,
                body = "Body",
                stepCounter = InteractiveTooltipStepCounterProperties(
                    currentPage = 1,
                    totalPage = 4
                ),
                needCloseIcon = true
            )
        }

        TooltipDirection.Bottom.Right -> {
            InteractiveBottomDirectionTooltipPopup(
                modifier = Modifier.width(280.dp),
                tooltipState = tooltipState,
                title = "Title",
                direction = TooltipDirection.Bottom.Right,
                anchorViewCoordinates = anchorViewCoordinates,
                onDismissRequest = onDismissRequest,
                body = "Body",
                primaryButton = InteractiveTooltipButtonProperties(
                    text = "Primary button",
                    onClick = {
                        showToast(message = "Primary button clicked")
                        tooltipState.dismiss()
                    }
                ),
                secondaryButton = InteractiveTooltipButtonProperties(
                    text = "Secondary button",
                    onClick = {
                        showToast(message = "Secondary button clicked")
                        tooltipState.dismiss()
                    }
                ),
                stepCounter = InteractiveTooltipStepCounterProperties(
                    currentPage = 1,
                    totalPage = 4
                ),
                needDivider = true
            )
        }

        TooltipDirection.Left -> {
            InteractiveLeftDirectionTooltipPopup(
                modifier = Modifier.width(180.dp),
                title = "Left",
                anchorViewCoordinates = anchorViewCoordinates,
                onDismissRequest = onDismissRequest,
                stepCounter = InteractiveTooltipStepCounterProperties(
                    currentPage = 1,
                    totalPage = 4
                )
            )
        }

        TooltipDirection.Right -> {
            InteractiveRightDirectionTooltipPopup(
                modifier = Modifier.width(180.dp),
                title = "Right",
                anchorViewCoordinates = anchorViewCoordinates,
                onDismissRequest = onDismissRequest,
                stepCounter = InteractiveTooltipStepCounterProperties(
                    currentPage = 1,
                    totalPage = 4
                )
            )
        }

        TooltipDirection.None -> {
            InteractiveNoneTooltipPopup(
                modifier = Modifier.width(280.dp),
                title = "None",
                position = NoneTooltipPosition.TopCentre,
                anchorViewCoordinates = anchorViewCoordinates,
                onDismissRequest = onDismissRequest,
                stepCounter = InteractiveTooltipStepCounterProperties(
                    currentPage = 1,
                    totalPage = 4
                )
            )
        }
    }
}

private fun String.toTooltipDirection(): TooltipDirection {
    // The string value will be : MutableState(value=[TooltipDirection])@xxxxxxxxxx
    val normalizedStringValue = substring(indexOf("=") + 1, indexOf(")"))
    return when (normalizedStringValue) {
        TooltipDirection.None.toString() -> TooltipDirection.None
        TooltipDirection.Left.toString() -> TooltipDirection.Left
        TooltipDirection.Right.toString() -> TooltipDirection.Right
        TooltipDirection.Top.Left.toString() -> TooltipDirection.Top.Left
        TooltipDirection.Top.Centre.toString() -> TooltipDirection.Top.Centre
        TooltipDirection.Top.Right.toString() -> TooltipDirection.Top.Right
        TooltipDirection.Bottom.Left.toString() -> TooltipDirection.Bottom.Left
        TooltipDirection.Bottom.Centre.toString() -> TooltipDirection.Bottom.Centre
        else -> TooltipDirection.Bottom.Right
    }
}

@CombinedThemePreviews
@Composable
private fun TooltipScreenPreview() {
    AndroidThemeForPreviews {
        TooltipScreen(
            modifier = Modifier.fillMaxSize()
        )
    }
}
