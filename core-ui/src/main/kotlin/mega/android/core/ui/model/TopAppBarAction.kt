package mega.android.core.ui.model

import androidx.annotation.DrawableRes
import androidx.annotation.PluralsRes
import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource


/**
 * Common interface to define actions for [MegaTopAppBar]
 */
interface TopAppBarAction {
    /**
     * Description for this action to be shown in long clicks or in drop down menu
     */
    @Composable
    fun getDescription(): String

    /**
     * similar to xml definition of menus, this sets the order of menu actions, lower will be showed first.
     */
    val orderInCategory: Int
        get() = 100

    /**
     * True if the action should be enabled, false otherwise.
     */
    val enabled: Boolean
        get() = true

    /**
     * Tag for testing purposes.
     */
    val testTag: String

    /**
     * Painter for the icon of this action when it's shown in the toolbar
     */
    @Composable
    fun getIconPainter(): Painter

    /**
     * If icon should be highlighted
     */
    val highlightIcon: Boolean
        get() = false
}

/**
 * Utility abstract class for actions with description and icon from resources
 * @param iconRes the drawable resource to be used to generate the icon painter
 * @param descriptionRes the string resource to be used to generate the description of this action
 * @param testTag tag for testing purposes.
 */
abstract class TopAppBarActionString(
    @DrawableRes val iconRes: Int,
    @StringRes val descriptionRes: Int,
    override val testTag: String,
) : TopAppBarAction {
    @Composable
    override fun getDescription() = stringResource(id = descriptionRes)

    @Composable
    override fun getIconPainter() = painterResource(id = iconRes)
}

/**
 * Utility abstract class for actions with description and icon from resources using plurals
 * @param iconRes the drawable resource to be used to generate the icon painter
 * @param descriptionRes the plural resource to be used to generate the description of this action
 * @param amount the amount to be used in [descriptionRes] plural
 * @param testTag tag for testing purposes.
 */
abstract class TopAppBarActionPlural(
    @DrawableRes val iconRes: Int,
    @PluralsRes val descriptionRes: Int,
    private val amount: Int,
    override val testTag: String,
) : TopAppBarAction {
    @Composable
    override fun getDescription() = pluralStringResource(id = descriptionRes, amount, amount)

    @Composable
    override fun getIconPainter() = painterResource(id = iconRes)
}