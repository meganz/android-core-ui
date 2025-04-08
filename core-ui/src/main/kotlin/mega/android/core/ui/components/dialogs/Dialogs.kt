package mega.android.core.ui.components.dialogs

import androidx.annotation.IntDef
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandIn
import androidx.compose.animation.shrinkOut
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.Placeable
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.fastForEach
import androidx.compose.ui.util.fastForEachIndexed
import androidx.compose.ui.window.DialogProperties
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import mega.android.core.ui.components.LinkSpannedText
import mega.android.core.ui.components.text.SpannableText
import mega.android.core.ui.model.MegaSpanStyle
import mega.android.core.ui.model.SpanIndicator
import mega.android.core.ui.model.SpanStyleWithAnnotation
import mega.android.core.ui.preview.CombinedThemePreviews
import mega.android.core.ui.theme.AndroidThemeForPreviews
import mega.android.core.ui.theme.AppTheme
import mega.android.core.ui.theme.spacing.LocalSpacing
import mega.android.core.ui.theme.values.TextColor
import kotlin.math.max

@Retention(AnnotationRetention.SOURCE)
@IntDef(VERTICAL, HORIZONTAL)
annotation class BasicDialogButtonsDirection

const val VERTICAL = 0
const val HORIZONTAL = 1

@Immutable
data class BasicDialogButton(
    val text: String,
    val onClick: () -> Unit,
    val enabled: Boolean = true
)

@Composable
fun BasicDialog(
    title: String,
    positiveButtonText: String,
    onPositiveButtonClicked: () -> Unit,
    modifier: Modifier = Modifier,
    description: String? = null,
    negativeButtonText: String? = null,
    onNegativeButtonClicked: (() -> Unit)? = null,
    dismissOnClickOutside: Boolean = true,
    dismissOnBackPress: Boolean = true,
    isPositiveButtonEnabled: Boolean = true,
    isNegativeButtonEnabled: Boolean = true,
    isVisible: Boolean = true,
    @BasicDialogButtonsDirection buttonDirection: Int = HORIZONTAL,
    onDismiss: () -> Unit = {},
) {
    BasicDialog(
        title = SpannableText(text = title),
        positiveButtonText = positiveButtonText,
        onPositiveButtonClicked = onPositiveButtonClicked,
        modifier = modifier,
        description = description?.let { SpannableText(text = it) },
        negativeButtonText = negativeButtonText,
        onNegativeButtonClicked = onNegativeButtonClicked,
        dismissOnClickOutside = dismissOnClickOutside,
        dismissOnBackPress = dismissOnBackPress,
        isPositiveButtonEnabled = isPositiveButtonEnabled,
        isNegativeButtonEnabled = isNegativeButtonEnabled,
        isVisible = isVisible,
        onDismiss = onDismiss
    )
}

@Composable
fun BasicDialog(
    title: SpannableText,
    positiveButtonText: String,
    onPositiveButtonClicked: () -> Unit,
    modifier: Modifier = Modifier,
    description: SpannableText? = null,
    negativeButtonText: String? = null,
    onNegativeButtonClicked: (() -> Unit)? = null,
    dismissOnClickOutside: Boolean = true,
    dismissOnBackPress: Boolean = true,
    isPositiveButtonEnabled: Boolean = true,
    isNegativeButtonEnabled: Boolean = true,
    isVisible: Boolean = true,
    @BasicDialogButtonsDirection buttonDirection: Int = HORIZONTAL,
    onDismiss: () -> Unit = {},
) {
    BasicDialog(
        modifier = modifier,
        title = title,
        buttons = persistentListOf(
            BasicDialogButton(
                text = negativeButtonText.orEmpty(),
                onClick = { onNegativeButtonClicked?.invoke() },
                enabled = isNegativeButtonEnabled
            ),
            BasicDialogButton(
                text = positiveButtonText,
                onClick = onPositiveButtonClicked,
                enabled = isPositiveButtonEnabled
            )
        ),
        onDismissRequest = onDismiss,
        description = description,
        dismissOnClickOutside = dismissOnClickOutside,
        dismissOnBackPress = dismissOnBackPress,
        isVisible = isVisible,
        buttonDirection = buttonDirection
    )
}

