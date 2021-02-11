package org.ahivs.dependencies

object Version {
    const val kotlin = "1.4.20"
    const val gradleAndroid = "4.1.1"
    const val gradleJunit5 = "1.7.0.0"
    const val coroutines = "1.3.9"

    object Android {
        const val appcompat = "1.2.0"
        const val coreKtx = "1.3.2"
        const val lifecycleVersion = "2.3.0"
        const val material = "1.3.0"
        const val constraintLayoutVersion = "2.0.4"
        const val androidxActivity = "1.1.0"

    }

    //testlib version
    const val mockito = "2.25.0"
    const val junit5 = "5.5.1"
}

object Libs {
    const val kotlin = "org.jetbrains.kotlin:kotlin-stdlib:${Version.kotlin}"

    //android provided libs
    object Android {
        const val appcompat = "androidx.appcompat:appcompat:${Version.Android.appcompat}"
        const val coreKtx = "androidx.core:core-ktx:${Version.Android.coreKtx}"
        const val material = "com.google.android.material:material:${Version.Android.material}"
        const val constraintLayout =
            "androidx.constraintlayout:constraintlayout:${Version.Android.constraintLayoutVersion}"
        const val liveData =
            "androidx.lifecycle:lifecycle-livedata-ktx:${Version.Android.lifecycleVersion}"
        const val viewModel =
            "androidx.lifecycle:lifecycle-viewmodel-ktx:${Version.Android.lifecycleVersion}"
        const val androidxActivity =
            "androidx.activity:activity-ktx:${Version.Android.androidxActivity}"
        const val coroutines =
            "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Version.coroutines}"

    }
}

object TestLibs {
    const val mockitoCore = "org.mockito:mockito-core:${Version.mockito}"
    const val mockitoInline = "org.mockito:mockito-inline:${Version.mockito}"
    const val coroutine = "org.jetbrains.kotlinx:kotlinx-coroutines-test:${Version.coroutines}"

    const val junit5 = "org.junit.jupiter:junit-jupiter-api:${Version.junit5}"
    const val junit5Engine = "org.junit.jupiter:junit-jupiter-engine:${Version.junit5}"
    const val junit5Params = "org.junit.jupiter:junit-jupiter-params:${Version.junit5}"

    object Android {
        const val arch = "androidx.arch.core:core-testing:${Version.Android.lifecycleVersion}"
    }
}

object GradlePlugin {
    const val android = "com.android.tools.build:gradle:${Version.gradleAndroid}"
    const val kotlin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Version.kotlin}"
    const val junit5 = "de.mannodermaus.gradle.plugins:android-junit5:${Version.gradleJunit5}"
}