package mega.android.core.ui.components.inputfields

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import mega.android.core.ui.R
import mega.android.core.ui.preview.CombinedThemePreviews
import mega.android.core.ui.theme.AndroidThemeForPreviews
import mega.android.core.ui.theme.AppTheme
import mega.android.core.ui.theme.spacing.LocalSpacing
import mega.android.core.ui.theme.tokens.TextColor

private val inputFieldHeight = 56.dp

/**
 * Text input field
 * @param modifier
 * @param onValueChanged
 * @param errorText
 */
@Composable
fun TextInputField(
    modifier: Modifier,
    label: String,
    keyboardType: KeyboardType,
    imeAction: ImeAction = ImeAction.Done,
    capitalization: KeyboardCapitalization = KeyboardCapitalization.Words,
    text: String = "",
    successText: String? = null,
    errorText: String? = null,
    maxCharLimit: Int = Int.MAX_VALUE,
    optionalLabelText: String? = null,
    onValueChanged: ((String) -> Unit)? = null,
    onFocusChanged: ((Boolean) -> Unit)? = null,
) = BaseTextField(
    modifier = modifier,
    label = label,
    keyboardType = keyboardType,
    imeAction = imeAction,
    capitalization = capitalization,
    text = text,
    successText = successText,
    errorText = errorText,
    isPasswordMode = false,
    maxCharLimit = maxCharLimit,
    optionalLabelText = optionalLabelText,
    onValueChanged = onValueChanged,
    onFocusChanged = onFocusChanged
)

/**
 * Password text input field with
 * @param modifier
 * @param onValueChanged
 * @param errorText
 */
@Composable
fun PasswordTextInputField(
    modifier: Modifier,
    label: String,
    text: String = "",
    imeAction: ImeAction = ImeAction.Done,
    successText: String? = null,
    errorText: String? = null,
    maxCharLimit: Int = Int.MAX_VALUE,
    onValueChanged: ((String) -> Unit)? = null,
    onFocusChanged: ((Boolean) -> Unit)? = null,
) = BaseTextField(
    modifier = modifier,
    label = label,
    text = text,
    keyboardType = KeyboardType.Password,
    imeAction = imeAction,
    capitalization = KeyboardCapitalization.None,
    successText = successText,
    errorText = errorText,
    isPasswordMode = true,
    maxCharLimit = maxCharLimit,
    onValueChanged = onValueChanged,
    onFocusChanged = onFocusChanged,
)

@Composable
private fun BaseTextField(
    modifier: Modifier,
    label: String,
    text: String,
    isPasswordMode: Boolean,
    keyboardType: KeyboardType,
    imeAction: ImeAction,
    capitalization: KeyboardCapitalization,
    successText: String?,
    errorText: String?,
    maxCharLimit: Int = Int.MAX_VALUE,
    optionalLabelText: String? = null,
    onValueChanged: ((String) -> Unit)? = null,
    onFocusChanged: ((Boolean) -> Unit)? = null,
) {
    val spacing = LocalSpacing.current
    val interactionSource: MutableInteractionSource = remember { MutableInteractionSource() }
    var baseText by remember(text) { mutableStateOf(text) }
    var isFocused by remember { mutableStateOf(false) }
    var showPassword by remember { mutableStateOf(false) }
    val focusedColor = when {
        successText.isNullOrBlank().not() -> AppTheme.colors.support.success
        errorText != null -> AppTheme.colors.support.error
        else -> AppTheme.colors.border.strongSelected
    }
    val unfocusedColor = when {
        successText.isNullOrBlank().not() -> AppTheme.colors.support.success
        errorText != null -> AppTheme.colors.support.error
        else -> AppTheme.colors.border.strong
    }
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
        Row(modifier = Modifier.wrapContentWidth()) {
            Text(
                text = label,
                style = AppTheme.typography.titleSmall,
                color = AppTheme.colors.text.primary
            )
            optionalLabelText?.let {
                Text(
                    modifier = Modifier.padding(start = spacing.x8),
                    text = optionalLabelText,
                    style = AppTheme.typography.bodyMedium,
                    color = AppTheme.colors.text.secondary
                )
            }
        }

        OutlinedTextField(
            modifier = Modifier
                .padding(vertical = spacing.x4)
                .fillMaxWidth()
                .heightIn(min = inputFieldHeight)
                .onFocusChanged {
                    isFocused = it.isFocused
                    onFocusChanged?.invoke(it.isFocused)
                },
            keyboardOptions = KeyboardOptions(
                keyboardType = keyboardType,
                autoCorrect = isPasswordMode.not(),
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
            singleLine = true,
            textStyle = AppTheme.typography.bodyLarge,
            isError = successText.isNullOrBlank() && errorText != null,
            visualTransformation = if (!isPasswordMode || showPassword) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                if (baseText.isNotEmpty()) {
                    when {
                        isPasswordMode.not() && isFocused -> Icon(
                            modifier = Modifier
                                .clickable {
                                    baseText = ""
                                    onValueChanged?.invoke("")
                                },
                            painter = painterResource(id = R.drawable.ic_close),
                            tint = AppTheme.colors.icon.primary,
                            contentDescription = "Clear Text"
                        )

                        isPasswordMode -> {
                            val eyeIcon =
                                if (showPassword) R.drawable.ic_eye_off else R.drawable.ic_eye

                            Icon(
                                modifier = Modifier
                                    .padding(horizontal = spacing.x8)
                                    .clickable { showPassword = !showPassword },
                                painter = painterResource(id = eyeIcon),
                                tint = AppTheme.colors.icon.secondary,
                                contentDescription = "Show Password"
                            )
                        }
                    }
                }
            },
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
        }
    }
}

@CombinedThemePreviews
@Composable
private fun DefaultErrorTextFieldPreview() {
    AndroidThemeForPreviews {
        TextInputField(
            Modifier,
            "Default Error",
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
            Modifier,
            "Default Success",
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
        PasswordTextInputField(Modifier, "Password", onValueChanged = {})
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
            Modifier,
            "Username",
            optionalLabelText = "(Optional)",
            onValueChanged = {},
            keyboardType = KeyboardType.Text
        )
    }
}