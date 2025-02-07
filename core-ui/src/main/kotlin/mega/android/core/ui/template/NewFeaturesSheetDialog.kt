package mega.android.core.ui.template

import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import mega.android.core.ui.components.common.PromotionalListAttributes
import mega.android.core.ui.components.dialogs.PromotionalFullImageDialog
import mega.android.core.ui.components.dialogs.PromotionalIllustrationDialog
import mega.android.core.ui.components.dialogs.PromotionalImageDialog
import mega.android.core.ui.components.dialogs.PromotionalPlainDialog
import mega.android.core.ui.components.sheets.PromotionalFullImageSheet
import mega.android.core.ui.components.sheets.PromotionalIllustrationSheet
import mega.android.core.ui.components.sheets.PromotionalImageSheet
import mega.android.core.ui.components.sheets.PromotionalPlainSheet
import mega.android.core.ui.model.NewFeaturesAttributes
import mega.android.core.ui.theme.devicetype.DeviceType
import mega.android.core.ui.theme.devicetype.LocalDeviceType

@Composable
fun NewFeaturesSheetDialog(
    attributes: NewFeaturesAttributes,
    onDismissRequest: () -> Unit = {},
    onPrimaryButtonClick: () -> Unit = {},
    onSecondaryButtonClick: () -> Unit = {}
) {
    val deviceType = LocalDeviceType.current

    val listItems = attributes.featuresList?.map {
        PromotionalListAttributes(it.title, it.subtitle, it.icon)
    } ?: emptyList()
    val sheetsModifier = Modifier.statusBarsPadding()

    when (attributes) {
        is NewFeaturesAttributes.FullImage -> {
            if (deviceType == DeviceType.Tablet) {
                PromotionalFullImageDialog(
                    modifier = Modifier.testTag(TestTags.NEW_FEATURES_SHEET_DIALOG_FULL_IMAGE_DIALOG),
                    image = attributes.image,
                    title = attributes.title,
                    headline = attributes.headline,
                    description = attributes.description,
                    primaryButton = attributes.primaryButtonText?.let { it to onPrimaryButtonClick },
                    secondaryButton = attributes.secondaryButtonText?.let { it to onSecondaryButtonClick },
                    onDismissRequest = onDismissRequest,
                    listItems = listItems,
                    footer = attributes.footer,
                    showCloseButton = attributes.showCloseButton,
                )
            } else {
                PromotionalFullImageSheet(
                    modifier = sheetsModifier.testTag(TestTags.NEW_FEATURES_SHEET_DIALOG_FULL_IMAGE_SHEET),
                    image = attributes.image,
                    title = attributes.title,
                    headline = attributes.headline,
                    description = attributes.description,
                    primaryButton = attributes.primaryButtonText?.let { it to onPrimaryButtonClick },
                    secondaryButton = attributes.secondaryButtonText?.let { it to onSecondaryButtonClick },
                    onDismissRequest = onDismissRequest,
                    listItems = listItems,
                    footer = attributes.footer,
                    showCloseButton = attributes.showCloseButton,
                )
            }

        }

        is NewFeaturesAttributes.Image -> {
            if (deviceType == DeviceType.Tablet) {
                PromotionalImageDialog(
                    modifier = Modifier.testTag(TestTags.NEW_FEATURES_SHEET_DIALOG_IMAGE_DIALOG),
                    image = attributes.image,
                    title = attributes.title,
                    headline = attributes.headline,
                    description = attributes.description,
                    primaryButton = attributes.primaryButtonText?.let { it to onPrimaryButtonClick },
                    secondaryButton = attributes.secondaryButtonText?.let { it to onSecondaryButtonClick },
                    onDismissRequest = onDismissRequest,
                    listItems = listItems,
                    footer = attributes.footer,
                    showCloseButton = attributes.showCloseButton,
                )
            } else {
                PromotionalImageSheet(
                    modifier = sheetsModifier.testTag(TestTags.NEW_FEATURES_SHEET_DIALOG_IMAGE_SHEET),
                    image = attributes.image,
                    title = attributes.title,
                    headline = attributes.headline,
                    description = attributes.description,
                    primaryButton = attributes.primaryButtonText?.let { it to onPrimaryButtonClick },
                    secondaryButton = attributes.secondaryButtonText?.let { it to onSecondaryButtonClick },
                    onDismissRequest = onDismissRequest,
                    listItems = listItems,
                    footer = attributes.footer,
                    showCloseButton = attributes.showCloseButton,
                )
            }
        }

        is NewFeaturesAttributes.Illustration -> {
            if (deviceType == DeviceType.Tablet) {
                PromotionalIllustrationDialog(
                    modifier = Modifier.testTag(TestTags.NEW_FEATURES_SHEET_DIALOG_ILLUSTRATION_DIALOG),
                    illustration = attributes.illustration,
                    title = attributes.title,
                    headline = attributes.headline,
                    description = attributes.description,
                    primaryButton = attributes.primaryButtonText?.let { it to onPrimaryButtonClick },
                    secondaryButton = attributes.secondaryButtonText?.let { it to onSecondaryButtonClick },
                    onDismissRequest = onDismissRequest,
                    listItems = listItems,
                    footer = attributes.footer,
                    showCloseButton = attributes.showCloseButton,
                    illustrationMode = attributes.illustrationMode,
                )
            } else {
                PromotionalIllustrationSheet(
                    modifier = sheetsModifier.testTag(TestTags.NEW_FEATURES_SHEET_DIALOG_ILLUSTRATION_SHEET),
                    illustration = attributes.illustration,
                    title = attributes.title,
                    headline = attributes.headline,
                    description = attributes.description,
                    primaryButton = attributes.primaryButtonText?.let { it to onPrimaryButtonClick },
                    secondaryButton = attributes.secondaryButtonText?.let { it to onSecondaryButtonClick },
                    onDismissRequest = onDismissRequest,
                    listItems = listItems,
                    footer = attributes.footer,
                    showCloseButton = attributes.showCloseButton,
                    illustrationMode = attributes.illustrationMode,
                )
            }
        }

        is NewFeaturesAttributes.Plain -> {
            if (deviceType == DeviceType.Tablet) {
                PromotionalPlainDialog(
                    modifier = Modifier.testTag(TestTags.NEW_FEATURES_SHEET_DIALOG_PLAIN_DIALOG),
                    title = attributes.title,
                    headline = attributes.headline,
                    description = attributes.description,
                    primaryButton = attributes.primaryButtonText?.let { it to onPrimaryButtonClick },
                    secondaryButton = attributes.secondaryButtonText?.let { it to onSecondaryButtonClick },
                    onDismissRequest = onDismissRequest,
                    listItems = listItems,
                    footer = attributes.footer,
                    showCloseButton = attributes.showCloseButton,
                )
            } else {
                PromotionalPlainSheet(
                    modifier = sheetsModifier.testTag(TestTags.NEW_FEATURES_SHEET_DIALOG_PLAIN_SHEET),
                    title = attributes.title,
                    headline = attributes.headline,
                    description = attributes.description,
                    primaryButton = attributes.primaryButtonText?.let { it to onPrimaryButtonClick },
                    secondaryButton = attributes.secondaryButtonText?.let { it to onSecondaryButtonClick },
                    onDismissRequest = onDismissRequest,
                    listItems = listItems,
                    footer = attributes.footer,
                    showCloseButton = attributes.showCloseButton,
                )
            }
        }
    }
}

