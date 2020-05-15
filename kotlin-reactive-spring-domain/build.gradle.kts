import org.springframework.boot.gradle.tasks.bundling.BootJar

plugins {
    kotlin("kapt")
}

apply(plugin = "kotlin-jpa")
apply(plugin = "kotlin-kapt")

dependencies {
    api("org.springframework.boot:spring-boot-starter-data-jpa")

    api("com.querydsl:querydsl-jpa:4.2.2")
    kapt("com.querydsl:querydsl-apt:4.2.2:jpa")

    runtimeOnly("com.h2database:h2")
}

sourceSets {
    kotlin.sourceSets.register("$buildDir/generated/source/kapt/main")
}

val jar: Jar by tasks
val bootJar: BootJar by tasks

bootJar.enabled = false
jar.enabled = true
