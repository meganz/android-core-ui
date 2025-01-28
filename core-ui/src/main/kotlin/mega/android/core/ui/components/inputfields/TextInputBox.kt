package mega.android.core.ui.components.inputfields

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.ImeOptions
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import mega.android.core.ui.R
import mega.android.core.ui.components.MegaText
import mega.android.core.ui.components.image.MegaIcon
import mega.android.core.ui.components.spannedTextWithAnnotation
import mega.android.core.ui.model.InputFieldLabelSpanStyle
import mega.android.core.ui.model.MegaSpanStyle
import mega.android.core.ui.model.SpanIndicator
import mega.android.core.ui.model.SpanStyleWithAnnotation
import mega.android.core.ui.preview.CombinedThemePreviews
import mega.android.core.ui.theme.AndroidThemeForPreviews
import mega.android.core.ui.theme.AppTheme
import mega.android.core.ui.theme.spacing.LocalSpacing
import mega.android.core.ui.theme.values.IconColor
import mega.android.core.ui.theme.values.TextColor

private val inputBoxHeight = 142.dp

/**
 * Text input box
 */
@Composable
fun TextInputBox(
    modifier: Modifier,
    keyboardType: KeyboardType,
    imeAction: ImeAction = ImeAction.Done,
    capitalization: KeyboardCapitalization = KeyboardCapitalization.Words,
    text: String = "",
    label: String = "",
    maxCharLimit: Int = Int.MAX_VALUE,
    showCharCount: Boolean = false,
    enableClear: Boolean = false,
    optionalLabelText: String? = null,
    onValueChanged: ((String) -> Unit)? = null,
    onFocusChanged: ((Boolean) -> Unit)? = null,
) {
    BaseTextInputBox(
        modifier = modifier,
        label = {
            if (label.isNotBlank()) {
                Row(modifier = Modifier.wrapContentWidth()) {
                    Text(
                        text = label,
                        style = AppTheme.typography.titleSmall,
                        color = AppTheme.colors.text.primary
                    )
                    optionalLabelText?.let {
                        Text(
                            modifier = Modifier.padding(start = LocalSpacing.current.x8),
                            text = optionalLabelText,
                            style = AppTheme.typography.bodyMedium,
                            color = AppTheme.colors.text.secondary
                        )
                    }
                }
            }
        },
        keyboardType = keyboardType,
        imeAction = imeAction,
        capitalization = capitalization,
        text = text,
        maxCharLimit = maxCharLimit,
        showCharCount = showCharCount,
        enableClear = enableClear,
        onValueChanged = onValueChanged,
        onFocusChanged = onFocusChanged
    )
}

/**
 * Text input box with annotated label.
 *
 * @param text The input text to be shown in the text box
 * @param keyboardType The keyboard type to be used in this text box. Note that this input type
 * is honored by keyboard and shows corresponding keyboard but this is not guaranteed. For example,
 * some keyboards may send non-ASCII character even if you set [KeyboardType.Ascii].
 * @param modifier The [Modifier] to be applied to this text box
 * @param imeAction The IME action. This IME action is honored by keyboard and may show specific
 * icons on the keyboard. For example, search icon may be shown if [ImeAction.Search] is specified.
 * When [ImeOptions.singleLine] is false, the keyboard might show return key rather than the action
 * requested here.
 * @param capitalization informs the keyboard whether to automatically capitalize characters,
 * words or sentences. Only applicable to only text based [KeyboardType]s such as
 * [KeyboardType.Text], [KeyboardType.Ascii]. It will not be applied to [KeyboardType]s such as
 * [KeyboardType.Number].
 * @param maxCharLimit The maximum character to be displayed in the text box.
 * @param spannedLabel The optional spanned label to be displayed inside the text box container.
 * @param onValueChanged The callback that is triggered when the text is changed. In this component,
 *   this callback will be called when the user clear all the text by clicking the close icon.
 * @param onFocusChanged The callback that is triggered when the focus state of this text field changes.
 */
@Composable
fun AnnotatedLabelTextInputBox(
    text: String,
    keyboardType: KeyboardType,
    modifier: Modifier = Modifier,
    imeAction: ImeAction = ImeAction.Done,
    capitalization: KeyboardCapitalization = KeyboardCapitalization.Words,
    maxCharLimit: Int = Int.MAX_VALUE,
    showCharCount: Boolean = false,
    enableClear: Boolean = false,
    spannedLabel: InputFieldLabelSpanStyle? = null,
    onValueChanged: ((String) -> Unit)? = null,
    onFocusChanged: ((Boolean) -> Unit)? = null,
) {
    BaseTextInputBox(
        modifier = modifier,
        text = text,
        label = {
            spannedLabel?.let {
                val annotatedLabelString = spannedTextWithAnnotation(
                    it.value,
                    it.spanStyles
                )
                Text(
                    text = annotatedLabelString,
                    style = it.baseStyle.copy(
                        color = AppTheme.textColor(textColor = it.baseTextColor)
                    )
                )
            }
        },
        keyboardType = keyboardType,
        imeAction = imeAction,
        capitalization = capitalization,
        maxCharLimit = maxCharLimit,
        showCharCount = showCharCount,
        enableClear = enableClear,
        onValueChanged = onValueChanged,
        onFocusChanged = onFocusChanged
    )
}

/**
 * Text input box
 */
