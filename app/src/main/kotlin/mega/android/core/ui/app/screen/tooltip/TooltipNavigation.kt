package mega.android.core.ui.app.screen.tooltip

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable

internal const val tooltipScreenRoute = "tooltipScreenRoute"

internal fun NavGraphBuilder.tooltipScreen() {
    composable(route = tooltipScreenRoute) {
        TooltipRoute(
            modifier = Modifier
                .fillMaxSize()
                .safeDrawingPadding()
        )
    }
}

internal fun NavController.navigateToTooltip() {
    navigate(route = tooltipScreenRoute)
}
