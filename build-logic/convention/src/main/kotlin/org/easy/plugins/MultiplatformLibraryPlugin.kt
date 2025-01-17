package org.easy.plugins

import com.android.build.api.dsl.ApplicationExtension
import com.android.build.api.dsl.LibraryExtension
import org.easy.configs.configureKotlinAndroid
import org.easy.configs.configureMultiplatformLibrary
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.getByType

class MultiplatformLibraryPlugin: Plugin<Project> {
  override fun apply(target: Project) {
    with(target) {
      pluginManager.apply("org.jetbrains.kotlin.multiplatform")
      pluginManager.apply("com.android.library")

      configureMultiplatformLibrary()

      val extension = extensions.getByType<LibraryExtension>()
      configureKotlinAndroid(extension)
    }
  }
}