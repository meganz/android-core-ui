package mega.android.core.ui.components.common

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import coil.compose.AsyncImage
import coil.request.ImageRequest
import mega.android.core.ui.components.LinkSpannedText
import mega.android.core.ui.components.MegaText
import mega.android.core.ui.components.list.IconContentListItem
import mega.android.core.ui.components.list.ImageContentListItem
import mega.android.core.ui.components.text.ContentText
import mega.android.core.ui.theme.AppTheme
import mega.android.core.ui.theme.spacing.LocalSpacing
import mega.android.core.ui.theme.values.TextColor

@Immutable
data class PromotionalListAttributes(
    val title: String,
    val subtitle: String,
    @DrawableRes val icon: Int,
    val imageUrl: String? = null
)

@Composable
internal fun PromotionalImage(
    image: Any?,
    description: String,
    modifier: Modifier = Modifier
) {
    AsyncImage(
        modifier = modifier
            .aspectRatio(16f / 9f)
            .clip(AppTheme.shapes.small),
        model = ImageRequest.Builder(LocalContext.current)
            .data(image)
            .crossfade(true)
            .build(),
        contentDescription = description,
        contentScale = ContentScale.FillWidth,
    )
}

@Composable
internal fun PromotionalFullImage(
    image: Any?,
    description: String,
    modifier: Modifier = Modifier
) {
    AsyncImage(
        modifier = modifier
            .aspectRatio(1.5f),
        model = ImageRequest.Builder(LocalContext.current)
            .data(image)
            .crossfade(true)
            .build(),
        contentDescription = description,
        contentScale = ContentScale.FillWidth,
    )
}

@Composable
internal fun PromotionalContent(
    modifier: Modifier,
    title: String,
    headline: String,
    description: ContentText? = null,
    isIllustration: Boolean = false,
    listItems: List<PromotionalListAttributes> = emptyList(),
    contentText: String? = null,
    footer: ContentText? = null,
) {
    Column(
        modifier = modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            modifier = Modifier
                .fillMaxWidth(),
            text = title,
            color = AppTheme.colors.components.interactive,
            style = AppTheme.typography.titleMedium,
            textAlign = TextAlign.Center
        )

        MegaText(
            modifier = Modifier
                .padding(
                    top = LocalSpacing.current.x8,
                    start = LocalSpacing.current.x16,
                    end = LocalSpacing.current.x16
                )
                .fillMaxWidth(),
            text = headline,
            textColor = TextColor.Primary,
            style = AppTheme.typography.headlineMedium,
            textAlign = TextAlign.Center
        )

        if (description != null) {
            LinkSpannedText(
                modifier = Modifier
                    .padding(
                        top = LocalSpacing.current.x16,
                        start = LocalSpacing.current.x16,
                        end = LocalSpacing.current.x16
                    )
                    .fillMaxWidth(),
                value = description.text,
                baseTextColor = TextColor.Secondary,
                baseStyle = AppTheme.typography.bodyMedium.copy(textAlign = description.textAlign),
                spanStyles = description.spanStyles,
                onAnnotationClick = description.onClick
            )
        }

        val listItemModifier = Modifier
            .fillMaxWidth()
            .padding(
                top = LocalSpacing.current.x24,
                start = LocalSpacing.current.x16,
                end = LocalSpacing.current.x16
            )

        listItems.forEach { item ->

            if (isIllustration) {
                ImageContentListItem(
                    modifier = listItemModifier,
                    title = item.title,
                    subtitle = item.subtitle,
                    imageUrl = item.imageUrl.orEmpty()
                )
            } else {
                IconContentListItem(
                    modifier = listItemModifier,
                    iconResId = item.icon,
                    title = item.title,
                    subtitle = item.subtitle
                )
            }
        }

        if (!contentText.isNullOrBlank()) {
            MegaText(
                modifier = Modifier
                    .padding(
                        top = LocalSpacing.current.x24,
                        start = LocalSpacing.current.x16,
                        end = LocalSpacing.current.x16
                    )
                    .fillMaxWidth()
                    .align(Alignment.CenterHorizontally),
                text = contentText,
                textColor = TextColor.Secondary,
                style = AppTheme.typography.bodyMedium,
            )
        }

        if (footer != null) {
            MegaText(
                modifier = Modifier
                    .padding(
                        top = LocalSpacing.current.x24,
                        start = LocalSpacing.current.x16,
                        end = LocalSpacing.current.x16
                    )
                    .fillMaxWidth(),
                text = footer.text,
                textColor = TextColor.Secondary,
                style = AppTheme.typography.bodySmall,
                textAlign = footer.textAlign
            )
        }

        Spacer(
            modifier = Modifier
                .height(LocalSpacing.current.x32)
                .fillMaxWidth()
        )
    }
}