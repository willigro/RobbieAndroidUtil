import Depends.Kotlin.implementKotlinForModule
import Depends.Views.implementLayouts
import Depends.Coroutines.implementCoroutines

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))

    // =========== Kotlin ==============
    implementKotlinForModule()

    // =========== AppCompat ==============
    implementation(Depends.AppCompat.getAppcompat())

    // =========== Views ==============
    implementLayouts()

    // =========== Coroutines ==============
    implementCoroutines()
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            groupId = Versions.GROUP_ID
            artifactId = Modules.widgets.clearModule()
            version = Versions.VERSION_NAME

            artifact("$buildDir/outputs/aar/${artifactId}-release.aar")        }
    }
}
