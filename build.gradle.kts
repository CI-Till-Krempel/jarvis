plugins {
    kotlin("jvm") version "1.9.0"
    id("org.jetbrains.compose") version "1.5.0"
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(compose.desktop.currentOs)
}
