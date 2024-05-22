package mega.android.core.ui.app.component

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import mega.android.core.ui.R
import mega.android.core.ui.app.util.Section
import mega.android.core.ui.components.image.MegaIcon
import mega.android.core.ui.components.inputfields.HelpTextSuccess
import mega.android.core.ui.components.inputfields.InputFieldType
import mega.android.core.ui.components.inputfields.ReadOnlyInputField
import mega.android.core.ui.components.inputfields.ReadOnlyInputFieldItem
import mega.android.core.ui.components.inputfields.ReadOnlyPasswordInputFieldItem
import mega.android.core.ui.theme.spacing.LocalSpacing
import mega.android.core.ui.theme.values.IconColor

@Composable
fun ReadOnlyInputFieldComponent() {
    val uriHandler = LocalUriHandler.current
    Spacer(modifier = Modifier.height(LocalSpacing.current.x16))

    Section(header = "Single Read Only Input Field") {
        ReadOnlyInputField(modifier = Modifier.padding(horizontal = LocalSpacing.current.x16)) {
            ReadOnlyInputFieldItem(
                text = "Text",
                label = "Label",
                optionalLabelText = "Optional",
            )
        }
    }

    Section(header = "Grouped Read Only Input Field") {
        ReadOnlyInputField(modifier = Modifier.padding(horizontal = LocalSpacing.current.x16)) {
            ReadOnlyInputFieldItem(
                text = "Text",
                label = "Label",
                optionalLabelText = "Optional",
                showDivider = true,
                helpText = {
                    HelpTextSuccess(text = "Success message")
                }
            )

            ReadOnlyPasswordInputFieldItem(
                text = "Password123@#$%^&",
                label = "Password",
                showDivider = true,
                trailingIcon = {
                    MegaIcon(
                        painter = painterResource(id = R.drawable.ic_alert_circle),
                        contentDescription = "Alert",
                        tint = IconColor.Primary
                    )
                },
                helpText = {
                    HelpTextSuccess(text = "Success message")
                }
            )


            ReadOnlyInputFieldItem(
                text = "instagram.com",
                label = "Website",
                showDivider = false,
                inputFieldType = InputFieldType.Link {
                    uriHandler.openUri("https://www.instagram.com")
                },
            )

            ReadOnlyInputFieldItem(
                text = "This is very very long text that takes multiple lines to display and is not truncated",
                label = "Label",
                optionalLabelText = "Optional",
                showDivider = false,
                firstTrailingIcon = {
                    MegaIcon(
                        painter = painterResource(id = R.drawable.ic_alert_circle),
                        contentDescription = "Alert",
                        tint = IconColor.Primary
                    )
                },
                secondTrailingIcon = {
                    MegaIcon(
                        painter = painterResource(id = R.drawable.ic_alert_circle),
                        contentDescription = "Alert",
                        tint = IconColor.Primary
                    )
                }
            )
        }
    }
}