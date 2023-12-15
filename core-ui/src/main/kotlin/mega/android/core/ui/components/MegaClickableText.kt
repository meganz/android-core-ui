package mega.android.core.ui.components

import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.LocalTextStyle
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.tooling.preview.Preview
import mega.android.core.ui.theme.AppTheme

/**
 * Custom implementation of ClickableText following our design
 * Applies Primary Link color from Color Tokens
 * @param text
 * @param onClick
 * @param modifier
 * @param style
 */
@Composable
fun MegaClickableText(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    style: TextStyle = LocalTextStyle.current,
) {
    val annotatedLinkString = buildAnnotatedString {
        append(text)
    }

    ClickableText(
        modifier = modifier,
        text = annotatedLinkString,
        style = style.copy(color = AppTheme.colors.link.primary),
        onClick = { onClick() }
    )
}

@Preview
@Composable
fun MegaClickableTextPreview() {
    MegaClickableText(text = "Lost your authenticator device?", onClick = {})
}
