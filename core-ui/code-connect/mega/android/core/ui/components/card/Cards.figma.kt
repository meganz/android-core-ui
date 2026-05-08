/*
 * Code Connect mapping for the Figma `Pricing card` component set.
 *
 * Figma file : HagXfSTF94ICEFj8rfNyLc  (ANDROID - New)
 * Component  : Pricing card  (node 61291:4206)
 * Variants matched:
 *   State = Default  (maps to PlansComponent for the single row variant)
 *   (State=Selected, Show list, Show badge are runtime state or list content.)
 *
 * Figma properties IGNORED:
 *   Show badge — simplified; Compose PlansCard has no flat badge toggle.
 *
 * Compose side:
 *   - PlansCard(title, subtitle?, price?, features, primaryButtonText?, …)
 *   - PlansComponent(plan, modifier, selected, trailingElement?) — single pricing row
 *
 * Complex model params (Plan, PlanFeature sealed/data classes) cannot be flattened
 * into Figma props — examples emit `emptyList()` with TODOs.
 *
 * NOTE — this file requires the `com.figma.code.connect` Gradle plugin.
 *
 * Property names VERIFIED via MCP on 2026-04-23.
 */

package mega.android.core.ui.components.card

import androidx.compose.runtime.Composable
import com.figma.code.connect.FigmaConnect
import com.figma.code.connect.FigmaVariant
import mega.android.core.ui.components.card.plans.PlansComponent
import mega.android.core.ui.components.card.plans.model.Plan

@FigmaConnect(url = "https://www.figma.com/design/HagXfSTF94ICEFj8rfNyLc/ANDROID---New?node-id=61291-4206")
class PlansCardConnection {
    @Composable
    fun PlansCardExample() {
        // TODO: populate Plan model + features from Figma content.
        PlansCard(
            title = "Pro I",
            features = emptyList(),
        )
    }
}

@FigmaConnect(url = "https://www.figma.com/design/HagXfSTF94ICEFj8rfNyLc/ANDROID---New?node-id=61291-4206")
@FigmaVariant("State", "Default")
class PlansComponentConnection {
    @Composable
    fun PlansComponentExample() {
        // TODO: populate Plan model from Figma content.
        PlansComponent(
            plan = Plan(
                id = "plan",
                title = "Plan",
                description = "Description",
                price = "\$X.XX",
            ),
            selected = false,
        )
    }
}
