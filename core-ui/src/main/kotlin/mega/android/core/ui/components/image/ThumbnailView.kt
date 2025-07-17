package mega.android.core.ui.components.image

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.compose.AsyncImagePainter
import coil3.compose.rememberAsyncImagePainter
import coil3.request.ImageRequest
import coil3.request.crossfade
import coil3.request.error
import coil3.request.placeholder
import mega.android.core.ui.R
import mega.android.core.ui.preview.CombinedThemePreviews
import mega.android.core.ui.theme.AndroidThemeForPreviews
import java.io.File

/**
 * Thumbnail View for NodesView
 * @param modifier [Modifier]
 * @param imageFile File
 * @param contentDescription Content Description for image,
 * @param defaultImage in case of imageFile is null
 * @param contentScale [ContentScale]
 */
@Composable
fun ThumbnailView(
    contentDescription: String?,
    imageFile: File?,
    @DrawableRes defaultImage: Int,
    modifier: Modifier = Modifier,
    contentScale: ContentScale = ContentScale.Fit,
) {
    ThumbnailView(
        contentDescription = contentDescription,
        data = imageFile,
        defaultImage = defaultImage,
        modifier = modifier,
        contentScale = contentScale,
    )
}

/**
 * Thumbnail view
 *
 * @param contentDescription content description
 * @param data any data [File], [Uri], [Bitmap], [ThumbnailRequest]
 * @param defaultImage default image
 * @param modifier
 * @param contentScale content scale
 */
@Composable
fun ThumbnailView(
    contentDescription: String?,
    data: Any?,
    @DrawableRes defaultImage: Int,
    modifier: Modifier = Modifier,
    contentScale: ContentScale = ContentScale.Fit,
) {
    var padding by remember { mutableStateOf(0.dp) }
    var cornerRadius by remember { mutableStateOf(0.dp) }

    data?.let {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(data)
                .crossfade(true)
                .build(),
            contentDescription = contentDescription,
            placeholder = painterResource(id = defaultImage),
            error = painterResource(id = defaultImage),
            contentScale = contentScale,
            modifier = modifier
                .aspectRatio(1f)
                .padding(padding)
                .clip(androidx.compose.foundation.shape.RoundedCornerShape(cornerRadius)),
            onError = {
                padding = 0.dp
                cornerRadius = 0.dp
            },
            onSuccess = {
                padding = 4.dp
                cornerRadius = 4.dp
            }
        )
    } ?: run {
        Image(
            modifier = modifier,
            painter = painterResource(id = defaultImage),
            contentDescription = contentDescription,
            contentScale = contentScale,
        )
    }
}

/**
 * Thumbnail view
 *
 * @param contentDescription content description
 * @param data any data [File], [Uri], [Bitmap], [ThumbnailRequest]
 * @param defaultImage default image
 * @param modifier
 * @param contentScale content scale
 */
@Composable
fun ThumbnailView(
    contentDescription: String?,
    data: Any?,
    @DrawableRes defaultImage: Int,
    modifier: Modifier = Modifier,
    contentScale: ContentScale = ContentScale.Fit,
    onSuccess: (Modifier) -> Modifier,
) {
    data?.let {
        var padding by remember { mutableStateOf(0.dp) }
        var cornerRadius by remember { mutableStateOf(0.dp) }

        val painter = rememberAsyncImagePainter(
            model = ImageRequest.Builder(LocalContext.current)
                .data(data)
                .crossfade(true)
                .error(defaultImage)
                .placeholder(defaultImage)
                .build()
        )

        var finalModifier: Modifier = modifier

        val state = painter.state.value
        if (state is AsyncImagePainter.State.Success) {
            finalModifier = onSuccess(modifier)
            padding = 4.dp
            cornerRadius = 4.dp
        } else if (state is AsyncImagePainter.State.Error) {
            padding = 0.dp
            cornerRadius = 0.dp
        }

        Image(
            painter = painter,
            modifier = finalModifier
                .aspectRatio(1f)
                .padding(padding)
                .clip(androidx.compose.foundation.shape.RoundedCornerShape(cornerRadius)),
            contentScale = contentScale,
            contentDescription = contentDescription
        )
    } ?: run {
        Image(
            modifier = modifier,
            painter = painterResource(id = defaultImage),
            contentDescription = contentDescription,
            contentScale = contentScale,
        )
    }
}

@CombinedThemePreviews
@Composable
private fun ThumbnailViewPreview() {
    AndroidThemeForPreviews {
        ThumbnailView(
            contentDescription = "image",
            imageFile = null as File?,
            defaultImage = R.drawable.illustration_mega_anniversary,
        )
    }
}
