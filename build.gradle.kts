// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.jetbrains.kotlin.android) apply false
    id("com.google.dagger.hilt.android") version "2.44" apply false
    id("org.jlleitschuh.gradle.ktlint") version "12.1.1"
    id("org.jetbrains.kotlin.plugin.serialization") version "1.9.22" apply false
    id("com.google.devtools.ksp") version "1.9.22-1.0.17" apply false
    id("com.google.gms.google-services") version "4.4.2" apply false
    id("com.google.firebase.crashlytics") version "3.0.2" apply false
    id("com.autonomousapps.dependency-analysis") version "1.33.0"
}

buildscript {
    dependencies {
        classpath("com.google.dagger:hilt-android-gradle-plugin:2.51.1")
    }
}

dependencyAnalysis {
    issues {
        all {
            onUnusedDependencies {
                severity("fail")
            }
            onUsedTransitiveDependencies {
                severity("warn")
            }
            onIncorrectConfiguration {
                severity("fail")
            }
            onUnusedAnnotationProcessors {
                severity("fail")
            }
            onRedundantPlugins {
                severity("fail")
            }
            // Ignore ktx dependencies if a transitive dependency is used
            ignoreKtx(true)
        }
    }
}