internal object TestTags {
    private const val NEW_FEATURES_SHEET_DIALOG_SCREEN = "NEW_FEATURES_SHEET_DIALOG_screen"
    const val NEW_FEATURES_SHEET_DIALOG_FULL_IMAGE_DIALOG = "$NEW_FEATURES_SHEET_DIALOG_SCREEN:full_image_dialog"
    const val NEW_FEATURES_SHEET_DIALOG_FULL_IMAGE_SHEET = "$NEW_FEATURES_SHEET_DIALOG_SCREEN:full_image_sheet"
    const val NEW_FEATURES_SHEET_DIALOG_ILLUSTRATION_DIALOG = "$NEW_FEATURES_SHEET_DIALOG_SCREEN:illustration_dialog"
    const val NEW_FEATURES_SHEET_DIALOG_ILLUSTRATION_SHEET = "$NEW_FEATURES_SHEET_DIALOG_SCREEN:illustration_sheet"
    const val NEW_FEATURES_SHEET_DIALOG_IMAGE_DIALOG = "$NEW_FEATURES_SHEET_DIALOG_SCREEN:image_dialog"
    const val NEW_FEATURES_SHEET_DIALOG_IMAGE_SHEET = "$NEW_FEATURES_SHEET_DIALOG_SCREEN:image_sheet"
    const val NEW_FEATURES_SHEET_DIALOG_PLAIN_DIALOG = "$NEW_FEATURES_SHEET_DIALOG_SCREEN:plain_dialog"
    const val NEW_FEATURES_SHEET_DIALOG_PLAIN_SHEET = "$NEW_FEATURES_SHEET_DIALOG_SCREEN:plain_sheet"
}