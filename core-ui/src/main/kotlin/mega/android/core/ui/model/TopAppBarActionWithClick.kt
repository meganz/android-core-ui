package mega.privacy.android.shared.original.core.ui.model

import mega.android.core.ui.model.TopAppBarAction

/**
 * Menu action with click
 *
 * @property topAppBarAction [TopAppBarAction]
 * @property onClick action to be executed when the menu item is clicked
 */
data class TopAppBarActionWithClick(
    val topAppBarAction: TopAppBarAction,
    val onClick: () -> Unit,
)
