import org.springframework.boot.gradle.tasks.bundling.BootJar

plugins {
    kotlin("kapt")
}

apply(plugin = "kotlin-jpa")
apply(plugin = "kotlin-kapt")

dependencies {
    api("org.springframework.boot:spring-boot-starter-data-jpa")

    api("com.querydsl:querydsl-jpa:${Versions.querydsl}")
    kapt("com.querydsl:querydsl-apt:${Versions.querydsl}:jpa")

    runtimeOnly("com.h2database:h2")
}

sourceSets {
    kotlin.sourceSets.register("$buildDir/generated/source/kapt/main")
}

val jar: Jar by tasks
val bootJar: BootJar by tasks

bootJar.enabled = false
jar.enabled = true
