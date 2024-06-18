package mega.android.core.ui.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
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
import mega.android.core.ui.app.component.CardComponentCatalog
import mega.android.core.ui.app.component.CheckboxCatalog
import mega.android.core.ui.app.component.ContentListComponent
import mega.android.core.ui.app.component.DividerComponentCatalog
import mega.android.core.ui.app.component.DropDownMenuComponent
import mega.android.core.ui.app.component.ListComponentCatalog
import mega.android.core.ui.app.component.MegaDialogComponent
import mega.android.core.ui.app.component.PasswordGeneratorInputComponentCatalog
import mega.android.core.ui.app.component.ProgressIndicatorCatalog
import mega.android.core.ui.app.component.PromotionalDialogsCatalog
import mega.android.core.ui.app.component.PromotionalSheetsCatalog
import mega.android.core.ui.app.component.PromptCatalog
import mega.android.core.ui.app.component.RadioButtonCatalog
import mega.android.core.ui.app.component.ReadOnlyInputFieldComponent
import mega.android.core.ui.app.component.ShimmerListItem
import mega.android.core.ui.app.component.SliderComponentCatalog
import mega.android.core.ui.app.component.StateViewCatalog
import mega.android.core.ui.app.component.TabsComponentCatalog
import mega.android.core.ui.app.component.TextComponentCatalog
import mega.android.core.ui.app.component.TextThumbnailComponentCatalog
import mega.android.core.ui.app.component.VerificationTextInputFieldCatalog
import mega.android.core.ui.components.inputfields.TextInputBox
import mega.android.core.ui.components.inputfields.TextInputField
import mega.android.core.ui.components.surface.ThemedSurface
import mega.android.core.ui.model.IllustrationIconSizeMode
import mega.android.core.ui.preview.CombinedThemePreviews
import mega.android.core.ui.theme.AndroidTheme
import mega.android.core.ui.theme.AndroidThemeForPreviews

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            // A surface container using the 'background' color from the theme
            AndroidTheme(isDark = isSystemInDarkTheme()) {
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
    var illustrationMode by remember { mutableStateOf(IllustrationIconSizeMode.Small) }
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .navigationBarsPadding()
    ) {
        item(key = 0) {
            DropDownMenuComponent()
        }
        item(key = 1) {
            ListComponentCatalog()
        }
        item(key = 2) {
            ButtonComponentCatalog()
        }
        item(key = 3) {
            ContentListComponent()
        }
        item(key = 4) {
            TextComponentCatalog()
        }
        item(key = 5) {
            TabsComponentCatalog()
        }
        item(key = 6) {
            CheckboxCatalog()
        }
        item(key = 7) {
            DividerComponentCatalog()
        }
        item(key = 8) {
            ProgressIndicatorCatalog()
        }
        item(key = 9) {
            ShimmerListItem()
        }
        item(key = 10) {
            PromptCatalog()
        }
        item(key = 11) {
            PromotionalSheetsCatalog(
                showCloseButton = showCloseButton,
                illustrationMode = illustrationMode,
                onShowCloseButtonChange = { showCloseButton = it },
                onIllustrationModeChange = { illustrationMode = it }
            )
        }
        item(key = 12) {
            PromotionalDialogsCatalog(
                showCloseButton = showCloseButton,
                illustrationsMode = illustrationMode
            )
        }
        item(key = 13) {
            TextThumbnailComponentCatalog()
        }
        item(key = 14) {
            TextInputField(
                modifier = Modifier,
                label = "Test InputField",
                keyboardType = KeyboardType.Email,
                maxCharLimit = 190
            )
        }
        item(key = 15) {
            TextInputBox(
                modifier = Modifier,
                label = "Test InputBox",
                keyboardType = KeyboardType.Text
            )
        }
        item(key = 16) {
            ReadOnlyInputFieldComponent()
        }
        item(key = 17) {
            SliderComponentCatalog()
        }
        item(key = 18) {
            PasswordGeneratorInputComponentCatalog()
        }
        item(key = 19) {
            MegaDialogComponent()
        }
        item(key = 20) {
            BottomSheetComponentCatalog()
        }
        item(key = 21) {
            StateViewCatalog()
        }
        item(key = 22) {
            VerificationTextInputFieldCatalog()
        }
        item(key = 23) {
            RadioButtonCatalog()
        }
        item(key = 24 ) {
            CardComponentCatalog()
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