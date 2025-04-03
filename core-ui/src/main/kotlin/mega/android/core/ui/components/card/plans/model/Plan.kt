package mega.android.core.ui.components.card.plans.model

import androidx.compose.runtime.Immutable
import mega.android.core.ui.components.badge.BadgeType

typealias PlanBadgeAttributes = Pair<BadgeType, String?>

/**
 * Represents a plan with its title, description, price, and optional badge attributes.
 *
 * @property id The unique identifier of the plan.
 * @property title The title of the plan.
 * @property description The description of the plan.
 * @property price The price of the plan.
 * @property badgeAttributes Optional attributes for the plan's badge, including type and text.
 * @property enabled Indicates whether the plan is enabled or not.
 * @property tag Optional tag for the plan, used for testing purposes.
 */
@Immutable
data class Plan(
    val id: String,
    val title: String,
    val description: String,
    val price: String,
    val badgeAttributes: PlanBadgeAttributes? = null,
    val enabled: Boolean = true,
    val tag: String? = null,
)
