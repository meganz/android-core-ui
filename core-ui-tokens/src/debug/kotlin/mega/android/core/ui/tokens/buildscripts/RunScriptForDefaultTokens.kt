package mega.android.core.ui.tokens.buildscripts

import mega.android.core.ui.tokens.buildscripts.kotlingenerator.GenerateColorTokens

/**
 * Runs the Script to generate Kotlin files with the tokens from json files
 */
fun main(args: Array<String>) {
    GenerateColorTokens().generateThemeForAndroidNew()
}