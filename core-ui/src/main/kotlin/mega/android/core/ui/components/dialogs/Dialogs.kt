package mega.android.core.ui.components.dialogs

import androidx.annotation.IntDef
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandIn
import androidx.compose.animation.shrinkOut
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import mega.android.core.ui.components.LinkSpannedText
import mega.android.core.ui.components.dialogs.internal.MegaBasicDialogContent
import mega.android.core.ui.components.dialogs.internal.MegaBasicDialogFlowRow
import mega.android.core.ui.components.text.SpannableText
import mega.android.core.ui.model.MegaSpanStyle
import mega.android.core.ui.model.SpanIndicator
import mega.android.core.ui.model.SpanStyleWithAnnotation
import mega.android.core.ui.preview.CombinedThemePreviews
import mega.android.core.ui.theme.AndroidThemeForPreviews
import mega.android.core.ui.theme.AppTheme
import mega.android.core.ui.theme.spacing.LocalSpacing
import mega.android.core.ui.theme.values.TextColor

@Retention(AnnotationRetention.SOURCE)
@IntDef(VERTICAL, HORIZONTAL)
annotation class BasicDialogButtonsDirection

const val VERTICAL = 0
const val HORIZONTAL = 1

@Immutable
data class BasicDialogButton(
    val text: String,
    val onClick: () -> Unit,
    val enabled: Boolean = true,
)


@Composable
fun BasicDialog(
    positiveButtonText: String,
    onPositiveButtonClicked: () -> Unit,
    modifier: Modifier = Modifier,
    description: String,
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
        title = null,
        positiveButtonText = positiveButtonText,
        onPositiveButtonClicked = onPositiveButtonClicked,
        modifier = modifier,
        description = SpannableText(text = description),
        negativeButtonText = negativeButtonText,
        onNegativeButtonClicked = onNegativeButtonClicked,
        dismissOnClickOutside = dismissOnClickOutside,
        dismissOnBackPress = dismissOnBackPress,
        isPositiveButtonEnabled = isPositiveButtonEnabled,
        isNegativeButtonEnabled = isNegativeButtonEnabled,
        isVisible = isVisible,
        buttonDirection = buttonDirection,
        onDismiss = onDismiss
    )
}

@Composable
fun BasicDialog(
    title: String?,
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
        title = title?.let { SpannableText(text = it) },
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
        onDismiss = onDismiss,
        buttonDirection = buttonDirection,
    )
}

@Composable
fun BasicDialog(
    title: SpannableText?,
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
    title: String?,
    buttons: ImmutableList<BasicDialogButton>,
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier,
    description: String? = null,
    dismissOnClickOutside: Boolean = true,
    dismissOnBackPress: Boolean = true,
    isVisible: Boolean = true,
    @BasicDialogButtonsDirection buttonDirection: Int = HORIZONTAL,
) {
    BasicDialog(
        modifier = modifier,
        title = title?.let { SpannableText(it) },
        buttons = buttons,
        onDismissRequest = onDismissRequest,
        description = description?.let { SpannableText(it) },
        dismissOnClickOutside = dismissOnClickOutside,
        dismissOnBackPress = dismissOnBackPress,
        isVisible = isVisible,
        buttonDirection = buttonDirection
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BasicDialog(
    title: SpannableText?,
    buttons: ImmutableList<BasicDialogButton>,
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier,
    description: SpannableText? = null,
    dismissOnClickOutside: Boolean = true,
    dismissOnBackPress: Boolean = true,
    isVisible: Boolean = true,
    @BasicDialogButtonsDirection buttonDirection: Int = HORIZONTAL,
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
                title = title?.text?.takeIf { it.isNotEmpty() }?.let {
                    @Composable {
                        LinkSpannedText(
                            value = it,
                            spanStyles = title.annotations ?: emptyMap(),
                            onAnnotationClick = title.onAnnotationClick ?: {},
                            baseStyle = AppTheme.typography.headlineSmall,
                            baseTextColor = TextColor.Primary,
                        )
                    }
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
private fun BasicDialogOnDescriptionPreview() {
    AndroidThemeForPreviews {
        BasicDialog(
            description = "A dialog is a type of modal window that appears in front of app content to provide critical information, or prompt for a decision to be made.",
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
private fun BasicDialogNoTitlePreview() {
    AndroidThemeForPreviews {
        BasicDialog(
            title = "",
            description = "Basic dialog description",
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
                    text = "Action a bit longer",
                    onClick = {}
                )
            ),
            onDismissRequest = {},
            buttonDirection = VERTICAL
        )
    }
}

