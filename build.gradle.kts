import net.researchgate.release.*

plugins {
    kotlin("jvm") version "1.6.20"
    id("net.researchgate.release") version ("2.8.1")
}

group = "org.example"

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib"))
}
fun ReleaseExtension.git(configure: GitAdapter.GitConfig.() -> Unit) = (getProperty("git") as GitAdapter.GitConfig).configure()

release {
    failOnSnapshotDependencies = false
    failOnCommitNeeded = false
    preTagCommitMessage = "ci: creating tag: "
    tagCommitMessage = "ci: creating tag: "
    newVersionCommitMessage = "ci: new version commit: "
    versionPatterns = mapOf(
        """(\d+)\.(\d+)\.(\d+)\+(\d+)""" to KotlinClosure2<java.util.regex.Matcher, Project, String>({ matcher, _ ->
            matcher.replaceAll("${(matcher.group(1))}.${(matcher.group(2))}.${(matcher.group(3).toInt() + 1)}.${(matcher.group(4).toInt() + 1)}") })
    )
    failOnUpdateNeeded = false
    git {
        requireBranch = "master|develop|hotfix\\/.+"
    }
}
