version = version("1.2.0")

dependencies {
    compileOnlyApi(project(":addons:manifest-addon-loader"))
    api("com.kishku7.TerraK7", "paralithic", Versions.Libraries.paralithic)
}
