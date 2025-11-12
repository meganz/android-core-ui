package mega.android.core.ui.components.inputfields

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.autofill.ContentType
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.contentType
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.ImeOptions
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import mega.android.core.ui.R
import mega.android.core.ui.components.MegaText
import mega.android.core.ui.components.inputFieldHeight
import mega.android.core.ui.components.spannedTextWithAnnotation
import mega.android.core.ui.components.visualtransformation.CCExpiryDateVisualTransformation
import mega.android.core.ui.model.InputFieldLabelSpanStyle
import mega.android.core.ui.model.MegaSpanStyle
import mega.android.core.ui.model.SpanIndicator
import mega.android.core.ui.model.SpanStyleWithAnnotation
import mega.android.core.ui.preview.CombinedThemePreviews
import mega.android.core.ui.theme.AndroidThemeForPreviews
import mega.android.core.ui.theme.AppTheme
import mega.android.core.ui.theme.spacing.LocalSpacing
import mega.android.core.ui.theme.textColor
import mega.android.core.ui.theme.values.TextColor
import mega.android.core.ui.tokens.theme.DSTokens

/**
 * A read only text input field with an optional custom trailing icon
 *
 * @param text the input text to be shown in the text field
 * @param modifier the [Modifier] to be applied to this text field
 * @param isPasswordMode whether in password mode
 * @param label the optional label to be displayed inside the text field container
 * @param inputTextAlign the alignment of the text within the lines of the paragraph
 * @param showDefaultTrailingIcon whether the component needs to display the default icons. Available default icons:
 *  - Close icon when the text is not empty.
 *  - Eye icon for password mode.
 * @param trailingIcon the optional trailing icon to be displayed at the end of the text field container
 * @param successText the optional supporting text to be displayed below the text field
 * @param errorText the optional error text to be displayed below the text field
 * @param optionalLabelText the optional label to be displayed above the text field
 * @param onValueChanged the callback that is triggered when the text is changed. In this component,
 *   this callback will be called when the user clear all the text by clicking the close icon.
 */
@Composable
fun ReadOnlyTextInputField(
    text: String,
    modifier: Modifier = Modifier,
    isPasswordMode: Boolean = false,
    label: String? = null,
    inputTextAlign: TextAlign = TextAlign.Unspecified,
    showDefaultTrailingIcon: Boolean = false,
    trailingIcon: @Composable (() -> Unit)? = null,
    successText: String? = null,
    errorText: String? = null,
    warningText: String? = null,
    optionalLabelText: String? = null,
    onValueChanged: ((String) -> Unit)? = null,
) {
    BaseReadOnlyTextField(
        modifier = modifier,
        label = label,
        text = text,
        successText = successText,
        errorText = errorText,
        warningText = warningText,
        inputTextAlign = inputTextAlign,
        isPasswordMode = isPasswordMode,
        optionalLabelText = optionalLabelText,
        onValueChanged = onValueChanged,
        showDefaultTrailingIcon = showDefaultTrailingIcon,
        trailingIcon = trailingIcon
    )
}

/**
 * Text input field
 * @param modifier
 * @param onValueChanged
 * @param errorText
 */
@Composable
fun TextInputField(
    modifier: Modifier,
    keyboardType: KeyboardType,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    imeAction: ImeAction = ImeAction.Done,
    capitalization: KeyboardCapitalization = KeyboardCapitalization.Words,
    text: String = "",
    placeholder: String? = null,
    label: String? = null,
    inputTextAlign: TextAlign = TextAlign.Unspecified,
    showTrailingIcon: Boolean = true,
    successText: String? = null,
    errorText: String? = null,
    warningText: String? = null,
    maxCharLimit: Int = Int.MAX_VALUE,
    optionalLabelText: String? = null,
    contentType: ContentType? = null,
    suffix: @Composable (() -> Unit)? = null,
    onValueChanged: ((String) -> Unit)? = null,
    onFocusChanged: ((Boolean) -> Unit)? = null,
) {
    val spacing = LocalSpacing.current
    BaseTextField(
        modifier = modifier,
        label = {
            label?.let {
                Row(
                    modifier = Modifier
                        .wrapContentWidth()
                        .padding(bottom = spacing.x4)
                ) {
                    Text(
                        text = label,
                        style = AppTheme.typography.titleSmall,
                        color = DSTokens.colors.text.primary
                    )
                    optionalLabelText?.let {
                        Text(
                            modifier = Modifier.padding(start = spacing.x8),
                            text = optionalLabelText,
                            style = AppTheme.typography.bodyMedium,
                            color = DSTokens.colors.text.secondary
                        )
                    }
                }
            }
        },
        placeholder = placeholder,
        suffix = suffix,
        visualTransformation = visualTransformation,
        keyboardType = keyboardType,
        imeAction = imeAction,
        capitalization = capitalization,
        text = text,
        successText = successText,
        errorText = errorText,
        warningText = warningText,
        inputTextAlign = inputTextAlign,
        isPasswordMode = false,
        showTrailingIcon = showTrailingIcon,
        maxCharLimit = maxCharLimit,
        contentType = contentType,
        onValueChanged = onValueChanged,
        onFocusChanged = onFocusChanged
    )
}

