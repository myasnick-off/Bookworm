plugins {
    id("core-module")
    id("kotlin-parcelize")
}

dependencies {
    implementation(project(":core-ui"))
    implementation(project(":core-di"))

    // view
    implementation(Dependencies.ANDROID_MATERIAL)

    // Coroutines
    implementation(Dependencies.COROUTINES)

    // Dagger 2
    implementation(Dependencies.DAGGER)
    kapt(Dependencies.DAGGER_COMPILER)

    // Navigation Component
    implementation(Dependencies.NAV_FRAGMENT)
    implementation(Dependencies.NAV_UI)
}