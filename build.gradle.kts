// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version "8.1.1" apply false
    id("org.jetbrains.kotlin.android") version "1.9.10" apply false
    id("com.android.library") version "8.1.1" apply false
}

buildscript {
    repositories {
        google()
        mavenCentral()
        maven(url = "https://jitpack.io")
    }
    dependencies {
        classpath("org.jfrog.buildinfo:build-info-extractor-gradle:4.32.0")
    }
}

allprojects {
    apply(plugin = "com.jfrog.artifactory")
    apply(plugin = "maven-publish")
    repositories {
        google()
        mavenCentral()
        maven(url = "https://jitpack.io")
    }
}

apply(from = "${project.rootDir}/publish.gradle")