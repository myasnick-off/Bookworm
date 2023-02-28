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
    // view
    implementation(Dependencies.ANDROID_MATERIAL)
    implementation(Dependencies.FRAGMENT_KTX)
    implementation(Dependencies.CONSTRAINT_LAYOUT)
}