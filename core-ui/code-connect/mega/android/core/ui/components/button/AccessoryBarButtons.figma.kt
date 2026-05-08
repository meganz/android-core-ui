/*
 * Code Connect mapping for the Figma `Accessory Bar Buttons` component set.
 *
 * Figma file : HagXfSTF94ICEFj8rfNyLc  (ANDROID - New)
 * Component  : Accessory Bar Buttons  (node 60983:1298)
 * Variants matched: Buttons (1 | 2).
 *
 * AccessoryBarButtonContent carries icon + text + onClick — cannot be mapped as flat
 * Figma properties; icons are baked into the example.
 *
 * Property names VERIFIED via MCP on 2026-04-23.
 */

package mega.android.core.ui.components.button

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import com.figma.code.connect.FigmaConnect
import com.figma.code.connect.FigmaVariant
import mega.android.core.ui.R
import mega.android.core.ui.model.AccessoryBarButtonContent

@FigmaConnect(url = "https://www.figma.com/design/HagXfSTF94ICEFj8rfNyLc/ANDROID---New?node-id=60983-1298")
@FigmaVariant("Buttons", "1")
class AccessoryBarButtonConnection {
    @Composable
    fun AccessoryBarButtonExample() {
        AccessoryBarButton(
            button = AccessoryBarButtonContent(
                text = "Action",
                icon = painterResource(R.drawable.ic_alert_circle_medium_thin_outline),
                onClick = {},
            ),
        )
    }
}

@FigmaConnect(url = "https://www.figma.com/design/HagXfSTF94ICEFj8rfNyLc/ANDROID---New?node-id=60983-1298")
@FigmaVariant("Buttons", "2")
class AccessoryBarButtonGroupConnection {
    @Composable
    fun AccessoryBarButtonGroupExample() {
        AccessoryBarButtonGroup(
            firstButton = AccessoryBarButtonContent(
                text = "Action 1",
                icon = painterResource(R.drawable.ic_alert_circle_medium_thin_outline),
                onClick = {},
            ),
            secondButton = AccessoryBarButtonContent(
                text = "Action 2",
                icon = painterResource(R.drawable.ic_check_medium_thin_outline),
                onClick = {},
            ),
        )
    }
}
