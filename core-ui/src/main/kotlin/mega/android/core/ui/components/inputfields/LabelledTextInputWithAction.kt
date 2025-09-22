package mega.android.core.ui.components.inputfields

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.autofill.ContentType
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.ImeOptions
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import mega.android.core.ui.R
import mega.android.core.ui.components.button.SecondaryLargeIconButton
import mega.android.core.ui.components.image.MegaIcon
import mega.android.core.ui.components.spannedTextWithAnnotation
import mega.android.core.ui.model.InputFieldLabelSpanStyle
import mega.android.core.ui.preview.CombinedThemePreviews
import mega.android.core.ui.preview.CombinedThemePreviewsTablet
import mega.android.core.ui.theme.AndroidThemeForPreviews
import mega.android.core.ui.theme.AppTheme
import mega.android.core.ui.theme.spacing.LocalSpacing
import mega.android.core.ui.theme.textColor
import mega.android.core.ui.theme.values.IconColor
import mega.android.core.ui.theme.values.TextColor
import mega.android.core.ui.tokens.theme.DSTokens

/**
 * Labelled text input field with annotated label.
 *
 * @param text The input text to be shown in the text field
 * @param keyboardType The keyboard type to be used in this text field. Note that this input type
 * is honored by keyboard and shows corresponding keyboard but this is not guaranteed. For example,
 * some keyboards may send non-ASCII character even if you set [KeyboardType.Ascii].
 * @param modifier The [Modifier] to be applied to this text field
 * @param imeAction The IME action. This IME action is honored by keyboard and may show specific
 * icons on the keyboard. For example, search icon may be shown if [ImeAction.Search] is specified.
 * When [ImeOptions.singleLine] is false, the keyboard might show return key rather than the action
 * requested here.
 * @param capitalization informs the keyboard whether to automatically capitalize characters,
 * words or sentences. Only applicable to only text based [KeyboardType]s such as
 * [KeyboardType.Text], [KeyboardType.Ascii]. It will not be applied to [KeyboardType]s such as
 * [KeyboardType.Number].
 * @param spannedLabel The optional spanned label to be displayed inside the text field container.
 * @param inputTextAlign The alignment of the text within the lines of the paragraph
 * @param showTrailingIcon whether the component needs to display the default icons. Available default icons:
 *  - Close icon when the text is not empty.
 *  - Eye icon for password mode.
 * @param isPasswordMode Whether the text field is in password mode or not. If true, the text
 * @param successText The optional supporting text to be displayed below the text field
 * @param errorText The optional error text to be displayed below the text field
 * @param maxCharLimit The maximum character to be displayed in the text field.
 * @param contentType The [ContentType] for autofill.
 * @param onValueChanged The callback that is triggered when the text is changed. In this component,
 *   this callback will be called when the user clear all the text by clicking the close icon.
 * @param onFocusChanged The callback that is triggered when the focus state of this text field changes.
 */
@Composable
fun LabelledTextInputWithAction(
    text: String,
    keyboardType: KeyboardType,
    modifier: Modifier = Modifier,
    imeAction: ImeAction = ImeAction.Done,
    capitalization: KeyboardCapitalization = KeyboardCapitalization.None,
    spannedLabel: InputFieldLabelSpanStyle? = null,
    inputTextAlign: TextAlign = TextAlign.Unspecified,
    showTrailingIcon: Boolean = true,
    isPasswordMode: Boolean = false,
    successText: String? = null,
    errorText: String? = null,
    warningText: String? = null,
    maxCharLimit: Int = Int.MAX_VALUE,
    contentType: ContentType? = null,
    onValueChanged: ((String) -> Unit)? = null,
    onFocusChanged: ((Boolean) -> Unit)? = null,
    trailingView: @Composable (() -> Unit)? = null,
) {
    BaseTextField(
        modifier = modifier,
        label = {
            spannedLabel?.let {
                val annotatedLabelString = spannedTextWithAnnotation(
                    it.value,
                    it.spanStyles
                )
                Text(
                    modifier = Modifier.padding(bottom = LocalSpacing.current.x4),
                    text = annotatedLabelString,
                    style = it.baseStyle.copy(
                        color = DSTokens.textColor(textColor = it.baseTextColor)
                    )
                )
            }
        },
        keyboardType = keyboardType,
        imeAction = imeAction,
        capitalization = capitalization,
        text = TextFieldValue(text),
        successText = successText,
        errorText = errorText,
        warningText = warningText,
        inputTextAlign = inputTextAlign,
        isPasswordMode = isPasswordMode,
        showTrailingIcon = showTrailingIcon,
        maxCharLimit = maxCharLimit,
        contentType = contentType,
        onValueChanged = { onValueChanged?.invoke(it.text) },
        onFocusChanged = onFocusChanged,
        trailingView = trailingView,
    )
}

