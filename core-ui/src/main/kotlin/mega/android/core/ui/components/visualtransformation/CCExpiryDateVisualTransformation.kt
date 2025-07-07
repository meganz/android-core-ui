package mega.android.core.ui.components.visualtransformation

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.withStyle

class CCExpiryDateVisualTransformation(private val hintColor: Color) : VisualTransformation {

    override fun filter(text: AnnotatedString): TransformedText {
        val formattedText = buildAnnotatedString {
            when {
                text.isBlank() -> {
                    appendMonthHint()
                    appendSlash()
                    appendYearHint()
                }

                text.length == 2 -> {
                    // MM
                    append(text)
                    appendSlash()
                }

                text.length > 2 -> {
                    // MM
                    append(text.substring(0, 2))
                    appendSlash()
                    // YY
                    append(text.substring(2))
                }

                else -> append(text)
            }
        }

        /**
         * The offset translator should ignore the slash characters, so conversion from original offset
         * to transformed text works like
         * - The 2nd char of the original text is 5th char in the transformed text because EXPIRY_DATE_SLASH has spaces.
         * Similarly, the reverse conversion works like
         * - The 5th char of the transformed text is 2nd char in the original text because EXPIRY_DATE_SLASH has spaces.
         */
        val offset = object : OffsetMapping {
            override fun originalToTransformed(offset: Int): Int {
                return when {
                    offset < 2 -> offset
                    else -> offset + 3
                }.coerceIn(0, formattedText.length)
            }

            override fun transformedToOriginal(offset: Int): Int {
                return when {
                    offset < 3 -> offset
                    offset == 3 -> 2
                    else -> offset - 3
                }.coerceIn(0, text.length)
            }
        }

        return TransformedText(formattedText, offset)
    }

    private fun AnnotatedString.Builder.appendMonthHint() {
        withStyle(SpanStyle(color = hintColor)) {
            append(EXPIRY_DATE_MONTH_HINT_FORMAT)
        }
    }

    private fun AnnotatedString.Builder.appendSlash() {
        withStyle(SpanStyle(color = hintColor)) {
            append(EXPIRY_DATE_SLASH)
        }
    }

    private fun AnnotatedString.Builder.appendYearHint() {
        withStyle(SpanStyle(color = hintColor)) {
            append(EXPIRY_DATE_YEAR_HINT_FORMAT)
        }
    }

    companion object {
        private const val EXPIRY_DATE_MONTH_HINT_FORMAT = "MM"
        private const val EXPIRY_DATE_SLASH = " / "
        private const val EXPIRY_DATE_YEAR_HINT_FORMAT = "YY"
    }
}
