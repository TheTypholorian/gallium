plugins {
    id("maven-publish")
}

group = "net.typho"
version = "1.0"

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            from(components["java"])
        }
    }

    repositories {
        maven {
            name = "GitHubPackages"
            url = uri("https://maven.pkg.github.com/TheTypholorian/gallium")

            credentials {
                username = project.findProperty("gpr.user").toString()
                password = project.findProperty("gpr.token").toString()
            }
        }
    }
}