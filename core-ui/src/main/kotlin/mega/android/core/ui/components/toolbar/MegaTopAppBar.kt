package mega.android.core.ui.components.toolbar

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import mega.android.core.ui.R
import mega.android.core.ui.components.LocalTopAppBarScrollBehavior
import mega.android.core.ui.components.MegaText
import mega.android.core.ui.components.OVERLAP_FRACTION_THRESHOLD
import mega.android.core.ui.components.button.SecondarySmallIconButton
import mega.android.core.ui.components.divider.StrongDivider
import mega.android.core.ui.components.inputfields.SearchInputField
import mega.android.core.ui.components.menu.IconButtonWithTooltip
import mega.android.core.ui.components.menu.TopAppBarActionsComponent
import mega.android.core.ui.model.menu.MenuActionIconWithClick
import mega.android.core.ui.model.menu.MenuActionWithIcon
import mega.android.core.ui.preview.CombinedThemePreviews
import mega.android.core.ui.theme.AndroidThemeForPreviews
import mega.android.core.ui.theme.AppTheme
import mega.android.core.ui.theme.spacing.LocalSpacing
import mega.android.core.ui.tokens.theme.DSTokens

@Composable
fun MegaTopAppBar(
    title: String,
    navigationType: AppBarNavigationType,
    modifier: Modifier = Modifier,
    drawBottomLineOnScrolledContent: Boolean = false,
    trailingIcons: @Composable RowScope.() -> Unit = {},
) {
    @OptIn(ExperimentalMaterial3Api::class)
    DefaultTopAppBar(
        modifier = modifier,
        title = title,
        navigationIcon = navigationType.navigationIcon(),
        actions = trailingIcons,
        scrollBehavior = LocalTopAppBarScrollBehavior.current,
        drawBottomLineOnScrolledContent = drawBottomLineOnScrolledContent,
    )
}

@Composable
fun MegaTopAppBar(
    title: String,
    navigationType: AppBarNavigationType,
    actions: List<MenuActionIconWithClick>,
    modifier: Modifier = Modifier,
    drawBottomLineOnScrolledContent: Boolean = false,
    actionsEnabled: Boolean = true,
    maxActionsToShow: Int = Int.MAX_VALUE,
) {
    @OptIn(ExperimentalMaterial3Api::class)
    DefaultTopAppBar(
        modifier = modifier,
        title = title,
        navigationIcon = navigationType.navigationIcon(),
        scrollBehavior = LocalTopAppBarScrollBehavior.current,
        actions = {
            TopAppBarActionsComponent(actions, actionsEnabled, maxActionsToShow)
        },
        drawBottomLineOnScrolledContent = drawBottomLineOnScrolledContent,
    )
}

@Composable
fun MegaTopAppBar(
    title: String,
    navigationType: AppBarNavigationType,
    actions: List<MenuActionWithIcon>,
    onActionPressed: ((MenuActionWithIcon) -> Unit),
    modifier: Modifier = Modifier,
    drawBottomLineOnScrolledContent: Boolean = false,
    actionsEnabled: Boolean = true,
    maxActionsToShow: Int = 4,
) = MegaTopAppBar(
    modifier = modifier,
    title = title,
    navigationType = navigationType,
    actions = actions.addClick(onActionPressed),
    actionsEnabled = actionsEnabled,
    drawBottomLineOnScrolledContent = drawBottomLineOnScrolledContent,
    maxActionsToShow = maxActionsToShow,
)

@Composable
fun MegaTopAppBar(
    title: String,
    subtitle: String?,
    navigationType: AppBarNavigationType,
    actions: List<MenuActionWithIcon>,
    onActionPressed: ((MenuActionWithIcon) -> Unit),
    modifier: Modifier = Modifier,
    drawBottomLineOnScrolledContent: Boolean = false,
    actionsEnabled: Boolean = true,
    maxActionsToShow: Int = 4,
) = MegaTopAppBar(
    modifier = modifier,
    title = title,
    subtitle = subtitle,
    navigationType = navigationType,
    actions = actions.addClick(onActionPressed),
    actionsEnabled = actionsEnabled,
    drawBottomLineOnScrolledContent = drawBottomLineOnScrolledContent,
    maxActionsToShow = maxActionsToShow,
)

