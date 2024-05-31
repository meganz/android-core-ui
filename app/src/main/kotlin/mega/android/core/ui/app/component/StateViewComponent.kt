package mega.android.core.ui.app.component

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import mega.android.core.ui.app.util.Section
import mega.android.core.ui.components.state.EmptyStateView
import mega.android.core.ui.model.MegaSpanStyle
import mega.android.core.ui.model.SpanIndicator
import mega.android.core.ui.model.SpanStyleWithAnnotation
import mega.android.core.ui.theme.spacing.LocalSpacing
import mega.android.core.ui.theme.values.LinkColor

@Composable
fun StateViewCatalog() {
    Spacer(modifier = Modifier.height(LocalSpacing.current.x16))
    Section(header = "EmptyStateView") {
        EmptyStateView(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            illustration = android.R.drawable.ic_delete,
            title = " A short and concise explanation. If possible in positive way",
            description = "Explain clearly the next action to populate the space. You may also explain why the space is empty and include the benefit of taking this step. Please contact [A]support.email.com[/A]",
            descriptionSpanStyles = mapOf(
                SpanIndicator('A') to SpanStyleWithAnnotation(
                    megaSpanStyle = MegaSpanStyle.LinkColorStyle(
                        spanStyle = SpanStyle(),
                        linkColor = LinkColor.Primary
                    ),
                    annotation = "support.email.com"
                        .substringAfter("[A]")
                        .substringBefore("[/A]")
                )
            ),
            buttonText = "Primary Button",
            onButtonClick = { }
        )
    }
}