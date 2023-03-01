plugins {
    id("core-module")
    id("kotlin-parcelize")
}

android {
    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    implementation(project(":core-navigation"))
    // view
    implementation(Dependencies.ANDROID_MATERIAL)
    implementation(Dependencies.FRAGMENT_KTX)
    implementation(Dependencies.CONSTRAINT_LAYOUT)
    implementation(Dependencies.SWIPE_REFRESH)


    // Navigation Component
    implementation(Dependencies.NAV_FRAGMENT)
    implementation(Dependencies.NAV_UI)
}