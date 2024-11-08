// dotenv plugin
plugins {
    kotlin("jvm") version libs.versions.kotlin

    alias(libs.plugins.plugin.publish)
}

assert(JavaVersion.current().isCompatibleWith(JavaVersion.VERSION_17))

group = "com.sinch.gradle"
version = "0.1.0"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(libs.kotest.runner.junit5)
    testImplementation(libs.kotest.assertions.core)
}

tasks.test {
    useJUnitPlatform()
    jvmArgs("--add-opens=java.base/java.lang=ALL-UNNAMED")
}

val gitRepoUrl = "https://github.com/sinch/gradle-nexus-helper"

@Suppress("UnstableApiUsage")
gradlePlugin {
    website = gitRepoUrl
    vcsUrl = "$gitRepoUrl.git"

    plugins {
        create("nexusHelperPlugin") {
            id = "$group.${rootProject.name}"
            displayName = "Gradle Nexus access helper"
            description =
                "A plugin simplifying the access to Nexus Maven repos."
            tags = listOf("nexus", "publishing", "repositories")
            implementationClass = "$id.NexusHelperPlugin"
        }
    }
}
