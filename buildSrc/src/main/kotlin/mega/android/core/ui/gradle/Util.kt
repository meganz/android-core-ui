package mega.android.core.ui.gradle

import org.gradle.api.Project
import java.io.File
import java.time.OffsetDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

const val VERSION_FILE = "version.txt"

/**
 * Read version from version.txt file
 *
 * @param project
 * @return
 */
private fun getVersionFromFile(project: Project): String =
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

/**
 * get Patch of the version
 *
 * @return
 */
private fun getPatch(): String =
    System.getenv(PATCH_VERSION) ?: OffsetDateTime.now(ZoneOffset.UTC)
        .format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"))

/**
 * get the full version string
 *
 * @param project
 * @return
 */
fun getLibVersion(project: Project): String {
    val version = getVersionFromFile(project)
    val patch = getPatch()
    val snapshot = System.getenv(SNAPSHOT).orEmpty()
    return "${version}.${patch}$snapshot"
}

// environment variable keys
const val SNAPSHOT = "SNAPSHOT"
const val PATCH_VERSION = "PATCH_VERSION"  // patch in the version string
const val LIB_COMMIT = "LIB_COMMIT"  // commit of current lib
const val GITLAB_USER_NAME = "gitlabUserName" // user who triggered the build
