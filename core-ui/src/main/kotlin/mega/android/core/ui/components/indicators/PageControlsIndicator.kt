package mega.android.core.ui.components.indicators

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import mega.android.core.ui.preview.CombinedThemePreviews
import mega.android.core.ui.theme.AndroidThemeForPreviews
import mega.android.core.ui.tokens.theme.DSTokens
import mega.android.core.ui.theme.spacing.LocalSpacing

/**
 * Circular Pager Indicator in Jetpack Compose
 */
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PageControlsIndicator(
    pagerState: PagerState,
    itemCount: Int,
    onClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    val spacing = LocalSpacing.current
    val listState = rememberLazyListState()
    val indicatorSize = 8.dp
    val indicatorSpacing = spacing.x16
    val widthInPx = LocalDensity.current.run { indicatorSize.toPx() }
    val selectedColor = DSTokens.colors.icon.accent
    val defaultColor = DSTokens.colors.icon.disabled

    val currentItem by remember {
        derivedStateOf {
            pagerState.currentPage % itemCount
        }
    }

    LaunchedEffect(key1 = currentItem) {
        val viewportSize = listState.layoutInfo.viewportSize
        listState.animateScrollToItem(
            index = currentItem,
            scrollOffset = (widthInPx / 2 - viewportSize.width / 2).toInt()
        )
    }

    LazyRow(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentSize(),
        state = listState,
        contentPadding = PaddingValues(vertical = indicatorSpacing),
        horizontalArrangement = Arrangement.spacedBy(indicatorSpacing),
        userScrollEnabled = false
    ) {
        items(itemCount) { index ->
            Box(
                modifier = Modifier
                    .size(indicatorSize)
                    .clickable {
                        // the page count is infinity, need to calculation to correct result
                        // if(currentItem < index) need to slide (index - currentItem) counts
                        // if(currentItem > index) need to slide (currentItem - index) counts
                        onClick.invoke(pagerState.currentPage + (index - currentItem))
                    }
                    .background(
                        color = if ((index == currentItem)) selectedColor else defaultColor,
                        shape = CircleShape
                    )
            )
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@CombinedThemePreviews
@Composable
private fun CircularPagerIndicatorPreview() {
    AndroidThemeForPreviews {
        PageControlsIndicator(
            pagerState = rememberPagerState(
                initialPage = 0,
                initialPageOffsetFraction = 0f,
                pageCount = { 3 }
            ),
            itemCount = 3,
            onClick = {}
        )
    }
}
