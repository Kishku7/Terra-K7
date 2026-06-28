// Plugin-only fork of Terra (PolyhedralDev/Terra), Bukkit/Paper platform, MC 1.21.11.
// preRelease(false): no git hash in version (vendored tree is not a git repo here).
preRelease(false)

versionProjects(":common:api", version("1.0"))
versionProjects(":common:implementation", version("1.0"))
versionProjects(":platforms", version("1.0"))


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
    forImmediateSubProjects(":platforms") {
        configureDistribution()
    }
    project(":platforms:bukkit:common").configureDistribution()
    forSubProjects(":common:addons") {
        apply(plugin = "com.gradleup.shadow")

        tasks.named("build") {
            finalizedBy(tasks.named("shadowJar"))
        }

        dependencies {
            "compileOnly"(project(":common:api"))
            "testImplementation"(project(":common:api"))
        }
    }
}

