package mega.android.core.ui.buildscripts

import mega.android.core.ui.buildscripts.GenerateTokens.Companion.DEFAULT_PACKAGE

/**
 * Runs the Script to generate Kotlin files with the tokens from json files
 */
fun main(args: Array<String>) {
    //generate the tokens with NEW core tokens
    GenerateTokens().generate(
        appPrefix = "AndroidNew",
        packageName = DEFAULT_PACKAGE,
        destinationPath = "core-ui/src/main/kotlin",
        generateInterfaces = true,
    )
}