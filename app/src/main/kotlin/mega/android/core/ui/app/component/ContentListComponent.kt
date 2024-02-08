package mega.android.core.ui.app.component

import android.R
import android.widget.Toast
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.unit.dp
import mega.android.core.ui.app.util.Section
import mega.android.core.ui.components.list.IconContentListItem
import mega.android.core.ui.components.list.ImageContentListItem
import mega.android.core.ui.components.list.NumberContentListItem
import mega.android.core.ui.components.list.PlainContentListItem
import mega.android.core.ui.model.MegaSpanStyle
import mega.android.core.ui.model.SpanIndicator
import mega.android.core.ui.model.SpanStyleWithAnnotation
import mega.android.core.ui.theme.spacing.LocalSpacing
import mega.android.core.ui.theme.tokens.LinkColor

@Composable
fun ContentListComponent() {
    val context = LocalContext.current

    Spacer(modifier = Modifier.height(LocalSpacing.current.x16))

    Section(header = "Plain Content List Item") {
        PlainContentListItem(
            modifier = Modifier
                .padding(16.dp),
            title = "This is a [A]title[/A]",
            titleAnnotations = mapOf(
                SpanIndicator('A') to SpanStyleWithAnnotation(
                    MegaSpanStyle.LinkColorStyle(
                        SpanStyle(),
                        LinkColor.Primary
                    ),
                    "title",
                )
            ),
            onTitleAnnotationClick = { annotation ->
                Toast.makeText(context, "Title annotation clicked: $annotation", Toast.LENGTH_SHORT).show()
            },
            subtitle = "This is a [A]subtitle[/A]",
            subtitleAnnotations = mapOf(
                SpanIndicator('A') to SpanStyleWithAnnotation(
                    MegaSpanStyle.LinkColorStyle(
                        SpanStyle(),
                        LinkColor.Primary
                    ),
                    "subtitle",
                )
            ),
            onSubtitleAnnotationClick = { annotation ->
                Toast.makeText(context, "Subtitle annotation clicked: $annotation", Toast.LENGTH_SHORT).show()
            }
        )
    }

    Section(header = "Image Content List Item") {
        ImageContentListItem(
            modifier = Modifier
                .padding(16.dp),
            imageUrl = "https://placehold.co/400x400/FF0000/FFFFFF/png",
            title = "This is a [A]title[/A]",
            titleAnnotations = mapOf(
                SpanIndicator('A') to SpanStyleWithAnnotation(
                    MegaSpanStyle.LinkColorStyle(
                        SpanStyle(),
                        LinkColor.Primary
                    ), "title",
                )
            ),
            onTitleAnnotationClick = { annotation ->
                Toast.makeText(context, "Title annotation clicked: $annotation", Toast.LENGTH_SHORT).show()
            },
            subtitle = "This is a [A]subtitle[/A]",
            subtitleAnnotations = mapOf(
                SpanIndicator('A') to SpanStyleWithAnnotation(
                    MegaSpanStyle.LinkColorStyle(
                        SpanStyle(),
                        LinkColor.Primary
                    ), "subtitle",
                )
            ),
            onSubtitleAnnotationClick = { annotation ->
                Toast.makeText(context, "Subtitle annotation clicked: $annotation", Toast.LENGTH_SHORT).show()
            }
        )
    }

    Section(header = "Icon Content List Item") {
        IconContentListItem(
            modifier = Modifier
                .padding(16.dp),
            iconResId = R.drawable.ic_dialog_info,
            title = "This is a [A]title[/A]",
            titleAnnotations = mapOf(
                SpanIndicator('A') to SpanStyleWithAnnotation(
                    MegaSpanStyle.LinkColorStyle(
                        SpanStyle(),
                        LinkColor.Primary
                    ), "title",
                )
            ),
            onTitleAnnotationClick = { annotation ->
                Toast.makeText(context, "Title annotation clicked: $annotation", Toast.LENGTH_SHORT).show()
            },
            subtitle = "This is a [A]subtitle[/A]",
            subtitleAnnotations = mapOf(
                SpanIndicator('A') to SpanStyleWithAnnotation(
                    MegaSpanStyle.LinkColorStyle(
                        SpanStyle(),
                        LinkColor.Primary
                    ), "subtitle",
                )
            ),
            onSubtitleAnnotationClick = { annotation ->
                Toast.makeText(context, "Subtitle annotation clicked: $annotation", Toast.LENGTH_SHORT).show()
            }
        )
    }

    Section(header = "Number Content List Item") {
        NumberContentListItem(
            modifier = Modifier
                .padding(16.dp),
            number = 1,
            title = "This is a [A]title[/A]",
            titleAnnotations = mapOf(
                SpanIndicator('A') to SpanStyleWithAnnotation(
                    MegaSpanStyle.LinkColorStyle(
                        SpanStyle(),
                        LinkColor.Primary
                    ), "title",
                )
            ),
            onTitleAnnotationClick = { annotation ->
                Toast.makeText(context, "Title annotation clicked: $annotation", Toast.LENGTH_SHORT).show()
            },
            subtitle = "This is a [A]subtitle[/A]",
            subtitleAnnotations = mapOf(
                SpanIndicator('A') to SpanStyleWithAnnotation(
                    MegaSpanStyle.LinkColorStyle(
                        SpanStyle(),
                        LinkColor.Primary
                    ), "subtitle",
                )
            ),
            onSubtitleAnnotationClick = { annotation ->
                Toast.makeText(context, "Subtitle annotation clicked: $annotation", Toast.LENGTH_SHORT).show()
            }
        )
    }
}