/**
 * Labelled text input field with annotated label.
 *
 * @param textValue The input text to be shown in the text field
 * @param keyboardType The keyboard type to be used in this text field. Note that this input type
 * is honored by keyboard and shows corresponding keyboard but this is not guaranteed. For example,
 * some keyboards may send non-ASCII character even if you set [KeyboardType.Ascii].
 * @param modifier The [Modifier] to be applied to this text field
 * @param imeAction The IME action. This IME action is honored by keyboard and may show specific
 * icons on the keyboard. For example, search icon may be shown if [ImeAction.Search] is specified.
 * When [ImeOptions.singleLine] is false, the keyboard might show return key rather than the action
 * requested here.
 * @param capitalization informs the keyboard whether to automatically capitalize characters,
 * words or sentences. Only applicable to only text based [KeyboardType]s such as
 * [KeyboardType.Text], [KeyboardType.Ascii]. It will not be applied to [KeyboardType]s such as
 * [KeyboardType.Number].
 * @param spannedLabel The optional spanned label to be displayed inside the text field container.
 * @param inputTextAlign The alignment of the text within the lines of the paragraph
 * @param showTrailingIcon whether the component needs to display the default icons. Available default icons:
 *  - Close icon when the text is not empty.
 *  - Eye icon for password mode.
 * @param successText The optional supporting text to be displayed below the text field
 * @param errorText The optional error text to be displayed below the text field
 * @param isPasswordMode Whether the text field is in password mode or not. If true, the text
 * @param maxCharLimit The maximum character to be displayed in the text field.
 * @param contentType The [ContentType] for autofill.
 * @param onValueChanged The callback that is triggered when the text is changed. In this component,
 *   this callback will be called when the user clear all the text by clicking the close icon.
 * @param onFocusChanged The callback that is triggered when the focus state of this text field changes.
 */
@Composable
fun LabelledTextInputWithAction(
    textValue: TextFieldValue,
    keyboardType: KeyboardType,
    modifier: Modifier = Modifier,
    imeAction: ImeAction = ImeAction.Done,
    capitalization: KeyboardCapitalization = KeyboardCapitalization.None,
    spannedLabel: InputFieldLabelSpanStyle? = null,
    inputTextAlign: TextAlign = TextAlign.Unspecified,
    showTrailingIcon: Boolean = true,
    successText: String? = null,
    errorText: String? = null,
    warningText: String? = null,
    isPasswordMode: Boolean = false,
    maxCharLimit: Int = Int.MAX_VALUE,
    contentType: ContentType? = null,
    onValueChanged: ((TextFieldValue) -> Unit)? = null,
    onFocusChanged: ((Boolean) -> Unit)? = null,
    trailingView: @Composable (() -> Unit)? = null,
) {
    BaseTextField(
        modifier = modifier,
        label = {
            spannedLabel?.let {
                val annotatedLabelString = spannedTextWithAnnotation(
                    it.value,
                    it.spanStyles
                )
                Text(
                    modifier = Modifier.padding(bottom = LocalSpacing.current.x4),
                    text = annotatedLabelString,
                    style = it.baseStyle.copy(
                        color = DSTokens.textColor(textColor = it.baseTextColor)
                    )
                )
            }
        },
        keyboardType = keyboardType,
        imeAction = imeAction,
        capitalization = capitalization,
        textValue = textValue,
        successText = successText,
        errorText = errorText,
        warningText = warningText,
        inputTextAlign = inputTextAlign,
        isPasswordMode = isPasswordMode,
        showTrailingIcon = showTrailingIcon,
        maxCharLimit = maxCharLimit,
        contentType = contentType,
        onValueChanged = onValueChanged,
        onFocusChanged = onFocusChanged,
        trailingView = trailingView,
    )
}

