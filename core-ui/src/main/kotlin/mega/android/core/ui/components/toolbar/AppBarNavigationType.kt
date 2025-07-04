package mega.android.core.ui.components.toolbar

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import mega.android.core.ui.R

/**
 * App bar navigation type
 */
@Immutable
sealed class AppBarNavigationType(
    @DrawableRes private val iconResource: Int?
) {
    open val onNavigationIconClicked: () -> Unit = {}

    @Composable
    internal fun navigationIcon(): @Composable () -> Unit = {
        iconResource?.let { iconResource ->
            IconButton(
                modifier = Modifier
                    .wrapContentHeight(),
                onClick = onNavigationIconClicked
            ) {
                Icon(
                    painter = painterResource(id = iconResource),
                    contentDescription = "Navigation Icon"
                )
            }
        }
    }

    /**
     * No navigation
     */
    @Immutable
    data object None : AppBarNavigationType(null)

    /**
     * Back navigation, represented by a back arrow icon
     */
    @Immutable
    data class Back(override val onNavigationIconClicked: () -> Unit) :
        AppBarNavigationType(R.drawable.ic_arrow_left_medium_thin_outline)

    /**
     * Close navigation, represented by a cross icon
     */
    @Immutable
    data class Close(override val onNavigationIconClicked: () -> Unit) :
        AppBarNavigationType(R.drawable.ic_close_medium_thin_outline)
}