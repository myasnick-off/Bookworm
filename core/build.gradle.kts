plugins {
    id("core-module")
    id("kotlin-parcelize")
}

dependencies {
    implementation(project(":core-ui"))

    implementation(Dependencies.ANDROID_MATERIAL)

    // Navigation Component
    implementation(Dependencies.NAV_FRAGMENT)
    implementation(Dependencies.NAV_UI)
}