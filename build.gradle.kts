// Top-level build file where you can add configuration options common to all sub-projects/modules.
import com.android.build.gradle.BaseExtension
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

buildscript {
    repositories {
        mavenLocal()
        google()
        jcenter()
        mavenCentral()
    }
    dependencies {
        classpath(Depends.Gradle.getGradlePlugin())
        classpath(Depends.Kotlin.getKotlin())
        classpath(Depends.Kotlin.getKotlinExtensions())

        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.gradle.kts files
    }
}

//ext.publish = { artifactId ->
//    project.afterEvaluate {
//        publishing {
//            publications {
//                library(MavenPublication) {
//                    setGroupId project.groupId
//                            setArtifactId artifactId
//                            version android.defaultConfig.versionName
//
//                            artifact bundleReleaseAar
//
//                            println "publishing: $groupId"
//                    println "publishing: " + artifactId
//                    println "publishing: " + bundleReleaseAar
//                }
//            }
//        }
//    }
//}

allprojects {
    repositories {
        google()
        jcenter()
        mavenLocal()
    }
    androidCompile()
}

fun Project.androidCompile() {
    // We want to implement this code only specific modules
    if (Modules.modules.map {
            val a = it.split(":")
            val b = a[a.size - 1]
//            println(a)
//            println(b)
            b
        }.contains(name).not()) return

    val isApplication = if (":$name" == Modules.app) {
        apply(plugin = "com.android.application")
        true
    } else {
        apply(plugin = "com.android.library")
        apply(plugin = "maven-publish")
        false
    }

    apply(plugin = "kotlin-android")
    apply(plugin = "kotlin-android-extensions")
    apply(plugin = "kotlin-kapt")

    configure<BaseExtension> {
        compileSdkVersion(Versions.COMPILE_SKD_VERSION)
        buildToolsVersion(Versions.BUILD_TOOLS_VERSION)

        defaultConfig {
            if (isApplication)
                applicationId(Versions.APPLICATION_ID)

            minSdkVersion(Versions.MIN_SDK_VERSION)
            targetSdkVersion(Versions.TARGET_SDK_VERION)
            versionCode(Versions.VERSION_CODE)
            versionName(Versions.VERSION_NAME)

            testInstrumentationRunner(Depends.Test.ANDROID_JUNIT_RUNNER)
        }

        buildTypes {
            getByName("release") {
                minifyEnabled(false)
                proguardFiles(
                    getDefaultProguardFile("proguard-android-optimize.txt"),
                    "proguard-rules.pro"
                )
            }
        }

        compileOptions {
            sourceCompatibility = JavaVersion.VERSION_1_8
            targetCompatibility = JavaVersion.VERSION_1_8
        }

        tasks.withType<KotlinCompile> {
            kotlinOptions.jvmTarget = JavaVersion.VERSION_1_8.toString()
        }

        sourceSets {
            getByName("androidTest").java.srcDirs("src/androidTest/java")
        }

        packagingOptions {
            exclude("META-INF/DEPENDENCIES")
            exclude("META-INF/LICENSE")
            exclude("META-INF/LICENSE.txt")
            exclude("META-INF/license.txt")
            exclude("META-INF/NOTICE")
            exclude("META-INF/NOTICE.txt")
            exclude("META-INF/notice.txt")
            exclude("META-INF/ASL2.0")
            exclude("META-INF/*")
            exclude("META-INF/*.kotlin_module")
        }
    }
}