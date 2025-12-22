plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.roborazzi)
    id("com.google.devtools.ksp")
    id("com.google.dagger.hilt.android")
}

android {
    namespace = "com.ym.learn.player"
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
    ksp("com.google.dagger:hilt-android-compiler:2.57.1")
    implementation("androidx.hilt:hilt-navigation-compose:1.0.0")

    // Media3 核心库
    implementation("androidx.media3:media3-exoplayer:1.3.1")
    // 用于在 Compose 中集成 Media3 的 UI 控件
    implementation("androidx.media3:media3-ui:1.3.1")
    // Media3 与 Compose 集成
    implementation("androidx.media3:media3-session:1.3.1")
    // 可选：用于控制音频焦点等
    implementation("androidx.media3:media3-common:1.3.1")

    // ... 您其他的依赖项
    implementation("androidx.compose.material:material-icons-extended:1.7.4")

    // 可选：如果使用 pillarbox-ui 这个 Compose 封装库
    // implementation("ch.srgssr.pillarbox:pillarbox-ui:<pillarbox_version>")

//    testImplementation("com.google.dagger:hilt-android-testing:2.57.1")
    testImplementation(libs.androidx.compose.ui.test)
    debugImplementation(libs.androidx.ui.test.manifest)
    testImplementation(libs.androidx.junit)
    testImplementation(libs.robolectric)
    testImplementation(libs.roborazzi)
//    androidTestImplementation("androidx.test:runner:1.5.2")
//    androidTestImplementation("androidx.test:rules:1.5.2")

    implementation(project(":data"))
    implementation(project(":ui"))
    testImplementation(project(":screenshot-testing"))
}