version = version("1.0.0")

dependencies {
    compileOnlyApi(project(":addons:manifest-addon-loader"))
    compileOnlyApi(project(":addons:biome-provider-pipeline"))
    compileOnlyApi(project(":addons:library-image"))
}
