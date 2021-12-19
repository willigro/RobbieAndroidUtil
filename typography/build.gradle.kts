import Depends.Kotlin.implementKotlinForModule
import Depends.Views.implementLayouts
import Depends.Module.implementModules

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))

    // =========== Modules ==============
    implementModules(Modules.core)

    // =========== Kotlin ==============
    implementKotlinForModule()

    // =========== Views ==============
    implementLayouts()

    // =========== Material ==============
    implementation(Depends.Material.getMaterial())
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            groupId = Versions.GROUP_ID
            artifactId = Modules.typography.clearModule()
            version = Versions.VERSION_NAME

            artifact("$buildDir/outputs/aar/${artifactId}-release.aar")
        }
    }
}
