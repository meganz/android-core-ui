/*
 * Code Connect mapping for the (former) Figma `Icon button` component set.
 *
 * STATUS: Missing-in-Figma stub.
 *
 * The original node `54820:2041` that this file targeted has been REPURPOSED by the
 * designer — it now hosts the `FAB` component (see fab/MegaFab.figma.kt). Searching the
 * `ANDROID - New` library for "Icon button" returns no plain Icon button component_set
 * — the designer's library cleanup removed it.
 *
 * Compose still ships 5 `*IconButton` functions in
 *   core-ui/src/main/kotlin/mega/android/core/ui/components/button/IconButton.kt
 * (PrimaryLargeIconButton, PrimarySmallIconButton, SecondaryLargeIconButton,
 * SecondarySmallIconButton, SecondaryNavigationIconButton). They wrap Material3
 * `FilledIconButton` and remain in use by application code, so Code Connect mappings
 * are intentionally NOT redirected to FAB (which wraps Material3 `FloatingActionButton`
 * — a different component with different UX semantics).
 *
 * Follow-up needed from the designer:
 *   - Was Icon button intentionally retired from `ANDROID - New`?
 *   - If yes: agree on the per-call-site replacement strategy in application code
 *     (likely not blanket FAB substitution).
 *   - If no: provide the new node URL so this file can be revived.
 *
 * This stub keeps the file in place so the Code Connect dry-run is unblocked. The
 * @FigmaConnect classes were removed; replace this stub with real connections once the
 * Figma side is settled.
 */

package mega.android.core.ui.components.button