/**
 * Text input field
 * @param modifier
 * @param onValueChanged
 * @param errorText
 */
@Composable
fun TextInputField(
    modifier: Modifier,
    keyboardType: KeyboardType,
    textFieldValue: TextFieldValue,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    imeAction: ImeAction = ImeAction.Done,
    capitalization: KeyboardCapitalization = KeyboardCapitalization.Words,
    label: String? = null,
    inputTextAlign: TextAlign = TextAlign.Unspecified,
    showTrailingIcon: Boolean = true,
    trailingView: @Composable (() -> Unit)? = null,
    suffix: @Composable (() -> Unit)? = null,
    isPasswordMode: Boolean = false,
    successText: String? = null,
    errorText: String? = null,
    warningText: String? = null,
    placeholder: String? = null,
    maxCharLimit: Int = Int.MAX_VALUE,
    optionalLabelText: String? = null,
    contentType: ContentType? = null,
    onValueChanged: ((TextFieldValue) -> Unit)? = null,
    onFocusChanged: ((Boolean) -> Unit)? = null,
) {
    val spacing = LocalSpacing.current

    BaseTextField(
        modifier = modifier,
        label = {
            label?.let {
                Row(
                    modifier = Modifier
                        .wrapContentWidth()
                        .padding(bottom = spacing.x4)
                ) {
                    Text(
                        text = label,
                        style = AppTheme.typography.titleSmall,
                        color = DSTokens.colors.text.primary
                    )
                    optionalLabelText?.let {
                        Text(
                            modifier = Modifier.padding(start = spacing.x8),
                            text = optionalLabelText,
                            style = AppTheme.typography.bodyMedium,
                            color = DSTokens.colors.text.secondary
                        )
                    }
                }
            }
        },
        visualTransformation = visualTransformation,
        keyboardType = keyboardType,
        imeAction = imeAction,
        capitalization = capitalization,
        textValue = textFieldValue,
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
        placeholder = placeholder,
        suffix = suffix
    )
}

/**
 * Password text input field with
 * @param modifier
 * @param onValueChanged
 * @param errorText
 */
@Composable
fun PasswordTextInputField(
    modifier: Modifier,
    label: String?,
    text: String = "",
    inputTextAlign: TextAlign = TextAlign.Unspecified,
    showTrailingIcon: Boolean = true,
    imeAction: ImeAction = ImeAction.Done,
    successText: String? = null,
    errorText: String? = null,
    warningText: String? = null,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    maxCharLimit: Int = Int.MAX_VALUE,
    contentType: ContentType = ContentType.Password,
    onValueChanged: ((String) -> Unit)? = null,
    onFocusChanged: ((Boolean) -> Unit)? = null,
) = BaseTextField(
    modifier = modifier,
    label = label?.let {
        {
            Text(
                modifier = Modifier
                    .wrapContentWidth()
                    .padding(bottom = LocalSpacing.current.x4),
                text = label,
                style = AppTheme.typography.titleSmall,
                color = DSTokens.colors.text.primary
            )
        }
    },
    text = text,
    keyboardType = KeyboardType.Password,
    keyboardActions = keyboardActions,
    imeAction = imeAction,
    capitalization = KeyboardCapitalization.None,
    successText = successText,
    errorText = errorText,
    warningText = warningText,
    inputTextAlign = inputTextAlign,
    isPasswordMode = true,
    showTrailingIcon = showTrailingIcon,
    maxCharLimit = maxCharLimit,
    contentType = contentType,
    onValueChanged = onValueChanged,
    onFocusChanged = onFocusChanged,
)

/**
 * Text input field for expiration date.
 *
 * @param text The input text to be shown in the text field
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
 * @param onValueChanged The callback that is triggered when the text is changed. In this component,
 *   this callback will be called when the user clear all the text by clicking the close icon.
 * @param onFocusChanged The callback that is triggered when the focus state of this text field changes.
 */
@Composable
fun ExpirationDateInputField(
    text: String,
    modifier: Modifier = Modifier,
    imeAction: ImeAction = ImeAction.Done,
    capitalization: KeyboardCapitalization = KeyboardCapitalization.Words,
    spannedLabel: InputFieldLabelSpanStyle? = null,
    inputTextAlign: TextAlign = TextAlign.Unspecified,
    showTrailingIcon: Boolean = true,
    successText: String? = null,
    errorText: String? = null,
    warningText: String? = null,
    onValueChanged: ((String) -> Unit)? = null,
    onFocusChanged: ((Boolean) -> Unit)? = null,
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
        visualTransformation = CCExpiryDateVisualTransformation(
            hintColor = DSTokens.textColor(TextColor.Placeholder)
        ),
        keyboardType = KeyboardType.Number,
        imeAction = imeAction,
        capitalization = capitalization,
        text = text,
        successText = successText,
        errorText = errorText,
        warningText = warningText,
        inputTextAlign = inputTextAlign,
        isPasswordMode = false,
        showTrailingIcon = showTrailingIcon,
        maxCharLimit = 4,
        onValueChanged = onValueChanged,
        onFocusChanged = onFocusChanged
    )
}

