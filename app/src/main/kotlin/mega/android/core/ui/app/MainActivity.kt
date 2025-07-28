package mega.android.core.ui.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.core.view.WindowCompat
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import mega.android.core.ui.app.screen.floatingtoolbar.FloatingToolbarScreen
import mega.android.core.ui.app.screen.main.mainScreen
import mega.android.core.ui.app.screen.main.mainScreenRoute
import mega.android.core.ui.app.screen.reorderablelist.navigateToReorderableListScreen
import mega.android.core.ui.app.screen.reorderablelist.reorderableListScreen
import mega.android.core.ui.app.screen.tooltip.navigateToTooltip
import mega.android.core.ui.app.screen.tooltip.tooltipScreen
import mega.android.core.ui.components.surface.ThemedSurface
import mega.android.core.ui.theme.AndroidTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            val navController = rememberNavController()
            // A surface container using the 'background' color from the theme
            AndroidTheme(isDark = isSystemInDarkTheme(), fromAutofill = true) {
                ThemedSurface {
                    NavHost(navController = navController, startDestination = mainScreenRoute) {
                        mainScreen(
                            onNavigateToTooltip = {
                                navController.navigateToTooltip()
                            },
                            onNavigateToReorderableList = {
                                navController.navigateToReorderableListScreen()
                            },
                            onNavigateToFloatingToolbar = {
                                navController.navigate("floatingToolbar")
                            }
                        )

                        tooltipScreen()

                        reorderableListScreen(navController::popBackStack)
                        
                        composable("floatingToolbar") {
                            FloatingToolbarScreen()
                        }
                    }
                }
            }
        }
    }
}
