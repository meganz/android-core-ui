package mega.android.core.ui.app.component

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import mega.android.core.ui.app.util.Section
import mega.android.core.ui.components.dropdown.DropDownItem
import mega.android.core.ui.components.dropdown.MegaDropDownMenu
import mega.android.core.ui.theme.spacing.LocalSpacing


@Composable
fun DropDownMenuComponent() {
    val context = LocalContext.current
    Spacer(modifier = Modifier.height(LocalSpacing.current.x16))

    Section(header = "Drop Down Menu") {
        val dropdownItems = listOf(
            DropDownItem(0, "Item 0"),
            DropDownItem(1, "Item 1", hasDivider = true),
            DropDownItem(2, "Item 2", enabled = false)
        )
        val onItemClick: (DropDownItem) -> Unit = {
            Toast.makeText(context, "Item ${it.id} clicked", Toast.LENGTH_SHORT).show()
        }

        var isContextMenuVisible by rememberSaveable {
            mutableStateOf(false)
        }
        var pressOffset by remember {
            mutableStateOf(DpOffset.Zero)
        }
        var itemHeight by remember {
            mutableStateOf(0.dp)
        }

        val density = LocalDensity.current

        Box(modifier = Modifier
            .fillMaxWidth()
            .onSizeChanged {
                itemHeight = with(density) { it.height.toDp() }
            }
            .background(color = Color.LightGray, shape = RoundedCornerShape(8.dp))
            .pointerInput(true) {
                detectTapGestures(onLongPress = {
                    isContextMenuVisible = true
                    pressOffset = DpOffset(it.x.toDp(), it.y.toDp())
                })
            }
            .padding(16.dp), contentAlignment = Alignment.Center) {
            Text(text = "My Drop Down Menu")
        }

        MegaDropDownMenu(
            expanded = isContextMenuVisible, onDismissRequest = {
                isContextMenuVisible = false
            }, offset = pressOffset.copy(
                y = pressOffset.y - itemHeight,
            ), dropdownItems = dropdownItems, onItemClick = onItemClick
        )
    }
}
