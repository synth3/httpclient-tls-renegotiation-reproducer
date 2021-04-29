
import com.bmuschko.gradle.docker.tasks.container.DockerCreateContainer
import com.bmuschko.gradle.docker.tasks.container.DockerStartContainer
import com.bmuschko.gradle.docker.tasks.container.DockerStopContainer
import com.bmuschko.gradle.docker.tasks.image.DockerBuildImage
import org.gradle.api.tasks.testing.logging.TestExceptionFormat

plugins {
    java
    id("com.bmuschko.docker-remote-api") version "6.7.0"
}

repositories {
    // Use Maven Central for resolving dependencies.
    mavenCentral()
}

dependencies {
    // Use JUnit test framework.
    testImplementation("junit:junit:4.13.1")

    implementation("org.apache.httpcomponents.client5:httpclient5:5.0.3")
    implementation("log4j:log4j:1.2.17")
    implementation("org.slf4j:slf4j-log4j12:1.7.30")
}

val buildHttpdImage by tasks.creating(DockerBuildImage::class) {
    dockerFile.set(file("docker/httpd/Dockerfile"))
    inputDir.set(file("docker/httpd/"))
    images.add("httpclient-tls-renegotiation-reproducer:latest")
}

val createHttpdContainer by tasks.creating(DockerCreateContainer::class) {
    dependsOn(buildHttpdImage)
    targetImageId(buildHttpdImage.imageId)
    hostConfig.portBindings.set(listOf("8443:8443"))
    hostConfig.autoRemove.set(true)
    containerName.set("httpclient-tls-renegotiation-reproducer");
}

val startHttpdContainer by tasks.creating(DockerStartContainer::class) {
    dependsOn(createHttpdContainer)
    targetContainerId(createHttpdContainer.containerId)
}

val stopHttpdContainer by tasks.creating(DockerStopContainer::class) {
    targetContainerId(createHttpdContainer.containerId)
}

tasks {
    named<Test>("test") {
        dependsOn(startHttpdContainer)
        finalizedBy(stopHttpdContainer)
        testLogging.showExceptions = true
        testLogging.exceptionFormat = TestExceptionFormat.FULL
        testLogging.showStandardStreams = true
    }
}
