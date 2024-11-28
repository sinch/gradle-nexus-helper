
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

### Misc

As of `0.1.1`, `NEXUSHELPER_ADDITIONAL_REPOS` can be provided with a comma-separated list of URLs of Gradle dependency
repos to automatically include via `dependencyResolutionManagement` method. The default value is
"https://repo.spring.io/milestone" string.
