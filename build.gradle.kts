import org.jetbrains.changelog.exceptions.MissingVersionException
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.jetbrains.intellij.tasks.PrepareSandboxTask

allprojects {
    repositories {
        mavenCentral()
    }
}

plugins {
    alias(libs.plugins.changelog)
    alias(libs.plugins.gradleIntelliJ)
    alias(libs.plugins.gradleJvmWrapper)
    alias(libs.plugins.kotlinJvm)
    id("java")
}

val pluginVersion: String by project
val riderSdkVersion: String by project
val untilBuildVersion: String by project
val buildConfiguration: String by project
val dotNetPluginId: String by project

val dotNetSrcDir = File(projectDir, "src/dotnet")

version = pluginVersion

val riderSdkPath by lazy {
    val path = tasks.setupDependencies.get().idea.get().classes.resolve("lib/DotNetSdkForRdPlugins")
    if (!path.isDirectory) error("$path does not exist or not a directory")

    println("Rider SDK path: $path")
    return@lazy path
}

sourceSets {
    main {
        kotlin.srcDir("src/rider/generated/kotlin")
        kotlin.srcDir("src/rider/main/kotlin")
        resources.srcDir("src/rider/main/resources")
    }
}

tasks {

    val generateDotNetSdkProperties by registering {
        val dotNetSdkGeneratedPropsFile = File(projectDir, "build/DotNetSdkPath.Generated.props")
        doLast {
            dotNetSdkGeneratedPropsFile.writeTextIfChanged("""<Project>
  <PropertyGroup>
    <DotNetSdkPath>$riderSdkPath</DotNetSdkPath>
  </PropertyGroup>
</Project>
""")
        }
    }

    val generateNuGetConfig by registering {
        val nuGetConfigFile = File(dotNetSrcDir, "nuget.config")
        doLast {
            nuGetConfigFile.writeTextIfChanged("""
            <?xml version="1.0" encoding="utf-8"?>
            <!-- Auto-generated from 'generateNuGetConfig' task of old.build_gradle.kts -->
            <!-- Run `gradlew :prepare` to regenerate -->
            <configuration>
                <packageSources>
                    <add key="rider-sdk" value="$riderSdkPath" />
                </packageSources>
            </configuration>
            """.trimIndent())
        }
    }

    val rdGen = ":protocol:rdgen"

    register("prepare") {
        dependsOn(rdGen, generateDotNetSdkProperties, generateNuGetConfig)
    }

    val compileDotNet by registering {
        dependsOn(rdGen, generateDotNetSdkProperties, generateNuGetConfig)
        doLast {
            exec {
                executable("dotnet")
                args("build", "-c", buildConfiguration)
            }
        }
    }

    withType<KotlinCompile> {
        dependsOn(rdGen)
        kotlinOptions {
            jvmTarget = "17"
        }
    }

    buildPlugin {
        dependsOn(compileDotNet)
    }

    patchPluginXml {
        untilBuild.set(untilBuildVersion)
        val latestChangelog = try {
            changelog.getUnreleased()
        } catch (_: MissingVersionException) {
            changelog.getLatest()
        }
        changeNotes.set(provider {
            changelog.renderItem(
                latestChangelog
                    .withHeader(false)
                    .withEmptySections(false),
                org.jetbrains.changelog.Changelog.OutputType.HTML
            )
        })
    }

    withType<PrepareSandboxTask> {
        dependsOn(compileDotNet)

        val outputFolder = file("$dotNetSrcDir/$dotNetPluginId/bin/${dotNetPluginId}/$buildConfiguration")
        val pluginFiles = listOf(
            "$outputFolder/${dotNetPluginId}.dll",
            "$outputFolder/${dotNetPluginId}.pdb"
        )

        from(pluginFiles) {
            into("${rootProject.name}/dotnet")
        }

        doLast {
            for (f in pluginFiles) {
                val file = file(f)
                if (!file.exists()) throw RuntimeException("File \"$file\" does not exist.")
            }
        }
    }

    runIde {
        jvmArgs("-Xmx1500m")
    }
}

intellij {
    type.set("RD")
    version.set(riderSdkVersion)
    downloadSources.set(false)
}

val riderModel by configurations.creating {
    isCanBeConsumed = true
    isCanBeResolved = false
}

artifacts {
    add(riderModel.name, provider {
        val sdkRoot = tasks.setupDependencies.get().idea.get().classes
        sdkRoot.resolve("lib/rd/rider-model.jar").also {
            check(it.isFile) {
                "rider-model.jar is not found at $riderModel"
            }
        }
    }) {
        builtBy(tasks.setupDependencies)
    }
}

fun File.writeTextIfChanged(content: String) {
    val bytes = content.toByteArray()

    if (!exists() || !readBytes().contentEquals(bytes)) {
        println("Writing $path")
        parentFile.mkdirs()
        writeBytes(bytes)
    }
}
