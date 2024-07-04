import mega.android.core.ui.gradle.GITLAB_USER_NAME
import mega.android.core.ui.gradle.LIB_COMMIT
import mega.android.core.ui.gradle.generateLibVersion

// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.com.android.application) apply false
    id("org.jetbrains.kotlin.android") version "1.9.24" apply false
    alias(libs.plugins.com.android.library) apply false
    alias(libs.plugins.jfrog.artifactory) apply false
    alias(libs.plugins.mega.artifactory.publish.convention) apply false
    alias(libs.plugins.mega.android.library.jacoco.convention) apply false
}

buildscript {
    repositories {
        google()
        mavenCentral()
        maven(url = "https://jitpack.io")
    }
    dependencies {
        classpath("org.jfrog.buildinfo:build-info-extractor-gradle:${libs.versions.jfrog.artifactory.get()}")
    }
}

allprojects {
    apply(plugin = "com.jfrog.artifactory")
    apply(plugin = "maven-publish")
    repositories {
        google()
        mavenCentral()
        maven {
            url =
                uri("${System.getenv("ARTIFACTORY_BASE_URL")}/artifactory/mega-gradle/core-ui")
        }
    }
}

// Library version and properties
extra["libVersion"] = generateLibVersion(project)
extra["mavenRepoKey"] = "core-ui"
extra["mavenGroupId"] = "mega.android.core"
extra["commit"] = System.getenv(LIB_COMMIT)?.takeIf { it.isNotBlank() } ?: "N/A"
extra["builder"] = System.getenv(GITLAB_USER_NAME)?.takeIf { it.isNotBlank() } ?: "N/A"

//apply(from = "${project.rootDir}/publish.gradle")