/**
 * Text input field with annotated label.
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
 * @param successText The optional supporting text to be displayed below the text field
 * @param errorText The optional error text to be displayed below the text field
 * @param maxCharLimit The maximum character to be displayed in the text field.
 * @param contentType The [ContentType] for autofill.
 * @param onValueChanged The callback that is triggered when the text is changed. In this component,
 *   this callback will be called when the user clear all the text by clicking the close icon.
 * @param onFocusChanged The callback that is triggered when the focus state of this text field changes.
 */
@Composable
fun AnnotatedLabelTextInputField(
    text: String,
    keyboardType: KeyboardType,
    modifier: Modifier = Modifier,
    imeAction: ImeAction = ImeAction.Done,
    capitalization: KeyboardCapitalization = KeyboardCapitalization.Words,
    spannedLabel: InputFieldLabelSpanStyle? = null,
    inputTextAlign: TextAlign = TextAlign.Unspecified,
    showTrailingIcon: Boolean = true,
    successText: String? = null,
    errorText: String? = null,
    warningText: String? = null,
    maxCharLimit: Int = Int.MAX_VALUE,
    contentType: ContentType? = null,
    onValueChanged: ((String) -> Unit)? = null,
    onFocusChanged: ((Boolean) -> Unit)? = null,
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
        text = text,
        successText = successText,
        errorText = errorText,
        warningText = warningText,
        inputTextAlign = inputTextAlign,
        isPasswordMode = false,
        showTrailingIcon = showTrailingIcon,
        maxCharLimit = maxCharLimit,
        contentType = contentType,
        onValueChanged = onValueChanged,
        onFocusChanged = onFocusChanged
    )
}

/**
 * Text input field with annotated label.
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
 * @param maxCharLimit The maximum character to be displayed in the text field.
 * @param contentType The [ContentType] for autofill.
 * @param onValueChanged The callback that is triggered when the text is changed. In this component,
 *   this callback will be called when the user clear all the text by clicking the close icon.
 * @param onFocusChanged The callback that is triggered when the focus state of this text field changes.
 */
@Composable
fun AnnotatedLabelTextInputField(
    textValue: TextFieldValue,
    keyboardType: KeyboardType,
    modifier: Modifier = Modifier,
    imeAction: ImeAction = ImeAction.Done,
    capitalization: KeyboardCapitalization = KeyboardCapitalization.Words,
    spannedLabel: InputFieldLabelSpanStyle? = null,
    inputTextAlign: TextAlign = TextAlign.Unspecified,
    showTrailingIcon: Boolean = true,
    successText: String? = null,
    errorText: String? = null,
    warningText: String? = null,
    maxCharLimit: Int = Int.MAX_VALUE,
    contentType: ContentType? = null,
    onValueChanged: ((TextFieldValue) -> Unit)? = null,
    onFocusChanged: ((Boolean) -> Unit)? = null,
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
        isPasswordMode = false,
        showTrailingIcon = showTrailingIcon,
        maxCharLimit = maxCharLimit,
        contentType = contentType,
        onValueChanged = onValueChanged,
        onFocusChanged = onFocusChanged
    )
}

