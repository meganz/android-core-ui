package mega.android.core.ui.components.dialogs

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandIn
import androidx.compose.animation.shrinkOut
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import mega.android.core.ui.components.LinkSpannedText
import mega.android.core.ui.components.text.SpannableText
import mega.android.core.ui.model.MegaSpanStyle
import mega.android.core.ui.model.SpanIndicator
import mega.android.core.ui.model.SpanStyleWithAnnotation
import mega.android.core.ui.preview.CombinedThemePreviews
import mega.android.core.ui.theme.AndroidThemeForPreviews
import mega.android.core.ui.theme.AppTheme
import mega.android.core.ui.theme.values.TextColor

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
    onDismiss: () -> Unit = {},
) {
    AnimatedVisibility(
        visible = isVisible,
        enter = expandIn(),
        exit = shrinkOut()
    ) {
        AlertDialog(
            modifier = modifier,
            onDismissRequest = onDismiss,
            containerColor = AppTheme.colors.background.surface1,
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
            confirmButton = {
                DialogButton(
                    buttonText = positiveButtonText,
                    onButtonClicked = onPositiveButtonClicked,
                    enabled = isPositiveButtonEnabled
                )
            },
            dismissButton = {
                DialogButton(
                    buttonText = negativeButtonText.orEmpty(),
                    onButtonClicked = {
                        onNegativeButtonClicked?.invoke()
                    },
                    enabled = isNegativeButtonEnabled
                )
            },
            properties = DialogProperties(
                dismissOnBackPress = dismissOnBackPress,
                dismissOnClickOutside = dismissOnClickOutside
            ),
            shape = RoundedCornerShape(28.dp)
        )
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