plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.roborazzi)
    id("com.google.devtools.ksp")
    id("com.google.dagger.hilt.android")
}

android {
    namespace = "com.ym.learn.login"
    compileSdk = 36
    testOptions.unitTests.isIncludeAndroidResources = true
    defaultConfig {
        minSdk = 24
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
    }
}

dependencies {
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.material3)
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation(libs.androidx.lifecycle.runtime.compose)
    implementation("com.google.dagger:hilt-android:2.57.1")
    implementation(libs.androidx.junit.ktx)
    implementation(libs.ui.test.junit4)
    ksp("com.google.dagger:hilt-android-compiler:2.57.1")
    implementation("androidx.hilt:hilt-navigation-compose:1.0.0")

//    testImplementation("com.google.dagger:hilt-android-testing:2.57.1")
    testImplementation(libs.junit)
    testImplementation(libs.androidx.compose.ui.test)
    debugImplementation(libs.androidx.ui.test.manifest)
    testImplementation(libs.androidx.junit)
    testImplementation(libs.robolectric)//模拟android环境的本地化jvm测试
    testImplementation(libs.roborazzi)
//    androidTestImplementation("androidx.test:runner:1.5.2")
//    androidTestImplementation("androidx.test:rules:1.5.2")

    implementation(project(":data"))
    implementation(project(":ui"))
    testImplementation(project(":screenshot-testing"))
}