plugins {
    id("core-module")
}

dependencies {
    // Dagger 2
    implementation(Dependencies.DAGGER)
    kapt(Dependencies.DAGGER_COMPILER)
}