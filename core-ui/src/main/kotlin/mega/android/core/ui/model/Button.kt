package mega.android.core.ui.model

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import mega.android.core.ui.components.button.MegaOutlinedButton
import mega.android.core.ui.components.button.PrimaryFilledButton
import mega.android.core.ui.components.button.SecondaryFilledButton
import mega.android.core.ui.components.button.TextOnlyButton

sealed interface Button {
    data class PrimaryButton(
        val text: String,
        val modifier: Modifier = Modifier,
        val onClick: () -> Unit = {},
        val leadingIcon: Painter? = null,
        val trailingIcon: Painter? = null,
        val enabled: Boolean = true,
        val isLoading: Boolean = false,
    ) : Button

    data class SecondaryButton(
        val text: String,
        val modifier: Modifier = Modifier,
        val onClick: () -> Unit = {},
        val leadingIcon: Painter? = null,
        val trailingIcon: Painter? = null,
        val enabled: Boolean = true,
        val isLoading: Boolean = false,
    ) : Button

    data class TextOnlyButton(
        val text: String,
        val modifier: Modifier = Modifier,
        val onClick: () -> Unit = {},
        val enabled: Boolean = true
    ) : Button

    data class MegaOutlinedButton(
        val text: String,
        val modifier: Modifier = Modifier,
        val onClick: () -> Unit = {},
        val leadingIcon: Painter? = null,
        val trailingIcon: Painter? = null,
        val enabled: Boolean = true,
        val isLoading: Boolean = false,
    ) : Button
}

@Composable
private fun Button.PrimaryButton.LocalPrimaryButton() {
    PrimaryFilledButton(
        modifier = modifier,
        text = text,
        onClick = onClick,
        leadingIcon = leadingIcon,
        trailingIcon = trailingIcon,
        enabled = enabled,
        isLoading = isLoading,
    )
}

@Composable
private fun Button.SecondaryButton.LocalSecondaryButton() {
    SecondaryFilledButton(
        modifier = modifier,
        text = text,
        onClick = onClick,
        leadingIcon = leadingIcon,
        trailingIcon = trailingIcon,
        enabled = enabled,
        isLoading = isLoading,
    )
}

@Composable
private fun Button.TextOnlyButton.LocalTextOnlyButton() {
    TextOnlyButton(
        modifier = modifier,
        text = text,
        onClick = onClick,
        enabled = enabled
    )
}

@Composable
private fun Button.MegaOutlinedButton.LocalMegaOutlinedButton() {
    MegaOutlinedButton(
        modifier = modifier,
        text = text,
        onClick = onClick,
        leadingIcon = leadingIcon,
        trailingIcon = trailingIcon,
        enabled = enabled,
        isLoading = isLoading,
    )
}

@Composable
internal fun Button.LocalButton() {
    when (this) {
        is Button.PrimaryButton -> LocalPrimaryButton()
        is Button.SecondaryButton -> LocalSecondaryButton()
        is Button.TextOnlyButton -> LocalTextOnlyButton()
        is Button.MegaOutlinedButton -> LocalMegaOutlinedButton()
    }
}