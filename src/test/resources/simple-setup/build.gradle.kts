//
plugins {
    `maven-publish`
    `java-library`
}

publishing(nexusPublishing(isSnapshot = true, components["java"]) { artifactId = "someId" })

println("publication name is '${publishing.publications["maven"].name}'")
