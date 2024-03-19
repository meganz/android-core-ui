package mega.android.core.ui.components.inputfields

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import mega.android.core.ui.preview.CombinedThemePreviews
import mega.android.core.ui.theme.AndroidThemeForPreviews
import mega.android.core.ui.theme.AppTheme
import mega.android.core.ui.theme.spacing.LocalSpacing

private val inputBoxHeight = 142.dp

/**
 * Text input box
 */
@Composable
fun TextInputBox(
    modifier: Modifier,
    label: String,
    keyboardType: KeyboardType,
    imeAction: ImeAction = ImeAction.Done,
    capitalization: KeyboardCapitalization = KeyboardCapitalization.Words,
    text: String = "",
    maxCharLimit: Int = Int.MAX_VALUE,
    optionalLabelText: String? = null,
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
        Row(modifier = Modifier.wrapContentWidth()){
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
                .height(inputBoxHeight)
                .onFocusChanged {
                    isFocused = it.isFocused
                    onFocusChanged?.invoke(it.isFocused)
                }
                .verticalScroll(rememberScrollState()),
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
        )
    }
}


@CombinedThemePreviews
@Composable
private fun TextInputBoxPreview() {
    AndroidThemeForPreviews {
        TextInputBox(modifier = Modifier,
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Done,
            capitalization = KeyboardCapitalization.None,
            label = "Notes",
            text = "This is a note",
            onValueChanged = {
            })
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




