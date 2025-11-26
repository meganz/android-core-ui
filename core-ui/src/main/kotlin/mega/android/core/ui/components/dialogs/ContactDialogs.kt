package mega.android.core.ui.components.dialogs

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import mega.android.core.ui.components.MegaText
import mega.android.core.ui.components.dialogs.internal.MegaBasicDialogContent
import mega.android.core.ui.components.dialogs.internal.MegaBasicDialogFlowRow
import mega.android.core.ui.components.profile.MediumProfilePicture
import mega.android.core.ui.preview.CombinedThemePreviews
import mega.android.core.ui.theme.AndroidThemeForPreviews
import mega.android.core.ui.theme.AppTheme
import mega.android.core.ui.theme.values.TextColor
import java.io.File

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BasicContactDialog(
    modifier: Modifier = Modifier,
    title: String? = null,
    description: String? = null,
    contactName: String? = null,
    contactEmail: String? = null,
    contactAvatarFile: File? = null,
    contactAvatarColor: Color = Color.Unspecified,
    positiveButtonText: String,
    onPositiveButtonClicked: () -> Unit,
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
            title = {
                title?.let {
                    MegaText(
                        text = title,
                        style = AppTheme.typography.headlineSmall,
                        textColor = TextColor.Primary,
                    )
                }
            },
            text = {
                description?.let {
                    MegaText(
                        text = it,
                        style = AppTheme.typography.bodyMedium,
                        textColor = TextColor.Secondary,
                    )
                }
            },
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
            content = {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    MediumProfilePicture(
                        imageFile = contactAvatarFile,
                        contentDescription = contactName,
                        name = contactName,
                        avatarColor = contactAvatarColor,
                        modifier = Modifier.padding(8.dp),
                    )
                    Column(modifier = Modifier.padding(horizontal = 8.dp)) {
                        contactName?.let {
                            MegaText(
                                text = it,
                                style = AppTheme.typography.bodyLarge,
                                textColor = TextColor.Primary,
                            )
                        }
                        contactEmail?.let {
                            MegaText(
                                text = it,
                                style = AppTheme.typography.bodyMedium,
                                textColor = TextColor.Secondary,
                            )
                        }
                    }
                }
            },
        )
    }
}

@CombinedThemePreviews
@Composable
private fun BasicContactDialogWithDescriptionPreview() {
    AndroidThemeForPreviews {
        BasicContactDialog(
            title = "Basic contact dialog title",
            description = "This basic contact dialog is showing a description here",
            contactName = "John doe",
            contactEmail = "johndoe@sample.com",
            positiveButtonText = "Action 1",
            onPositiveButtonClicked = {},
            negativeButtonText = "Action 2",
            onNegativeButtonClicked = {},
        )
    }
}

@CombinedThemePreviews
@Composable
private fun BasicContactDialogPreview() {
    AndroidThemeForPreviews {
        BasicContactDialog(
            title = "Basic contact dialog title",
            contactName = "John doe",
            contactEmail = "johndoe@sample.com",
            positiveButtonText = "Action 1",
            onPositiveButtonClicked = {},
            negativeButtonText = "Action 2",
            onNegativeButtonClicked = {},
        )
    }
}