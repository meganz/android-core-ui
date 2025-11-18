package mega.android.core.ui.app.screen.main

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.autofill.ContentType
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import mega.android.core.ui.app.component.BottomSheetComponentCatalog
import mega.android.core.ui.app.component.ButtonComponentCatalog
import mega.android.core.ui.app.component.CardComponentCatalog
import mega.android.core.ui.app.component.CheckboxCatalog
import mega.android.core.ui.app.component.ContentListComponent
import mega.android.core.ui.app.component.DividerComponentCatalog
import mega.android.core.ui.app.component.DropDownMenuComponent
import mega.android.core.ui.app.component.LabelledTextInputWithActionCatalog
import mega.android.core.ui.app.component.ListComponentCatalog
import mega.android.core.ui.app.component.MegaDialogComponent
import mega.android.core.ui.app.component.PasswordGeneratorInputComponentCatalog
import mega.android.core.ui.app.component.ProgressIndicatorCatalog
import mega.android.core.ui.app.component.PromotionalDialogsCatalog
import mega.android.core.ui.app.component.PromotionalSheetsCatalog
import mega.android.core.ui.app.component.PromptCatalog
import mega.android.core.ui.app.component.RadioButtonCatalog
import mega.android.core.ui.app.component.ReadOnlyInputFieldComponent
import mega.android.core.ui.app.component.SearchFieldComponent
import mega.android.core.ui.app.component.SettingsCatalog
import mega.android.core.ui.app.component.ShimmerListItem
import mega.android.core.ui.app.component.SliderComponentCatalog
import mega.android.core.ui.app.component.StateViewCatalog
import mega.android.core.ui.app.component.TabsComponentCatalog
import mega.android.core.ui.app.component.TextComponentCatalog
import mega.android.core.ui.app.component.TextThumbnailComponentCatalog
import mega.android.core.ui.app.component.TransparentNavigationComponentCatalog
import mega.android.core.ui.app.component.VerificationTextInputFieldCatalog
import mega.android.core.ui.components.LocalSnackBarHostState
import mega.android.core.ui.components.MegaScaffold
import mega.android.core.ui.components.inputfields.AnnotatedLabelTextInputField
import mega.android.core.ui.components.inputfields.ExpirationDateInputField
import mega.android.core.ui.components.inputfields.PasswordTextInputField
import mega.android.core.ui.components.inputfields.TextInputBox
import mega.android.core.ui.components.inputfields.TextInputField
import mega.android.core.ui.components.settings.SettingsNavigationItem
import mega.android.core.ui.components.toolbar.AppBarNavigationType
import mega.android.core.ui.components.toolbar.MegaSearchTopAppBar
import mega.android.core.ui.model.IllustrationIconSizeMode
import mega.android.core.ui.model.InputFieldLabelSpanStyle
import mega.android.core.ui.model.MegaSpanStyle
import mega.android.core.ui.model.SpanIndicator
import mega.android.core.ui.model.SpanStyleWithAnnotation
import mega.android.core.ui.preview.CombinedThemePreviews
import mega.android.core.ui.theme.AndroidThemeForPreviews
import mega.android.core.ui.theme.AppTheme
import mega.android.core.ui.theme.values.TextColor

