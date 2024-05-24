package mega.android.core.ui.gradle

import org.gradle.api.Project
import java.io.File
import java.time.OffsetDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

const val VERSION_FILE = "version.txt"

/**
 * Read version from version.txt file in the project root
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
fun generateLibVersion(project: Project): String {
    val version = getVersionFromFile(project).split(".")
    val major = version[0]
    val minor = version[1]
    val patch = getPatch()
    val snapshot = System.getenv(SNAPSHOT).orEmpty()
    println("major: $major, minor: $minor, patch: $patch, snapshot: $snapshot, version: $version")
    return "${major}.${minor}.${patch}$snapshot"
}

// environment variable keys
const val SNAPSHOT = "SNAPSHOT"
const val PATCH_VERSION = "PATCH_VERSION"  // patch in the version string
const val LIB_COMMIT = "LIB_COMMIT"  // commit of current lib
const val GITLAB_USER_NAME = "gitlabUserName" // user who triggered the build
