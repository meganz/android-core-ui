package mega.android.core.ui.app.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import mega.android.core.ui.components.button.SecondarySmallIconButton
import mega.android.core.ui.components.text.SecondaryTopNavigationButton
import mega.android.core.ui.components.toolbar.TransparentNavigationBar
import mega.android.core.ui.theme.spacing.LocalSpacing

@Composable
fun TransparentNavigationComponentCatalog() {
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
        TransparentNavigationBar(
            navigationIcon = painterResource(id = mega.android.core.ui.R.drawable.ic_action_back),
            trailingIcons = {
                SecondaryTopNavigationButton(
                    modifier = Modifier.padding(end = LocalSpacing.current.x16),
                    text = "Text button",
                    onClick = {}
                )

                SecondarySmallIconButton(modifier = Modifier
                    .padding(end = LocalSpacing.current.x12)
                    .size(32.dp),
                    icon = painterResource(id = mega.android.core.ui.R.drawable.ic_search_large),
                    onClick = {})
            }
        )
    }
}