plugins {
    id("java")
    id("maven-publish")
}

group = "net.typho"
version = "v0.1"
description = "The Java library for reflection-based debugging tools"

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            from(components["java"])

            groupId = "com.github.TheTypholorian"
            artifactId = "gallium"
            version = project.version.toString()
        }
    }

    repositories {
        maven {
            url = uri("https://jitpack.io")
        }
    }
}

java {
    sourceCompatibility = JavaVersion.VERSION_21
    targetCompatibility = JavaVersion.VERSION_21
}