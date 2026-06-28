version = version("1.0.1")

dependencies {
    compileOnlyApi(project(":addons:manifest-addon-loader"))
    compileOnlyApi(project(":addons:chunk-generator-noise-3d"))
    compileOnlyApi(project(":addons:structure-terrascript-loader"))
}