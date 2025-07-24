package mega.android.core.ui.model.menu

/**
 * Menu action with click
 *
 * @property menuAction [MenuAction]
 * @property onClick action to be executed when the menu item is clicked
 */
data class MenuActionWithClick(
    val menuAction: MenuAction,
    val onClick: () -> Unit,
)
