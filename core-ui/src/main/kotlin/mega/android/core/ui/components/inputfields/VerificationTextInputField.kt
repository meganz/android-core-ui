package mega.android.core.ui.components.inputfields

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.keyframes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.*
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.core.text.isDigitsOnly
import mega.android.core.ui.preview.CombinedThemePreviews
import mega.android.core.ui.theme.AndroidThemeForPreviews
import mega.android.core.ui.theme.AppTheme
import mega.android.core.ui.theme.spacing.LocalSpacing

/**
 * Verification code text field
 * @param value                     the default value or remember value
 * @param isCodeCorrect             Pass the verification state
 * @param errorText                 when error state show the error text
 * @param onValueChange             the input field value change observer
 * @param textColor                 the input field text color
 * @param isEnabled                  the input field enable state
 */
@Composable
fun VerificationTextInputField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    isCodeCorrect: Boolean? = null,
    errorText: String = "",
    isEnabled: Boolean = true
) {
    val focusState = remember {
        mutableStateOf(false)
    }
    val textFieldValue = TextFieldValue(text = value, selection = TextRange(value.length))
    var lastTextValue by remember { mutableStateOf(value) }
    val cipherMask = ""
    val textColor = AppTheme.colors.text.primary
    val spacing = LocalSpacing.current

    Column(modifier = modifier) {
        BasicTextField(
            value = textFieldValue,
            onValueChange = {
                val newText = it.text.take(DEFAULT_VERIFICATION_INPUT_LENGTH)
                if (lastTextValue != newText && newText.isDigitsOnly()) {
                    lastTextValue = newText
                    onValueChange(newText)
                }
            },
            modifier = Modifier.onFocusChanged {
                focusState.value = it.hasFocus
            },
            enabled = isEnabled,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            keyboardActions = KeyboardActions.Default,
            singleLine = true,
            cursorBrush = SolidColor(Color.Unspecified)
        ) {
            Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
                repeat(DEFAULT_VERIFICATION_INPUT_LENGTH) { position ->
                    val selection = position == value.length

                    // Box for type each character
                    Box(
                        modifier = Modifier
                            .border(
                                when {
                                    //error text is not empty
                                    isCodeCorrect == false -> {
                                        BorderStroke(
                                            width = TextFieldDefaults.UnfocusedIndicatorThickness,
                                            color = AppTheme.colors.support.error
                                        )
                                    }

                                    //pass the verification state
                                    isCodeCorrect == true -> {
                                        BorderStroke(
                                            width = TextFieldDefaults.UnfocusedIndicatorThickness,
                                            color = AppTheme.colors.support.success
                                        )
                                    }

                                    //the box is focus state
                                    focusState.value && selection -> {
                                        BorderStroke(
                                            width = TextFieldDefaults.UnfocusedIndicatorThickness,
                                            color = AppTheme.colors.border.strongSelected
                                        )
                                    }

                                    else -> {
                                        BorderStroke(
                                            width = TextFieldDefaults.UnfocusedIndicatorThickness,
                                            color = AppTheme.colors.border.disabled
                                        )
                                    }
                                },
                                RoundedCornerShape(spacing.x8)
                            )
                            .size(
                                width = spacing.x40,
                                height = spacing.x48
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        val cursorRectState = remember {
                            mutableStateOf(Rect(0f, 0f, 0f, 0f))
                        }
                        val text = value.getOrNull(position)?.toString() ?: ""
                        val showCursor = focusState.value && selection

                        // the content of each input box
                        Text(
                            text = if (cipherMask.isNotEmpty() && text.isNotEmpty()) cipherMask else text,
                            modifier = if (showCursor) Modifier.cursor(
                                cursorBrush = SolidColor(AppTheme.colors.text.primary),
                                cursorRect = cursorRectState.value,
                            ) else Modifier,
                            color = textColor,
                            onTextLayout = { textLayoutResult ->
                                cursorRectState.value = textLayoutResult.getCursorRect(0)
                            },
                            style = AppTheme.typography.bodyLarge
                        )

                    }
                }
            }
        }

        if (isCodeCorrect == false) {
            ErrorFooter(
                modifier = Modifier
                    .padding(top = spacing.x4)
                    .fillMaxWidth(),
                text = errorText
            )
        }
    }

}

