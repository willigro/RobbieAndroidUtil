import org.gradle.api.artifacts.dsl.DependencyHandler
import org.gradle.kotlin.dsl.project

const val IMPLEMENTATION = "implementation"
const val TEST_IMPLEMENTATION = "testImplementation"
const val ANDROID_TEST_IMPLEMENTATION = "androidTestImplementation"
const val DEBUG_IMPLEMENTATION = "androidTestImplementation"
const val KAPT = "kapt"
const val ANNOTATION_PROCESSOR = "annotationProcessor"

fun DependencyHandler.implement(url: String) {
    add(IMPLEMENTATION, url)
}

fun DependencyHandler.testImplement(url: String) {
    add(TEST_IMPLEMENTATION, url)
}

fun DependencyHandler.androidTestImplement(url: String) {
    add(ANDROID_TEST_IMPLEMENTATION, url)
}

fun DependencyHandler.debugImplement(url: String) {
    add(DEBUG_IMPLEMENTATION, url)
}

fun DependencyHandler.kapt(url: String) {
    add(KAPT, url)
}

fun DependencyHandler.annotationProcessor(url: String) {
    add(ANNOTATION_PROCESSOR, url)
}

object Depends {

    object Module {
        fun DependencyHandler.implementAllModules(vararg less: String) {
            val result = Modules.modules - less
            result.forEach { add(IMPLEMENTATION, project(it)) }
        }

        fun DependencyHandler.implementModules(vararg modules: String) {
            modules.forEach { add(IMPLEMENTATION, project(it)) }
        }

        fun DependencyHandler.androidTestImplementationModules(vararg modules: String) {
            modules.forEach { add(ANDROID_TEST_IMPLEMENTATION, project(it)) }
        }

        fun DependencyHandler.testImplementationModules(vararg modules: String) {
            modules.forEach { add(TEST_IMPLEMENTATION, project(it)) }
        }
    }

    object Gradle {
        fun getGradlePlugin() = "com.android.tools.build:gradle:${Versions.GRADLE_TOOL_BUILD}"
    }

    object Kotlin {
        fun getKotlin() = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.KOTLIN_VERSION}"
        fun getKotlinExtensions() =
            "org.jetbrains.kotlin:kotlin-android-extensions:${Versions.KOTLIN_VERSION}"

