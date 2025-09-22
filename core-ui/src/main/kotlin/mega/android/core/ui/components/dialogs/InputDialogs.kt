package mega.android.core.ui.components.dialogs

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import mega.android.core.ui.components.MegaText
import mega.android.core.ui.components.dialogs.internal.MegaBasicDialogContent
import mega.android.core.ui.components.dialogs.internal.MegaBasicDialogFlowRow
import mega.android.core.ui.components.inputfields.TextInputField
import mega.android.core.ui.preview.CombinedThemePreviews
import mega.android.core.ui.theme.AndroidThemeForPreviews
import mega.android.core.ui.theme.AppTheme
import mega.android.core.ui.theme.values.TextColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BasicInputDialog(
    title: String,
    onValueChange: (String) -> Unit,
    positiveButtonText: String,
    onPositiveButtonClicked: () -> Unit,
    modifier: Modifier = Modifier,
    inputValue: String = "",
    description: String? = null,
    inputLabel: String? = null,
    errorText: String? = null,
    negativeButtonText: String? = null,
    onNegativeButtonClicked: (() -> Unit)? = null,
    dialogProperties: MegaDialogProperties = MegaDialogProperties.default,
    keyboardType: KeyboardType = KeyboardType.Text,
    inputTextAlign: TextAlign = TextAlign.Unspecified,
    placeholder: String? = null,
    isAutoShowKeyboard: Boolean = true,
    suffix: @Composable (() -> Unit)? = null,
    onDismiss: () -> Unit = {},
) {
    var textFieldValue by rememberSaveable(
        inputs = arrayOf(inputValue),
        stateSaver = TextFieldValue.Saver
    ) {
        mutableStateOf(value = TextFieldValue(inputValue, TextRange(inputValue.length)))
    }

    BasicInputDialog(
        title = title,
        inputValue = textFieldValue,
        onValueChange = {
            textFieldValue = it
            onValueChange.invoke(it.text)
        },
        positiveButtonText = positiveButtonText,
        onPositiveButtonClicked = onPositiveButtonClicked,
        description = description,
        inputLabel = inputLabel,
        errorText = errorText,
        negativeButtonText = negativeButtonText,
        onNegativeButtonClicked = onNegativeButtonClicked,
        dialogProperties = dialogProperties,
        keyboardType = keyboardType,
        inputTextAlign = inputTextAlign,
        placeholder = placeholder,
        isAutoShowKeyboard = isAutoShowKeyboard,
        suffix = suffix,
        onDismiss = onDismiss,
        modifier = modifier,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BasicInputDialog(
    title: String,
    inputValue: TextFieldValue,
    onValueChange: (TextFieldValue) -> Unit,
    positiveButtonText: String,
    onPositiveButtonClicked: () -> Unit,
    modifier: Modifier = Modifier,
    description: String? = null,
    inputLabel: String? = null,
    errorText: String? = null,
    negativeButtonText: String? = null,
    onNegativeButtonClicked: (() -> Unit)? = null,
    dialogProperties: MegaDialogProperties = MegaDialogProperties.default,
    keyboardType: KeyboardType = KeyboardType.Text,
    inputTextAlign: TextAlign = TextAlign.Unspecified,
    placeholder: String? = null,
    isAutoShowKeyboard: Boolean = true,
    suffix: @Composable (() -> Unit)? = null,
    onDismiss: () -> Unit = {},
) {
    val focusRequester = remember { FocusRequester() }
    var textFieldValue by rememberSaveable(
        inputs = arrayOf(inputValue),
        stateSaver = TextFieldValue.Saver
    ) {
        mutableStateOf(value = inputValue)
    }

    // Update textFieldValue when inputValue changes
    LaunchedEffect(inputValue) {
        textFieldValue = inputValue
    }

    // Request focus when dialog opens if auto-show keyboard is enabled
    SideEffect {
        if (isAutoShowKeyboard) {
            focusRequester.requestFocus()
        }
    }

    BasicAlertDialog(
        onDismissRequest = onDismiss,
        modifier = modifier,
        properties = DialogProperties(
            dismissOnBackPress = dialogProperties.dismissOnBackPress,
            dismissOnClickOutside = dialogProperties.dismissOnClickOutside
        )
    ) {
        MegaBasicDialogContent(
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
            buttons = {
                MegaBasicDialogFlowRow {
                    if (negativeButtonText != null && onNegativeButtonClicked != null) {
                        DialogButton(
                            buttonText = negativeButtonText,
                            onButtonClicked = onNegativeButtonClicked,
                            enabled = dialogProperties.isNegativeButtonEnabled
                        )
                    }
                    DialogButton(
                        buttonText = positiveButtonText,
                        onButtonClicked = onPositiveButtonClicked,
                        enabled = dialogProperties.isPositiveButtonEnabled
                    )
                }
            },
            inputContent = {
                TextInputField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .focusRequester(focusRequester)
                        .onFocusChanged {
                            if (it.isFocused && isAutoShowKeyboard) {
                                textFieldValue = textFieldValue.copy(
                                    selection = TextRange(0, textFieldValue.text.length)
                                )
                            }
                        },
                    textFieldValue = textFieldValue,
                    label = inputLabel,
                    onValueChanged = onValueChange,
                    keyboardType = keyboardType,
                    errorText = errorText,
                    placeholder = placeholder,
                    suffix = suffix,
                    inputTextAlign = inputTextAlign
                )
            },
            shape = RoundedCornerShape(28.dp)
        )
    }
}

@CombinedThemePreviews
@Composable
private fun BasicInputDialogPreview() {
    AndroidThemeForPreviews {
        BasicInputDialog(
            title = "Basic input dialog title",
            description = "Basic input dialog description",
            inputValue = "",
            onValueChange = { },
            inputLabel = "Sample label",
            positiveButtonText = "Action 1",
            onPositiveButtonClicked = {},
            negativeButtonText = "Action 2",
            onNegativeButtonClicked = {},
        )
    }
}

@CombinedThemePreviews
@Composable
private fun BasicInputDialogWithPlaceholderPreview() {
    AndroidThemeForPreviews {
        BasicInputDialog(
            title = "Basic input dialog title",
            inputValue = "",
            onValueChange = { },
            placeholder = "Placeholder text",
            positiveButtonText = "Action 1",
            onPositiveButtonClicked = {},
            negativeButtonText = "Action 2",
            onNegativeButtonClicked = {},
        )
    }
}

@CombinedThemePreviews
@Composable
private fun BasicInputDialogWithSuffixPreview() {
    AndroidThemeForPreviews {
        BasicInputDialog(
            title = "Basic input dialog title",
            inputValue = "Value",
            onValueChange = { },
            suffix = {
                MegaText(
                    text = "Suffix",
                    style = AppTheme.typography.bodyLarge,
                    textColor = TextColor.Placeholder,
                )
            },
            positiveButtonText = "Action 1",
            onPositiveButtonClicked = {},
            negativeButtonText = "Action 2",
            onNegativeButtonClicked = {},
            inputTextAlign = TextAlign.End,
            isAutoShowKeyboard = true
        )
    }
}