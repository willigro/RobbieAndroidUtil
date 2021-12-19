import Depends.Kotlin.implementKotlinForModule
import Depends.Views.implementLayouts
import Depends.Coroutines.implementCoroutines
import Depends.Module.implementModules

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))

    // =========== Modules ==============
    implementModules(Modules.core)

    // =========== Kotlin ==============
    implementKotlinForModule()

    // =========== AppCompat ==============
    implementation(Depends.AppCompat.getAppcompat())

    // =========== Material ==============
    implementation(Depends.Material.getMaterial())

    // =========== Views ==============
    implementLayouts()

    // =========== Coroutines ==============
    implementCoroutines()
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            groupId = Versions.GROUP_ID
            artifactId = Modules.buttons.clearModule()
            version = Versions.VERSION_NAME

            artifact("$buildDir/outputs/aar/${artifactId}-release.aar")
        }
    }
}