@Composable
fun MegaTopAppBar(
    title: String,
    subtitle: String?,
    navigationType: AppBarNavigationType,
    actions: List<MenuActionIconWithClick>,
    modifier: Modifier = Modifier,
    drawBottomLineOnScrolledContent: Boolean = false,
    actionsEnabled: Boolean = true,
    maxActionsToShow: Int = Int.MAX_VALUE,
) {
    @OptIn(ExperimentalMaterial3Api::class)
    DefaultTopAppBar(
        modifier = modifier,
        title = title,
        subtitle = subtitle,
        navigationIcon = navigationType.navigationIcon(),
        scrollBehavior = LocalTopAppBarScrollBehavior.current,
        actions = {
            TopAppBarActionsComponent(actions, actionsEnabled, maxActionsToShow)
        },
        drawBottomLineOnScrolledContent = drawBottomLineOnScrolledContent,
    )
}

/**
 * MegaTopAppBar that admits trailing icons and actions at the same time. Trailing icons will be drawn first and then the actions.
 * [actions] is the preferred way to add simple actions, trailingIcons should be used for other more complex cases, like animated widgets.
 * @param title
 * @param navigationType
 * @param trailingIcons
 * @param actions
 * @param modifier
 * @param drawBottomLineOnScrolledContent
 * @param actionsEnabled
 * @param maxActionsToShow
 */
@Composable
fun MegaTopAppBar(
    title: String,
    navigationType: AppBarNavigationType,
    trailingIcons: @Composable RowScope.() -> Unit,
    actions: List<MenuActionIconWithClick>,
    modifier: Modifier = Modifier,
    drawBottomLineOnScrolledContent: Boolean = false,
    actionsEnabled: Boolean = true,
    maxActionsToShow: Int = Int.MAX_VALUE,
) {
    @OptIn(ExperimentalMaterial3Api::class)
    DefaultTopAppBar(
        modifier = modifier,
        title = title,
        navigationIcon = navigationType.navigationIcon(),
        scrollBehavior = LocalTopAppBarScrollBehavior.current,
        actions = {
            trailingIcons()
            TopAppBarActionsComponent(actions, actionsEnabled, maxActionsToShow)
        },
        drawBottomLineOnScrolledContent = drawBottomLineOnScrolledContent,
    )
}


/**
 * MegaTopAppBar that admits trailing icons and actions at the same time. Trailing icons will be drawn first and then the actions.
 * [actions] is the preferred way to add simple actions, trailingIcons should be used for other more complex cases, like animated widgets.
 * @param title
 * @param navigationType
 * @param trailingIcons
 * @param actions
 * @param onActionPressed
 * @param modifier
 * @param drawBottomLineOnScrolledContent
 * @param actionsEnabled
 * @param maxActionsToShow
 */
@Composable
fun MegaTopAppBar(
    title: String,
    navigationType: AppBarNavigationType,
    trailingIcons: @Composable RowScope.() -> Unit,
    actions: List<MenuActionWithIcon>,
    onActionPressed: ((MenuActionWithIcon) -> Unit),
    modifier: Modifier = Modifier,
    drawBottomLineOnScrolledContent: Boolean = false,
    actionsEnabled: Boolean = true,
    maxActionsToShow: Int = 4,
) = MegaTopAppBar(
    modifier = modifier,
    title = title,
    navigationType = navigationType,
    actions = actions.addClick(onActionPressed),
    trailingIcons = trailingIcons,
    actionsEnabled = actionsEnabled,
    drawBottomLineOnScrolledContent = drawBottomLineOnScrolledContent,
    maxActionsToShow = maxActionsToShow,
)

