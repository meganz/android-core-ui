package mega.android.core.ui.app.screen.reorderablelist

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable

internal const val reorderableListScreenRoute = "reorderableListScreenRoute"

internal fun NavGraphBuilder.reorderableListScreen(
    closeScreen: () -> Unit,
) {
    composable(route = reorderableListScreenRoute) {
        ReorderableListRoute(
            modifier = Modifier.fillMaxSize(),
            closeScreen = closeScreen,
        )
    }
}

internal fun NavController.navigateToReorderableListScreen() {
    navigate(route = reorderableListScreenRoute)
}