/**
 * the cursor ext fun
 */
@Suppress("ModifierInspectorInfo")
internal fun Modifier.cursor(
    cursorBrush: Brush,
    cursorRect: Rect
) = composed {
    val cursorAlpha = remember { Animatable(ALPHA_1F_TO_SHOW) }
    val isBrushSpecified = !(cursorBrush is SolidColor && cursorBrush.value.isUnspecified)
    // only show cursor if brush is specified
    if (isBrushSpecified) {
        LaunchedEffect(cursorBrush) {
            cursorAlpha.animateTo(ALPHA_0F_TO_HIDE, cursorAnimationSpec)
        }

        drawWithContent {
            this.drawContent()
            val cursorAlphaValue = cursorAlpha.value.coerceIn(ALPHA_0F_TO_HIDE, ALPHA_1F_TO_SHOW)
            if (cursorAlphaValue != ALPHA_0F_TO_HIDE) {
                val cursorWidth = defaultCursorThickness.toPx()
                val cursorX = (cursorRect.left + cursorWidth / 2)
                    .coerceAtMost(size.width - cursorWidth / 2)

                drawLine(
                    cursorBrush,
                    Offset(cursorX, cursorRect.top),
                    Offset(cursorX, cursorRect.bottom),
                    alpha = cursorAlphaValue,
                    strokeWidth = cursorWidth,
                )
            }
        }
    } else {
        Modifier
    }
}

/**
 * Draw the cursor of the TextInput, perform animation once per second
 * disappear 500ms, display 500ms
 */
private val cursorAnimationSpec: AnimationSpec<Float>
    get() = infiniteRepeatable(
        animation = keyframes {
            durationMillis = CURSOR_ANIMATION_DURATION
            ALPHA_1F_TO_SHOW at TIME_DISPLAY_START_TIME
            ALPHA_1F_TO_SHOW at TIME_DISPLAY_END_TIME
            ALPHA_0F_TO_HIDE at TIME_DISAPPEAR_START_TIME
            ALPHA_0F_TO_HIDE at TIME_DISAPPEAR_END_TIME
        }
    )

/**
 * the cursor width of the TextInput
 */
private val defaultCursorThickness = 1.dp

/**
 * max input digest count
 */
const val DEFAULT_VERIFICATION_INPUT_LENGTH = 6

/**
 * Change the view alpha 0f or 1f
 */
private const val ALPHA_0F_TO_HIDE = 0F
private const val ALPHA_1F_TO_SHOW = 1F

/**
 * The total animation duration is 1000 ms
 * Use half of the duration for showing cursor (0-499 ms)
 * and half of the duration for hiding cursor (500-999 ms)
 * and repeat infinitely
 */
private const val CURSOR_ANIMATION_DURATION = 1000
private const val TIME_DISPLAY_START_TIME = 0
private const val TIME_DISPLAY_END_TIME = 499
private const val TIME_DISAPPEAR_START_TIME = 500
private const val TIME_DISAPPEAR_END_TIME = 999


@CombinedThemePreviews
@Composable
private fun DefaultVerificationTextInputFieldPreview() {
    AndroidThemeForPreviews {
        VerificationTextInputField(
            value = "",
            onValueChange = {

            },
            errorText = "Incorrect verification code"
        )
    }
}

@CombinedThemePreviews
@Composable
private fun VerificationTextInputFieldPreview() {
    AndroidThemeForPreviews {
        VerificationTextInputField(value = "",
            onValueChange = {

            })
    }
}