// Plugin-only fork of Terra (PolyhedralDev/Terra), Bukkit/Paper platform, MC 1.21.11.
// preRelease(false): no git hash in version (vendored tree is not a git repo here).
preRelease(false)

versionProjects(":shared_common:api", version("1.0"))
versionProjects(":shared_common:implementation", version("1.0"))
versionProjects(":Fabric", version("1.0"))
versionProjects(":Plugin", version("1.0"))


allprojects {
    group = "com.dfsek.terra"

    configureCompilation()
    configureDependencies()
    configurePublishing()

    tasks.withType<JavaCompile>().configureEach {
        options.isFork = true
        options.isIncremental = true
        options.release.set(21)
    }

    tasks.withType<Test>().configureEach {
        useJUnitPlatform()

        maxHeapSize = "2G"
        ignoreFailures = false
        failFast = true
        maxParallelForks = (Runtime.getRuntime().availableProcessors() - 1).takeIf { it > 0 } ?: 1

        reports.html.required.set(false)
        reports.junitXml.required.set(false)
    }

    tasks.withType<Copy>().configureEach {
        duplicatesStrategy = DuplicatesStrategy.EXCLUDE
    }

    tasks.withType<Jar>().configureEach {
        duplicatesStrategy = DuplicatesStrategy.EXCLUDE
    }
}

afterEvaluate {
    project(":Fabric").configureDistribution()
    project(":Plugin").configureDistribution()
    project(":Plugin:common").configureDistribution()
    forSubProjects(":addons") {
        apply(plugin = "com.gradleup.shadow")

        tasks.named("build") {
            finalizedBy(tasks.named("shadowJar"))
        }

        dependencies {
            "compileOnly"(project(":shared_common:api"))
            "testImplementation"(project(":shared_common:api"))
        }
    }
}

