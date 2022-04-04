import net.researchgate.release.*

plugins {
    kotlin("jvm") version "1.6.20"
    id("net.researchgate.release") version ("2.8.1")
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib"))
}
fun ReleaseExtension.git(configure: GitAdapter.GitConfig.() -> Unit) = (getProperty("git") as GitAdapter.GitConfig).configure()

release {
    versionPropertyFile = "../gradle.properties"
    failOnSnapshotDependencies = false

    preTagCommitMessage = "ci: creating tag: "
    tagCommitMessage = "ci: creating tag: "
    newVersionCommitMessage = "ci: new version commit: "
    versionPatterns = mapOf(
        """[.]*\.(\d+)\.(\d+)$""" to KotlinClosure2<java.util.regex.Matcher, Project, String>({ matcher, project ->
            matcher.replaceAll(".${(matcher.group(0)[1] + 1)}.${(matcher.group(0)[2] + 1)}")
        })
    )
    failOnUpdateNeeded = false
    git {
        requireBranch = "master|develop|hotfix\\/.+"
    }
}
