dependencies {
    api(project(":shared_common:api"))
    api(project(":shared_common:implementation:bootstrap-addon-loader"))

    testImplementation("org.slf4j", "slf4j-api", Versions.Libraries.slf4j)

    implementation("commons-io", "commons-io", Versions.Libraries.Internal.apacheIO)

    implementation("org.apache.commons", "commons-text", Versions.Libraries.Internal.apacheText)
    implementation("com.dfsek.tectonic", "yaml", Versions.Libraries.tectonic)



    implementation("com.dfsek", "paralithic", Versions.Libraries.paralithic)
}
