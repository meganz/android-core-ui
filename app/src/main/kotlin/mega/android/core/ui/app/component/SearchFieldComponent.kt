package mega.android.core.ui.app.component

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import mega.android.core.ui.app.util.Section
import mega.android.core.ui.components.inputfields.SearchInputField
import mega.android.core.ui.theme.spacing.LocalSpacing

@Composable
fun SearchFieldComponent() {
    Spacer(modifier = Modifier.height(LocalSpacing.current.x16))
    var text by remember { mutableStateOf("") }

    Section(header = "Search Input Field") {
        SearchInputField(
            modifier = Modifier,
            placeHolderText = "Search",
            text = text,
            onValueChanged = { text = it }
        )
    }
}