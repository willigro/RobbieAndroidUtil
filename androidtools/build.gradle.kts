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
        create<MavenPublication>(Modules.androidtools.clearModule()) {
//            from(components["java"])
            artifact("$buildDir/outputs/aar/${artifactId}-release.aar")

            pom {
                withXml {
                    val dependenciesNode = asNode().appendNode("dependencies")
                    configurations.getByName("implementation") {
                        dependencies.forEach {
                            val dependencyNode = dependenciesNode.appendNode("dependency")
                            dependencyNode.appendNode("groupId", it.group)
                            dependencyNode.appendNode("artifactId", it.name)
                            dependencyNode.appendNode("version", it.version)
                        }
                    }
                }
            }
        }
    }
}