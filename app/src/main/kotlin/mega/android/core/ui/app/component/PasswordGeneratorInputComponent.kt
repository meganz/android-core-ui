package mega.android.core.ui.app.component

import android.content.ClipData
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalClipboard
import androidx.compose.ui.platform.toClipEntry
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import mega.android.core.ui.app.util.Section
import mega.android.core.ui.components.inputfields.PasswordGeneratorInputBox
import mega.android.core.ui.components.inputfields.PasswordGeneratorInputField
import mega.android.core.ui.theme.spacing.LocalSpacing

@Composable
fun PasswordGeneratorInputComponentCatalog() {
    var password by remember { mutableStateOf("") }
    val passwordInputBoxText = "uY3&y2O&nRIZ1@MLpl*kc$7RyaH^*glAwlMWk2nGPkM"
    val scope = rememberCoroutineScope()
    val clipboardManager = LocalClipboard.current

    Spacer(modifier = Modifier.height(LocalSpacing.current.x16))
    Section(header = "Password Generator Fields") {
        PasswordGeneratorInputField(
            modifier = Modifier
                .padding(top = LocalSpacing.current.x8)
                .padding(horizontal = LocalSpacing.current.x8),
            label = "Password",
            onValueChanged = {
                password = it
            },
            imeAction = ImeAction.Next,
            text = password
        )

        PasswordGeneratorInputBox(
            modifier = Modifier
                .height(184.dp)
                .padding(top = LocalSpacing.current.x8)
                .padding(horizontal = LocalSpacing.current.x8),
            text = passwordInputBoxText,
            onCopyClick = {
                scope.launch {
                    clipboardManager.setClipEntry(
                        ClipData.newPlainText(
                            "PLAIN_TEXT",
                            passwordInputBoxText
                        ).toClipEntry()
                    )
                }
            }
        )
    }
}