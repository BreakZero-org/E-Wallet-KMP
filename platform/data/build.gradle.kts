plugins {
  id("easy.multiplatform.library")
}

kotlin {
  sourceSets {
    commonMain {
      dependencies {
        implementation(projects.platform.model)
        implementation(projects.platform.network)
        implementation(projects.platform.datastore)
        implementation(libs.kotlinx.coroutines.core)
        implementation(libs.koin.core)
        api(libs.paging.compose.common)
      }
    }
  }
}

android {
  namespace = "org.easy.wallet.data"
}