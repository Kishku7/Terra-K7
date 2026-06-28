// Kishku7 hard fork of PolyhedralDev/Paralithic (v2.0.1 base).
// Re-namespaced to com.kishku7.TerraK7.paralithic; depends on our seismic fork.
// Self-owned; builds to mavenLocal.

plugins {
    `java-library`
    `maven-publish`
}

group = "com.kishku7.TerraK7"
version = "2.0.1-kish.1"

repositories {
    mavenLocal()
    mavenCentral()
}

dependencies {
    implementation("org.jetbrains:annotations:26.0.1")
    implementation("org.slf4j:slf4j-api:2.0.16")
    implementation("com.kishku7.TerraK7:seismic:2.5.7-kish.1")

    api("org.ow2.asm:asm:9.7.1")
}

java {
    toolchain { languageVersion = JavaLanguageVersion.of(21) }
    withSourcesJar()
}

tasks.withType<JavaCompile>().configureEach {
    options.encoding = "UTF-8"
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            from(components["java"])
        }
    }
}
