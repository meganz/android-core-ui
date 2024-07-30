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
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.OutlinedTextFieldDefaults.ContainerBox
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
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import mega.android.core.ui.R
import mega.android.core.ui.components.MegaText
import mega.android.core.ui.preview.CombinedThemePreviews
import mega.android.core.ui.theme.AndroidThemeForPreviews
import mega.android.core.ui.theme.AppTheme
import mega.android.core.ui.theme.spacing.LocalSpacing
import mega.android.core.ui.theme.values.TextColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchInputField(
    modifier: Modifier,
    placeHolderText: String,
    text: String,
    imeAction: ImeAction = ImeAction.Search,
    capitalization: KeyboardCapitalization = KeyboardCapitalization.Words,
    onKeyboardAction: (() -> Unit)? = null,
    onValueChanged: ((String) -> Unit)? = null,
    onFocusChanged: ((Boolean) -> Unit)? = null,
    enabled: Boolean = true,
    isError: Boolean = false,
    autoCorrect: Boolean = true,
) {
    val spacing = LocalSpacing.current
    val interactionSource: MutableInteractionSource = remember { MutableInteractionSource() }
    var baseText by remember(text) { mutableStateOf(text) }
    var isFocused by remember { mutableStateOf(false) }

    val colors = OutlinedTextFieldDefaults.colors(
        focusedTextColor = AppTheme.colors.text.primary,
        unfocusedTextColor = AppTheme.colors.text.primary,
        cursorColor = AppTheme.colors.text.primary,
        selectionColors = TextSelectionColors(
            handleColor = AppTheme.colors.text.primary,
            backgroundColor = AppTheme.colors.text.primary.copy(alpha = 0.4f),
        ),
        focusedBorderColor = AppTheme.colors.border.strongSelected,
        unfocusedBorderColor = AppTheme.colors.border.strong,
        errorBorderColor = AppTheme.colors.support.error,
        errorTextColor = AppTheme.colors.text.primary,
        focusedPlaceholderColor = AppTheme.colors.text.placeholder,
        unfocusedPlaceholderColor = AppTheme.colors.text.placeholder,
        disabledTextColor = AppTheme.colors.text.disabled,
        disabledContainerColor = AppTheme.colors.button.disabled,
        disabledBorderColor = AppTheme.colors.border.disabled,
    )

    BasicTextField(
        modifier = modifier
            .fillMaxWidth()
            .height(40.dp)
            .onFocusChanged {
                isFocused = it.isFocused
                onFocusChanged?.invoke(it.isFocused)
            },
        value = baseText,
        onValueChange = {
            baseText = it
            onValueChanged?.invoke(it)
        },
        cursorBrush = SolidColor(AppTheme.colors.text.primary),
        enabled = enabled,
        textStyle = AppTheme.typography.bodyLarge.copy(
            // Basic text field requires a color to be set on the text style
            color = AppTheme.colors.text.primary
        ),
        interactionSource = interactionSource,
        keyboardOptions = KeyboardOptions(
            capitalization = capitalization,
            autoCorrect = autoCorrect,
            keyboardType = KeyboardType.Text,
            imeAction = imeAction
        ),
        keyboardActions = KeyboardActions {
            onKeyboardAction?.invoke()
        },
    ) { innerTextField ->
        OutlinedTextFieldDefaults.DecorationBox(
            value = baseText,
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
                if (baseText.isNotEmpty()) {
                    Icon(
                        modifier = Modifier
                            .clickable {
                                baseText = ""
                                onValueChanged?.invoke(baseText)
                            }
                            .padding(end = spacing.x12),
                        painter = painterResource(id = R.drawable.ic_x_thin),
                        tint = AppTheme.colors.icon.primary,
                        contentDescription = "Clear Text")
                }
            },
            leadingIcon = {
                Icon(
                    modifier = Modifier
                        .padding(start = spacing.x12, end = spacing.x16),
                    painter = painterResource(id = R.drawable.ic_search_large),
                    tint = AppTheme.colors.icon.secondary,
                    contentDescription = "Search"
                )
            },
            container = {
                ContainerBox(
                    enabled = enabled,
                    isError = isError,
                    interactionSource = interactionSource,
                    colors = colors,
                    shape = AppTheme.shapes.small,
                )
            }
        )
    }
}


@CombinedThemePreviews
@Composable
private fun SearchInputFieldPreview() {
    AndroidThemeForPreviews {
        SearchInputField(Modifier, text = "", placeHolderText = "Search", onValueChanged = {})
    }
}

@CombinedThemePreviews
@Composable
private fun SearchInputFieldFocusedPreview() {
    AndroidThemeForPreviews {
        val focusRequester = remember { FocusRequester() }
        SearchInputField(Modifier.focusRequester(focusRequester),
            text = "",
            placeHolderText = "Search",
            onValueChanged = {})
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
        SearchInputField(Modifier.focusRequester(focusRequester),
            text = "Facebook",
            placeHolderText = "Search",
            onValueChanged = {})
        LaunchedEffect(Unit) {
            focusRequester.requestFocus()
        }
    }
}