@Composable
internal fun BaseTextField(
    modifier: Modifier,
    textValue: TextFieldValue,
    isPasswordMode: Boolean,
    showTrailingIcon: Boolean,
    keyboardType: KeyboardType,
    imeAction: ImeAction,
    capitalization: KeyboardCapitalization,
    successText: String?,
    errorText: String?,
    warningText: String?,
    inputTextAlign: TextAlign,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    maxCharLimit: Int = Int.MAX_VALUE,
    placeholder: String? = null,
    contentType: ContentType? = null,
    label: @Composable (() -> Unit)? = null,
    suffix: @Composable (() -> Unit)? = null,
    onValueChanged: ((TextFieldValue) -> Unit)? = null,
    onFocusChanged: ((Boolean) -> Unit)? = null,
    trailingView: @Composable (() -> Unit)? = null,
) {
    val spacing = LocalSpacing.current
    val interactionSource: MutableInteractionSource = remember { MutableInteractionSource() }

    var isFocused by remember { mutableStateOf(false) }
    var showPassword by rememberSaveable { mutableStateOf(false) }
    val focusedColor = when {
        successText.isNullOrBlank().not() -> DSTokens.colors.support.success
        errorText != null -> DSTokens.colors.support.error
        warningText != null -> DSTokens.colors.support.warning
        else -> DSTokens.colors.border.strongSelected
    }
    val unfocusedColor = when {
        successText.isNullOrBlank().not() -> DSTokens.colors.support.success
        errorText != null -> DSTokens.colors.support.error
        warningText != null -> DSTokens.colors.support.warning
        else -> DSTokens.colors.border.strong
    }
    val colors = OutlinedTextFieldDefaults.colors(
        focusedTextColor = DSTokens.colors.text.primary,
        unfocusedTextColor = DSTokens.colors.text.primary,
        cursorColor = DSTokens.colors.text.primary,
        errorCursorColor = DSTokens.colors.text.primary,
        selectionColors = TextSelectionColors(
            handleColor = DSTokens.colors.text.primary,
            backgroundColor = DSTokens.colors.text.primary.copy(alpha = 0.4f),
        ),
        focusedBorderColor = focusedColor,
        unfocusedBorderColor = unfocusedColor,
        errorBorderColor = DSTokens.colors.support.error,
        errorTextColor = DSTokens.colors.text.primary,
        focusedPlaceholderColor = DSTokens.colors.text.primary,
        unfocusedPlaceholderColor = DSTokens.colors.text.placeholder,
        disabledTextColor = DSTokens.colors.text.disabled,
        disabledContainerColor = DSTokens.colors.button.disabled,
        disabledBorderColor = DSTokens.colors.border.disabled,
    )

    Column(
        modifier = modifier
            .fillMaxWidth()
    ) {
        label?.invoke()

        Row(verticalAlignment = Alignment.CenterVertically) {
            OutlinedTextField(
                modifier = Modifier
                    .weight(1f)
                    .heightIn(min = inputFieldHeight)
                    .onFocusChanged {
                        isFocused = it.isFocused
                        onFocusChanged?.invoke(it.isFocused)
                    }
                    .semantics {
                        contentType?.let {
                            this.contentType = it
                        }
                    },
                keyboardOptions = KeyboardOptions(
                    keyboardType = keyboardType,
                    autoCorrectEnabled = isPasswordMode.not(),
                    imeAction = imeAction,
                    capitalization = capitalization
                ),
                placeholder = placeholder?.let {
                    {
                        MegaText(
                            text = it,
                            style = AppTheme.typography.bodyLarge,
                            textColor = TextColor.Placeholder
                        )
                    }
                },
                keyboardActions = keyboardActions,
                value = textValue,
                onValueChange = { textFieldValue ->
                    val newTextValue = when {
                        keyboardType == KeyboardType.Number -> {
                            val digits = textFieldValue.text.filter { it.isDigit() }
                            if (textFieldValue.text.length > maxCharLimit && digits.isNotBlank()) {
                                digits.substring(0, digits.length.coerceAtMost(maxCharLimit))
                            } else {
                                digits
                            }
                        }

                        else -> {
                            if (textFieldValue.text.length > maxCharLimit) {
                                textFieldValue.text.substring(0, maxCharLimit)
                            } else {
                                textFieldValue.text
                            }
                        }
                    }
                    onValueChanged?.invoke(textFieldValue.copy(text = newTextValue))
                },
                colors = colors,
                shape = RoundedCornerShape(8.dp),
                interactionSource = interactionSource,
                suffix = suffix,
                singleLine = true,
                textStyle = AppTheme.typography.bodyLarge.copy(textAlign = inputTextAlign),
                isError = successText.isNullOrBlank() && errorText != null,
                visualTransformation = if (!isPasswordMode) {
                    visualTransformation
                } else {
                    if (showPassword) VisualTransformation.None else PasswordVisualTransformation()
                },
                trailingIcon = if (textValue.text.isNotEmpty() && showTrailingIcon) {
                    when {
                        isPasswordMode.not() && isFocused -> {
                            {
                                Icon(
                                    modifier = Modifier
                                        .clickable {
                                            onValueChanged?.invoke(TextFieldValue(""))
                                        },
                                    painter = painterResource(id = R.drawable.ic_close_medium_thin_outline),
                                    tint = DSTokens.colors.icon.primary,
                                    contentDescription = "Clear Text"
                                )
                            }
                        }

                        isPasswordMode -> {
                            val eyeIcon =
                                if (showPassword) R.drawable.ic_eye_off_medium_thin_outline else R.drawable.ic_eye_medium_thin_outline
                            {
                                Icon(
                                    modifier = Modifier
                                        .padding(horizontal = spacing.x8)
                                        .clickable { showPassword = !showPassword },
                                    painter = painterResource(id = eyeIcon),
                                    tint = DSTokens.colors.icon.secondary,
                                    contentDescription = "Show Password"
                                )
                            }
                        }

                        else -> {
                            null
                        }
                    }
                } else {
                    null
                }
            )
            trailingView?.invoke()
        }

        val footerModifier = Modifier
            .padding(top = spacing.x4)
            .fillMaxWidth()

        when {
            !successText.isNullOrBlank() -> {
                HelpTextSuccess(
                    modifier = footerModifier,
                    text = successText
                )
            }

            !errorText.isNullOrBlank() -> {
                HelpTextError(
                    modifier = footerModifier,
                    text = errorText
                )
            }

            !warningText.isNullOrBlank() -> {
                HelpTextWarning(
                    modifier = footerModifier,
                    text = warningText
                )
            }
        }
    }
}

