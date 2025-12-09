package mega.android.core.ui.modifiers

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import mega.android.core.ui.components.LocalForceRaiseTopAppBar
import mega.android.core.ui.components.LocalTopAppBarScrollBehavior
import mega.android.core.ui.components.OVERLAP_FRACTION_THRESHOLD
import mega.android.core.ui.tokens.theme.DSTokens

/**
 * Modifier to apply the same background color as MegaTopAppBar, updating the elevation color according to the content scroll
 * This is usually used by components just below MegaTopAppBar when we need to behave as they are part of the app bar,
 * like with MegaFixedTabRow.
 */

@Composable
fun Modifier.scrolledTopAppBarBackgroundColor(): Modifier {
    @OptIn(ExperimentalMaterial3Api::class)
    val scrollBehavior = LocalTopAppBarScrollBehavior.current
    val scrolledColor = DSTokens.colors.background.surface1
    val notScrolledColor = DSTokens.colors.background.pageBackground
    val forceRaised = LocalForceRaiseTopAppBar.current
    //This are hardcoded values following the same implementation as the default TopAppBar as they are private
    @OptIn(ExperimentalMaterial3Api::class)
    val contentColor by remember(scrollBehavior) {
        derivedStateOf {
            val overlappingFraction = scrollBehavior?.state?.overlappedFraction ?: 0f
            when {
                (forceRaised?.intValue ?: 0) > 0 -> scrolledColor
                overlappingFraction > OVERLAP_FRACTION_THRESHOLD -> scrolledColor
                else -> notScrolledColor
            }
        }
    }
    val appBarContainerColor by
    animateColorAsState(
        targetValue = contentColor,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioNoBouncy,
            stiffness = 1600f
        )
    )
    return this.then(
        Modifier.background(appBarContainerColor)
    )
}