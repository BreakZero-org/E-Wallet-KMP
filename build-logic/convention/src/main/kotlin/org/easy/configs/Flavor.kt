package org.easy.configs

import com.android.build.api.dsl.ApplicationExtension
import com.android.build.api.dsl.ApplicationProductFlavor
import com.android.build.api.dsl.CommonExtension
import com.android.build.api.dsl.ProductFlavor

enum class FlavorDimension {
    Environment
}

enum class Flavor(val dimension: FlavorDimension, val applicationIdSuffix: String? = null) {
    staging(FlavorDimension.Environment, ".staging"),
    prod(FlavorDimension.Environment)
}

fun configureFlavors(
    commonExtension: CommonExtension<*, *, *, *, *, *>,
    flavorConfigurationBlock: ProductFlavor.(flavor: Flavor) -> Unit = {}
) {
    commonExtension.apply {
        flavorDimensions += FlavorDimension.Environment.name
        productFlavors {
            Flavor.values().forEach {
                create(it.name) {
                    dimension = it.dimension.name
                    flavorConfigurationBlock(this, it)
                    if (this@apply is ApplicationExtension && this is ApplicationProductFlavor) {
                        if (it.applicationIdSuffix != null) {
                            applicationIdSuffix = it.applicationIdSuffix
                        }
                    }
                }
            }
        }
    }
}