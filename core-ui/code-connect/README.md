# Figma Code Connect — MEGA Android Core UI

This directory holds the Figma Code Connect mapping files (`*.figma.kt`) that bridge the
components in the **ANDROID - New** Figma library (file key
`HagXfSTF94ICEFj8rfNyLc`) with their Jetpack Compose counterparts in
`core-ui/src/main/kotlin/mega/android/core/ui/components/`.

This tree lives **outside** `core-ui/src/main` on purpose: the files annotate Compose
functions with `@FigmaConnect` / `@FigmaProperty` / `@FigmaVariant` markers that are only
meaningful to the Figma Code Connect tooling. They will not compile as Kotlin until the
`com.figma.code.connect` Gradle plugin is added to `core-ui/build.gradle.kts`. Keeping
them out of the main source set means they do not affect the normal build, the
published AAR, or the ABI.

Folder layout mirrors the Compose source package tree exactly:

```
core-ui/code-connect/mega/android/core/ui/components/
  banner/Banners.figma.kt        ← hand-verified via MCP on 2026-04-22
  banner/HomeBanner.figma.kt
  button/Buttons.figma.kt
  ...
```

## Mapping matrix

Status legend:

- **Verified** — `node-id` comes from a real Figma component set we inspected with
  `get_context_for_code_connect`; property names should be accurate.
- **TODO-url** — structural mapping in place, but the URL still has `node-id=TODO` and
  property/variant names are guesses. Need an MCP run to finalise.
- **No-Figma** — Compose-only primitive or theming/structural helper; deliberately has no
  Figma counterpart.
- **Missing-in-Figma** — Compose component exists; Figma does not. Needs the Figma team
  to add a matching component to the library, then we update the URL and uncomment the
  stub.

| Compose category (source folder)         | Figma component set                 | Status            | File                                    |
|------------------------------------------|-------------------------------------|-------------------|-----------------------------------------|
| `banner/` (Inline*/Top* banners)         | Banners                             | Verified          | `banner/Banners.figma.kt`               |
| `banner/` (HomeBanner)                   | Home banner                         | Verified (nodeId) | `banner/HomeBanner.figma.kt`            |
| `button/` (Primary/Secondary/Outlined/Text)| Buttons                           | TODO-url          | `button/Buttons.figma.kt`               |
| `button/` (M3 variants)                  | Button (M3 Expressive)              | TODO-url          | `button/ButtonM3.figma.kt`              |
| `button/` (icon buttons)                 | Icon button                         | TODO-url          | `button/IconButtons.figma.kt`           |
| `button/` (radio)                        | Radio buttons                       | TODO-url          | `button/RadioButtons.figma.kt`          |
| `button/` (accessory bar)                | Accessory Bar Buttons               | TODO-url          | `button/AccessoryBarButtons.figma.kt`   |
| `button/` (anchored groups)              | Anchored buttons                    | TODO-url          | `button/AnchoredButtons.figma.kt`       |
| `card/`                                  | Pricing card                        | TODO-url          | `card/Cards.figma.kt`                   |
| `checkbox/`                              | Checkboxes                          | TODO-url          | `checkbox/Checkboxes.figma.kt`          |
| `chip/`                                  | Chips                               | TODO-url          | `chip/Chips.figma.kt`                   |
| `dialogs/`                               | Basic dialog / List dialog / Promotion dialog | TODO-url | `dialogs/Dialogs.figma.kt`              |
| `divider/`                               | Divider                             | TODO-url          | `divider/Dividers.figma.kt`             |
| `dropdown/`                              | Dropdown menu                       | TODO-url          | `dropdown/DropDown.figma.kt`            |
| `state/EmptyStateView`                   | Empty state                         | TODO-url          | `empty/EmptyState.figma.kt`             |
| `fab/`                                   | — (audit: "Missing")                | Missing-in-Figma  | `fab/MegaFab.figma.kt`                  |
| `indicators/`                            | HUD / Circular / Linear / Page controls / sf_Timer radial | TODO-url | `indicators/Indicators.figma.kt`        |
| `inputfields/` (text/password/expiration/labelled) | Input                     | TODO-url          | `inputfields/InputFields.figma.kt`      |
| `inputfields/HelpText*`                  | Help text                           | TODO-url          | `inputfields/HelpText.figma.kt`         |
| `inputfields/ReadOnly*`                  | sf_Read-only input field            | TODO-url          | `inputfields/ReadOnlyInputs.figma.kt`   |
| `inputfields/SearchInputField`           | Search                              | TODO-url          | `inputfields/SearchInput.figma.kt`      |
| `inputfields/VerificationTextInputField` | Verification                        | TODO-url          | `inputfields/VerificationInput.figma.kt`|
| `label/`                                 | Label                               | TODO-url          | `label/Labels.figma.kt`                 |
| `list/`                                  | List item / Header / Profile list   | TODO-url          | `list/ListItems.figma.kt`               |
| `navigation/`                            | Bottom app bar                      | TODO-url          | `navigation/NavigationBottomBar.figma.kt`|
| `profile/`                               | Avatar                              | TODO-url          | `profile/Avatar.figma.kt`               |
| `prompt/`                                | Prompt                              | Verified (nodeId) | `prompt/Prompts.figma.kt`               |
| `sheets/`                                | Sheet / Custom sheet / Promotion dialog (sheet form) | TODO-url | `sheets/Sheets.figma.kt`                |
| `slider/`                                | Slider                              | TODO-url          | `slider/Slider.figma.kt`                |
| `snackbar/`                              | Snackbar                            | TODO-url          | `snackbar/Snackbar.figma.kt`            |
| `tabs/`                                  | Tabs                                | TODO-url          | `tabs/Tabs.figma.kt`                    |
| `text/TopNavigationButtons`              | Top navigation                      | TODO-url          | `text/TopNavigationButtons.figma.kt`    |
| `thumbnail/IconThumbnail`                | image holder                        | TODO-url          | `thumbnail/Thumbnails.figma.kt`         |
| `toggle/`                                | Switch                              | TODO-url          | `toggle/Switch.figma.kt`                |
| `toolbar/`                               | Toolbars                            | TODO-url          | `toolbar/Toolbars.figma.kt`             |
| `tooltip/`                               | Tooltips                            | TODO-url          | `tooltip/Tooltips.figma.kt`             |
| `badge/`                                 | Badge (MD3 primitive; audit: "consider adding AndroidBadge") | TODO-url | `badge/Badges.figma.kt`  |

