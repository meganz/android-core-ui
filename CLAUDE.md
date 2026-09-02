# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## What this repo is

MEGA's Android design-system library: Jetpack Compose components themed with MEGA design tokens, published to MEGA's Artifactory as `mega.android.core:ui` (from `:core-ui`) and `mega.android.core:ui-tokens` (from `:core-ui-tokens`). The version comes from `version.txt` via `buildSrc`.

Read [docs/ARCHITECTURE.md](docs/ARCHITECTURE.md) for the design-system architecture: token layers and the generation pipeline, the core/snowflake/feature component taxonomy, module hierarchy, and how themes inject tokens.

Requires JDK 21. The build resolves MEGA convention plugins (`mega.artifactory.publish.convention`, `mega.android.library.jacoco.convention`) from Artifactory — the `ARTIFACTORY_BASE_URL` env var must be set for Gradle to sync.

## Modules

- `:core-ui` — the component library. Components live in `core-ui/src/main/kotlin/mega/android/core/ui/components/<category>/`.
- `:core-ui-tokens` — design tokens (colors, shapes, spacing) plus the generator that produces them from JSON.
- `:app` — demo/catalog app for manually testing components. Its `build.gradle.kts` can switch between depending on the local `:core-ui` module or a published artifact.
- `core-ui/code-connect/` — Figma Code Connect `*.figma.kt` files (see below). Deliberately outside any source set; not compiled by the normal build.
- `buildSrc` — version helpers and `GenerateLlmsTxtTask` (AI component-catalog generation).

## Commands

```bash
./gradlew assembleDebug                  # build (CI runs this)
./gradlew testDebugUnitTest              # all unit tests (CI runs this)
./gradlew :core-ui:testDebugUnitTest --tests "mega.android.core.ui.components.inputfields.TextInputFieldTest"   # single test class

./gradlew :core-ui:validateDebugScreenshotTest   # compare against reference screenshots
./gradlew :core-ui:updateDebugScreenshotTest     # regenerate reference PNGs — run when components change, commit the images

./gradlew :core-ui-tokens:generateDefaultTokens  # regenerate token Kotlin files from designSystemAssets/tokens.json
./gradlew :core-ui:generateLlmsTxt               # regenerate llms.txt component catalog (also runs automatically on every build)
./gradlew :core-ui:releaseSourcesJar             # sources JAR with llms.txt + screenshots bundled in META-INF
```

Unit tests are Compose UI tests run on the JVM via Robolectric (`@RunWith(AndroidJUnit4::class)` + `createComposeRule()`), in `core-ui/src/test/kotlin/`.

## Architecture

Tokens, theming, and the module hierarchy are covered in [docs/ARCHITECTURE.md](docs/ARCHITECTURE.md). The short version: `designSystemAssets/tokens.json` → `:core-ui-tokens:generateDefaultTokens` → committed generated Kotlin (semantic tokens in `:core-ui-tokens`, color enums in `core-ui/.../theme/values/`); `AndroidTheme(isDark = ...)` injects the palette via CompositionLocals; components read `DSTokens` or take `theme/values/` enums (`TextColor`, `IconColor`, …) — never hardcoded colors.

### AI component catalog (llms.txt)

`GenerateLlmsTxtTask` (buildSrc) scans the components source tree and generates `llms.txt`/`llms-full.txt` plus `@PreviewTest` screenshot-test sources into `core-ui/build/generated/`. These and the reference PNGs (`core-ui/src/screenshotTestDebug/reference/`, committed) are bundled into the sources JAR under `META-INF/mega.android.core.ui/` — never into the AAR. See README.md for details.

### Figma Code Connect

`figma.config.json` configures the Compose parser over `core-ui/code-connect/**/*.figma.kt`. The folder layout mirrors the components package tree, and `core-ui/code-connect/README.md` holds the component↔Figma mapping matrix with per-file status. These files annotate Compose components with `@FigmaConnect`/`@FigmaProperty` markers for the Figma tooling and won't compile as regular Kotlin.
