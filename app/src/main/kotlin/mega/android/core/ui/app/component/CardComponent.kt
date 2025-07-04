package mega.android.core.ui.app.component

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import mega.android.core.ui.R
import mega.android.core.ui.app.util.Section
import mega.android.core.ui.components.card.PlanFeature
import mega.android.core.ui.components.card.PlansCard
import mega.android.core.ui.theme.spacing.LocalSpacing

@Composable
fun CardComponentCatalog() {
    Spacer(modifier = Modifier.height(LocalSpacing.current.x16))

    Section(header = "Plans Card Component") {
        PlansCard(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(16.dp),
            title = "Title",
            subtitle = "Expires 00/00/0000",
            price = "\$X.XX* / month",
            featuresSectionTitle = "Features",
            features = listOf(
                PlanFeature(
                    icon = R.drawable.ic_copy_01_medium_thin_outline,
                    title = "Feature 1"
                ),
                PlanFeature(
                    icon = R.drawable.ic_copy_01_medium_thin_outline,
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