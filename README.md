
### Gradle Nexus access helper

A helper plugin that makes it easier to set up Nexus access for publishing and dependency fetching.

#### Configuration

The following Gradle properties needs to be present for the plugin to work:

```
NEXUS_DOMAIN
NEXUS_USER
NEXUS_PASSWORD
NEXUS_SNAPSHOT_REPO
NEXUS_RELEASE_REPO
NEXUS_DEPENDENCY_REPO
```

The actual URLs used for configuration will be `https://$NEXUS_DOMAIN/repository/$repo`