@Composable
fun BasicDialog(
    title: String,
    buttons: ImmutableList<BasicDialogButton>,
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier,
    description: String? = null,
    dismissOnClickOutside: Boolean = true,
    dismissOnBackPress: Boolean = true,
    isVisible: Boolean = true,
    @BasicDialogButtonsDirection buttonDirection: Int = HORIZONTAL
) {
    BasicDialog(
        modifier = modifier,
        title = SpannableText(title),
        buttons = buttons,
        onDismissRequest = onDismissRequest,
        description = SpannableText(description),
        dismissOnClickOutside = dismissOnClickOutside,
        dismissOnBackPress = dismissOnBackPress,
        isVisible = isVisible,
        buttonDirection = buttonDirection
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BasicDialog(
    title: SpannableText,
    buttons: ImmutableList<BasicDialogButton>,
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier,
    description: SpannableText? = null,
    dismissOnClickOutside: Boolean = true,
    dismissOnBackPress: Boolean = true,
    isVisible: Boolean = true,
    @BasicDialogButtonsDirection buttonDirection: Int = HORIZONTAL
) {
    AnimatedVisibility(
        visible = isVisible,
        enter = expandIn(),
        exit = shrinkOut()
    ) {
        BasicAlertDialog(
            onDismissRequest = onDismissRequest,
            modifier = modifier,
            properties = DialogProperties(
                dismissOnBackPress = dismissOnBackPress,
                dismissOnClickOutside = dismissOnClickOutside
            )
        ) {
            MegaBasicDialogContent(
                buttons = {
                    if (buttonDirection == HORIZONTAL) {
                        MegaBasicDialogFlowRow {
                            buttons.forEach {
                                DialogButton(
                                    buttonText = it.text,
                                    onButtonClicked = { it.onClick() },
                                    enabled = it.enabled
                                )
                            }
                        }
                    } else {
                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalAlignment = Alignment.End,
                            verticalArrangement = Arrangement.spacedBy(LocalSpacing.current.x8)
                        ) {
                            buttons.forEach {
                                DialogButton(
                                    buttonText = it.text,
                                    onButtonClicked = { it.onClick() },
                                    enabled = it.enabled
                                )
                            }
                        }
                    }
                },
                title = {
                    LinkSpannedText(
                        value = title.text.orEmpty(),
                        spanStyles = title.annotations ?: emptyMap(),
                        onAnnotationClick = title.onAnnotationClick ?: {},
                        baseStyle = AppTheme.typography.headlineSmall,
                        baseTextColor = TextColor.Primary,
                    )
                },
                text = {
                    description?.let {
                        LinkSpannedText(
                            value = it.text.orEmpty(),
                            spanStyles = it.annotations ?: emptyMap(),
                            onAnnotationClick = it.onAnnotationClick ?: {},
                            baseStyle = AppTheme.typography.bodyMedium,
                            baseTextColor = TextColor.Secondary,
                        )
                    }
                },
                shape = RoundedCornerShape(28.dp)
            )
        }
    }
}

@Composable
private fun MegaBasicDialogContent(
    buttons: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    title: (@Composable () -> Unit)?,
    text: @Composable (() -> Unit)?,
    shape: Shape
) {
    Surface(
        modifier = modifier,
        shape = shape,
        color = AppTheme.colors.background.surface1
    ) {
        Column(modifier = Modifier.padding(DialogPadding)) {
            title?.let {
                Box(
                    modifier = Modifier
                        .padding(TitlePadding)
                        .align(Alignment.Start)
                ) {
                    title()
                }
            }
            text?.let {
                Box(
                    Modifier
                        .weight(weight = 1f, fill = false)
                        .padding(TextPadding)
                        .align(Alignment.Start)
                ) {
                    text()
                }
            }
            Box(modifier = Modifier.align(Alignment.End)) {
                buttons()
            }
        }
    }
}

/**
 * Simple clone of FlowRow that arranges its children in a horizontal flow with limited
 * customization.
 */
@Composable
private fun MegaBasicDialogFlowRow(content: @Composable () -> Unit) {
    Layout(content) { measurables, constraints ->
        val sequences = mutableListOf<List<Placeable>>()
        val crossAxisSizes = mutableListOf<Int>()
        val crossAxisPositions = mutableListOf<Int>()

        var mainAxisSpace = 0
        var crossAxisSpace = 0

        val currentSequence = mutableListOf<Placeable>()
        var currentMainAxisSize = 0
        var currentCrossAxisSize = 0

        // Return whether the placeable can be added to the current sequence.
        fun canAddToCurrentSequence(placeable: Placeable) =
            currentSequence.isEmpty() ||
                    currentMainAxisSize + ButtonsMainAxisSpacing.roundToPx() + placeable.width <=
                    constraints.maxWidth

        // Store current sequence information and start a new sequence.
        fun startNewSequence() {
            if (sequences.isNotEmpty()) {
                crossAxisSpace += ButtonsCrossAxisSpacing.roundToPx()
            }
            // Ensures that confirming actions appear above dismissive actions.
            @Suppress("ListIterator") sequences.add(0, currentSequence.toList())
            crossAxisSizes += currentCrossAxisSize
            crossAxisPositions += crossAxisSpace

            crossAxisSpace += currentCrossAxisSize
            mainAxisSpace = max(mainAxisSpace, currentMainAxisSize)

            currentSequence.clear()
            currentMainAxisSize = 0
            currentCrossAxisSize = 0
        }

        measurables.fastForEach { measurable ->
            // Ask the child for its preferred size.
            val placeable = measurable.measure(constraints)

            // Start a new sequence if there is not enough space.
            if (!canAddToCurrentSequence(placeable)) startNewSequence()

            // Add the child to the current sequence.
            if (currentSequence.isNotEmpty()) {
                currentMainAxisSize += ButtonsMainAxisSpacing.roundToPx()
            }
            currentSequence.add(placeable)
            currentMainAxisSize += placeable.width
            currentCrossAxisSize = max(currentCrossAxisSize, placeable.height)
        }

        if (currentSequence.isNotEmpty()) startNewSequence()

        val mainAxisLayoutSize = max(mainAxisSpace, constraints.minWidth)

        val crossAxisLayoutSize = max(crossAxisSpace, constraints.minHeight)

        layout(mainAxisLayoutSize, crossAxisLayoutSize) {
            sequences.fastForEachIndexed { i, placeables ->
                val childrenMainAxisSizes =
                    IntArray(placeables.size) { j ->
                        placeables[j].width +
                                if (j < placeables.lastIndex) ButtonsMainAxisSpacing.roundToPx() else 0
                    }
                val arrangement = Arrangement.End
                val mainAxisPositions = IntArray(childrenMainAxisSizes.size) { 0 }
                with(arrangement) {
                    arrange(
                        mainAxisLayoutSize,
                        childrenMainAxisSizes,
                        layoutDirection,
                        mainAxisPositions
                    )
                }
                placeables.fastForEachIndexed { j, placeable ->
                    placeable.place(x = mainAxisPositions[j], y = crossAxisPositions[i])
                }
            }
        }
    }
}

@Composable
@CombinedThemePreviews
private fun BasicDialogPreview() {
    AndroidThemeForPreviews {
        BasicDialog(
            title = SpannableText("Basic dialog title"),
            description = SpannableText("A dialog is a type of modal window that appears in front of app content to provide critical information, or prompt for a decision to be made."),
            positiveButtonText = "Action 1",
            onPositiveButtonClicked = {},
            negativeButtonText = "Action 2",
            onNegativeButtonClicked = {},
        )
    }
}

@Composable
@CombinedThemePreviews
private fun BasicDialogWithModifierPreview() {
    AndroidThemeForPreviews {
        BasicDialog(
            modifier = Modifier.width(312.dp),
            title = SpannableText("Basic dialog title"),
            description = SpannableText("This is a dilaog with width fixed so that long text will be wrapped to next line."),
            positiveButtonText = "Action 1",
            onPositiveButtonClicked = {},
            negativeButtonText = "Action 2",
            onNegativeButtonClicked = {},
        )
    }
}

@Composable
@CombinedThemePreviews
private fun BasicDialogNoDescriptionPreview() {
    AndroidThemeForPreviews {
        BasicDialog(
            title = SpannableText("Basic dialog title"),
            positiveButtonText = "Action 1",
            onPositiveButtonClicked = {},
            negativeButtonText = "Action 2",
            onNegativeButtonClicked = {},
        )
    }
}

@Composable
@CombinedThemePreviews
private fun BasicDialogWithSpannableTextPreview() {
    AndroidThemeForPreviews {
        BasicDialog(
            title = SpannableText(
                text = "[A]Basic dialog title[/A]",
                annotations = mapOf(
                    SpanIndicator('A') to SpanStyleWithAnnotation(
                        megaSpanStyle = MegaSpanStyle.DefaultColorStyle(
                            spanStyle = SpanStyle(
                                textDecoration = TextDecoration.Underline
                            ),
                        ),
                        annotation = "Basic dialog title"
                    ),
                ),
            ),
            description = SpannableText(
                text = "[A]This[/A] is a dilaog with spannable text.",
                annotations = mapOf(
                    SpanIndicator('A') to SpanStyleWithAnnotation(
                        megaSpanStyle = MegaSpanStyle.DefaultColorStyle(
                            spanStyle = SpanStyle(
                                textDecoration = TextDecoration.Underline
                            ),
                        ),
                        annotation = "This"
                    ),
                ),
            ),
            positiveButtonText = "Action 1",
            onPositiveButtonClicked = {},
        )
    }
}

@Composable
@CombinedThemePreviews
private fun BasicDialogWithVerticalButtonsPreview() {
    AndroidThemeForPreviews {
        BasicDialog(
            title = SpannableText("Basic dialog title"),
            description = SpannableText("A dialog is a type of modal window that appears in front of app content to provide critical information, or prompt for a decision to be made."),
            positiveButtonText = "Action 1",
            onPositiveButtonClicked = {},
            negativeButtonText = "Action 2",
            onNegativeButtonClicked = {},
            buttonDirection = VERTICAL
        )
    }
}

@CombinedThemePreviews
@Composable
private fun BasicDialogWithVertical3ButtonsPreview() {
    AndroidThemeForPreviews {
        BasicDialog(
            title = SpannableText("Basic dialog title"),
            description = SpannableText("A dialog is a type of modal window that appears in front of app content to provide critical information, or prompt for a decision to be made."),
            buttons = persistentListOf(
                BasicDialogButton(
                    text = "Action 1",
                    onClick = {}
                ),
                BasicDialogButton(
                    text = "Action 2",
                    onClick = {}
                ),
                BasicDialogButton(
                    text = "Action 3",
                    onClick = {}
                )
            ),
            onDismissRequest = {},
            buttonDirection = VERTICAL
        )
    }
}

private val ButtonsMainAxisSpacing = 8.dp
private val ButtonsCrossAxisSpacing = 12.dp

// Paddings for each of the dialog's parts.
private val DialogPadding = PaddingValues(all = 24.dp)
private val TitlePadding = PaddingValues(bottom = 16.dp)
private val TextPadding = PaddingValues(bottom = 24.dp)
