dependencies {
    implementation(project(":kotlin-reactive-spring-domain"))
    implementation(project(":kotlin-reactive-spring-common"))

    // webflux
    implementation("org.springframework.boot:spring-boot-starter-webflux")

    // kotlin coroutine
    implementation("io.projectreactor.kotlin:reactor-kotlin-extensions")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor")
    testImplementation("io.projectreactor:reactor-test")
}