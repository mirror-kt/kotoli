plugins {
    kotlin("jvm") version "1.4.30"
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
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.4.2")
    implementation("net.dv8tion:JDA:4.2.0_227")
}

application {
    mainClassName = "dev.mirror.kt.kotoli.MainKt"
}

tasks {
    distTar {
        archiveFileName.set("kotoli.tar")
    }
}
