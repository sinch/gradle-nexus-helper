@file:Suppress("unused")

package org.gradle.kotlin.dsl

import com.sinch.gradle.nexushelper.DEFAULT_PUBLICATION_NAME
import com.sinch.gradle.nexushelper.nexusMavenRepositoryAction
import com.sinch.gradle.nexushelper.nexusPublishing
import com.sinch.gradle.nexushelper.nexusPublishingAction
import org.gradle.api.Action
import org.gradle.api.artifacts.repositories.MavenArtifactRepository
import org.gradle.api.component.SoftwareComponent
import org.gradle.api.plugins.ExtensionAware
import org.gradle.api.publish.PublishingExtension
import org.gradle.api.publish.maven.MavenPublication

fun ExtensionAware.nexusMavenRepositoryAction(path: String): MavenArtifactRepository.() -> Unit = nexusMavenRepositoryAction(path)

fun ExtensionAware.nexusPublishingAction(
    isSnapshot: Boolean,
    component: SoftwareComponent,
    publicationName: String = DEFAULT_PUBLICATION_NAME,
    publicationConfiguration: MavenPublication.() -> Unit = {},
): Action<PublishingExtension> = nexusPublishingAction(isSnapshot, component, publicationName, publicationConfiguration)

fun ExtensionAware.nexusPublishing(
    isSnapshot: Boolean,
    component: SoftwareComponent,
    publicationName: String = DEFAULT_PUBLICATION_NAME,
    publicationConfiguration: MavenPublication.() -> Unit = {},
): PublishingExtension = nexusPublishing(isSnapshot, component, publicationName, publicationConfiguration)
