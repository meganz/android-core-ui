package mega.android.core.ui.app

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import mega.android.core.ui.components.MegaText
import mega.android.core.ui.components.image.Icon
import mega.android.core.ui.components.list.MultiLineListItem
import mega.android.core.ui.components.list.OneLineListItem
import mega.android.core.ui.theme.AppTheme
import mega.android.core.ui.theme.spacing.LocalSpacing
import mega.android.core.ui.theme.tokens.IconColor
import mega.android.core.ui.theme.tokens.TextColor

@Composable
fun ListComponentCatalog() {
    Spacer(modifier = Modifier.height(LocalSpacing.current.x16))

    Section(header = "One Line List Item") {
        OneLineListItem(
            text = "List item",
        )
    }
    Section(header = "Two Line List Item") {
        MultiLineListItem(
            title = "List item",
            subtitle = "Supporting line text lorem ipsum lorem ipsum"
        )
    }
    Section(header = "Three Line List Item") {
        MultiLineListItem(
            title = "List item",
            subtitle = "Supporting line text lorem ipsum lorem ipsum lorem ipsum lorem ipsum"
        )
    }
    Section(header = "One Line List Item with Elements") {
        OneLineListItem(
            text = "List item",
            leadingElement = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_launcher_foreground),
                    tint = IconColor.Primary,
                    contentDescription = null,
                    modifier = Modifier
                        .size(24.dp)
                        .align(Alignment.Center)
                )
            },
            trailingElement = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_launcher_foreground),
                    contentDescription = null,
                    tint = IconColor.Primary,
                    modifier = Modifier.size(24.dp)
                )
            },
        )
    }
    Section(header = "Multi Line List Item with Elements") {
        MultiLineListItem(
            title = "List item",
            subtitle = "Supporting line text lorem ipsum lorem ipsum",
            leadingElement = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_launcher_foreground),
                    contentDescription = null,
                    tint = IconColor.Primary,
                    modifier = Modifier
                        .size(24.dp)
                        .align(Alignment.Center),
                )
            },
            trailingElement = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_launcher_foreground),
                    contentDescription = null,
                    tint = IconColor.Primary,
                    modifier = Modifier
                        .size(24.dp),
                )
            }
        )
    }
}

@Composable
fun Section(header: String, content: @Composable ColumnScope.() -> Unit) {
    Column(
        modifier = Modifier.padding(vertical = LocalSpacing.current.x2)
    ) {
        MegaText(
            modifier = Modifier.padding(
                start = LocalSpacing.current.x16,
                bottom = LocalSpacing.current.x4
            ),
            text = header,
            textColor = TextColor.Primary,
            style = AppTheme.typography.titleMedium
        )
        Divider(
            color = Color.Gray, thickness = 1.dp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = LocalSpacing.current.x16)
        )
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
        )
        content()
    }
}