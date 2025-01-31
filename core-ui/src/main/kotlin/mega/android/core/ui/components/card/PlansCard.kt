package mega.android.core.ui.components.card

import androidx.annotation.DrawableRes
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import mega.android.core.ui.R
import mega.android.core.ui.components.MegaText
import mega.android.core.ui.components.button.PrimaryFilledButton
import mega.android.core.ui.components.button.SecondaryFilledButton
import mega.android.core.ui.components.image.MegaIcon
import mega.android.core.ui.components.list.FlexibleLineListItem
import mega.android.core.ui.preview.CombinedThemePreviews
import mega.android.core.ui.theme.AndroidThemeForPreviews
import mega.android.core.ui.theme.AppTheme
import mega.android.core.ui.theme.shape.shapes
import mega.android.core.ui.theme.spacing.LocalSpacing
import mega.android.core.ui.theme.values.IconColor
import mega.android.core.ui.theme.values.LinkColor
import mega.android.core.ui.theme.values.TextColor

@Immutable
data class PlanFeature(
    @DrawableRes val icon: Int,
    val title: String
)

@Composable
fun PlansCard(
    title: String,
    modifier: Modifier = Modifier,
    subtitle: String? = null,
    price: String? = null,
    featuresSectionTitle: String? = null,
    features: List<PlanFeature> = emptyList(),
    linkText: String? = null,
    onLinkClickListener: (() -> Unit)? = null,
    primaryButtonText: String? = null,
    onPrimaryButtonClick: (() -> Unit)? = null,
    secondaryButtonText: String? = null,
    onSecondaryButtonClick: (() -> Unit)? = null,
    footerText: String? = null
) {
    val shape = remember { shapes.small }
    val spacing = LocalSpacing.current

    Column(
        modifier = modifier
            .clip(shape)
            .border(1.dp, AppTheme.colors.border.strong, shape)
            .padding(spacing.x16)
    ) {
        MegaText(
            modifier = Modifier
                .fillMaxWidth()
                .testTag(PlansCardTestTags.TITLE),
            text = title,
            textColor = TextColor.Primary,
            style = AppTheme.typography.titleMedium
        )

        subtitle?.let {
            MegaText(
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag(PlansCardTestTags.SUBTITLE),
                text = it,
                textColor = TextColor.Primary,
                style = AppTheme.typography.bodyMedium
            )
        }

        price?.let {
            MegaText(
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag(PlansCardTestTags.PRICING),
                text = it,
                textColor = TextColor.Secondary,
                style = AppTheme.typography.bodyMedium
            )
        }

        if (features.isNotEmpty()) {
            MegaText(
                modifier = Modifier
                    .padding(top = spacing.x24)
                    .testTag(PlansCardTestTags.FEATURES_SECTION_TITLE),
                text = featuresSectionTitle.orEmpty(),
                textColor = TextColor.Primary,
                style = AppTheme.typography.titleSmall
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(top = spacing.x8)
                    .testTag(PlansCardTestTags.FEATURES),
                verticalArrangement = Arrangement.spacedBy(spacing.x8)
            ) {
                features.forEachIndexed { index, feature ->
                    FlexibleLineListItem(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight()
                            .testTag("${PlansCardTestTags.FEATURE_ITEM}_$index"),
                        contentPadding = PaddingValues(0.dp),
                        subtitle = feature.title,
                        leadingElement = {
                            MegaIcon(
                                modifier = Modifier
                                    .size(24.dp)
                                    .align(Alignment.Center),
                                painter = painterResource(id = feature.icon),
                                tint = IconColor.Primary
                            )
                        },
                        minHeight = 0.dp,
                        enableClick = false
                    )
                }
            }
        }

        linkText?.let {
            MegaText(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = spacing.x24)
                    .clickable { onLinkClickListener?.invoke() }
                    .testTag(PlansCardTestTags.LINK),
                text = it,
                linkColor = LinkColor.Primary,
                style = AppTheme.typography.bodyMedium,
            )
        }

        primaryButtonText?.let {
            PrimaryFilledButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = spacing.x24)
                    .testTag(PlansCardTestTags.PRIMARY_BUTTON),
                text = it,
                onClick = {
                    onPrimaryButtonClick?.invoke()
                }
            )
        }

        secondaryButtonText?.let {
            SecondaryFilledButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = spacing.x8)
                    .testTag(PlansCardTestTags.SECONDARY_BUTTON),
                text = it,
                onClick = { onSecondaryButtonClick?.invoke() }
            )
        }

        footerText?.let {
            MegaText(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = spacing.x16)
                    .testTag(PlansCardTestTags.FOOTER),
                text = it,
                textColor = TextColor.Primary,
                style = AppTheme.typography.bodySmall
            )
        }
    }
}

@Composable
@CombinedThemePreviews
fun PlansCardPreview() {
    AndroidThemeForPreviews {
        PlansCard(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(16.dp),
            title = "Title",
            subtitle = "Expires 00/00/0000",
            price = "\$X.XX* / month",
            featuresSectionTitle = "Your plan includes",
            features = listOf(
                PlanFeature(
                    icon = R.drawable.ic_copy,
                    title = "Feature 1"
                ),
                PlanFeature(
                    icon = R.drawable.ic_copy,
                    title = "Feature 2"
                ),
            ),
            linkText = "See all benefits",
            onLinkClickListener = {},
            primaryButtonText = "Buy now",
            onPrimaryButtonClick = {},
            secondaryButtonText = "Cancel",
            onSecondaryButtonClick = {},
            footerText = "* Estimated price in your local currency. Your account will be billed in euros for all transactions."
        )
    }
}

internal object PlansCardTestTags {
    private const val PLANS_CARD = "plans_card"
    const val TITLE = "${PLANS_CARD}:title"
    const val SUBTITLE = "${PLANS_CARD}:subtitle"
    const val PRICING = "${PLANS_CARD}:pricing"
    const val FEATURES_SECTION_TITLE = "${PLANS_CARD}:features_section_title"
    const val FEATURES = "${PLANS_CARD}:features"
    const val FEATURE_ITEM = "${PLANS_CARD}:feature_item"
    const val LINK = "${PLANS_CARD}:link"
    const val PRIMARY_BUTTON = "${PLANS_CARD}:primary_button"
    const val SECONDARY_BUTTON = "${PLANS_CARD}:secondary_button"
    const val FOOTER = "${PLANS_CARD}:footer"
}