internal fun List<MenuActionWithIcon>.addClick(onActionPressed: ((MenuActionWithIcon) -> Unit)?): List<MenuActionIconWithClick> =
    this.map { MenuActionIconWithClick(it) { onActionPressed?.invoke(it) } }

@Deprecated(message = "Please use the version of MegaTopAppBar with AppBarNavigationType instead of injecting the navigation painter.")
@Composable
fun MegaTopAppBar(
    title: String,
    modifier: Modifier = Modifier,
    navigationIcon: Painter? = null,
    drawBottomLineOnScrolledContent: Boolean = false,
    trailingIcons: @Composable RowScope.() -> Unit = {},
    onNavigationIconClicked: () -> Unit = {},
) {
    @OptIn(ExperimentalMaterial3Api::class)
    DefaultTopAppBar(
        modifier = modifier,
        title = title,
        scrollBehavior = LocalTopAppBarScrollBehavior.current,
        navigationIcon = {
            navigationIcon?.let {
                IconButton(
                    modifier = Modifier
                        .wrapContentHeight(),
                    onClick = onNavigationIconClicked
                ) {
                    Icon(
                        painter = navigationIcon,
                        contentDescription = "$title Navigation Icon"
                    )
                }
            }
        },
        actions = trailingIcons,
        drawBottomLineOnScrolledContent = drawBottomLineOnScrolledContent,
    )
}

