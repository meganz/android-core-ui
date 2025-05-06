package mega.android.core.ui.components.inputfields

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.LocalTextSelectionColors
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.OutlinedTextFieldDefaults.Container
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
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
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import mega.android.core.ui.R
import mega.android.core.ui.components.MegaText
import mega.android.core.ui.preview.CombinedThemePreviews
import mega.android.core.ui.theme.AndroidThemeForPreviews
import mega.android.core.ui.theme.AppTheme
import mega.android.core.ui.tokens.theme.DSTokens
import mega.android.core.ui.theme.spacing.LocalSpacing
import mega.android.core.ui.theme.values.TextColor

@Composable
fun SearchInputField(
    placeHolderText: String,
    text: String,
    modifier: Modifier = Modifier,
    imeAction: ImeAction = ImeAction.Search,
    capitalization: KeyboardCapitalization = KeyboardCapitalization.Words,
    onKeyboardAction: (() -> Unit)? = null,
    onValueChanged: ((String) -> Unit)? = null,
    onFocusChanged: ((Boolean) -> Unit)? = null,
    enabled: Boolean = true,
    isError: Boolean = false,
    autoCorrect: Boolean = true,
) {
    // Holds the latest internal TextFieldValue state. We need to keep it to have the correct value
    // of the composition.
    var textFieldValueState by remember { mutableStateOf(TextFieldValue(text = text)) }
    // Last String value that either text field was recomposed with or updated in the onValueChange
    // callback. We keep track of it to prevent calling onValueChange(String) for same String when
    // BaseSearchInputField's onValueChange is called multiple times without recomposition in between.
    var lastTextValue by remember(text) { mutableStateOf(text) }

    BaseSearchInputField(
        modifier = modifier,
        value = textFieldValueState,
        placeHolderText = placeHolderText,
        onValueChange = { newTextFieldValueState ->
            textFieldValueState = newTextFieldValueState

            val stringChangedSinceLastInvocation = lastTextValue != newTextFieldValueState.text
            lastTextValue = newTextFieldValueState.text

            if (stringChangedSinceLastInvocation) {
                onValueChanged?.invoke(newTextFieldValueState.text)
            }
        },
        imeAction = imeAction,
        capitalization = capitalization,
        onKeyboardAction = onKeyboardAction,
        onFocusChanged = onFocusChanged,
        enabled = enabled,
        isError = isError,
        autoCorrect = autoCorrect
    )
}

