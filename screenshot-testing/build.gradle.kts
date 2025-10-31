plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.roborazzi)
}

android {
    namespace = "com.ym.learn.screenshottesting"
    compileSdk = 36
    namespace = "com.google.samples.apps.nowinandroid.core.screenshottesting"
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
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


    implementation(libs.androidx.compose.ui.test)
    implementation(libs.androidx.ui.test.manifest)
    implementation(libs.androidx.junit)
    implementation(libs.robolectric)
    implementation(libs.roborazzi)
    implementation(libs.roborazzi.accessibility.check)

    implementation(project(":ui"))

}