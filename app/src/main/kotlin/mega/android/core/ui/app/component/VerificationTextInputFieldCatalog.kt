package mega.android.core.ui.app.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import mega.android.core.ui.app.util.Section
import mega.android.core.ui.components.button.PrimaryFilledButton
import mega.android.core.ui.components.inputfields.VerificationTextInputField
import mega.android.core.ui.theme.spacing.LocalSpacing

@Composable
fun VerificationTextInputFieldCatalog() {
    val errorText by remember { mutableStateOf("Incorrect password") }
    var value by remember { mutableStateOf("") }
    var isCorrect: Boolean? by remember { mutableStateOf(null) }

    Spacer(modifier = Modifier.height(LocalSpacing.current.x16))

    Section(
        header = "Verification Input Field"
    ) {
        Column(modifier = Modifier.padding(all = LocalSpacing.current.x16)) {
            VerificationTextInputField(
                value = value,
                errorText = errorText,
                isCodeCorrect = isCorrect,
                onValueChange = {
                    value = it
                }
            )

            Row(modifier = Modifier.padding(top = LocalSpacing.current.x16)) {
                PrimaryFilledButton(
                    modifier = Modifier.wrapContentSize(),
                    text = "Correct",
                    onClick = { isCorrect = true })

                PrimaryFilledButton(
                    modifier = Modifier
                        .wrapContentSize()
                        .padding(start = LocalSpacing.current.x32),
                    text = "Incorrect",
                    onClick = { isCorrect = false })
            }
        }
    }

}