### No-Figma / Compose-only (intentionally not mapped)

These are documented in the top-of-file comment block of the nearest category file so
developers know the omission is deliberate:

- `RoundCard` — internal card container primitive.
- `DialogButton` — atomic dialog sub-piece.
- `MegaDialog` — Compose-only dialog wrapper.
- `MegaReorderableLazyColumn` — drag/drop list container.
- `SheetActionHeader` — atomic sheet sub-piece.
- `SnackbarLifetimeController` — Compose-only state holder.
- `ForceRiceTopAppBarEffect` — internal scroll effect.
- `ContentHighlighter` — visual effect inside tooltips.
- `MegaIcon` — icon atom; the graphic itself comes from the Icon library, not the
  component library.
- `MegaText`, `MegaClickableText`, `LinkSpannedText`, `SpannedText`, `SpannableText`,
  `NetworkConnectionBanner`, `HighlightedText` — typography / spanning primitives.
- `LoadingView`, `MegaScaffold`, `MegaScaffoldWithTopAppBarScrollBehavior`,
  `ClippedShadow` — structural helpers.
- `BoxSurface`, `CardSurface`, `ColumnSurface`, `RowSurface`, `ThemedSurface` — theming
  constructs.
- `SettingsActionItem`, `SettingsNavigationItem`, `SettingsToggleItem` — shared with iOS
  settings; currently not in `ANDROID - New`.
- `Counter`, `FastScrollLazyColumn`, `FastScrollLazyVerticalGrid`,
  `FastScrollForLazyColumn`, `VerticalScrollbar`, `TextThumbnail` — audit marks these as
  "Missing" on the Figma side (see Missing-in-Figma stubs in the relevant category file).

Feature-specific Figma sets (`Queue list item`, `Video list item`, `Content block`) are
deliberately skipped — they are product surfaces, not shared design-system components.

## Verifying a TODO-url mapping via MCP

1. Open the Figma file (`ANDROID - New`, key `HagXfSTF94ICEFj8rfNyLc`) on desktop and
   select the component set you want to map (e.g. `Buttons`).
2. Call `get_context_for_code_connect` via the Figma MCP plugin — pass the selected
   node's id. The response contains:
   - the real Figma node id (the `…?node-id=AAAAA-BBBBB` suffix),
   - the authoritative variant property names and allowed values,
   - the authoritative editable-text / boolean property names.
3. Open the corresponding `*.figma.kt` file in this directory. Replace:
   - `node-id=TODO` in the `private const val …_URL` with the real id,
   - every `@FigmaVariant("…", "…")  // TODO verify` line with the real pair,
   - every `@FigmaProperty(FigmaType.Text,  "✏️ …")  // TODO verify` line with the real
     property name (keep the `✏️ ` prefix only if Figma itself uses it).
4. Remove the `// TODO verify with get_context_for_code_connect` comment and the file-
   header `NOTE — DRAFT` line once every TODO is resolved.
5. Once the plugin is wired up, run the Code Connect CLI to publish the mappings so
   Figma's Dev Mode panel starts serving the Compose snippet for that component.

## Counts

- **37** `.figma.kt` files generated (plus this README).
- **137** active `@FigmaConnect` classes emitted.
- **2** files with verified node ids (`banner/Banners.figma.kt`, `banner/HomeBanner.figma.kt`,
  `prompt/Prompts.figma.kt`) — note that `Banners.figma.kt` is the only one with
  MCP-verified property names; the rest still need a `get_context_for_code_connect` pass.
- **1** Missing-in-Figma stub file (`fab/MegaFab.figma.kt`), plus an inline
  Missing-in-Figma stub inside `thumbnail/Thumbnails.figma.kt` for `TextThumbnail`.
- ~30 Compose functions are intentionally **Compose-only** (no Figma) — see the list
  above.
