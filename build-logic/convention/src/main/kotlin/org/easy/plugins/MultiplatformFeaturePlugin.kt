package org.easy.plugins

import com.android.build.gradle.LibraryExtension
import org.easy.configs.configureFlavors
import org.easy.configs.configureKotlinAndroid
import org.easy.configs.configureMultiplatformLibrary
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

class MultiplatformFeaturePlugin: Plugin<Project> {
  override fun apply(target: Project) {
    with(target) {
      with(pluginManager) {
        apply("org.jetbrains.kotlin.multiplatform")
        apply("com.android.library")
      }
      configureMultiplatformLibrary()

      extensions.configure<LibraryExtension> {
        configureKotlinAndroid(this)
        defaultConfig.targetSdk = 34
        testOptions.animationsDisabled = true
        configureFlavors(this)

        resourcePrefix = path.split("""\W""".toRegex()).drop(1).distinct().joinToString(separator = "_").lowercase() + "_"
      }
    }
  }
}