// Kishku7 hard fork of PolyhedralDev/Seismic (v2.5.7 base).
// Re-namespaced to com.kishku7.TerraK7.seismic. Self-owned; builds to mavenLocal.
// Durability fix: TrigonometryUtils LUT self-test uses java.base SplittableRandom
// instead of RandomGenerator.getDefault(), so it no longer needs the jdk.random
// module to be accessible (which Paper's plugin classloader blocks). See README.

plugins {
    `java-library`
    `maven-publish`
}

group = "com.kishku7.TerraK7"
version = "2.5.7-kish.1"

repositories {
    mavenCentral()
}

dependencies {
    api("org.jetbrains:annotations:26.0.2-1")
    implementation("org.slf4j:slf4j-api:2.0.17")
}

java {
    toolchain { languageVersion = JavaLanguageVersion.of(21) }
    withSourcesJar()
}

tasks.withType<JavaCompile>().configureEach {
    options.encoding = "UTF-8"
}

// Emit META-INF/CLASS_MANIFEST_seismic listing every compiled class as a dotted name.
// Paralithic's NativeMath reads this to discover the seismic math classes to register.
tasks.register("dumpClasses") {
    dependsOn("compileJava")
    val outputDir = layout.buildDirectory.dir("classes/java/main").get().asFile
    val outputFile = layout.buildDirectory.file("tmp/META-INF/CLASS_MANIFEST_seismic").get().asFile
    inputs.dir(outputDir)
    outputs.file(outputFile)
    doLast {
        outputFile.parentFile.mkdirs()
        outputFile.printWriter().use { writer ->
            fileTree(outputDir).matching { include("**/*.class") }.forEach {
                writer.println(
                    it.relativeTo(outputDir).path
                        .replace('/', '.').replace('\\', '.')
                        .removeSuffix(".class")
                )
            }
        }
    }
}

tasks.withType<Jar>().configureEach {
    dependsOn("dumpClasses")
    from(layout.buildDirectory.dir("tmp/META-INF")) {
        into("META-INF")
    }
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            from(components["java"])
        }
    }
}
