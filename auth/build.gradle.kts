import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    //alias(libs.plugins.kotlinNativeCocoaPods)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.jetbrainsCompose)
    alias(libs.plugins.serializationPlugin)
    id("module.publication")
    alias(libs.plugins.squareup.wire)
    //id("io.github.ttypic.swiftklib") version "0.6.3"
}

kotlin {
    /*
    if (System.getProperty("kwasm", "true").toBoolean()) {
        @OptIn(ExperimentalWasmDsl::class)
        wasmJs {
            moduleName = "kmp-auth"
            browser {
                testTask {
                    useKarma {
                        useChrome()
                    }
                }
            }
            binaries.executable()
        }
    }

     */
    jvm()
    androidTarget {
        publishLibraryVariants("release")
        @OptIn(ExperimentalKotlinGradlePluginApi::class)
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_17)
        }
    }

    /*
    cocoapods {
        ios.deploymentTarget = "11.0"
        framework {
            baseName = "MultiplatformAuthGoogle"
            isStatic = true
        }
        pod("GoogleSignIn")
    }

     */

    //iosX64()
    //iosArm64()
    //iosSimulatorArm64()
    //linuxX64()

    sourceSets {
        //iosMain.dependencies {
            //implementation(libs.github.mirzemehdi.google)
            //implementation(libs.androidx.datastore.core.okio)
        //}

        jvmMain.dependencies {
            implementation(libs.ktor.server.netty)
            implementation(libs.androidx.datastore.core.okio)
        }

        commonMain.dependencies {
            //implementation(libs.google.api.services.oauth2) // Replace with the latest version
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material3)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(libs.kotlinx.coroutines.core)
            implementation(libs.androidx.lifecycle.viewmodel)
            implementation(libs.lifecycle.viewmodel.compose)
            implementation(libs.androidx.lifecycle.runtime.compose)
            implementation(libs.kotlinx.serialization.json)
            implementation(libs.dreamfighter.kmp.network)
            implementation(libs.dreamfighter.kmp.annotation)
            implementation(libs.ktor.server.websockets)
            implementation(libs.androidx.datastore.core.okio)
            //implementation(libs.wire.runtime)
            // put your Multiplatform dependencies here
        }

        androidMain.dependencies {
            implementation(libs.androidx.startup.runtime)
            implementation(libs.androidx.credentials)
            implementation(libs.androidx.credentials.play.services.auth)
            implementation(libs.androidx.startup.runtime)
            implementation(libs.androidx.credentials)
            implementation(libs.androidx.credentials.play.services.auth)
            implementation(libs.google.identity.googleid)
            implementation(libs.kotlinx.coroutines.android)
            implementation(compose.components.resources)
            implementation(libs.kmpauth.google)
            //implementation(libs.github.mirzemehdi.google)
            //implementation("com.google.devtools.ksp:symbol-processing-api:2.0.21-1.0.25")
        }
        val commonTest by getting {
            dependencies {
                implementation(libs.kotlin.test)
                implementation(libs.system.lambda)
            }
        }
    }
    /*
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64(),
    ).forEach {
        it.compilations {
            val main by getting {
                cinterops {
                    create("Utils")
                }
            }
        }
    }

     */
}

android {
    namespace = "id.dreamfighter.kmp.auth"
    compileSdk = libs.versions.android.compileSdk.get().toInt()
    //sourceSets["main"].res.srcDirs("src/androidMain/res")
    sourceSets["main"].resources.srcDirs("src/commonMain/resources")
    defaultConfig {
        minSdk = libs.versions.android.minSdk.get().toInt()
    }
}

/*
swiftklib {
    create("Utils") {
        path = file("native/Utils")
        packageName("id.dreamfighter.multiplatform.swift")
    }
}
dependencies {
    add("kspCommonMainMetadata", libs.dreamfighter.kmp.ksp)
}


tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().all {
    if (name != "kspCommonMainMetadata") {
        dependsOn("kspCommonMainKotlinMetadata")
    }
}
 */

wire {
    kotlin {
    }
    sourcePath {
        srcDir("src/commonMain/proto")
    }
}

