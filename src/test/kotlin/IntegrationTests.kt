import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.string.shouldContain
import org.gradle.testkit.runner.GradleRunner
import java.io.File

class IntegrationTests : StringSpec() {
    val projectDirRoot = "src/test/resources"

    fun getGradleRunnerOutput(projectDir: String): String {
        val output =
            GradleRunner
                .create()
                .withProjectDir(File("$projectDirRoot/$projectDir"))
                .withPluginClasspath()
                .withArguments("properties")
                .build()
                .output
        println(output)
        return output
    }

    init {
        "simple setup" {
            getGradleRunnerOutput("simple-setup") shouldContain "publication name is 'maven'"
        }
    }
}