@Composable
internal fun MainComposeView(
    onNavigateToTooltip: () -> Unit,
    onNavigateToReorderableList: () -> Unit,
    onNavigateToFloatingToolbar: () -> Unit,
) {
    var showCloseButton by remember { mutableStateOf(true) }
    var illustrationMode by remember { mutableStateOf(IllustrationIconSizeMode.Small) }
    var descriptionClickable by remember { mutableStateOf(false) }
    var snackbarMessage: String? by remember { mutableStateOf(null) }
    MegaScaffold { innerPadding ->
        val snackbarHostState = LocalSnackBarHostState.current
        LaunchedEffect(snackbarMessage) {
            snackbarMessage?.let {
                snackbarHostState?.showSnackbar(it)
                snackbarMessage = null
            }
        }
        LazyColumn(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .navigationBarsPadding(),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            item(key = -1) {
                TransparentNavigationComponentCatalog()
            }
            item(key = 0) {
                DropDownMenuComponent()
            }
            item(key = 1) {
                ListComponentCatalog()
            }
            item(key = 2) {
                ButtonComponentCatalog { snackbarMessage = it }
            }
            item(key = 3) {
                ContentListComponent()
            }
            item(key = 4) {
                TextComponentCatalog()
            }
            item(key = 5) {
                TabsComponentCatalog()
            }
            item(key = 6) {
                CheckboxCatalog()
            }
            item(key = 7) {
                DividerComponentCatalog()
            }
            item(key = 8) {
                ProgressIndicatorCatalog()
            }
            item(key = 9) {
                ShimmerListItem()
            }
            item(key = 10) {
                PromptCatalog()
            }
            item(key = 11) {
                PromotionalSheetsCatalog(
                    showCloseButton = showCloseButton,
                    illustrationMode = illustrationMode,
                    footerClickable = descriptionClickable,
                    onShowCloseButtonChange = { showCloseButton = it },
                    onIllustrationModeChange = { illustrationMode = it },
                    onShowClickableFooterChange = { descriptionClickable = it }
                )
            }
            item(key = 12) {
                PromotionalDialogsCatalog(
                    showCloseButton = showCloseButton,
                    illustrationsMode = illustrationMode,
                    showClickableDescription = descriptionClickable,
                )
            }
            item(key = 13) {
                TextThumbnailComponentCatalog()
            }
            item(key = 14) {
                TextInputField(
                    modifier = Modifier,
                    label = "Test InputField",
                    keyboardType = KeyboardType.Email,
                    maxCharLimit = 190
                )
            }
            item(key = 15) {
                TextInputBox(
                    modifier = Modifier,
                    label = "Test InputBox",
                    keyboardType = KeyboardType.Text
                )
            }
            item(key = 16) {
                ReadOnlyInputFieldComponent()
            }
            item(key = 17) {
                SliderComponentCatalog()
            }
            item(key = 18) {
                PasswordGeneratorInputComponentCatalog()
            }
            item(key = 19) {
                MegaDialogComponent()
            }
            item(key = 20) {
                BottomSheetComponentCatalog()
            }
            item(key = 21) {
                StateViewCatalog()
            }
            item(key = 22) {
                VerificationTextInputFieldCatalog()
            }
            item(key = 23) {
                RadioButtonCatalog()
            }
            item(key = 24) {
                CardComponentCatalog()
            }
            item(key = 25) {
                SearchFieldComponent()
            }
            item(key = 26) {
                SettingsCatalog()
            }
            item(key = 27) {
                SettingsNavigationItem(
                    key = "tooltip",
                    title = "Tooltip",
                    onClicked = { onNavigateToTooltip() }
                )
            }
            item(key = 28) {
                LabelledTextInputWithActionCatalog()
            }
            item(key = 29) {
                SettingsNavigationItem(
                    key = "reorderableList",
                    title = "ReorderableList",
                    onClicked = { onNavigateToReorderableList() }
                )
            }
            item(key = 30) {
                SettingsNavigationItem(
                    key = "floatingToolbar",
                    title = "Floating Toolbar",
                    onClicked = { onNavigateToFloatingToolbar() }
                )
            }
            item(key = 31) {
                val emailRequester = remember { FocusRequester() }
                var text by remember { mutableStateOf("") }

                TextInputField(
                    modifier = Modifier
                        .focusRequester(emailRequester)
                        .padding(start = 16.dp, end = 16.dp),
                    keyboardType = KeyboardType.Email,
                    imeAction = ImeAction.Next,
                    capitalization = KeyboardCapitalization.None,
                    label = "Email",
                    text = text,
                    onValueChanged = {
                        text = it
                    },
                    contentType = ContentType.EmailAddress
                )
            }

            // TextInputField Variants Testing
            item(key = 32) {
                var textFieldValue by rememberSaveable(
                    stateSaver = TextFieldValue.Saver
                ) {
                    mutableStateOf(value = TextFieldValue("", TextRange(0)))
                }

                TextInputField(
                    modifier = Modifier.padding(start = 16.dp, end = 16.dp),
                    textFieldValue = textFieldValue,
                    keyboardType = KeyboardType.Text,
                    label = "TextFieldValue Version",
                    onValueChanged = { textFieldValue = it }
                )
            }

            item(key = 33) {
                var passwordText by remember { mutableStateOf("") }

                PasswordTextInputField(
                    modifier = Modifier.padding(start = 16.dp, end = 16.dp),
                    label = "Password Field",
                    text = passwordText,
                    onValueChanged = { passwordText = it }
                )
            }

            item(key = 34) {
                var annotatedText by remember { mutableStateOf("") }

                AnnotatedLabelTextInputField(
                    modifier = Modifier.padding(start = 16.dp, end = 16.dp),
                    text = annotatedText,
                    keyboardType = KeyboardType.Text,
                    onValueChanged = { annotatedText = it },
                    spannedLabel = InputFieldLabelSpanStyle(
                        value = "Annotated [A](Optional)[/A]",
                        spanStyles = mapOf(
                            SpanIndicator('A') to SpanStyleWithAnnotation(
                                megaSpanStyle = MegaSpanStyle.TextColorStyle(
                                    spanStyle = AppTheme.typography.bodyMedium.toSpanStyle(),
                                    textColor = TextColor.Secondary
                                ),
                                annotation = null
                            )
                        ),
                        baseStyle = AppTheme.typography.titleSmall,
                        baseTextColor = TextColor.Primary
                    ),
                )
            }

            item(key = 35) {
                var annotatedTextFieldValue by rememberSaveable(
                    stateSaver = TextFieldValue.Saver
                ) {
                    mutableStateOf(value = TextFieldValue("", TextRange(0)))
                }

                AnnotatedLabelTextInputField(
                    modifier = Modifier.padding(start = 16.dp, end = 16.dp),
                    keyboardType = KeyboardType.Text,
                    textValue = annotatedTextFieldValue,
                    onValueChanged = { annotatedTextFieldValue = it },
                    spannedLabel = InputFieldLabelSpanStyle(
                        value = "Annotated [A](Optional)[/A]",
                        spanStyles = mapOf(
                            SpanIndicator('A') to SpanStyleWithAnnotation(
                                megaSpanStyle = MegaSpanStyle.TextColorStyle(
                                    spanStyle = AppTheme.typography.bodyMedium.toSpanStyle(),
                                    textColor = TextColor.Secondary
                                ),
                                annotation = null
                            )
                        ),
                        baseStyle = AppTheme.typography.titleSmall,
                        baseTextColor = TextColor.Primary
                    ),
                )
            }

            item(key = 36) {
                var expiryText by remember { mutableStateOf("") }

                ExpirationDateInputField(
                    modifier = Modifier.padding(start = 16.dp, end = 16.dp),
                    text = expiryText,
                    onValueChanged = { expiryText = it },
                    spannedLabel = InputFieldLabelSpanStyle(
                        value = "Annotated [A](Optional)[/A]",
                        spanStyles = mapOf(
                            SpanIndicator('A') to SpanStyleWithAnnotation(
                                megaSpanStyle = MegaSpanStyle.TextColorStyle(
                                    spanStyle = AppTheme.typography.bodyMedium.toSpanStyle(),
                                    textColor = TextColor.Secondary
                                ),
                                annotation = null
                            )
                        ),
                        baseStyle = AppTheme.typography.titleSmall,
                        baseTextColor = TextColor.Primary
                    ),
                )
            }

            item(key = 37) {
                var numberText by remember { mutableStateOf("") }

                TextInputField(
                    modifier = Modifier.padding(start = 16.dp, end = 16.dp),
                    keyboardType = KeyboardType.Number,
                    label = "Number Field",
                    text = numberText,
                    onValueChanged = { numberText = it }
                )
            }

            item(key = 38) {
                var phoneText by remember { mutableStateOf("") }

                TextInputField(
                    modifier = Modifier.padding(start = 16.dp, end = 16.dp),
                    keyboardType = KeyboardType.Phone,
                    label = "Phone Field",
                    text = phoneText,
                    onValueChanged = { phoneText = it }
                )
            }

            item(key = 39) {
                var urlText by remember { mutableStateOf("") }

                TextInputField(
                    modifier = Modifier.padding(start = 16.dp, end = 16.dp),
                    keyboardType = KeyboardType.Uri,
                    label = "URL Field",
                    text = urlText,
                    onValueChanged = { urlText = it }
                )
            }

            item(key = 40) {
                var multilineText by remember { mutableStateOf("") }

                TextInputField(
                    modifier = Modifier.padding(start = 16.dp, end = 16.dp),
                    keyboardType = KeyboardType.Text,
                    label = "Multiline Field",
                    text = multilineText,
                    onValueChanged = { multilineText = it }
                )
            }

            item(key = 41) {
                var queryText by remember { mutableStateOf("") }
                var isSearching by remember { mutableStateOf(false) }

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
}

@Composable
@CombinedThemePreviews
private fun MainComposeViewPreview() {
    AndroidThemeForPreviews {
        MainComposeView(
            onNavigateToTooltip = {},
            onNavigateToReorderableList = {},
            onNavigateToFloatingToolbar = {},
        )
    }
}
