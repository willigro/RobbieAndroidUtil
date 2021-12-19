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
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            groupId = Versions.GROUP_ID
            artifactId = Modules.textfield.clearModule()
            version = Versions.VERSION_NAME

            artifact("$buildDir/outputs/aar/${artifactId}-release.aar")        }
    }
}
