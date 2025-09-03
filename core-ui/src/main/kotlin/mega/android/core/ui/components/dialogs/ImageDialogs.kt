package mega.android.core.ui.components.dialogs

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import mega.android.core.ui.R
import mega.android.core.ui.components.LinkSpannedText
import mega.android.core.ui.components.MegaText
import mega.android.core.ui.components.dialogs.internal.MegaBasicDialogContent
import mega.android.core.ui.components.dialogs.internal.MegaBasicDialogFlowRow
import mega.android.core.ui.components.text.SpannableText
import mega.android.core.ui.preview.CombinedThemePreviews
import mega.android.core.ui.theme.AndroidThemeForPreviews
import mega.android.core.ui.theme.AppTheme
import mega.android.core.ui.theme.spacing.LocalSpacing
import mega.android.core.ui.theme.values.TextColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BasicImageDialog(
    title: String,
    imagePainter: Painter,
    positiveButtonText: String,
    onPositiveButtonClicked: () -> Unit,
    modifier: Modifier = Modifier,
    contentScale: ContentScale = ContentScale.Fit,
    description: String? = null,
    negativeButtonText: String? = null,
    onNegativeButtonClicked: (() -> Unit)? = null,
    dismissOnClickOutside: Boolean = true,
    dismissOnBackPress: Boolean = true,
    isPositiveButtonEnabled: Boolean = true,
    isNegativeButtonEnabled: Boolean = true,
    onDismiss: () -> Unit = {},
) {
    BasicAlertDialog(
        onDismissRequest = onDismiss,
        modifier = modifier,
        properties = DialogProperties(
            dismissOnBackPress = dismissOnBackPress,
            dismissOnClickOutside = dismissOnClickOutside
        )
    ) {
        MegaBasicDialogContent(
            buttons = {
                MegaBasicDialogFlowRow {
                    if (negativeButtonText != null && onNegativeButtonClicked != null) {
                        DialogButton(
                            buttonText = negativeButtonText,
                            onButtonClicked = onNegativeButtonClicked,
                            enabled = isNegativeButtonEnabled
                        )
                    }
                    DialogButton(
                        buttonText = positiveButtonText,
                        onButtonClicked = onPositiveButtonClicked,
                        enabled = isPositiveButtonEnabled
                    )
                }
            },
            title = {
                MegaText(
                    text = title,
                    style = AppTheme.typography.headlineSmall,
                    textColor = TextColor.Primary,
                )
            },
            text = {
                if (!description.isNullOrEmpty()) {
                    Column(modifier = Modifier.fillMaxWidth()) {
                        MegaText(
                            text = description,
                            style = AppTheme.typography.bodyMedium,
                            textColor = TextColor.Secondary,
                        )
                    }
                }
            },
            imageContent = {
                Image(
                    painter = imagePainter,
                    contentDescription = null,
                    modifier = Modifier.fillMaxWidth(),
                    contentScale = contentScale
                )
            },
            shape = RoundedCornerShape(28.dp),
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BasicImageDialog(
    title: String,
    imagePainter: Painter,
    positiveButtonText: String,
    onPositiveButtonClicked: () -> Unit,
    description: SpannableText,
    modifier: Modifier = Modifier,
    contentScale: ContentScale = ContentScale.Fit,
    negativeButtonText: String? = null,
    onNegativeButtonClicked: (() -> Unit)? = null,
    dismissOnClickOutside: Boolean = true,
    dismissOnBackPress: Boolean = true,
    isPositiveButtonEnabled: Boolean = true,
    isNegativeButtonEnabled: Boolean = true,
    onDismiss: () -> Unit = {},
) {
    BasicAlertDialog(
        onDismissRequest = onDismiss,
        modifier = modifier,
        properties = DialogProperties(
            dismissOnBackPress = dismissOnBackPress,
            dismissOnClickOutside = dismissOnClickOutside
        )
    ) {
        MegaBasicDialogContent(
            buttons = {
                MegaBasicDialogFlowRow {
                    if (negativeButtonText != null && onNegativeButtonClicked != null) {
                        DialogButton(
                            buttonText = negativeButtonText,
                            onButtonClicked = onNegativeButtonClicked,
                            enabled = isNegativeButtonEnabled
                        )
                    }
                    DialogButton(
                        buttonText = positiveButtonText,
                        onButtonClicked = onPositiveButtonClicked,
                        enabled = isPositiveButtonEnabled
                    )
                }
            },
            title = {
                MegaText(
                    text = title,
                    style = AppTheme.typography.headlineSmall,
                    textColor = TextColor.Primary,
                )
            },
            text = {
                LinkSpannedText(
                    value = description.text.orEmpty(),
                    spanStyles = description.annotations ?: emptyMap(),
                    onAnnotationClick = description.onAnnotationClick ?: {},
                    baseStyle = AppTheme.typography.bodyMedium,
                    baseTextColor = TextColor.Secondary,
                )
            },
            imageContent = {
                Image(
                    painter = imagePainter,
                    contentDescription = null,
                    modifier = Modifier.fillMaxWidth(),
                    contentScale = contentScale
                )
            },
            shape = RoundedCornerShape(28.dp),
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BasicImageDialog(
    title: String,
    imagePainter: Painter,
    buttons: ImmutableList<BasicDialogButton>,
    modifier: Modifier = Modifier,
    contentScale: ContentScale = ContentScale.Fit,
    description: String? = null,
    dismissOnClickOutside: Boolean = true,
    dismissOnBackPress: Boolean = true,
    onDismiss: () -> Unit = {},
    @BasicDialogButtonsDirection buttonDirection: Int = HORIZONTAL,
) {
    BasicAlertDialog(
        onDismissRequest = onDismiss,
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
                MegaText(
                    text = title,
                    style = AppTheme.typography.headlineSmall,
                    textColor = TextColor.Primary,
                )
            },
            text = {
                if (!description.isNullOrEmpty()) {
                    Column(modifier = Modifier.fillMaxWidth()) {
                        MegaText(
                            text = description,
                            style = AppTheme.typography.bodyMedium,
                            textColor = TextColor.Secondary,
                        )
                    }
                }
            },
            imageContent = {
                Image(
                    painter = imagePainter,
                    contentDescription = null,
                    modifier = Modifier.fillMaxWidth(),
                    contentScale = contentScale
                )
            },
            shape = RoundedCornerShape(28.dp),
        )
    }
}

@CombinedThemePreviews
@Composable
private fun BasicImageDialogPreview() {
    AndroidThemeForPreviews {
        BasicImageDialog(
            title = "Basic input dialog title",
            positiveButtonText = "Action 1",
            onPositiveButtonClicked = {},
            negativeButtonText = "Action 2",
            onNegativeButtonClicked = {},
            imagePainter = painterResource(id = R.drawable.illustration_mega_anniversary)
        )
    }
}

@CombinedThemePreviews
@Composable
private fun BasicImageDialogWithVertical3ButtonsPreview() {
    AndroidThemeForPreviews {
        BasicImageDialog(
            title = "Basic dialog title",
            description = "A dialog is a type of modal window that appears in front of app content to provide critical information, or prompt for a decision to be made.",
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
            imagePainter = painterResource(id = R.drawable.ic_eye_medium_thin_outline),
            buttonDirection = VERTICAL,
            contentScale = ContentScale.FillWidth,
        )
    }
}