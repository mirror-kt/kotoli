plugins {
    kotlin("jvm") version "1.4.21-2"
    application
}

group = "dev.mirror-kt"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    jcenter()
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.4.0-M1")
    implementation("net.dv8tion:JDA:4.2.0_211")
}

application {
    mainClassName = "dev.mirror.kt.kotoli.MainKt"
}

tasks {
    distTar {
        archiveFileName.set("kotoli.tar")
    }
}
