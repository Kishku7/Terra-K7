version = version("1.0.0")

dependencies {
    compileOnlyApi(project(":addons:manifest-addon-loader"))
    compileOnlyApi(project(":addons:config-noise-function"))
    compileOnlyApi(project(":addons:structure-terrascript-loader"))
}
