package mega.android.core.ui.app.screen.main

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable

internal const val mainScreenRoute = "mainScreenRoute"

internal fun NavGraphBuilder.mainScreen(
    onNavigateToTooltip: () -> Unit
) {
    composable(route = mainScreenRoute) {
        MainComposeView(onNavigateToTooltip = onNavigateToTooltip)
    }
}
