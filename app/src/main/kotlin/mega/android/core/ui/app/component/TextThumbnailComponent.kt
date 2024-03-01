package mega.android.core.ui.app.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import mega.android.core.ui.app.util.Section
import mega.android.core.ui.components.thumbnail.TextThumbnail
import mega.android.core.ui.theme.spacing.LocalSpacing

@Composable
fun TextThumbnailComponentCatalog() {
    Spacer(modifier = Modifier.height(LocalSpacing.current.x16))

    Section(header = "Text Thumbnail") {
        Row(
            modifier = Modifier.padding(horizontal = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            TextThumbnail(modifier = Modifier.size(32.dp), text = "Hello", numberOfCharacters = 1)
            TextThumbnail(modifier = Modifier.size(32.dp), text = "Hello", numberOfCharacters = 2)
            TextThumbnail(modifier = Modifier.size(32.dp), text = "Hello", numberOfCharacters = 3)
            TextThumbnail(modifier = Modifier.size(64.dp), text = "Hello", numberOfCharacters = 1)
            TextThumbnail(modifier = Modifier.size(64.dp), text = "Hello", numberOfCharacters = 2)
            TextThumbnail(modifier = Modifier.size(64.dp), text = "Hello", numberOfCharacters = 3)
        }
    }
}