        fun DependencyHandler.implementKotlinForModule() {
            implement("org.jetbrains.kotlin:kotlin-stdlib:${Versions.KOTLIN_VERSION}")
            implement("androidx.core:core-ktx:${Versions.KOTLIN_KTX}")
        }
    }

    object AppCompat {
        fun getAppcompat() =
            "androidx.appcompat:appcompat:${Versions.APPCOMPAT}"
    }

    object Material {
        fun getMaterial() =
            "com.google.android.material:material:${Versions.MATERIAL}"
    }

    object Views {
        fun DependencyHandler.implementLayouts() {
            add(
                IMPLEMENTATION,
                "androidx.constraintlayout:constraintlayout:${Versions.CONSTRAINT_LAYOUT}"
            )
        }
    }

    object IdlingResource {
        fun DependencyHandler.implementIdling() {
            implement("androidx.test.espresso:espresso-idling-resource:${Versions.ESPRESSO}")
        }
    }

    object Test {
        const val ANDROID_JUNIT_RUNNER = "androidx.test.runner.AndroidJUnitRunner"

        const val JUNIT = "junit:junit:${Versions.JUNIT}"
        const val FRAGMENT_TESTING = "androidx.fragment:fragment-testing:${Versions.FRAGMENT}"
        const val TEST_CORE = "androidx.test:core:${Versions.ANDROID_X_TEST_CORE}"
        const val TEST_CORE_KTX = "androidx.test:core-ktx:${Versions.ANDROID_X_TEST_CORE}"
        const val TEXT_EXT_KTX_JUNIT = "androidx.test.ext:junit-ktx:${Versions.JUNIT_EXT}"
        const val TEXT_EXT_JUNIT = "androidx.test.ext:junit:${Versions.JUNIT_EXT}"
        const val COROUTINES_TEST =
            "org.jetbrains.kotlinx:kotlinx-coroutines-test:${Versions.COROUTINES}"
        const val ARCH_CORE_TEST = "androidx.arch.core:core-testing:${Versions.ARCH_TESTING}"
        const val ROBOLETRIC = "org.robolectric:robolectric:${Versions.ROBOLETRIC}"
        const val HAMCREST = "org.hamcrest:hamcrest-all:${Versions.HAMCREST}"
        const val MOCKK = "io.mockk:mockk:${Versions.MOCKK}"
        const val MOCKK_AGENT = "io.mockk:mockk-agent-jvm:${Versions.MOCKK}"

        fun DependencyHandler.implementTest() {
            testImplement(JUNIT)

            debugImplement(FRAGMENT_TESTING)

            implement(TEST_CORE)
            testImplement(TEST_CORE_KTX)
            testImplement(TEXT_EXT_KTX_JUNIT)
            testImplement(TEXT_EXT_JUNIT)
            testImplement(COROUTINES_TEST)
            testImplement(ARCH_CORE_TEST)
            testImplement(ROBOLETRIC)
            testImplement(HAMCREST)

            testImplement(MOCKK)
            testImplement(MOCKK_AGENT)
        }

        fun DependencyHandler.implementAllInDebugTest() {
            debugImplement(JUNIT)
            debugImplement(FRAGMENT_TESTING)
            debugImplement(TEST_CORE)
            debugImplement(TEST_CORE_KTX)
            debugImplement(TEXT_EXT_KTX_JUNIT)
            debugImplement(TEXT_EXT_JUNIT)
            debugImplement(COROUTINES_TEST)
            debugImplement(ARCH_CORE_TEST)
            debugImplement(ROBOLETRIC)
            debugImplement(HAMCREST)
            debugImplement(MOCKK)
            debugImplement(MOCKK_AGENT)
        }
    }

    object AndroidTest {

        const val ESPRESSO_IDLING =
            "androidx.test.espresso:espresso-idling-resource:${Versions.ESPRESSO}"
        const val FRAGMENT_TESTING = "androidx.fragment:fragment-testing:${Versions.FRAGMENT}"

        const val EXPRESSO_CORE = "androidx.test.espresso:espresso-core:${Versions.ESPRESSO}"
        const val EXPRESSO_CONTRIB = "androidx.test.espresso:espresso-contrib:${Versions.ESPRESSO}"

        const val COROUTINES_TEST =
            "org.jetbrains.kotlinx:kotlinx-coroutines-test:${Versions.COROUTINES}"

        fun DependencyHandler.implementAndroidTest() {
            androidTestImplement("junit:junit:${Versions.JUNIT}")
            androidTestImplement("androidx.test.ext:junit:${Versions.JUNIT_EXT}")
            androidTestImplement("androidx.arch.core:core-testing:${Versions.ARCH_TESTING}")
            androidTestImplement(COROUTINES_TEST)
        }

        fun DependencyHandler.implementEspressoTest() {
            androidTestImplement(EXPRESSO_CORE)
            androidTestImplement(EXPRESSO_CONTRIB)
        }

        fun DependencyHandler.implementEspressoTestAllInDebug() {
            implement(EXPRESSO_CORE)
            implement(EXPRESSO_CONTRIB)
        }
    }

    object ViewModel {
        fun DependencyHandler.implementViewModel() {
            implement("androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.VIEWMODEL}")
        }
    }

    object Coroutines {
        fun DependencyHandler.implementCoroutines() {
            implement("org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.COROUTINES}")
            implement("org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.COROUTINES}")
        }
    }

    object Legacy {
        fun DependencyHandler.implementLegacySupport() {
            implement("androidx.legacy:legacy-support-v4:1.0.0")
        }
    }

    object MultiDex {
        fun DependencyHandler.implementMultidex() {
            implement("androidx.multidex:multidex:2.0.1")
        }
    }
}