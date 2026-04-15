# Core UI

Android Compose UI component library providing themed components for the Mega design system.

## AI Component Discoverability

This library auto-generates metadata and screenshots so AI coding tools can discover and choose the right component. The output ships in the **sources JAR** (never in the AAR or consumer APKs).

### What gets generated

| File | Contents |
|------|----------|
| `llms.txt` | Concise component index (~226 components, grouped by category) |
| `llms-full.txt` | Full reference with signatures, parameter types/defaults, usage examples, and screenshot references |
| `screenshots/*.png` | Rendered preview images of each component (~124 images) |

### Gradle commands

```bash
# 1. Generate llms.txt + llms-full.txt + @PreviewTest source files
#    (runs automatically on every build via preBuild)
./gradlew :core-ui:generateLlmsTxt

# 2. Render component screenshots (run on demand when components change)
./gradlew :core-ui:updateDebugScreenshotTest

# 3. Build the sources JAR with all of the above bundled in
./gradlew :core-ui:releaseSourcesJar
```

Step 1 runs automatically as part of every build. Step 2 is manual — run it when components are added or changed, then commit the resulting images.

### Where the output lives

| Location | Purpose |
|----------|---------|
| `core-ui/build/generated/llms/` | Generated `llms.txt` and `llms-full.txt` |
| `core-ui/build/generated/screenshotTest/kotlin/` | Generated `@PreviewTest` source files |
| `core-ui/src/screenshotTestDebug/reference/` | Rendered screenshot PNGs (committed to source control) |

All three are bundled into the sources JAR at:
```
META-INF/mega.android.core.ui/llms.txt
META-INF/mega.android.core.ui/llms-full.txt
META-INF/mega.android.core.ui/screenshots/
```

### Accessing from a consuming project

The sources JAR is published alongside the AAR. To point an AI tool at the component catalog, extract it from the sources JAR:

```bash
jar xf core-ui-sources.jar META-INF/mega.android.core.ui/llms.txt
```

Or reference it in the consuming project's `CLAUDE.md`:

```
For UI components, read META-INF/mega.android.core.ui/llms.txt from the core-ui sources JAR.
```
