import mega.privacy.megagradle.plugin.extension.Dependency

plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    alias(libs.plugins.mega.artifactory.publish.convention)
    alias(libs.plugins.mega.android.library.jacoco.convention)
}

android {
    namespace = "mega.android.core.ui"
    compileSdk = 34

    defaultConfig {
        minSdk = 26
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    testOptions {
        unitTests {
            isIncludeAndroidResources = true
        }
    }

    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.14"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
}


val libVersion: String by rootProject.extra
val commit: String by rootProject.extra
val mavenRepoKey: String by rootProject.extra
val mavenGroupId: String by rootProject.extra
val builder: String by rootProject.extra

megaPublish {
    repoKey = mavenRepoKey
    groupId = mavenGroupId
    artifactId = "ui"
    version = libVersion
    libPath = "${layout.buildDirectory.get()}/outputs/aar/${project.name}-release.aar"
    sourcePath = "${layout.buildDirectory.get()}/libs/${project.name}-sources.jar"
    properties = mapOf(
        "commit" to commit,
        "builder" to builder,
    )
    dependentTasks = listOf("assembleRelease", "releaseSourcesJar")
    dependencies = listOf(
        Dependency(
            groupId = mavenGroupId,
            artifactId = "ui-tokens",
            version = libs.versions.mega.core.ui.tokens.get(),
            scope = "runtime"
        )
    )
}

dependencies {
    implementation(libs.mega.core.ui.tokens)
    implementation(platform(libs.compose.bom))
    implementation(libs.bundles.compose.bom)
    implementation(libs.coil.compose)
    implementation(libs.core.ktx)
    implementation(libs.appcompat)
    implementation(libs.constraintlayout.compose)
    implementation(libs.google.font)
    testImplementation(libs.compose.junit)
    debugImplementation(libs.compose.manifest)
    debugImplementation(libs.roboelectric)
    testImplementation(libs.junit)
    androidTestImplementation(libs.junit.test.ext)
    androidTestImplementation(libs.espresso.core)
}
