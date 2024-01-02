package mega.android.core.ui.shared.theme

import mega.android.core.ui.buildscripts.GenerateTokens


/**
 * Runs the Script to generate Kotlin files with the tokens from json files
 */
fun main(args: Array<String>) {
    GenerateTokens().generate(
        appPrefix = "CoreUI",
        packageName = "mega.android.core.ui.shared.theme.tokens",
        destinationPath = "shared/theme/src/main/kotlin",
        assetsFolder = "designSystemAssets"
    )
}