@Composable
internal fun BaseTextField(
    modifier: Modifier,
    text: String,
    isPasswordMode: Boolean,
    showTrailingIcon: Boolean,
    keyboardType: KeyboardType,
    imeAction: ImeAction,
    capitalization: KeyboardCapitalization,
    successText: String?,
    errorText: String?,
    warningText: String?,
    inputTextAlign: TextAlign,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    maxCharLimit: Int = Int.MAX_VALUE,
    contentType: ContentType? = null,
    placeholder: String? = null,
    label: @Composable (() -> Unit)? = null,
    suffix: @Composable (() -> Unit)? = null,
    onValueChanged: ((String) -> Unit)? = null,
    onFocusChanged: ((Boolean) -> Unit)? = null,
    trailingView: @Composable (() -> Unit)? = null,
) {
    val spacing = LocalSpacing.current
    val interactionSource: MutableInteractionSource = remember { MutableInteractionSource() }
    var baseText by rememberSaveable(text) { mutableStateOf(text) }
    var isFocused by remember { mutableStateOf(false) }
    var showPassword by rememberSaveable { mutableStateOf(false) }
    val focusedColor = when {
        successText.isNullOrBlank().not() -> DSTokens.colors.support.success
        errorText != null -> DSTokens.colors.support.error
        warningText != null -> DSTokens.colors.support.warning
        else -> DSTokens.colors.border.strongSelected
    }
    val unfocusedColor = when {
        successText.isNullOrBlank().not() -> DSTokens.colors.support.success
        errorText != null -> DSTokens.colors.support.error
        warningText != null -> DSTokens.colors.support.warning
        else -> DSTokens.colors.border.strong
    }
    val colors = OutlinedTextFieldDefaults.colors(
        focusedTextColor = DSTokens.colors.text.primary,
        unfocusedTextColor = DSTokens.colors.text.primary,
        cursorColor = DSTokens.colors.text.primary,
        errorCursorColor = DSTokens.colors.text.primary,
        selectionColors = TextSelectionColors(
            handleColor = DSTokens.colors.text.primary,
            backgroundColor = DSTokens.colors.text.primary.copy(alpha = 0.4f),
        ),
        focusedBorderColor = focusedColor,
        unfocusedBorderColor = unfocusedColor,
        errorBorderColor = DSTokens.colors.support.error,
        errorTextColor = DSTokens.colors.text.primary,
        focusedPlaceholderColor = DSTokens.colors.text.primary,
        unfocusedPlaceholderColor = DSTokens.colors.text.placeholder,
        disabledTextColor = DSTokens.colors.text.disabled,
        disabledContainerColor = DSTokens.colors.button.disabled,
        disabledBorderColor = DSTokens.colors.border.disabled,
    )

    Column(
        modifier = modifier
            .fillMaxWidth()
    ) {
        label?.invoke()

        Row(verticalAlignment = Alignment.CenterVertically) {
            OutlinedTextField(
                modifier = Modifier
                    .weight(1f)
                    .heightIn(min = inputFieldHeight)
                    .onFocusChanged {
                        isFocused = it.isFocused
                        onFocusChanged?.invoke(it.isFocused)
                    }
                    .semantics {
                        contentType?.let {
                            this.contentType = it
                        }
                    },
                keyboardOptions = KeyboardOptions(
                    keyboardType = keyboardType,
                    autoCorrectEnabled = isPasswordMode.not(),
                    imeAction = imeAction,
                    capitalization = capitalization
                ),
                keyboardActions = keyboardActions,
                value = baseText,
                onValueChange = { newValue ->
                    baseText = when {
                        keyboardType == KeyboardType.Number -> {
                            val digits = newValue.filter { it.isDigit() }
                            if (newValue.length > maxCharLimit && digits.isNotBlank()) {
                                digits.substring(0, digits.length.coerceAtMost(maxCharLimit))
                            } else {
                                digits
                            }
                        }

                        else -> {
                            if (newValue.length > maxCharLimit) {
                                newValue.substring(0, maxCharLimit)
                            } else {
                                newValue
                            }
                        }
                    }
                    onValueChanged?.invoke(baseText)
                },
                placeholder = placeholder?.let {
                    {
                        MegaText(
                            text = it,
                            style = AppTheme.typography.bodyLarge,
                            textColor = TextColor.Placeholder
                        )
                    }
                },
                suffix = suffix,
                colors = colors,
                shape = RoundedCornerShape(8.dp),
                interactionSource = interactionSource,
                singleLine = true,
                textStyle = AppTheme.typography.bodyLarge.copy(textAlign = inputTextAlign),
                isError = successText.isNullOrBlank() && errorText != null,
                visualTransformation = if (!isPasswordMode) {
                    visualTransformation
                } else {
                    if (showPassword) VisualTransformation.None else PasswordVisualTransformation()
                },
                trailingIcon = if (baseText.isNotEmpty() && showTrailingIcon) {
                    when {
                        isPasswordMode.not() && isFocused -> {
                            {
                                Icon(
                                    modifier = Modifier
                                        .clickable {
                                            baseText = ""
                                            onValueChanged?.invoke("")
                                        },
                                    painter = painterResource(id = R.drawable.ic_close_medium_thin_outline),
                                    tint = DSTokens.colors.icon.primary,
                                    contentDescription = "Clear Text"
                                )
                            }
                        }

                        isPasswordMode -> {
                            val eyeIcon =
                                if (showPassword) R.drawable.ic_eye_off_medium_thin_outline else R.drawable.ic_eye_medium_thin_outline
                            {
                                Icon(
                                    modifier = Modifier
                                        .padding(horizontal = spacing.x8)
                                        .clickable { showPassword = !showPassword },
                                    painter = painterResource(id = eyeIcon),
                                    tint = DSTokens.colors.icon.secondary,
                                    contentDescription = "Show Password"
                                )
                            }
                        }

                        else -> {
                            null
                        }
                    }
                } else {
                    null
                }
            )
            trailingView?.invoke()
        }

        val footerModifier = Modifier
            .padding(top = spacing.x4)
            .fillMaxWidth()

        when {
            !successText.isNullOrBlank() -> {
                HelpTextSuccess(
                    modifier = footerModifier,
                    text = successText
                )
            }

            !errorText.isNullOrBlank() -> {
                HelpTextError(
                    modifier = footerModifier,
                    text = errorText
                )
            }

            !warningText.isNullOrBlank() -> {
                HelpTextWarning(
                    modifier = footerModifier,
                    text = warningText
                )
            }
        }
    }
}

