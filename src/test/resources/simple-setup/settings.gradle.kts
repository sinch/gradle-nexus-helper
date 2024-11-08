// nexushelper test
plugins {
    id("com.sinch.gradle.nexushelper")
}

listOf(
    "NEXUS_DOMAIN",
    "NEXUS_USER",
    "NEXUS_PASSWORD",
    "NEXUS_SNAPSHOT_REPO",
    "NEXUS_RELEASE_REPO",
    "NEXUS_DEPENDENCY_REPO",
).forEach {
    println(
        settings.extensions.extraProperties.get(it),
    )
}
