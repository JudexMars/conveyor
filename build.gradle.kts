plugins {
    kotlin("jvm") version "1.9.22"
    id("com.gradleup.shadow") version "8.3.5"
}

group = "org.example"
version = "0.0.1-SNAPSHOT"
description = "conveyor"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("io.ktor:ktor-server-core:2.3.12")
    implementation("io.ktor:ktor-server-netty:2.3.12")
    implementation("ch.qos.logback:logback-classic:1.4.14")

    testImplementation("io.ktor:ktor-server-tests:2.3.12")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.withType<Test> {
    useJUnitPlatform()
}

tasks.shadowJar {
    archiveBaseName.set("conveyor")
    archiveClassifier.set("all")
    archiveVersion.set("")
    manifest {
        attributes["Main-Class"] = "org.example.conveyor.ConveyorApplicationKt"
    }
    // Мержим все service files (важно для Netty и SLF4J)
    mergeServiceFiles()
}

// Делаем shadowJar основной задачей сборки JAR
tasks.jar {
    enabled = false
}

tasks.build {
    dependsOn(tasks.shadowJar)
}
