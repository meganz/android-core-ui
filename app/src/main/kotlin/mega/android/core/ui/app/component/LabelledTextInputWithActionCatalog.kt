package mega.android.core.ui.app.component

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import mega.android.core.ui.R
import mega.android.core.ui.app.util.Section
import mega.android.core.ui.components.button.SecondaryLargeIconButton
import mega.android.core.ui.components.inputfields.LabelledTextInputWithAction
import mega.android.core.ui.model.InputFieldLabelSpanStyle
import mega.android.core.ui.preview.CombinedThemePreviews
import mega.android.core.ui.theme.AndroidThemeForPreviews
import mega.android.core.ui.theme.AppTheme
import mega.android.core.ui.theme.spacing.LocalSpacing
import mega.android.core.ui.theme.values.TextColor

@Composable
fun LabelledTextInputWithActionCatalog(){
    Section(header = "Labelled Text Input with Action") {
        LabelledTextInputWithAction(
            textValue = TextFieldValue("Text"),
            keyboardType = KeyboardType.Text,
            modifier = Modifier.padding(LocalSpacing.current.x16),
            imeAction = ImeAction.Done,
            capitalization = KeyboardCapitalization.Words,
            spannedLabel = InputFieldLabelSpanStyle(
                value = "Label",
                spanStyles = emptyMap(),
                baseStyle = AppTheme.typography.titleSmall,
                baseTextColor = TextColor.Primary
            ),
            inputTextAlign = TextAlign.Start,
            showTrailingIcon = true,
            isPasswordMode = true,
            successText = "Success message",
            maxCharLimit = 100,
            onValueChanged = {},
            onFocusChanged = {},
            trailingView = {
                SecondaryLargeIconButton(
                    modifier = Modifier.padding(start = 8.dp),
                    icon = painterResource(id = R.drawable.ic_eye),
                    onClick = {
                    }
                )
            }
        )
    }
}

@Composable
@CombinedThemePreviews
private fun LabelledTextInputWithActionCatalogPreview() {
    AndroidThemeForPreviews {
        LabelledTextInputWithActionCatalog()
    }
}