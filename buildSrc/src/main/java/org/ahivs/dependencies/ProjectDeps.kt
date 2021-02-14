package org.ahivs.dependencies

object Version {
    const val kotlin = "1.4.20"
    const val gradleAndroid = "4.1.1"
    const val gradleJunit5 = "1.7.0.0"
    const val coroutines = "1.3.9"
    const val okhttp = "4.2.0"
    const val dagger = "2.24"
    const val retrofit = "2.6.1"
    const val timber = "4.7.1"
    const val picasso = "2.71828"

    object Android {
        const val appcompat = "1.2.0"
        const val coreKtx = "1.3.2"
        const val lifecycleVersion = "2.3.0"
        const val material = "1.3.0"
        const val constraintLayoutVersion = "2.0.4"
        const val androidxActivity = "1.1.0"
        const val playServicesLocation = "17.1.0"
        const val swipeRefreshLayout = "1.1.0"
    }

    //testlib version
    const val mockito = "2.25.0"
    const val mockitoKotlin = "2.2.0"
    const val junit5 = "5.5.1"
    const val archTesting = "2.1.0"
}

object Libs {
    const val kotlin = "org.jetbrains.kotlin:kotlin-stdlib:${Version.kotlin}"

    const val dagger = "com.google.dagger:dagger:${Version.dagger}"
    const val daggerCompiler = "com.google.dagger:dagger-compiler:${Version.dagger}"
    const val daggerAndroid = "com.google.dagger:dagger-android:${Version.dagger}"
    const val daggerAndroidSupport = "com.google.dagger:dagger-android-support:${Version.dagger}"
    const val daggerAndroidProcessor =
        "com.google.dagger:dagger-android-processor:${Version.dagger}"

    //retrofit
    const val retrofit = "com.squareup.retrofit2:retrofit:${Version.retrofit}"
    const val retrofitRxJava = "com.squareup.retrofit2:adapter-rxjava2:${Version.retrofit}"
    const val retrofitJsonConverter = "com.squareup.retrofit2:converter-gson:${Version.retrofit}"

    const val okHttpLoggingInterceptor =
        "com.squareup.okhttp3:logging-interceptor:${Version.okhttp}"
    const val okHttpClient = "com.squareup.okhttp3:okhttp:${Version.okhttp}"

    const val timber = "com.jakewharton.timber:timber:${Version.timber}"
    const val picasso = "com.squareup.picasso:picasso:${Version.picasso}"

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
        const val playServicesLocation =
            "com.google.android.gms:play-services-location:${Version.Android.playServicesLocation}"
        const val swipeRefreshLayout =
            "androidx.swiperefreshlayout:swiperefreshlayout:${Version.Android.swipeRefreshLayout}"
    }
}

object TestLibs {
    const val mockitoCore = "org.mockito:mockito-core:${Version.mockito}"
    const val mockitoInline = "org.mockito:mockito-inline:${Version.mockito}"
    const val mockitoKotlin = "com.nhaarman.mockitokotlin2:mockito-kotlin:${Version.mockitoKotlin}"
    const val coroutine = "org.jetbrains.kotlinx:kotlinx-coroutines-test:${Version.coroutines}"

    const val junit5 = "org.junit.jupiter:junit-jupiter-api:${Version.junit5}"
    const val junit5Engine = "org.junit.jupiter:junit-jupiter-engine:${Version.junit5}"
    const val junit5Params = "org.junit.jupiter:junit-jupiter-params:${Version.junit5}"

    object Android {
        const val arch = "androidx.arch.core:core-testing:${Version.archTesting}"
    }
}

object GradlePlugin {
    const val android = "com.android.tools.build:gradle:${Version.gradleAndroid}"
    const val kotlin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Version.kotlin}"
    const val junit5 = "de.mannodermaus.gradle.plugins:android-junit5:${Version.gradleJunit5}"
}