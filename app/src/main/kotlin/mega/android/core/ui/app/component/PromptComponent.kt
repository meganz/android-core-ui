package mega.android.core.ui.app.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import mega.android.core.ui.app.util.Section
import mega.android.core.ui.components.prompt.ErrorPrompt
import mega.android.core.ui.components.prompt.SuccessPrompt
import mega.android.core.ui.components.prompt.TransparentPrompt
import mega.android.core.ui.theme.spacing.LocalSpacing

@Composable
fun PromptCatalog() {
    Spacer(modifier = Modifier.height(LocalSpacing.current.x16))

    Section(header = "Prompt") {
        Column {
            SuccessPrompt(
                message = "This is Success prompt",
            )
            Spacer(modifier = Modifier.height(LocalSpacing.current.x8))
            ErrorPrompt(
                message = "This is Error prompt",
            )
            Spacer(modifier = Modifier.height(LocalSpacing.current.x8))
            TransparentPrompt(
                message = "This is Transparent prompt",
            )
        }
    }
}