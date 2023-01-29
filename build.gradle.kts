import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.8.0"
    id("dev.fastmc.maven-repo").version("1.0-SNAPSHOT")
}

group = "dev.fastmc"
version = "1.0-SNAPSHOT"

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(8))
    }
    withSourcesJar()
}

kotlin {
    val jvmArgs = mutableSetOf<String>()
    (rootProject.findProperty("kotlin.daemon.jvm.options") as? String)
        ?.split("\\s+".toRegex())?.toCollection(jvmArgs)
    System.getProperty("gradle.kotlin.daemon.jvm.options")
        ?.split("\\s+".toRegex())?.toCollection(jvmArgs)
    kotlinDaemonJvmArgs = jvmArgs.toList()
}

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    testImplementation("org.junit.jupiter:junit-jupiter-params:5.8.1")

    testImplementation("org.joml:joml:1.10.4")
    testImplementation("it.unimi.dsi:fastutil:8.5.11")

    compileOnly("org.joml:joml:1.10.4")
    compileOnly("it.unimi.dsi:fastutil:8.5.11")
}

tasks {
    test {
        javaToolchains {
            this@test.javaLauncher.set(launcherFor {
                languageVersion.set(JavaLanguageVersion.of(8))
            })
        }
        useJUnitPlatform()
    }

    withType<KotlinCompile> {
        kotlinOptions {
            freeCompilerArgs += listOf(
                "-Xlambdas=indy",
                "-Xjvm-default=all",
                "-Xbackend-threads=0"
            )
        }
    }
}

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            from(components["java"])
        }
    }
}