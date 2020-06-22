
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.6.20"
    application
}

group = "org.theagilemonkeys"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven("https://repo.softmotions.com/repository/softmotions-public")
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation("io.horizen:sidechains-sdk:0.3.0")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.13.2")
}

application {
    mainClass.set("com.theagilemonkeys.notes.NotesAppKt")
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

tasks.withType<Jar> {
    exclude("META-INF/*.RSA", "META-INF/*.SF", "META-INF/*.DSA")
}

task("copyDependencies", Copy::class) {
    from(configurations.default).into("$buildDir/libs")
    dependsOn("build")
}