// Top-level build file where you can add configuration options common to all sub-projects/modules.
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.jlleitschuh.gradle.ktlint.reporter.ReporterType

buildscript {
    repositories {
        google()
        mavenCentral()
        maven { url = uri("https://plugins.gradle.org/m2/") } // Gradle Plugin Portal
        maven { url = uri("https://developer.huawei.com/repo/") }
    }
    dependencies {
        classpath(Deps.GradlePlugins.gradle)
        classpath(Deps.GradlePlugins.kotlin)
        classpath(Deps.GradlePlugins.crashlytics)
        classpath(Deps.googleServices)
        classpath(Deps.GradlePlugins.hilt)
        classpath(Deps.GradlePlugins.safeArgs)
        classpath(Deps.GradlePlugins.apollo)

        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.gradle files
    }
}

plugins {
    id("org.jlleitschuh.gradle.ktlint") version "10.1.0"
}

allprojects {
    repositories {
        google()
        mavenCentral()
        maven { url = uri("https://github.com/omadahealth/omada-nexus/raw/master/release") }
        maven { url = uri("https://oss.sonatype.org/content/repositories/snapshots") }
        maven { url = uri("https://jitpack.io") }
    }
}

subprojects {
    apply(plugin = "org.jlleitschuh.gradle.ktlint")

    configure<org.jlleitschuh.gradle.ktlint.KtlintExtension> {
        version.set("0.42.1")
        debug.set(true)
        verbose.set(true)
        android.set(true)
        outputToConsole.set(true)
        outputColorName.set("RED")
        ignoreFailures.set(true)
        enableExperimentalRules.set(false)
        reporters {
            reporter(ReporterType.PLAIN)
            reporter(ReporterType.CHECKSTYLE)
        }
        filter {
            exclude("**/generated/**")
            include("**/kotlin/**")
        }
    }

    // `ktlintCheck` runs when a build includes `check`, notably during presubmit. In these cases
    // we prefer `ktlintCheck` run as early as possible since it fails in seconds. This prevents a
    // build from running for several minutes doing other intensive tasks (resource processing, code
    // generation, compilation, etc) only to fail on a formatting nit.
    // Using `mustRunAfter` avoids creating a task dependency. The order is enforced only if
    // `ktlintCheck` is already scheduled to run, so we can still build and launch from the IDE
    // while the code is "dirty".
    tasks.whenTaskAdded {
        if (name == "preBuild") {
            mustRunAfter("ktlintCheck")
        }
    }

    tasks.withType<KotlinCompile>().configureEach {
        kotlinOptions {
            jvmTarget = "1.8"
            freeCompilerArgs = freeCompilerArgs + listOf(
                "-Xopt-in=kotlin.RequiresOptIn",
                "-Xopt-in=androidx.paging.ExperimentalPagingApi",
                "-Xopt-in=kotlinx.coroutines.ExperimentalCoroutinesApi",
                "-Xopt-in=kotlinx.coroutines.FlowPreview",
                "-Xopt-in=kotlin.ExperimentalStdlibApi",
            )
        }
    }

}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}

