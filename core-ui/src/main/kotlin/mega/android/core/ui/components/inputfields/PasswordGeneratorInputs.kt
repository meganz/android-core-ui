package mega.android.core.ui.components.inputfields

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import mega.android.core.ui.R
import mega.android.core.ui.components.inputFieldHeight
import mega.android.core.ui.preview.CombinedThemePreviews
import mega.android.core.ui.theme.AndroidThemeForPreviews
import mega.android.core.ui.theme.AppTheme
import mega.android.core.ui.tokens.theme.DSTokens
import mega.android.core.ui.theme.font.GoogleFontFamily
import mega.android.core.ui.theme.spacing.LocalSpacing
import mega.android.core.ui.theme.values.TextColor

@Composable
fun PasswordGeneratorInputBox(
    text: String,
    onCopyClick: () -> Unit,
    modifier: Modifier = Modifier,
    showCopyIcon: Boolean = true,
) {
    val spacing = LocalSpacing.current
    Column(
        modifier = modifier
            .background(color = DSTokens.colors.background.surface1, shape = DSTokens.shapes.small)
            .border(
                BorderStroke(1.dp, color = DSTokens.colors.border.subtle),
                shape = DSTokens.shapes.small
            )
    ) {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(spacing.x16),
            text = getAnnotatedString(
                text = text.toList().joinToString(" "),
                changeSpaceStyle = true
            ),
            fontFamily = GoogleFontFamily().workSans,
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            letterSpacing = 2.sp,
        )
        if (showCopyIcon) {
            Icon(
                modifier = Modifier
                    .padding(bottom = spacing.x16, end = spacing.x16)
                    .align(Alignment.End)
                    .clickable(onClick = onCopyClick),
                painter = painterResource(id = R.drawable.ic_copy_01_medium_thin_outline),
                tint = DSTokens.colors.icon.secondary,
                contentDescription = "Copy Password"
            )
        }
    }
}

@Composable
fun PasswordGeneratorInputField(
    label: String,
    modifier: Modifier = Modifier,
    text: String = "",
    imeAction: ImeAction = ImeAction.Done,
    successText: String? = null,
    errorText: String? = null,
    maxCharLimit: Int = Int.MAX_VALUE,
    onValueChanged: ((String) -> Unit)? = null,
    onFocusChanged: ((Boolean) -> Unit)? = null,
) {
    val spacing = LocalSpacing.current
    val interactionSource: MutableInteractionSource = remember { MutableInteractionSource() }
    var baseText by rememberSaveable(text) { mutableStateOf(text) }
    var showPassword by rememberSaveable { mutableStateOf(false) }
    val focusedColor = when {
        successText.isNullOrBlank().not() -> DSTokens.colors.support.success
        errorText != null -> DSTokens.colors.support.error
        else -> DSTokens.colors.border.strongSelected
    }
    val unfocusedColor = when {
        successText.isNullOrBlank().not() -> DSTokens.colors.support.success
        errorText != null -> DSTokens.colors.support.error
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
    val annotatedString = getAnnotatedString(text = baseText)

    Column(
        modifier = modifier
            .fillMaxWidth()
    ) {
        Text(
            text = label,
            style = AppTheme.typography.titleSmall,
            color = DSTokens.colors.text.primary
        )

        OutlinedTextField(
            modifier = Modifier
                .padding(vertical = spacing.x4)
                .fillMaxWidth()
                .heightIn(min = inputFieldHeight)
                .onFocusChanged {
                    onFocusChanged?.invoke(it.isFocused)
                },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
                autoCorrect = false,
                imeAction = imeAction,
                capitalization = KeyboardCapitalization.None
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
            textStyle = TextStyle.Default.copy(
                fontFamily = GoogleFontFamily().workSans,
                fontWeight = FontWeight.Medium,
                fontSize = 16.sp,
                letterSpacing = 0.sp
            ),
            isError = successText.isNullOrBlank() && errorText != null,
            visualTransformation = if (showPassword) ColorsTransformation(annotatedString) else PasswordVisualTransformation(),
            trailingIcon = {
                if (baseText.isNotEmpty()) {
                    val eyeIcon = if (showPassword) R.drawable.ic_eye_off_medium_thin_outline else R.drawable.ic_eye_medium_thin_outline
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

@Composable
internal fun getAnnotatedString(text: String, changeSpaceStyle: Boolean = false) =
    buildAnnotatedString {
        val letterStyle = SpanStyle(color = DSTokens.colors.text.primary)
        val digitStyle = SpanStyle(color = DSTokens.colors.indicator.magenta)
        val symbolStyle = SpanStyle(color = DSTokens.colors.indicator.indigo)
        val spaceStyle = SpanStyle(fontSize = 0.sp)

        text.forEach {
            when {
                it.isLetter() -> withStyle(style = letterStyle) { append(it) }
                it.isDigit() -> withStyle(style = digitStyle) { append(it) }
                it == ' ' && changeSpaceStyle -> withStyle(style = spaceStyle) { append(it) }
                else -> withStyle(style = symbolStyle) { append(it) }
            }
        }
    }

class ColorsTransformation(private val annotatedString: AnnotatedString) : VisualTransformation {
    override fun filter(text: AnnotatedString): TransformedText {
        return TransformedText(annotatedString, OffsetMapping.Identity)
    }
}

@CombinedThemePreviews
@Composable
private fun PasswordGeneratorInputBoxPreview() {
    AndroidThemeForPreviews {
        PasswordGeneratorInputBox(
            modifier = Modifier.height(184.dp),
            onCopyClick = {},
            text = "uY3&y2O&nRIZ1@MLpl*kc$7RyaH^*glAwlMWk2nGPkM"
        )
    }
}

@CombinedThemePreviews
@Composable
private fun PasswordGeneratorInputFieldPreview() {
    AndroidThemeForPreviews {
        PasswordGeneratorInputField(label = "Password", text = "Password", onValueChanged = {})
    }
}