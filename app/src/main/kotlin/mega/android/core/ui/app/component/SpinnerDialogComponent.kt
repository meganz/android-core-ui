package mega.android.core.ui.app.component

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import mega.android.core.ui.app.util.Section
import mega.android.core.ui.components.button.PrimaryFilledButton
import mega.android.core.ui.components.dialogs.BasicSpinnerDialog
import mega.android.core.ui.theme.spacing.LocalSpacing

@Composable
fun SpinnerDialogComponent() {
    val spacing = LocalSpacing.current
    var showDialog by remember { mutableStateOf(false) }

    Spacer(modifier = Modifier.height(spacing.x16))

    Section(header = "Spinner Dialog") {
        PrimaryFilledButton(
            modifier = Modifier.padding(horizontal = LocalSpacing.current.x16),
            text = "Open Spinner Dialog",
            onClick = { showDialog = true }
        )

        if (showDialog) {
            BasicSpinnerDialog(
                contentText = "Processing link...",
                dismissOnClickOutside = true,
                dismissOnBackPress = true,
                onDismiss = { showDialog = false }
            )
        }
    }
}