package mega.android.core.ui.components.card.plans

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import mega.android.core.ui.components.badge.BadgeType
import mega.android.core.ui.components.button.MegaRadioButton
import mega.android.core.ui.components.card.plans.model.Plan
import mega.android.core.ui.preview.CombinedThemePreviews
import mega.android.core.ui.theme.AndroidThemeForPreviews
import mega.android.core.ui.theme.spacing.LocalSpacing

@Composable
fun PlansRadioSelection(
    plans: ImmutableList<Plan>,
    onPlanSelected: (Plan) -> Unit,
    modifier: Modifier = Modifier,
    selectedPlan: Plan? = null,
) {
    val spacing = LocalSpacing.current

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(spacing.x16),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        plans.forEach { plan ->
            PlansComponent(
                modifier = Modifier
                    .then(
                        if (plan.tag.isNullOrBlank()) {
                            Modifier
                        } else {
                            Modifier.testTag(plan.tag)
                        }
                    )
                    .fillMaxWidth()
                    .clickable(
                        onClick = { onPlanSelected(plan) },
                        enabled = plan.enabled,
                    ),
                selected = selectedPlan == plan,
                plan = plan,
                trailingElement = {
                    MegaRadioButton(
                        selected = selectedPlan == plan,
                        identifier = plan,
                        onOptionSelected = { onPlanSelected(plan) },
                        enabled = plan.enabled,
                    )
                },
            )
        }
    }
}

@CombinedThemePreviews
@Composable
internal fun PlansRadioSelectionPreview() {
    AndroidThemeForPreviews {
        var selectedPlan by remember { mutableStateOf<Plan?>(null) }
        val plans = listOf(
            Plan("1", "Plan 1", "Description 1", "$10", badgeAttributes = BadgeType.Mega to "Save 20%"),
            Plan("1", "Plan 2", "Description 2", "$20"),
            Plan("1", "Plan 3", "Description 3", "$30"),
            Plan("1", "Plan 4", "Description 4", "$40", enabled = false),
        )
        PlansRadioSelection(
            modifier = Modifier.padding(16.dp),
            plans = plans.toImmutableList(),
            onPlanSelected = {
                selectedPlan = it
            },
            selectedPlan = selectedPlan
        )
    }
}