rootProject.name = "Terra"


fun includeImmediateChildren(dir: File, type: String) {
    dir.walkTopDown().maxDepth(1).forEach {
        if (!it.isDirectory || !File(it, "build.gradle.kts").exists()) return@forEach
        val addonDir = it.relativeTo(file(".")).path.replace("/", ":").replace("\\", ":")
        logger.info("Including $type directory \"$addonDir\" as subproject.")
        include(addonDir)
    }
}

includeImmediateChildren(file("shared_common/api"), "API")

includeImmediateChildren(file("shared_common/implementation"), "implementation")

includeImmediateChildren(file("addons"), "addon")

// Bukkit platform (plugin) + the mod platforms (Terra-K7 Fabric mod port, option C).
// Mod modules re-enabled 2026-06-27 for the mod port: fabric + mixin-common + mixin-lifecycle.
// Still dropped vs upstream: forge, quilt, sponge, minestom, allay, cli, merged.
include(":Plugin")
include(":Plugin:common")
includeImmediateChildren(file("Plugin/nms"), "Bukkit NMS")
include(":Fabric")
pluginManagement {
    repositories {
        gradlePluginPortal()
        maven("https://maven.solo-studios.ca/releases") {
            name = "Solo Studios"
        }
        maven("https://maven.solo-studios.ca/snapshots") {
            name = "Solo Studios"
        }
        maven("https://maven.fabricmc.net") {
            name = "Fabric Maven"
        }
        maven("https://maven.architectury.dev/") {
            name = "Architectury Maven"
        }
        maven("https://files.minecraftforge.net/maven/") {
            name = "Forge Maven"
        }
        maven("https://maven.quiltmc.org/repository/release/") {
            name = "Quilt"
        }
    }
}

// settings.gradle.kts
val isCiServer = System.getenv().containsKey("CI")
// Cache build artifacts, so expensive operations do not need to be re-computed
buildCache {
    local {
        isEnabled = !isCiServer
    }
}
