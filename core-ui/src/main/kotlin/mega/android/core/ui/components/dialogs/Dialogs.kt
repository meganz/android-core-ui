package mega.android.core.ui.components.dialogs

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandIn
import androidx.compose.animation.shrinkOut
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import mega.android.core.ui.preview.CombinedThemePreviews
import mega.android.core.ui.theme.AndroidThemeForPreviews
import mega.android.core.ui.theme.AppTheme

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
                Text(
                    text = title,
                    color = AppTheme.colors.text.primary,
                    style = AppTheme.typography.headlineSmall,
                )
            },
            text = {
                description?.let {
                    Text(
                        text = it,
                        color = AppTheme.colors.text.secondary,
                        style = AppTheme.typography.bodyMedium,
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
            title = "Basic dialog title",
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
            title = "Basic dialog title",
            description = "This is a dilaog with width fixed so that long text will be wrapped to next line.",
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
            title = "Basic dialog title",
            positiveButtonText = "Action 1",
            onPositiveButtonClicked = {},
            negativeButtonText = "Action 2",
            onNegativeButtonClicked = {},
        )
    }
}