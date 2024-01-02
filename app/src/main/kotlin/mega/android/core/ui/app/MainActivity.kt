package mega.android.core.ui.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import mega.android.core.ui.components.surface.ThemedSurface
import mega.android.core.ui.preview.CombinedThemePreviews
import mega.android.core.ui.shared.theme.CoreUITheme
import mega.android.core.ui.theme.AndroidThemeForPreviews

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            // A surface container using the 'background' color from the theme
            CoreUITheme(isDark = isSystemInDarkTheme()) {
                ThemedSurface {
                    MainComposeView()
                }
            }
        }
    }
}

@Composable
fun MainComposeView() {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
    ) {
        item {
            ListComponentCatalog()
            TextComponentCatalog()
        }
    }
}

@Composable
@CombinedThemePreviews
private fun MainComposeViewPreview() {
    AndroidThemeForPreviews {
        MainComposeView()
    }
}