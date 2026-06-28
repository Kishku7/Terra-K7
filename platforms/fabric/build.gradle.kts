import java.util.*

// Terra-K7 Fabric platform -- plain fabric-loom, mojmap-native, NO Architectury (Dave 2026-06-27).
// The old Architectury "common" modules (mixin-common + mixin-lifecycle) are folded in here as shared
// srcDirs (mod-26 shared_minecraft style); their mixins + access-widener ship from this one loom project.
plugins {
    id("fabric-loom") version "1.17.12"
}

sourceSets {
    named("main") {
        java {
            srcDir("../mixin-common/src/main/java")
            srcDir("../mixin-lifecycle/src/main/java")
        }
        resources {
            srcDir("../mixin-common/src/main/resources")
            srcDir("../mixin-lifecycle/src/main/resources")
        }
    }
}

dependencies {
    shadedApi(project(":common:implementation:base"))

    minecraft("com.mojang:minecraft:${Versions.Mod.minecraft}")
    mappings(loom.officialMojangMappings())

    modImplementation("net.fabricmc:fabric-loader:${Versions.Mod.fabricLoader}")
    modRuntimeOnly("net.fabricmc.fabric-api", "fabric-api", Versions.Fabric.fabricAPI)

    modImplementation("org.incendo", "cloud-fabric", Versions.Fabric.cloud) {
        exclude("me.lucko", "fabric-permissions-api")
    }
    include("org.incendo", "cloud-fabric", Versions.Fabric.cloud)

    // Mixin AP + MixinExtras (previously supplied per Architectury-common module).
    compileOnly("net.fabricmc:sponge-mixin:${Versions.Mod.mixin}")
    annotationProcessor("net.fabricmc:sponge-mixin:${Versions.Mod.mixin}")
    compileOnly("io.github.llamalad7:mixinextras-common:${Versions.Mod.mixinExtras}")
}

loom {
    accessWidenerPath.set(file("../mixin-common/src/main/resources/terra.accesswidener"))

    mixin {
        defaultRefmapName.set("terra.refmap.json")
        useLegacyMixinAp.set(true)
    }
}

addonDir(project.file("./run/config/Terra/addons"), tasks.named("configureLaunch").get())

tasks {
    remapJar {
        dependsOn("installAddons")
        inputFile.set(shadowJar.get().archiveFile)
        archiveFileName.set("${rootProject.name.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }}-fabric-${project.version}.jar")
    }
}

tasks.withType<JavaCompile>().configureEach {
    options.compilerArgs.add("-Xlint:none")
}
