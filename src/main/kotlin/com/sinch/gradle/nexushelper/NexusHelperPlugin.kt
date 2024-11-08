package com.sinch.gradle.nexushelper

import org.gradle.api.Action
import org.gradle.api.Plugin
import org.gradle.api.PolymorphicDomainObjectContainer
import org.gradle.api.artifacts.repositories.MavenArtifactRepository
import org.gradle.api.component.SoftwareComponent
import org.gradle.api.initialization.Settings
import org.gradle.api.plugins.ExtensionAware
import org.gradle.api.publish.PublishingExtension
import org.gradle.api.publish.maven.MavenPublication
import org.gradle.kotlin.dsl.nexusMavenRepositoryAction
import java.net.URI

const val DEFAULT_PUBLICATION_NAME: String = "maven"

fun ExtensionAware.reqProp(name: String): String =
    this.extensions.extraProperties
        .get(name) // throws UnknownPropertyException if not present at all
        ?.toString()
        ?: throw IllegalArgumentException("required property '$name' is null")

fun ExtensionAware.nexusMavenRepositoryAction(path: String): MavenArtifactRepository.() -> Unit =
    fun MavenArtifactRepository.() {
        url = URI("https://${reqProp("NEXUS_DOMAIN")}/repository/$path")
        credentials {
            it.username = reqProp("NEXUS_USER")
            it.password = reqProp("NEXUS_PASSWORD")
        }
    }

fun ExtensionAware.nexusPublishingAction(
    isSnapshot: Boolean,
    component: SoftwareComponent,
    publicationName: String = DEFAULT_PUBLICATION_NAME,
    publicationConfiguration: MavenPublication.() -> Unit = {},
): Action<PublishingExtension> =
    Action<PublishingExtension> { publishingExtension ->
        publishingExtension.publications { publicationContainer ->
            @Suppress("UNCHECKED_CAST")
            (publicationContainer as PolymorphicDomainObjectContainer<MavenPublication>).create(
                publicationName,
                MavenPublication::class.java,
            ) {
                it.from(component)
                it.publicationConfiguration()
            }
        }

        publishingExtension.repositories {
            it.maven(
                nexusMavenRepositoryAction(
                    reqProp(if (isSnapshot) "NEXUS_SNAPSHOT_REPO" else "NEXUS_RELEASE_REPO"),
                ),
            )
        }
    }

fun ExtensionAware.nexusPublishing(
    isSnapshot: Boolean,
    component: SoftwareComponent,
    publicationName: String = DEFAULT_PUBLICATION_NAME,
    publicationConfiguration: MavenPublication.() -> Unit = {},
): PublishingExtension {
    val publishingExtension = extensions.getByType(PublishingExtension::class.java)
    nexusPublishingAction(isSnapshot, component, publicationName, publicationConfiguration).execute(publishingExtension)
    return publishingExtension
}

@Suppress("unused")
class NexusHelperPlugin : Plugin<Settings> {
    override fun apply(settings: Settings) {
        settings.dependencyResolutionManagement { drm ->
            @Suppress("UnstableApiUsage")
            drm.repositories {
                it.mavenCentral()

                it.maven(settings.nexusMavenRepositoryAction(settings.reqProp("NEXUS_DEPENDENCY_REPO")))
            }
        }
    }
}
