version = version("1.0.0")

dependencies {
    implementation("com.dfsek.tectonic:yaml:${Versions.Libraries.tectonic}")
    compileOnlyApi(project(":addons:manifest-addon-loader"))
}
