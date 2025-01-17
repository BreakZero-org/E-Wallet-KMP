package org.easy.configs

import com.android.build.api.dsl.CommonExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Project
import org.gradle.api.plugins.JavaPluginExtension
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.provideDelegate
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

/**
 * Configure base Kotlin with Android options
 */
internal fun Project.configureKotlinAndroid(
  commonExtension: CommonExtension<*, *, *, *, *, *>,
) {
  commonExtension.apply {
    compileSdk = Version.compileSdk

    defaultConfig {
      minSdk = Version.minSdk
    }

    packaging {
      resources {
        excludes += "/META-INF/{AL2.0,LGPL2.1}"
      }
    }

    compileOptions {
      sourceCompatibility = JavaVersion.VERSION_17
      targetCompatibility = JavaVersion.VERSION_17

//      isCoreLibraryDesugaringEnabled = true
    }

    configureKotlin()

    dependencies {
//      libs.findLibrary("android.desugarJdkLibs").ifPresent {
//        add("coreLibraryDesugaring", it)
//      }
    }
  }
}

private fun Project.configureKotlin() {
  // Use withType to workaround https://youtrack.jetbrains.com/issue/KT-55947
  tasks.withType<KotlinCompile>().configureEach {
    compilerOptions {
      jvmTarget.set(JvmTarget.JVM_17)
      val warningsAsErrors: String? by project
      allWarningsAsErrors.set(warningsAsErrors.toBoolean())
      freeCompilerArgs.add(
        // Enable experimental coroutines APIs, including Flow
        "-opt-in=kotlinx.coroutines.ExperimentalCoroutinesApi"
      )
    }
  }
}

internal fun Project.configureKotlinJvm() {
  extensions.configure<JavaPluginExtension> {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
  }

  configureKotlin()
}