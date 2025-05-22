package mega.android.core.ui.app.screen.ReorderableList

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable

internal const val reorderableListScreenRoute = "reorderableListScreenRoute"

internal fun NavGraphBuilder.reorderableListScreen() {
    composable(route = reorderableListScreenRoute) {
        ReorderableListRoute(
            modifier = Modifier
                .fillMaxSize()
                .safeDrawingPadding()
        )
    }
}

internal fun NavController.navigateToReorderableListScreen() {
    navigate(route = reorderableListScreenRoute)
}