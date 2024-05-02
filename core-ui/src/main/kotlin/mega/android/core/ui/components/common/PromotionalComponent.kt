package mega.android.core.ui.components.common

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import mega.android.core.ui.components.MegaText
import mega.android.core.ui.components.list.FlexibleLineListItem
import mega.android.core.ui.theme.AppTheme
import mega.android.core.ui.theme.spacing.LocalSpacing
import mega.android.core.ui.theme.tokens.TextColor

@Immutable
data class PromotionalListAttributes(
    val title: String,
    val subtitle: String,
    @DrawableRes val icon: Int,
)

@Composable
internal fun PromotionalImage(
    imageUrl: String,
    description: String,
    modifier: Modifier = Modifier
) {
    AsyncImage(
        modifier = modifier
            .aspectRatio(16f / 9f)
            .clip(AppTheme.shapes.small)
            .background(AppTheme.colors.background.surface1),
        model = ImageRequest.Builder(LocalContext.current)
            .data(imageUrl)
            .crossfade(true)
            .build(),
        contentDescription = description,
        contentScale = ContentScale.FillWidth,
    )
}


@Composable
internal fun PromotionalFullImage(
    imageUrl: String,
    description: String,
    modifier: Modifier = Modifier
) {
    AsyncImage(
        modifier = modifier
            .aspectRatio(1.5f)
            .background(AppTheme.colors.background.surface1),
        model = ImageRequest.Builder(LocalContext.current)
            .data(imageUrl)
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
    listItems: List<PromotionalListAttributes> = emptyList(),
    contentText: String? = null,
    footer: String? = null,
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
            color = AppTheme.colors.button.brand,
            style = AppTheme.typography.titleMedium,
            textAlign = TextAlign.Center
        )

        MegaText(
            modifier = Modifier
                .padding(top = LocalSpacing.current.x8)
                .fillMaxWidth(),
            text = headline,
            textColor = TextColor.Primary,
            style = AppTheme.typography.headlineMedium,
            textAlign = TextAlign.Center
        )

        listItems.forEach { item ->
            FlexibleLineListItem(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = LocalSpacing.current.x24),
                title = item.title,
                subtitle = item.subtitle,
                leadingElement = {
                    Icon(
                        modifier = Modifier.size(48.dp),
                        painter = painterResource(id = item.icon),
                        contentDescription = item.title,
                    )
                }
            )
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

        if (!footer.isNullOrBlank()) {
            MegaText(
                modifier = Modifier
                    .padding(
                        top = LocalSpacing.current.x24,
                        start = LocalSpacing.current.x16,
                        end = LocalSpacing.current.x16
                    )
                    .fillMaxWidth(),
                text = footer,
                textColor = TextColor.Secondary,
                style = AppTheme.typography.bodySmall,
                textAlign = TextAlign.Center
            )
        }

        Spacer(
            modifier = Modifier
                .height(LocalSpacing.current.x32)
                .fillMaxWidth()
        )
    }
}