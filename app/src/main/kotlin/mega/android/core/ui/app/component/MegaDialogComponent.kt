package mega.android.core.ui.app.component

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import mega.android.core.ui.app.util.Section
import mega.android.core.ui.components.MegaText
import mega.android.core.ui.components.button.PrimaryFilledButton
import mega.android.core.ui.components.dialogs.BasicInputDialog
import mega.android.core.ui.components.dialogs.MegaDialog
import mega.android.core.ui.components.dialogs.MegaDialogBackground
import mega.android.core.ui.components.inputfields.PasswordGeneratorInputBox
import mega.android.core.ui.theme.AppTheme
import mega.android.core.ui.theme.spacing.LocalSpacing
import mega.android.core.ui.theme.values.TextColor

@Composable
fun MegaDialogComponent() {
    val spacing = LocalSpacing.current
    var showDialog by rememberSaveable { mutableStateOf(false) }
    var showInputDialog by rememberSaveable { mutableStateOf(false) }

    Spacer(modifier = Modifier.height(spacing.x16))

    Section(header = "Mega Dialog") {
        PrimaryFilledButton(
            modifier = Modifier.padding(horizontal = LocalSpacing.current.x16),
            text = "Open Mega Dialog",
            onClick = { showDialog = true }
        )
        Spacer(modifier = Modifier.height(spacing.x16))
        PrimaryFilledButton(
            modifier = Modifier.padding(horizontal = LocalSpacing.current.x16),
            text = "Open Mega Input Dialog",
            onClick = { showInputDialog = true }
        )

        if (showDialog) {
            MegaDialog(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(spacing.x16),
                dialogBackground = MegaDialogBackground.PageBackground,
                onDismissRequest = { showDialog = false }
            ) {
                MegaText(
                    modifier = Modifier.padding(start = spacing.x16),
                    text = "Sample Title",
                    textColor = TextColor.Primary,
                    style = AppTheme.typography.titleMedium
                )

                PasswordGeneratorInputBox(
                    modifier = Modifier
                        .height(184.dp)
                        .padding(top = LocalSpacing.current.x8)
                        .padding(horizontal = LocalSpacing.current.x8),
                    text = "uY3&y2O&nRIZ1@MLpl*kc$7RyaH^*glAwlMWk2nGPkM",
                    onCopyClick = {}
                )

                Row(modifier = Modifier.fillMaxWidth()) {
                    PrimaryFilledButton(
                        modifier = Modifier.padding(top = spacing.x16),
                        text = "Cancel",
                        onClick = { showDialog = false }
                    )

                    Spacer(modifier = Modifier.weight(1f))

                    PrimaryFilledButton(
                        modifier = Modifier.padding(top = spacing.x16),
                        text = "Confirm",
                        onClick = { showDialog = false }
                    )
                }
            }
        }

        if (showInputDialog) {
            var input by rememberSaveable(stateSaver = TextFieldValue.Saver) {
                mutableStateOf(TextFieldValue(".txt", TextRange(0)))
            }

            BasicInputDialog(
                title = "Type something",
                inputValue = input,
                onValueChange = { newValue ->
                    input = newValue
                },
                isAutoShowKeyboard = true,
                positiveButtonText = "Confirm",
                onPositiveButtonClicked = { showInputDialog = false },
                negativeButtonText = "Cancel",
                onNegativeButtonClicked = { showInputDialog = false },
                keyboardType = KeyboardType.Text,
                capitalization = KeyboardCapitalization.None,
                onDismiss = { showInputDialog = false },
                inputTextAlign = TextAlign.End
            )
        }
    }
}