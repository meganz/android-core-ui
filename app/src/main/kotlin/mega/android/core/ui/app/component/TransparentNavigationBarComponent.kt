package mega.android.core.ui.app.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import mega.android.core.ui.R
import mega.android.core.ui.components.button.SecondarySmallIconButton
import mega.android.core.ui.components.text.SecondaryTopNavigationButton
import mega.android.core.ui.components.toolbar.TransparentTopBar
import mega.android.core.ui.model.menu.MenuActionWithIcon
import mega.android.core.ui.theme.spacing.LocalSpacing

@Composable
fun TransparentNavigationComponentCatalog() {
    Column {
        // Original TransparentTopBar (no title)
        Box {
            AsyncImage(
                modifier = Modifier
                    .height(120.dp)
                    .background(Color.LightGray),
                model = ImageRequest.Builder(LocalContext.current)
                    .data("https:images.unsplash.com/photo-1579353977828-2a4eab540b9a?q=80&w=1000&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8Mnx8c2FtcGxlfGVufDB8fDB8fHww")
                    .crossfade(true)
                    .build(),
                contentDescription = "Transparent Navigation Bar Background Image",
                contentScale = ContentScale.Crop,
            )
            TransparentTopBar(
                modifier = Modifier.statusBarsPadding(),
                navigationIcon = painterResource(id = R.drawable.ic_chevron_left_medium_thin_outline),
                trailingIcons = {
                    SecondaryTopNavigationButton(
                        modifier = Modifier.padding(end = LocalSpacing.current.x16),
                        text = "Text button",
                        onClick = {}
                    )

                    SecondarySmallIconButton(
                        modifier = Modifier
                            .padding(end = LocalSpacing.current.x12)
                            .size(32.dp),
                        icon = painterResource(id = R.drawable.ic_search_large_medium_thin_outline),
                        onClick = {}
                    )
                }
            )
        }

        // New TransparentTopBar with title, subtitle and actions
        val actions = listOf(
            R.drawable.ic_search_large_medium_thin_outline,
            R.drawable.ic_more_vertical_medium_thin_outline,
        ).mapIndexed { index, iconRes ->
            object : MenuActionWithIcon {
                @Composable
                override fun getDescription() = "Action $index"

                override val testTag = "transparent_top_bar_action_$index"

                @Composable
                override fun getIconPainter() = painterResource(id = iconRes)
            }
        }

        Box {
            AsyncImage(
                modifier = Modifier
                    .height(120.dp)
                    .background(Color.LightGray),
                model = ImageRequest.Builder(LocalContext.current)
                    .data("https:images.unsplash.com/photo-1579353977828-2a4eab540b9a?q=80&w=1000&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8Mnx8c2FtcGxlfGVufDB8fDB8fHww")
                    .crossfade(true)
                    .build(),
                contentDescription = "Transparent Navigation Bar Background Image",
                contentScale = ContentScale.Crop,
            )
            TransparentTopBar(
                modifier = Modifier.statusBarsPadding(),
                title = "Title",
                subtitle = "Subtitle",
                navigationIcon = painterResource(id = R.drawable.ic_chevron_left_medium_thin_outline),
                actions = actions,
            )
        }
    }
}