@Composable
fun TransparentTopBar(
    modifier: Modifier = Modifier,
    navigationIcon: Painter? = null,
    trailingIcons: @Composable RowScope.() -> Unit = {},
    onNavigationIconClicked: () -> Unit = {},
    backgroundAlpha: Float = 0f
) {
    @OptIn(ExperimentalMaterial3Api::class)
    DefaultTopAppBar(
        modifier = Modifier
            .background(DSTokens.colors.background.pageBackground.copy(alpha = backgroundAlpha))
            .then(modifier),
        title = "",
        navigationIcon = {
            navigationIcon?.let {
                SecondarySmallIconButton(
                    modifier = Modifier
                        .padding(start = LocalSpacing.current.x12)
                        .size(32.dp),
                    icon = navigationIcon,
                    onClick = onNavigationIconClicked
                )
            }
        },
        actions = trailingIcons,
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.Transparent,
            scrolledContainerColor = Color.Transparent,
            navigationIconContentColor = DSTokens.colors.icon.primary,
            titleContentColor = DSTokens.colors.text.primary,
            actionIconContentColor = DSTokens.colors.icon.primary
        ),
        drawBottomLineOnScrolledContent = false
    )
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun DefaultTopAppBar(
    modifier: Modifier,
    title: String,
    subtitle: String? = null,
    navigationIcon: @Composable () -> Unit = {},
    actions: @Composable RowScope.() -> Unit = {},
    colors: TopAppBarColors? = null,
    drawBottomLineOnScrolledContent: Boolean,
    scrollBehavior: TopAppBarScrollBehavior? = null,
) {
    Box(modifier = modifier) {
        TopAppBar(
            title = {
                Column {
                    Text(
                        text = title,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        style = AppTheme.typography.titleLarge,
                    )

                    subtitle?.let {
                        Text(
                            text = it,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            style = AppTheme.typography.bodyMedium,
                            color = DSTokens.colors.text.secondary
                        )
                    }
                }
            },
            scrollBehavior = scrollBehavior,
            navigationIcon = navigationIcon,
            actions = actions,
            colors = colors ?: TopAppBarDefaults.topAppBarColors(
                containerColor = DSTokens.colors.background.pageBackground,
                scrolledContainerColor = DSTokens.colors.background.surface1,
                navigationIconContentColor = DSTokens.colors.icon.primary,
                titleContentColor = DSTokens.colors.text.primary,
                actionIconContentColor = DSTokens.colors.icon.primary
            )
        )
        // Bottom line if scroll behavior is overlapped
        if (drawBottomLineOnScrolledContent &&
            (LocalTopAppBarScrollBehavior.current?.state?.overlappedFraction ?: 0f)
            > OVERLAP_FRACTION_THRESHOLD
        ) {
            StrongDivider(Modifier.align(Alignment.BottomCenter))
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MegaSearchTopAppBar(
    navigationType: AppBarNavigationType,
    title: String,
    modifier: Modifier = Modifier,
    query: String? = null,
    onQueryChanged: ((String) -> Unit)? = null,
    isSearchingMode: Boolean = false,
    onSearchingModeChanged: ((Boolean) -> Unit)? = null,
    searchPlaceholder: String? = null,
    onSearchAction: ((String) -> Unit)? = null,
    actions: List<MenuActionIconWithClick>? = null,
) {
    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current
    var targetAlpha by remember { mutableFloatStateOf(0f) }
    val searchAlpha by animateFloatAsState(
        targetValue = targetAlpha,
        animationSpec = tween(300),
        label = "searchAlpha"
    )

    LaunchedEffect(isSearchingMode) {
        if (isSearchingMode) {
            targetAlpha = 1f
            focusRequester.requestFocus()
        } else {
            targetAlpha = 0f
            focusManager.clearFocus()
        }
    }

    TopAppBar(
        modifier = modifier,
        navigationIcon = {
            val navigation = if (isSearchingMode) {
                // In searching mode, we need to override the navigation to a back action that
                // exits the searching mode and clears the query
                AppBarNavigationType.Back(
                    onNavigationIconClicked = {
                        focusManager.clearFocus()
                        onSearchingModeChanged?.invoke(false)
                        onQueryChanged?.invoke("")
                    }
                )
            } else {
                navigationType
            }

            navigation.navigationIcon().invoke()
        },
        title = {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.CenterStart
            ) {
                SearchInputField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .graphicsLayer { alpha = searchAlpha }
                        .focusRequester(focusRequester),
                    text = query.orEmpty(),
                    onValueChanged = onQueryChanged,
                    placeHolderText = searchPlaceholder.orEmpty(),
                    onKeyboardAction = { onSearchAction?.invoke(query.orEmpty()) },
                )

                MegaText(
                    text = title,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    style = AppTheme.typography.titleLarge,
                    modifier = Modifier
                        .fillMaxWidth()
                        .graphicsLayer { alpha = 1f - searchAlpha }
                    ,
                )
            }
        },
        actions = {
            AnimatedVisibility(
                visible = !isSearchingMode || actions != null,
                enter = fadeIn(animationSpec = tween(50)),
                exit = fadeOut(animationSpec = tween(50))
            ) {
                Row {
                    if (!isSearchingMode) {
                        IconButtonWithTooltip(
                            modifier = Modifier.wrapContentSize(),
                            iconPainter = painterResource(
                                id = R.drawable.ic_search_large_medium_thin_outline
                            ),
                            description = "Search",
                            onClick = { onSearchingModeChanged?.invoke(!isSearchingMode) }
                        )
                    }

                    if (actions != null) {
                        TopAppBarActionsComponent(actions, true, 2)
                    }
                }
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = DSTokens.colors.background.pageBackground,
            scrolledContainerColor = DSTokens.colors.background.surface1,
            navigationIconContentColor = DSTokens.colors.icon.primary,
            titleContentColor = DSTokens.colors.text.primary,
            actionIconContentColor = DSTokens.colors.icon.primary
        )
    )
}

@CombinedThemePreviews
@Composable
private fun MegaTopAppBarTypePreview(
    @PreviewParameter(NavigationTypeProvider::class) navigationType: AppBarNavigationType
) {
    AndroidThemeForPreviews {
        MegaTopAppBar(
            title = "Title",
            navigationType = navigationType,
            trailingIcons = {
                IconButton(modifier = Modifier.wrapContentHeight(), onClick = {}) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_close_medium_thin_outline),
                        contentDescription = "Cancel Icon"
                    )
                }
            },
        )
    }
}

