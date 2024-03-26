package mega.android.core.ui.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import mega.android.core.ui.app.component.BottomSheetComponentCatalog
import mega.android.core.ui.app.component.ButtonComponentCatalog
import mega.android.core.ui.app.component.CheckboxCatalog
import mega.android.core.ui.app.component.ContentListComponent
import mega.android.core.ui.app.component.DividerComponentCatalog
import mega.android.core.ui.app.component.DropDownMenuComponent
import mega.android.core.ui.app.component.ListComponentCatalog
import mega.android.core.ui.app.component.ProgressIndicatorCatalog
import mega.android.core.ui.app.component.PromotionalDialogsCatalog
import mega.android.core.ui.app.component.PromotionalSheetsCatalog
import mega.android.core.ui.app.component.PromptCatalog
import mega.android.core.ui.app.component.ShimmerListItem
import mega.android.core.ui.app.component.TextComponentCatalog
import mega.android.core.ui.app.component.TextThumbnailComponentCatalog
import mega.android.core.ui.components.inputfields.TextInputBox
import mega.android.core.ui.components.inputfields.TextInputField
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
    var showCloseButton by remember { mutableStateOf(true) }
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .navigationBarsPadding()
    ) {
        item {
            DropDownMenuComponent()
        }
        item {
            ListComponentCatalog()
        }
        item {
            ButtonComponentCatalog()
        }
        item {
            ContentListComponent()
        }
        item {
            TextComponentCatalog()
        }
        item {
            CheckboxCatalog()
        }
        item {
            DividerComponentCatalog()
        }
        item {
            ProgressIndicatorCatalog()
        }
        item {
            ShimmerListItem()
        }
        item {
            PromptCatalog()
        }
        item {
            PromotionalSheetsCatalog(showCloseButton = showCloseButton) {
                showCloseButton = it
            }
        }
        item {
            PromotionalDialogsCatalog(showCloseButton = showCloseButton)
        }
        item {
            TextThumbnailComponentCatalog()
        }
        item {
            TextInputField(
                modifier = Modifier,
                label = "Test InputField",
                keyboardType = KeyboardType.Email,
                maxCharLimit = 190
            )
        }
        item {
            TextInputBox(
                modifier = Modifier,
                label = "Test InputBox",
                keyboardType = KeyboardType.Text
            )
        }
        item {
            BottomSheetComponentCatalog()
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