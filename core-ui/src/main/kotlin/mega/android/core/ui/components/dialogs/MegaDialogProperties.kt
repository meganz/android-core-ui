package mega.android.core.ui.components.dialogs

data class MegaDialogProperties(
    val dismissOnClickOutside: Boolean,
    val dismissOnBackPress: Boolean,
    val isPositiveButtonEnabled: Boolean,
    val isNegativeButtonEnabled: Boolean,
) {
    companion object {
        val default = MegaDialogProperties(
            dismissOnClickOutside = true,
            dismissOnBackPress = true,
            isPositiveButtonEnabled = true,
            isNegativeButtonEnabled = true,
        )
    }
}