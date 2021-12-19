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