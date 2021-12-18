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

    // =========== Views ==============
    implementLayouts()

    // =========== Material ==============
    implementation(Depends.Material.getMaterial())

    // =========== Coroutines ==============
    implementCoroutines()
}

publishing {
    publications {
        create<MavenPublication>(Modules.typography.clearModule()) {
            artifact("$buildDir/outputs/aar/${artifactId}-release.aar")
        }
    }
}