@Composable
private fun BaseReadOnlyTextField(
    text: String,
    isPasswordMode: Boolean,
    label: String?,
    successText: String?,
    errorText: String?,
    warningText: String?,
    inputTextAlign: TextAlign,
    modifier: Modifier = Modifier,
    showDefaultTrailingIcon: Boolean = false,
    optionalLabelText: String? = null,
    onValueChanged: ((String) -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
) {
    val spacing = LocalSpacing.current
    val interactionSource: MutableInteractionSource = remember { MutableInteractionSource() }
    var baseText by rememberSaveable(text) { mutableStateOf(text) }
    var showPassword by rememberSaveable { mutableStateOf(false) }
    val focusedColor = when {
        successText.isNullOrBlank().not() -> DSTokens.colors.support.success
        errorText != null -> DSTokens.colors.support.error
        warningText != null -> DSTokens.colors.support.warning
        else -> DSTokens.colors.border.strong
    }
    val colors = OutlinedTextFieldDefaults.colors(
        focusedTextColor = DSTokens.colors.text.primary,
        unfocusedTextColor = DSTokens.colors.text.primary,
        cursorColor = DSTokens.colors.text.primary,
        errorCursorColor = DSTokens.colors.text.primary,
        selectionColors = TextSelectionColors(
            handleColor = DSTokens.colors.text.primary,
            backgroundColor = DSTokens.colors.text.primary.copy(alpha = 0.4f),
        ),
        focusedBorderColor = focusedColor,
        unfocusedBorderColor = focusedColor,
        errorBorderColor = DSTokens.colors.support.error,
        errorTextColor = DSTokens.colors.text.primary,
        focusedPlaceholderColor = DSTokens.colors.text.primary,
        unfocusedPlaceholderColor = DSTokens.colors.text.placeholder,
        disabledTextColor = DSTokens.colors.text.disabled,
        disabledContainerColor = DSTokens.colors.button.disabled,
        disabledBorderColor = DSTokens.colors.border.disabled,
        focusedContainerColor = DSTokens.colors.background.pageBackground,
        errorContainerColor = DSTokens.colors.background.pageBackground,
        unfocusedContainerColor = DSTokens.colors.background.pageBackground
    )

    Column(modifier = modifier.fillMaxWidth()) {
        label?.let {
            Row(
                modifier = Modifier
                    .wrapContentWidth()
                    .padding(bottom = spacing.x4)
            ) {
                Text(
                    text = label,
                    style = AppTheme.typography.titleSmall,
                    color = DSTokens.colors.text.primary
                )
                optionalLabelText?.let {
                    Text(
                        modifier = Modifier.padding(start = spacing.x8),
                        text = optionalLabelText,
                        style = AppTheme.typography.bodyMedium,
                        color = DSTokens.colors.text.secondary
                    )
                }
            }
        }

        ReadOnlyOutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = inputFieldHeight),
            value = baseText,
            colors = colors,
            shape = RoundedCornerShape(8.dp),
            interactionSource = interactionSource,
            singleLine = true,
            textStyle = AppTheme.typography.bodyLarge.copy(textAlign = inputTextAlign),
            isError = successText.isNullOrBlank() && errorText != null,
            visualTransformation = if (!isPasswordMode || showPassword) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = if (trailingIcon != null) {
                {
                    trailingIcon()
                }
            } else if (baseText.isNotEmpty() && showDefaultTrailingIcon) {
                when {
                    isPasswordMode.not() -> {
                        {
                            Icon(
                                modifier = Modifier
                                    .clickable {
                                        baseText = ""
                                        onValueChanged?.invoke("")
                                    },
                                painter = painterResource(id = R.drawable.ic_close_medium_thin_outline),
                                tint = DSTokens.colors.icon.primary,
                                contentDescription = "Clear Text"
                            )
                        }
                    }

                    isPasswordMode -> {
                        val eyeIcon =
                            if (showPassword) R.drawable.ic_eye_off_medium_thin_outline else R.drawable.ic_eye_medium_thin_outline
                        {
                            Icon(
                                modifier = Modifier
                                    .padding(horizontal = spacing.x8)
                                    .clickable { showPassword = !showPassword },
                                painter = painterResource(id = eyeIcon),
                                tint = DSTokens.colors.icon.secondary,
                                contentDescription = "Show Password"
                            )
                        }
                    }

                    else -> {
                        null
                    }
                }
            } else {
                null
            }
        )

        val footerModifier = Modifier
            .padding(top = spacing.x4)
            .fillMaxWidth()

        when {
            !successText.isNullOrBlank() -> {
                HelpTextSuccess(
                    modifier = footerModifier,
                    text = successText,
                    textColor = TextColor.Primary
                )
            }

            !errorText.isNullOrBlank() -> {
                HelpTextError(
                    modifier = footerModifier,
                    text = errorText
                )
            }

            !warningText.isNullOrBlank() -> {
                HelpTextWarning(
                    modifier = footerModifier,
                    text = warningText
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ReadOnlyOutlinedTextField(
    value: String,
    modifier: Modifier = Modifier,
    textStyle: TextStyle = LocalTextStyle.current,
    label: @Composable (() -> Unit)? = null,
    placeholder: @Composable (() -> Unit)? = null,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    prefix: @Composable (() -> Unit)? = null,
    suffix: @Composable (() -> Unit)? = null,
    supportingText: @Composable (() -> Unit)? = null,
    isError: Boolean = false,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    singleLine: Boolean = false,
    maxLines: Int = if (singleLine) 1 else Int.MAX_VALUE,
    minLines: Int = 1,
    interactionSource: MutableInteractionSource? = null,
    shape: Shape = OutlinedTextFieldDefaults.shape,
    colors: TextFieldColors = OutlinedTextFieldDefaults.colors()
) {
    val internalInteractionSource = interactionSource ?: remember { MutableInteractionSource() }
    val mergedTextStyle = textStyle.merge(TextStyle(color = getTextColor(colors, isError)))

    BasicTextField(
        value = value,
        modifier = modifier.defaultMinSize(
            minWidth = OutlinedTextFieldDefaults.MinWidth,
            minHeight = OutlinedTextFieldDefaults.MinHeight
        ),
        onValueChange = {},
        readOnly = true,
        textStyle = mergedTextStyle,
        visualTransformation = visualTransformation,
        interactionSource = interactionSource,
        singleLine = singleLine,
        maxLines = maxLines,
        minLines = minLines,
        decorationBox = @Composable { innerTextField ->
            OutlinedTextFieldDefaults.DecorationBox(
                value = value,
                visualTransformation = visualTransformation,
                innerTextField = innerTextField,
                placeholder = placeholder,
                label = label,
                leadingIcon = leadingIcon,
                trailingIcon = trailingIcon,
                prefix = prefix,
                suffix = suffix,
                supportingText = supportingText,
                singleLine = singleLine,
                enabled = true,
                isError = isError,
                interactionSource = internalInteractionSource,
                colors = colors,
                container = {
                    OutlinedTextFieldDefaults.Container(
                        enabled = true,
                        isError = isError,
                        interactionSource = internalInteractionSource,
                        colors = colors,
                        shape = shape,
                        focusedBorderThickness = 1.dp
                    )
                }
            )
        }
    )
}

private fun getTextColor(colors: TextFieldColors, isError: Boolean): Color = when {
    isError -> colors.errorTextColor
    else -> colors.unfocusedTextColor
}

@CombinedThemePreviews
@Composable
private fun DefaultErrorTextFieldPreview() {
    AndroidThemeForPreviews {
        TextInputField(
            modifier = Modifier,
            label = "Default Error",
            onValueChanged = {},
            keyboardType = KeyboardType.Text,
            errorText = "Wrong password"
        )
    }
}

@CombinedThemePreviews
@Composable
private fun DefaultSuccessTextFieldPreview() {
    AndroidThemeForPreviews {
        TextInputField(
            modifier = Modifier,
            label = "Default Success",
            onValueChanged = {},
            keyboardType = KeyboardType.Text,
            successText = "Password correct!",
        )
    }
}

@CombinedThemePreviews
@Composable
private fun PasswordTextFieldPreview() {
    AndroidThemeForPreviews {
        PasswordTextInputField(Modifier, "Password", text = "Password", onValueChanged = {})
    }
}

@CombinedThemePreviews
@Composable
private fun PasswordTextFieldFocusedPreview() {
    AndroidThemeForPreviews {
        val focusRequester = remember { FocusRequester() }
        PasswordTextInputField(
            Modifier.focusRequester(focusRequester),
            "Password",
            onValueChanged = {})
        LaunchedEffect(Unit) {
            focusRequester.requestFocus()
        }
    }
}

@CombinedThemePreviews
@Composable
private fun OptionalTextFieldPreview() {
    AndroidThemeForPreviews {
        TextInputField(
            modifier = Modifier,
            label = "Username",
            optionalLabelText = "(Optional)",
            onValueChanged = {},
            keyboardType = KeyboardType.Text,
            placeholder = "Enter your username",
            suffix = {
                MegaText(
                    text = "Suffix",
                    textColor = TextColor.Placeholder,
                )
            },
            inputTextAlign = TextAlign.End
        )
    }
}

@CombinedThemePreviews
@Composable
private fun ReadOnlyTextFieldWithCustomTrailingIconPreview() {
    AndroidThemeForPreviews {
        ReadOnlyTextInputField(
            text = "kjnsadASDasjdasDASNDSAd",
            label = "Recovery Key",
            trailingIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_copy_01_medium_thin_outline),
                    tint = DSTokens.colors.icon.primary,
                    contentDescription = "Clear Text"
                )
            }
        )
    }
}

@CombinedThemePreviews
@Composable
private fun ReadOnlyTextFieldWithCustomTrailingIconAndPasswordModePreview() {
    AndroidThemeForPreviews {
        ReadOnlyTextInputField(
            text = "kjnsadASDasjdasDASNDSAd",
            label = "Recovery Key",
            isPasswordMode = true,
            showDefaultTrailingIcon = true
        )
    }
}

@CombinedThemePreviews
@Composable
private fun ReadOnlyTextFieldWithCloseIconPreview() {
    AndroidThemeForPreviews {
        ReadOnlyTextInputField(
            text = "kjnsadASDasjdasDASNDSAd",
            label = "Recovery Key",
            showDefaultTrailingIcon = true
        )
    }
}

@CombinedThemePreviews
@Composable
private fun ReadOnlyTextFieldWithAnErrorTextPreview() {
    AndroidThemeForPreviews {
        ReadOnlyTextInputField(
            text = "kjnsadASDasjdasDASNDSAd",
            label = "Recovery Key",
            errorText = "error"
        )
    }
}

@CombinedThemePreviews
@Composable
private fun ReadOnlyTextFieldWithASuccessTextPreview() {
    AndroidThemeForPreviews {
        ReadOnlyTextInputField(
            text = "kjnsadASDasjdasDASNDSAd",
            label = "Recovery Key",
            successText = "success"
        )
    }
}

@CombinedThemePreviews
@Composable
private fun AnnotatedLabelTextInputFieldPreview() {
    AndroidThemeForPreviews {
        AnnotatedLabelTextInputField(
            text = "Annotated label",
            keyboardType = KeyboardType.Text,
            spannedLabel = InputFieldLabelSpanStyle(
                value = "Annotated [A](Optional)[/A]",
                spanStyles = mapOf(
                    SpanIndicator('A') to SpanStyleWithAnnotation(
                        megaSpanStyle = MegaSpanStyle.TextColorStyle(
                            spanStyle = AppTheme.typography.bodyMedium.toSpanStyle(),
                            textColor = TextColor.Secondary
                        ),
                        annotation = null
                    )
                ),
                baseStyle = AppTheme.typography.titleSmall,
                baseTextColor = TextColor.Primary
            ),
            showTrailingIcon = true
        )
    }
}

@CombinedThemePreviews
@Composable
private fun AnnotatedLabelTextInputFieldWithAnErrorTextPreview() {
    val sampleText = "Annotated label text input"
    AndroidThemeForPreviews {
        Column {
            AnnotatedLabelTextInputField(
                text = "Annotated label",
                keyboardType = KeyboardType.Text,
                spannedLabel = InputFieldLabelSpanStyle(
                    value = "Annotated [A](Optional)[/A]",
                    spanStyles = mapOf(
                        SpanIndicator('A') to SpanStyleWithAnnotation(
                            megaSpanStyle = MegaSpanStyle.TextColorStyle(
                                spanStyle = AppTheme.typography.bodyMedium.toSpanStyle(),
                                textColor = TextColor.Secondary
                            ),
                            annotation = null
                        )
                    ),
                    baseStyle = AppTheme.typography.titleSmall,
                    baseTextColor = TextColor.Primary
                ),
                errorText = "error"
            )
            AnnotatedLabelTextInputField(
                textValue = TextFieldValue(sampleText, TextRange(0, sampleText.length)),
                keyboardType = KeyboardType.Text,
                spannedLabel = InputFieldLabelSpanStyle(
                    value = "Annotated [A](Optional)[/A]",
                    spanStyles = mapOf(
                        SpanIndicator('A') to SpanStyleWithAnnotation(
                            megaSpanStyle = MegaSpanStyle.TextColorStyle(
                                spanStyle = AppTheme.typography.bodyMedium.toSpanStyle(),
                                textColor = TextColor.Secondary
                            ),
                            annotation = null
                        )
                    ),
                    baseStyle = AppTheme.typography.titleSmall,
                    baseTextColor = TextColor.Primary
                ),
                errorText = "error"
            )
        }
    }
}

@CombinedThemePreviews
@Composable
private fun AnnotatedLabelTextInputFieldWithASuccessTextPreview() {
    AndroidThemeForPreviews {
        AnnotatedLabelTextInputField(
            text = "Annotated label",
            keyboardType = KeyboardType.Text,
            spannedLabel = InputFieldLabelSpanStyle(
                value = "Annotated [A](Optional)[/A]",
                spanStyles = mapOf(
                    SpanIndicator('A') to SpanStyleWithAnnotation(
                        megaSpanStyle = MegaSpanStyle.TextColorStyle(
                            spanStyle = AppTheme.typography.bodyMedium.toSpanStyle(),
                            textColor = TextColor.Secondary
                        ),
                        annotation = null
                    )
                ),
                baseStyle = AppTheme.typography.titleSmall,
                baseTextColor = TextColor.Primary
            ),
            successText = "success"
        )
    }
}