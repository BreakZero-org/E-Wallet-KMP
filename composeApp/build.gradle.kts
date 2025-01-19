import org.easy.configs.configureFlavors
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
  id("easy.multiplatform.application")
  kotlin("plugin.serialization") version "2.1.0"
}

kotlin {
  androidTarget {
    compilerOptions {
      jvmTarget.set(JvmTarget.JVM_17)
    }
  }

  sourceSets {
    androidMain.dependencies {
      implementation(compose.preview)
      implementation(libs.androidx.activity.compose)
      implementation(libs.androidx.activity.ktx)
      implementation(libs.koin.android)
      implementation(libs.koin.androidx.compose)
    }
    commonMain.dependencies {
      implementation(projects.platform.data)
      implementation(projects.platform.model)

      implementation(libs.haze)

      implementation(compose.runtime)
      implementation(compose.material3)
      implementation(compose.ui)
      implementation(compose.components.resources)
      implementation(compose.components.uiToolingPreview)
      implementation(libs.androidx.lifecycle.viewmodel)
      implementation(libs.androidx.lifecycle.runtime.compose)
      implementation(libs.navigation.compose)

      implementation(libs.kotlinx.serialization.json)

      implementation(libs.koin.core)
      implementation(libs.koin.compose)
      implementation(libs.koin.composeVM)

      implementation(libs.lifecycle.viewmodel.compose)
    }
  }
}

android {
  namespace = "org.easy.wallet"

  defaultConfig {
    applicationId = "org.easy.wallet"
    versionCode = 1
    versionName = "1.0"
  }
  configureFlavors(this)
}

dependencies {
  debugImplementation(compose.uiTooling)
}