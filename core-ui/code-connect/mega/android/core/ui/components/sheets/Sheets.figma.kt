/*
 * Code Connect mapping for the (former) Figma `Custom sheet` component set.
 *
 * STATUS: Missing-in-Figma stub.
 *
 * The original node `57789:6190` that this file targeted is no longer found in the
 * `ANDROID - New` library — `figma connect publish --dry-run` reports "node not found
 * in file". Searching the library for "Bottom sheet" returns a `Sheet` component
 * (componentKey 0f3e9a33469d850b86e53f5e3df6c9efaa7f47fc) but its node URL has not
 * been provided by the designer yet.
 *
 * Compose side (still in use):
 *   - MegaModalBottomSheet(sheetState, bottomSheetBackground, onDismissRequest, content)
 *   - PromotionalImageSheet / PromotionalFullImageSheet / PromotionalIllustrationSheet /
 *     PromotionalPlainSheet — sheet forms of Promotion dialog; intentionally Compose-
 *     only (the dialog forms map in dialogs/Dialogs.figma.kt).
 *
 * Follow-up needed from the designer: provide the new node URL for the sheet
 * component so this file can be revived with a real `@FigmaConnect`.
 *
 * This stub keeps the file in place so the Code Connect dry-run is unblocked.
 */

package mega.android.core.ui.components.sheets
