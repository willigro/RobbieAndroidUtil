import Depends.AndroidTest.implementAndroidTest
import Depends.AndroidTest.implementEspressoTest
import Depends.Kotlin.implementKotlinForModule
import Depends.Module.implementAllModules
import Depends.Test.implementTest
import Depends.Views.implementLayouts
import Depends.Legacy.implementLegacySupport
import Depends.MultiDex.implementMultidex
import Depends.Coroutines.implementCoroutines

plugins {
    id("kotlin-android")
}
dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    implementation("com.android.support:appcompat-v7:28.0.0")
    implementation("com.android.support.constraint:constraint-layout:2.0.4")

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
