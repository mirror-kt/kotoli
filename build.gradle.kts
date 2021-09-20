import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.5.31"
    application
}

group = "dev.mirror-kt"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven("https://oss.sonatype.org/content/repositories/snapshots")
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.4.2")
    implementation("dev.kord:kord-core:0.7.0-SNAPSHOT")
}

application {
    mainClassName = "dev.mirror.kt.kotoli.MainKt"
}

tasks {
    distTar {
        archiveFileName.set("kotoli.tar")
    }

    withType<KotlinCompile> {
        kotlinOptions {
            jvmTarget = "11"
        }
    }
}
