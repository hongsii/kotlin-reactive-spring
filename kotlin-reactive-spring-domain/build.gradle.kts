import org.springframework.boot.gradle.tasks.bundling.BootJar

apply(plugin = "org.jetbrains.kotlin.plugin.jpa")

dependencies {
    api("org.springframework.boot:spring-boot-starter-data-jpa")

    runtimeOnly("com.h2database:h2")
}

val jar: Jar by tasks
val bootJar: BootJar by tasks

bootJar.enabled = false
jar.enabled = true
