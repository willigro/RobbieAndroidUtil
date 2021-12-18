import Depends.AndroidTest.implementAndroidTest
import Depends.AndroidTest.implementEspressoTest
import Depends.Kotlin.implementKotlinForModule
import Depends.Module.implementAllModules
import Depends.Test.implementTest
import Depends.Views.implementLayouts
import Depends.Legacy.implementLegacySupport
import Depends.MultiDex.implementMultidex
import Depends.Coroutines.implementCoroutines

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))

    // =========== Modules ==============
    implementAllModules(Modules.app)

    // =========== Kotlin ==============
    implementKotlinForModule()

    // =========== AppCompat ==============
    implementation(Depends.AppCompat.getAppcompat())

    // =========== Views ==============
    implementLayouts()

    // =========== Coroutines ==============
    implementCoroutines()

    // =========== Legacy Support ==============
    implementLegacySupport()

    // =========== Material ==============
    implementKotlinForModule()

    // =========== Multidex ==============
    implementMultidex()

    // =========== Test ==============
    implementTest()
    implementEspressoTest()
    implementAndroidTest()
}