@Composable
fun SearchInputField(
    value: TextFieldValue,
    placeHolderText: String,
    onValueChange: (TextFieldValue) -> Unit,
    modifier: Modifier = Modifier,
    imeAction: ImeAction = ImeAction.Search,
    capitalization: KeyboardCapitalization = KeyboardCapitalization.Words,
    onKeyboardAction: (() -> Unit)? = null,
    onFocusChanged: ((Boolean) -> Unit)? = null,
    enabled: Boolean = true,
    isError: Boolean = false,
    autoCorrect: Boolean = true,
) {
    BaseSearchInputField(
        modifier = modifier,
        value = value,
        placeHolderText = placeHolderText,
        onValueChange = {
            if (value != it) {
                onValueChange(it)
            }
        },
        imeAction = imeAction,
        capitalization = capitalization,
        onKeyboardAction = onKeyboardAction,
        onFocusChanged = onFocusChanged,
        enabled = enabled,
        isError = isError,
        autoCorrect = autoCorrect
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun BaseSearchInputField(
    value: TextFieldValue,
    placeHolderText: String,
    onValueChange: (TextFieldValue) -> Unit,
    modifier: Modifier = Modifier,
    imeAction: ImeAction = ImeAction.Search,
    capitalization: KeyboardCapitalization = KeyboardCapitalization.Words,
    onKeyboardAction: (() -> Unit)? = null,
    onFocusChanged: ((Boolean) -> Unit)? = null,
    enabled: Boolean = true,
    isError: Boolean = false,
    autoCorrect: Boolean = true,
) {
    val spacing = LocalSpacing.current
    val interactionSource: MutableInteractionSource = remember { MutableInteractionSource() }
    var isFocused by remember { mutableStateOf(false) }
    val primaryTextSelectionColors = TextSelectionColors(
        handleColor = DSTokens.colors.text.primary,
        backgroundColor = DSTokens.colors.text.primary.copy(alpha = 0.4f),
    )
    val colors = OutlinedTextFieldDefaults.colors(
        focusedTextColor = DSTokens.colors.text.primary,
        unfocusedTextColor = DSTokens.colors.text.primary,
        cursorColor = DSTokens.colors.text.primary,
        errorCursorColor = DSTokens.colors.text.primary,
        focusedBorderColor = DSTokens.colors.border.strongSelected,
        unfocusedBorderColor = DSTokens.colors.border.strong,
        errorBorderColor = DSTokens.colors.support.error,
        errorTextColor = DSTokens.colors.text.primary,
        focusedPlaceholderColor = DSTokens.colors.text.placeholder,
        unfocusedPlaceholderColor = DSTokens.colors.text.placeholder,
        disabledTextColor = DSTokens.colors.text.disabled,
        disabledContainerColor = DSTokens.colors.button.disabled,
        disabledBorderColor = DSTokens.colors.border.disabled,
    )

    CompositionLocalProvider(
        LocalTextSelectionColors provides primaryTextSelectionColors
    ) {
        BasicTextField(
            modifier = modifier
                .fillMaxWidth()
                .height(40.dp)
                .onFocusChanged {
                    isFocused = it.isFocused
                    onFocusChanged?.invoke(it.isFocused)
                },
            value = value,
            onValueChange = onValueChange,
            enabled = enabled,
            textStyle = AppTheme.typography.bodyLarge.copy(
                // Basic text field requires a color to be set on the text style
                color = DSTokens.colors.text.primary
            ),
            interactionSource = interactionSource,
            keyboardOptions = KeyboardOptions(
                capitalization = capitalization,
                autoCorrectEnabled = autoCorrect,
                keyboardType = KeyboardType.Text,
                imeAction = imeAction
            ),
            keyboardActions = KeyboardActions {
                onKeyboardAction?.invoke()
            },
        ) { innerTextField ->
            OutlinedTextFieldDefaults.DecorationBox(
                value = value.text,
                innerTextField = innerTextField,
                enabled = enabled,
                singleLine = true,
                visualTransformation = VisualTransformation.None,
                placeholder = {
                    MegaText(
                        text = placeHolderText,
                        style = AppTheme.typography.bodyLarge,
                        textColor = TextColor.Placeholder
                    )
                },
                colors = colors,
                interactionSource = interactionSource,
                contentPadding = PaddingValues(8.dp),
                trailingIcon = {
                    if (value.text.isNotEmpty()) {
                        Icon(
                            modifier = Modifier
                                .clickable {
                                    onValueChange(TextFieldValue(text = ""))
                                }
                                .padding(end = spacing.x12),
                            painter = painterResource(id = R.drawable.ic_x_thin),
                            tint = DSTokens.colors.icon.primary,
                            contentDescription = "Clear Text")
                    }
                },
                leadingIcon = {
                    Icon(
                        modifier = Modifier
                            .padding(start = spacing.x12, end = spacing.x16),
                        painter = painterResource(id = R.drawable.ic_search_large),
                        tint = DSTokens.colors.icon.secondary,
                        contentDescription = "Search"
                    )
                },
                container = {
                    Container(
                        enabled = enabled,
                        isError = isError,
                        interactionSource = interactionSource,
                        colors = colors,
                        shape = DSTokens.shapes.small,
                    )
                }
            )
        }
    }
}


@CombinedThemePreviews
@Composable
private fun SearchInputFieldPreview() {
    AndroidThemeForPreviews {
        SearchInputField(
            modifier = Modifier,
            text = "",
            placeHolderText = "Search",
            onValueChanged = {}
        )
    }
}

@CombinedThemePreviews
@Composable
private fun SearchInputFieldFocusedPreview() {
    AndroidThemeForPreviews {
        val focusRequester = remember { FocusRequester() }
        SearchInputField(
            modifier = Modifier.focusRequester(focusRequester),
            text = "",
            placeHolderText = "Search",
            onValueChanged = {}
        )
        LaunchedEffect(Unit) {
            focusRequester.requestFocus()
        }
    }
}


@CombinedThemePreviews
@Composable
private fun SearchInputFieldFocusedPreviewWithText() {
    AndroidThemeForPreviews {
        val focusRequester = remember { FocusRequester() }
        SearchInputField(
            modifier = Modifier.focusRequester(focusRequester),
            text = "Facebook",
            placeHolderText = "Search",
            onValueChanged = {}
        )
        LaunchedEffect(Unit) {
            focusRequester.requestFocus()
        }
    }
}
