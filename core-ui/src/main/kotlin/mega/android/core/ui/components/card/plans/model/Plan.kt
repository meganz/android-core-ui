package mega.android.core.ui.components.card.plans.model

import androidx.compose.runtime.Immutable
import mega.android.core.ui.components.badge.BadgeType

typealias PlanBadgeAttributes = Pair<BadgeType, String?>

/**
 * Represents a plan with its title, description, price, and optional badge attributes.
 *
 * @property title The title of the plan.
 * @property description The description of the plan.
 * @property price The price of the plan.
 * @property badgeAttributes Optional attributes for the plan's badge, including type and text.
 */
@Immutable
data class Plan(
    val title: String,
    val description: String,
    val price: String,
    val badgeAttributes: PlanBadgeAttributes? = null,
)