@Composable
private fun BaseTextInputBox(
    modifier: Modifier = Modifier,
    keyboardType: KeyboardType,
    showCharCount: Boolean,
    enableClear: Boolean,
    imeAction: ImeAction = ImeAction.Done,
    capitalization: KeyboardCapitalization = KeyboardCapitalization.Words,
    text: String = "",
    maxCharLimit: Int = Int.MAX_VALUE,
    label: @Composable (() -> Unit)? = null,
    onValueChanged: ((String) -> Unit)? = null,
    onFocusChanged: ((Boolean) -> Unit)? = null,
) {
    val spacing = LocalSpacing.current
    val interactionSource: MutableInteractionSource = remember { MutableInteractionSource() }
    var baseText by remember(text) { mutableStateOf(text) }
    var isFocused by remember { mutableStateOf(false) }
    val focusedColor = AppTheme.colors.border.strongSelected
    val unfocusedColor = AppTheme.colors.border.strong

    val colors = OutlinedTextFieldDefaults.colors(
        focusedTextColor = AppTheme.colors.text.primary,
        unfocusedTextColor = AppTheme.colors.text.primary,
        cursorColor = AppTheme.colors.text.primary,
        errorCursorColor = AppTheme.colors.text.primary,
        selectionColors = TextSelectionColors(
            handleColor = AppTheme.colors.text.primary,
            backgroundColor = AppTheme.colors.text.primary.copy(alpha = 0.4f),
        ),
        focusedBorderColor = focusedColor,
        unfocusedBorderColor = unfocusedColor,
        errorBorderColor = AppTheme.colors.support.error,
        errorTextColor = AppTheme.colors.text.primary,
        focusedPlaceholderColor = AppTheme.colors.text.primary,
        unfocusedPlaceholderColor = AppTheme.colors.text.placeholder,
        disabledTextColor = AppTheme.colors.text.disabled,
        disabledContainerColor = AppTheme.colors.button.disabled,
        disabledBorderColor = AppTheme.colors.border.disabled,
    )

    Column(
        modifier = modifier
            .fillMaxWidth()
    ) {
        label?.invoke()

        OutlinedTextField(
            modifier = Modifier
                .padding(vertical = spacing.x4)
                .fillMaxWidth()
                .height(inputBoxHeight)
                .onFocusChanged {
                    isFocused = it.isFocused
                    onFocusChanged?.invoke(it.isFocused)
                },
            keyboardOptions = KeyboardOptions(
                keyboardType = keyboardType,
                autoCorrect = true,
                imeAction = imeAction,
                capitalization = capitalization
            ),
            value = baseText,
            onValueChange = {
                if (it.length <= maxCharLimit) {
                    baseText = it
                    onValueChanged?.invoke(it)
                }
            },
            colors = colors,
            shape = RoundedCornerShape(8.dp),
            interactionSource = interactionSource,
            singleLine = false,
            textStyle = AppTheme.typography.bodyLarge,
            visualTransformation = VisualTransformation.None,
            trailingIcon = {
                if (enableClear && baseText.isNotEmpty()) {
                    Box(
                        modifier = Modifier
                            .fillMaxHeight()
                            .clickable {
                                baseText = ""
                                onValueChanged?.invoke("")
                            }
                    ) {
                        MegaIcon(
                            modifier = Modifier
                                .align(Alignment.TopCenter)
                                .padding(spacing.x16)
                                .size(16.dp),
                            painter = painterResource(id = R.drawable.ic_close),
                            tint = IconColor.Primary
                        )
                    }
                }
            }
        )

        if (showCharCount) {
            MegaText(
                modifier = Modifier.align(Alignment.End),
                text = "${baseText.count()} / $maxCharLimit",
                style = AppTheme.typography.bodySmall,
                textColor = TextColor.Secondary
            )
        }
    }
}

@CombinedThemePreviews
@Composable
private fun TextInputBoxPreview() {
    AndroidThemeForPreviews {
        TextInputBox(
            modifier = Modifier,
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Done,
            capitalization = KeyboardCapitalization.None,
            label = "Notes",
            text = "This is a note",
            onValueChanged = {
            },
            showCharCount = true,
            enableClear = true
        )
    }
}

@CombinedThemePreviews
@Composable
private fun OptionalTextInputBoxPreview() {
    AndroidThemeForPreviews {
        TextInputBox(modifier = Modifier,
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Done,
            capitalization = KeyboardCapitalization.None,
            label = "Notes",
            text = "This is a note",
            optionalLabelText = "(Optional)",
            onValueChanged = {
            })
    }
}

@CombinedThemePreviews
@Composable
private fun AnnotatedLabelTextInputBoxPreview() {
    AndroidThemeForPreviews {
        AnnotatedLabelTextInputBox(
            text = "Annotated label",
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Done,
            capitalization = KeyboardCapitalization.None,
            spannedLabel = InputFieldLabelSpanStyle(
                value = "Annotated",
                spanStyles = mapOf(),
                baseStyle = AppTheme.typography.titleSmall,
                baseTextColor = TextColor.Primary
            ),
            onValueChanged = {}
        )
    }
}

@CombinedThemePreviews
@Composable
private fun OptionalAnnotatedLabelTextInputBoxPreview() {
    AndroidThemeForPreviews {
        AnnotatedLabelTextInputBox(
            text = "Annotated label",
            keyboardType = KeyboardType.Text,
            modifier = Modifier,
            imeAction = ImeAction.Done,
            capitalization = KeyboardCapitalization.None,
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
            onValueChanged = {}
        )
    }
}