@CombinedThemePreviews
@Composable
private fun MegaTransparentTopAppBarPreview() {
    AndroidThemeForPreviews {
        Box(modifier = Modifier.background(color = Color.Red)) {
            TransparentTopBar(
                navigationIcon = painterResource(id = R.drawable.ic_arrow_left_medium_thin_outline),
                trailingIcons = {
                    SecondarySmallIconButton(
                        modifier = Modifier
                            .padding(end = LocalSpacing.current.x12)
                            .size(32.dp),
                        icon = painterResource(id = R.drawable.ic_close_medium_thin_outline),
                        onClick = { })
                },
                onNavigationIconClicked = {}
            )
        }
    }
}

@CombinedThemePreviews
@Composable
private fun MegaTopAppBarActionsPreview(
    @PreviewParameter(SubtitleProvider::class) subtitle: String?
) {
    val actions =
        listOf(
            R.drawable.ic_alert_circle_medium_thin_outline,
            R.drawable.ic_alert_triangle_medium_thin_outline
        ).mapIndexed { i, iconRes ->
            object : MenuActionWithIcon {
                @Composable
                override fun getDescription() = "Action $i"

                override val testTag = getDescription()

                @Composable
                override fun getIconPainter() = painterResource(id = iconRes)

            }
        }
    AndroidThemeForPreviews {
        MegaTopAppBar(
            title = "Title",
            subtitle = subtitle,
            navigationType = AppBarNavigationType.Back {},
            actions = actions.map { MenuActionIconWithClick(it) {} },
            modifier = Modifier.padding(bottom = 80.dp) //make some space for the tooltip in interactive mode
        )
    }
}

@CombinedThemePreviews
@Composable
private fun MegaTopAppBarMaxActionsPreview() {
    val actions = listOf(
        R.drawable.ic_alert_circle_medium_thin_outline,
        R.drawable.ic_alert_triangle_medium_thin_outline,
        R.drawable.ic_check_medium_thin_outline,
        R.drawable.ic_close_medium_thin_outline,
        R.drawable.ic_info_medium_thin_outline,
        R.drawable.ic_help_circle_medium_thin_outline
    ).mapIndexed { i, iconRes ->
        val action = object : MenuActionWithIcon {
            @Composable
            override fun getDescription() = "Action $i"

            override val testTag = getDescription()

            @Composable
            override fun getIconPainter() = painterResource(id = iconRes)
        }

        MenuActionIconWithClick(action) {}
    }
    AndroidThemeForPreviews {
        MegaTopAppBar(
            title = "Max Actions Demo",
            maxActionsToShow = 2,
            navigationType = AppBarNavigationType.Back {},
            trailingIcons = {
                Icon(painterResource(R.drawable.ic_close_medium_thin_outline), "")
            },
            actions = actions,
            modifier = Modifier.padding(bottom = 80.dp) //make some space for the dropdown in interactive mode
        )
    }
}

@CombinedThemePreviews
@Composable
private fun MegaSearchTopAppBarPreview() {
    AndroidThemeForPreviews {
        var queryText by remember { mutableStateOf("") }
        var isSearching by remember { mutableStateOf(false) }

        Box(Modifier.fillMaxSize()) {
            MegaSearchTopAppBar(
                title = "Title",
                navigationType = AppBarNavigationType.Back {},
                query = queryText,
                onQueryChanged = { queryText = it },
                isSearchingMode = isSearching,
                onSearchingModeChanged = { isSearching = it },
                searchPlaceholder = "Search placeholder"
            )
        }
    }
}

private class NavigationTypeProvider : PreviewParameterProvider<AppBarNavigationType> {
    override val values: Sequence<AppBarNavigationType>
        get() = sequenceOf(
            AppBarNavigationType.None,
            AppBarNavigationType.Back {},
            AppBarNavigationType.Close {},
        )

}

private class SubtitleProvider : PreviewParameterProvider<String?> {
    override val values: Sequence<String?>
        get() = sequenceOf(null, "Subtitle")
}