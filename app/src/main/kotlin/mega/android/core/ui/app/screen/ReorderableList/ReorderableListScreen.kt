package mega.android.core.ui.app.screen.ReorderableList

import android.widget.Toast
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import mega.android.core.ui.components.list.MegaReorderableLazyColumn
import mega.android.core.ui.components.list.OneLineListItem
import mega.android.core.ui.preview.CombinedThemePreviews
import mega.android.core.ui.theme.AndroidThemeForPreviews

@Composable
internal fun ReorderableListRoute(modifier: Modifier = Modifier) {
    ReorderableListScreen(modifier = modifier)
}

data class Item(val id: Int, val title: String, val locked: Boolean = false)

@Composable
internal fun ReorderableListScreen(modifier: Modifier = Modifier) {

    val itemsList = (1..50).map { Item(it, "Item $it") }
    var items by remember { mutableStateOf(itemsList) }
    MegaReorderableLazyColumn(
        modifier = modifier,
        items = items,
        key = { it.id },
        onMove = { from, to ->
            items = with(items.toMutableList()) {
                removeAt(from.index).also { element ->
                    add(to.index, element)
                }
                this
            }
        },
        dragEnabled = { !it.locked }
    ) { item ->
        OneLineListItem(
            item.title + if (item.locked) " (locked)" else "",
            onClickListener = {
                items = items.map { if (it.id == item.id) it.copy(locked = !it.locked) else it }
            },
        )
    }
}


@CombinedThemePreviews
@Composable
private fun ReorderableListScreenPreview() {
    AndroidThemeForPreviews {
        ReorderableListScreen(
            modifier = Modifier.fillMaxSize()
        )
    }
}