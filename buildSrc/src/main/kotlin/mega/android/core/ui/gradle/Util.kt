package mega.android.core.ui.gradle

import org.gradle.api.Project
import java.io.File

const val VERSION_FILE = "version.txt"

fun readLibVersion(project: Project): String =
    try {
        File("${project.rootDir.absolutePath}/$VERSION_FILE").readText()
    } catch (e: Exception) {
        println("[ERROR] Failed to read\"$VERSION_FILE\" at project root!")
        throw e
    }

fun writeLibVersion(project: Project, version: String) {
    runCatching {
        require(version.isNotBlank()) { "[ERROR] Writing version failed. version cannot be blank!" }
        File("${project.rootDir.absolutePath}/$VERSION_FILE").writeText(version)
    }.onFailure {
        println("[ERROR] Writing version $version to \"$VERSION_FILE\" failed!")
        throw it
    }
}

