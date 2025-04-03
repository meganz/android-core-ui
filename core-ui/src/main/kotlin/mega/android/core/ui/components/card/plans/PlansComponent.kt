package mega.android.core.ui.components.card.plans

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import mega.android.core.ui.components.MegaText
import mega.android.core.ui.components.badge.Badge
import mega.android.core.ui.components.badge.BadgeType
import mega.android.core.ui.components.button.MegaRadioButton
import mega.android.core.ui.components.card.plans.model.Plan
import mega.android.core.ui.preview.CombinedThemePreviews
import mega.android.core.ui.theme.AndroidThemeForPreviews
import mega.android.core.ui.theme.AppTheme
import mega.android.core.ui.theme.spacing.LocalSpacing
import mega.android.core.ui.theme.values.TextColor


/**
 * A reusable component that displays a plan with a title, price, description, and an optional badge.
 * [Figma](https://www.figma.com/design/YeIAHyjIw6kTemmHdxKXGh/%5BDSN-2628%5D-12-and-24-month-standalone-plans-for-VPN?node-id=2498-121915&t=8QabI4P3dcJ9y3W1-0).
 *
 * @param plan The plan data to be displayed, including title, price, description, and optional badge attributes.
 * @param modifier Optional modifier to customize the layout.
 * @param selected Boolean indicating if the plan is currently selected.
 * @param trailingElement An optional composable element to display at the end of the row.
 *
 */
@Composable
fun PlansComponent(
    plan: Plan,
    modifier: Modifier = Modifier,
    selected: Boolean = false,
    trailingElement: (@Composable (BoxScope.() -> Unit))? = null,
) {
    val spacing = LocalSpacing.current

    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(AppTheme.shapes.small)
            .background(AppTheme.colors.background.pageBackground)
            .border(
                width = 1.dp,
                color = when {
                    plan.enabled == false -> AppTheme.colors.border.disabled
                    selected -> AppTheme.colors.border.strongSelected
                    else -> AppTheme.colors.border.strong
                },
                shape = AppTheme.shapes.small
            )
            .padding(spacing.x16),
    ) {
        Column(
            modifier = Modifier.weight(1f),
            horizontalAlignment = Alignment.Start
        ) {
            Row {
                MegaText(
                    text = plan.title,
                    style = AppTheme.typography.titleMedium,
                    textColor = if (plan.enabled) TextColor.Primary else TextColor.Disabled,
                )

                plan.badgeAttributes?.let {
                    Badge(
                        modifier = Modifier
                            .padding(start = spacing.x4)
                            .align(Alignment.CenterVertically),
                        badgeType = it.first,
                        text = it.second.orEmpty(),
                    )
                }
            }

            MegaText(
                text = plan.price,
                style = AppTheme.typography.titleLarge,
                textColor = if (plan.enabled) TextColor.Primary else TextColor.Disabled,
                modifier = Modifier.padding(top = spacing.x24)
            )
            MegaText(
                text = plan.description,
                style = AppTheme.typography.bodySmall,
                textColor = if (plan.enabled) TextColor.Secondary else TextColor.Disabled,
            )
        }

        trailingElement?.let { element ->
            Box(modifier = Modifier.align(Alignment.CenterVertically)) {
                element()
            }
        }
    }
}

@CombinedThemePreviews
@Composable
private fun PlansComponentPreview() {
    AndroidThemeForPreviews {
        PlansComponent(
            modifier = Modifier.padding(LocalSpacing.current.x16),
            plan = Plan(
                title = "Monthly",
                price = "$3.99",
                description = "EUR per month. Billed every month"
            ),
            selected = false,
            trailingElement = {
                MegaRadioButton(
                    identifier = "PlansComponentRadioButton",
                    onOptionSelected = {},
                    selected = false,
                )
            }
        )
    }
}

@CombinedThemePreviews
@Composable
private fun PlansComponentSelectedPreview() {
    AndroidThemeForPreviews {
        PlansComponent(
            modifier = Modifier.padding(LocalSpacing.current.x16),
            plan = Plan(
                title = "Yearly",
                price = "$1.99",
                description = "EUR per month. Billed €23.88 every 12 months",
                badgeAttributes = BadgeType.Mega to "Save 20%"
            ),
            selected = true,
            trailingElement = {
                MegaRadioButton(
                    identifier = "PlansComponentRadioButton",
                    onOptionSelected = {},
                    selected = false,
                )
            }
        )
    }
}

@CombinedThemePreviews
@Composable
private fun PlansComponentSecondarySelectedPreview() {
    AndroidThemeForPreviews {
        PlansComponent(
            modifier = Modifier.padding(LocalSpacing.current.x16),
            plan = Plan(
                title = "Yearly",
                price = "$1.99",
                description = "EUR per month. Billed €23.88 every 12 months",
                badgeAttributes = BadgeType.MegaSecondary to "Save 20%",
            ),
            selected = true,
            trailingElement = {
                MegaRadioButton(
                    identifier = "PlansComponentRadioButton",
                    onOptionSelected = {},
                    selected = false,
                )
            }
        )
    }
}