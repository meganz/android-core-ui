package mega.android.core.ui.components

import android.R.attr.onClick
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.LinkAnnotation
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withLink
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import mega.android.core.ui.preview.CombinedThemePreviews
import mega.android.core.ui.theme.AndroidThemeForPreviews
import mega.android.core.ui.tokens.theme.DSTokens

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
    val linkAnnotation = LinkAnnotation.Clickable(
        tag = "clickable_text",
        linkInteractionListener = { onClick() }
    )

    val annotatedLinkString = buildAnnotatedString {
        withLink(linkAnnotation) {
            append(text)
        }
    }

    Text(
        modifier = modifier,
        text = annotatedLinkString,
        style = style.copy(color = DSTokens.colors.link.primary)
    )
}

@CombinedThemePreviews
@Composable
fun MegaClickableTextPreview() {
    var clickCount by remember { mutableIntStateOf(0) }

    AndroidThemeForPreviews {
        Column {
            MegaClickableText(text = "Click counter: $clickCount", onClick = {
                clickCount++
            })
        }
    }
}
