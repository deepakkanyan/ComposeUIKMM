import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    id("com.google.gms.google-services")
    id("kotlinx-serialization")
}

kotlin {
    androidTarget {
        @OptIn(ExperimentalKotlinGradlePluginApi::class)
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_17)
        }
    }
    
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "ComposeApp"
            isStatic = true
        }
    }
    jvm("desktop")

    sourceSets {
        val desktopMain by getting

        androidMain.dependencies {
            implementation(compose.preview)
            implementation(libs.androidx.activity.compose)
            implementation(project.dependencies.platform(libs.firebase.bom))
            implementation(libs.accompanist.permissions)
            implementation(libs.koin.android) // Ensure this is added
            implementation(libs.androidx.activity)
            implementation(libs.ktor.client.android)
        }
        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material3)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)
            implementation(libs.androidx.lifecycle.viewmodel)
            implementation(libs.androidx.lifecycle.runtime.compose)

            implementation(libs.gitlive.firebase.firestore)
            implementation(libs.gitlive.firebase.auth)
            implementation(libs.gitlive.firebase.storage)


            implementation(libs.kotlinx.coroutines.core)
            implementation(libs.jetbrains.navigation.compose)
            implementation(libs.androidx.core.ktx)
            implementation(libs.kotlinx.serialization.json)
            implementation (libs.material.icons)
            implementation(compose.materialIconsExtended)

            //Koin
            implementation(libs.koin.core)
            implementation(libs.koin.compose)
            implementation(libs.koin.compose.viewmodel)

            //Datetime
            implementation(libs.kotlinx.datetime)

            //PhotoPicker
//            implementation(libs.peekaboo.ui)
//            implementation(libs.peekaboo.image.picker)
            implementation(libs.coil.compose)
            implementation(libs.coil.network.ktor)
        }

        iosMain.dependencies{
            implementation(libs.ktor.client.darwin)
        }

        desktopMain.dependencies {
            implementation(compose.desktop.currentOs)
            implementation(libs.kotlinx.coroutinesSwing)
            implementation("dev.gitlive:firebase-firestore-jvm:2.4.0")
        }
    }
}

android {
    namespace = "org.white.green"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    defaultConfig {
        applicationId = "org.white.green"
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}

dependencies {
    debugImplementation(compose.uiTooling)
    implementation(libs.ui.tooling.preview)
}

compose.desktop {
    application {
        mainClass = "org.white.green.MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "org.white.green"
            packageVersion = "1.0.0"
        }
    }
}
