package mega.android.core.ui.components.dialogs

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import mega.android.core.ui.components.LinkSpannedText
import mega.android.core.ui.components.button.MegaRadioButton
import mega.android.core.ui.components.dialogs.internal.MegaBasicDialogContent
import mega.android.core.ui.components.dialogs.internal.MegaBasicDialogFlowRow
import mega.android.core.ui.components.list.OneLineListItem
import mega.android.core.ui.components.text.SpannableText
import mega.android.core.ui.preview.CombinedThemePreviews
import mega.android.core.ui.theme.AndroidThemeForPreviews
import mega.android.core.ui.theme.AppTheme
import mega.android.core.ui.theme.values.TextColor
import mega.android.core.ui.tokens.theme.DSTokens


@Immutable
data class BasicDialogRadioOption(
    val ordinal: Int,
    val text: String,
    val enabled: Boolean = true,
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BasicRadioDialog(
    title: SpannableText,
    options: ImmutableList<BasicDialogRadioOption>,
    buttons: ImmutableList<BasicDialogButton>,
    onDismissRequest: () -> Unit,
    onOptionSelected: (BasicDialogRadioOption) -> Unit,
    modifier: Modifier = Modifier,
    selectedOption: BasicDialogRadioOption? = null,
    dialogProperties: DialogProperties = DialogProperties(
        dismissOnBackPress = true,
        dismissOnClickOutside = true
    ),
) {
    BasicAlertDialog(
        onDismissRequest = onDismissRequest,
        modifier = modifier,
        properties = dialogProperties
    ) {
        MegaBasicDialogContent(
            title = {
                title.text?.let {
                    LinkSpannedText(
                        value = it,
                        spanStyles = title.annotations ?: emptyMap(),
                        onAnnotationClick = title.onAnnotationClick ?: {},
                        baseStyle = AppTheme.typography.headlineSmall,
                        baseTextColor = TextColor.Primary,
                    )
                }
            },
            text = null,
            buttons = {
                MegaBasicDialogFlowRow {
                    buttons.forEach {
                        DialogButton(
                            buttonText = it.text,
                            onButtonClicked = { it.onClick() },
                            enabled = it.enabled
                        )
                    }
                }
            },
            inputContent = {
                options.forEach { option ->
                    OneLineListItem(
                        modifier = Modifier.fillMaxWidth(),
                        text = option.text,
                        leadingElement = {
                            MegaRadioButton(
                                selected = selectedOption == option,
                                identifier = option.ordinal,
                                onOptionSelected = {
                                    onOptionSelected(option)
                                },
                                enabled = option.enabled
                            )
                        },
                        contentPadding = PaddingValues(0.dp)
                    )
                }
            },
            shape = DSTokens.shapes.extraLarge
        )
    }
}

@Composable
@CombinedThemePreviews
private fun BasicRadioDialogPreview() {
    AndroidThemeForPreviews {
        BasicRadioDialog(
            title = SpannableText("Basic dialog title"),
            options = persistentListOf(
                BasicDialogRadioOption(0, "Option 1"),
                BasicDialogRadioOption(1, "Option 2"),
                BasicDialogRadioOption(2, "Option 3"),
            ),
            selectedOption = BasicDialogRadioOption(1, "Option 2"),
            onOptionSelected = {},
            buttons = persistentListOf(
                BasicDialogButton(
                    text = "Cancel",
                    onClick = {}
                ),
                BasicDialogButton(
                    text = "OK",
                    onClick = {}
                )
            ),
            onDismissRequest = {}
        )
    }
}