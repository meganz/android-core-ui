package mega.android.core.ui.app.component

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.unit.dp
import mega.android.core.ui.app.util.Section
import mega.android.core.ui.components.LinkSpannedText
import mega.android.core.ui.model.MegaSpanStyle
import mega.android.core.ui.model.SpanIndicator
import mega.android.core.ui.model.SpanStyleWithAnnotation
import mega.android.core.ui.theme.AppTheme
import mega.android.core.ui.theme.spacing.LocalSpacing
import mega.android.core.ui.theme.values.LinkColor
import mega.android.core.ui.theme.values.TextColor

@Composable
fun TextComponentCatalog() {
    Spacer(modifier = Modifier.height(LocalSpacing.current.x16))

    Section(header = "Link Spanned Text") {
        var counter by remember { mutableIntStateOf(1) }

        LinkSpannedText(
            value = "Click [A]here[/A] to increase the counter: [B]$counter[/B]\n and [R]here[/R] to reset",
            spanStyles = hashMapOf(
                SpanIndicator('A') to SpanStyleWithAnnotation(
                    MegaSpanStyle.LinkColorStyle(
                        SpanStyle(),
                        LinkColor.Primary
                    ), "url or whatever you want to receive in onAnnotationClick"
                ),
                SpanIndicator('R') to SpanStyleWithAnnotation(
                    MegaSpanStyle.TextColorStyle(
                        SpanStyle(),
                        TextColor.Secondary
                    ), "reset"
                ),
                SpanIndicator('B') to SpanStyleWithAnnotation(
                    MegaSpanStyle.LinkColorStyle(
                        SpanStyle(),
                        LinkColor.Primary
                    ), "d"
                )
            ),
            onAnnotationClick = { annotation ->
                if (annotation == "reset") {
                    counter = 1
                } else {
                    counter += 1
                }
            },
            modifier = Modifier.padding(16.dp),
            baseStyle = AppTheme.typography.titleSmall
        )
    }
}