@Composable
fun LabelledTextInputAction(
    @DrawableRes iconRes: Int,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .background(
                color = DSTokens.colors.background.surface3,
                shape = RoundedCornerShape(LocalSpacing.current.x8)
            ),
        contentAlignment = Alignment.Center
    ) {
        MegaIcon(
            painter = painterResource(id = iconRes),
            tint = IconColor.Primary
        )
    }
}

@Composable
@CombinedThemePreviews
@CombinedThemePreviewsTablet
private fun LabelledTextInputWithActionPreview() {
    AndroidThemeForPreviews {
        Column {
            LabelledTextInputWithAction(
                text = "Text",
                keyboardType = KeyboardType.Text,
                modifier = Modifier.padding(LocalSpacing.current.x16),
                imeAction = ImeAction.Done,
                capitalization = KeyboardCapitalization.Words,
                spannedLabel = InputFieldLabelSpanStyle(
                    value = "Label",
                    spanStyles = emptyMap(),
                    baseStyle = AppTheme.typography.titleSmall,
                    baseTextColor = TextColor.Primary
                ),
                inputTextAlign = TextAlign.Start,
                showTrailingIcon = true,
                successText = "Success message",
                errorText = "Error message",
                maxCharLimit = 100,
                onValueChanged = {},
                onFocusChanged = {},
                trailingView = {
                    SecondaryLargeIconButton(
                        modifier = Modifier.padding(start = 8.dp),
                        icon = painterResource(id = R.drawable.ic_eye_medium_thin_outline),
                        onClick = {
                        }
                    )
                }
            )
            LabelledTextInputWithAction(
                text = "Text",
                keyboardType = KeyboardType.Text,
                modifier = Modifier.padding(LocalSpacing.current.x16),
                imeAction = ImeAction.Done,
                capitalization = KeyboardCapitalization.Words,
                spannedLabel = InputFieldLabelSpanStyle(
                    value = "Label",
                    spanStyles = emptyMap(),
                    baseStyle = AppTheme.typography.titleSmall,
                    baseTextColor = TextColor.Primary
                ),
                inputTextAlign = TextAlign.Start,
                showTrailingIcon = true,
                errorText = "Error message",
                maxCharLimit = 100,
                onValueChanged = {},
                onFocusChanged = {}
            )
            LabelledTextInputWithAction(
                textValue = TextFieldValue("Text"),
                keyboardType = KeyboardType.Text,
                modifier = Modifier.padding(LocalSpacing.current.x16),
                imeAction = ImeAction.Done,
                capitalization = KeyboardCapitalization.Words,
                spannedLabel = InputFieldLabelSpanStyle(
                    value = "Label",
                    spanStyles = emptyMap(),
                    baseStyle = AppTheme.typography.titleSmall,
                    baseTextColor = TextColor.Primary
                ),
                inputTextAlign = TextAlign.Start,
                showTrailingIcon = true,
                isPasswordMode = true,
                successText = "Success message",
                maxCharLimit = 100,
                onValueChanged = {},
                onFocusChanged = {},
                trailingView = {
                    SecondaryLargeIconButton(
                        modifier = Modifier.padding(start = 8.dp),
                        icon = painterResource(id = R.drawable.ic_eye_medium_thin_outline),
                        onClick = {
                        }
                    )
                }
            )
            LabelledTextInputWithAction(
                textValue = TextFieldValue("Text"),
                keyboardType = KeyboardType.Text,
                modifier = Modifier.padding(LocalSpacing.current.x16),
                imeAction = ImeAction.Done,
                capitalization = KeyboardCapitalization.Words,
                spannedLabel = InputFieldLabelSpanStyle(
                    value = "Label",
                    spanStyles = emptyMap(),
                    baseStyle = AppTheme.typography.titleSmall,
                    baseTextColor = TextColor.Primary
                ),
                inputTextAlign = TextAlign.Start,
                showTrailingIcon = true,
                isPasswordMode = true,
                errorText = "Error message",
                maxCharLimit = 100,
                onValueChanged = {},
                onFocusChanged = {}
            )
        }
    }
}

@CombinedThemePreviews
@Composable
private fun LabelledTextInputActionPreview() {
    AndroidThemeForPreviews {
        LabelledTextInputAction(
            iconRes = R.drawable.ic_eye_medium_thin_outline
        )
    }
}
