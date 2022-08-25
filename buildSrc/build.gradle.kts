plugins {
    `kotlin-dsl`
}

dependencies {
    implementation("org.springframework.boot:org.springframework.boot.gradle.plugin:2.7.2")
    implementation("io.spring.gradle:dependency-management-plugin:1.0.13.RELEASE")
}

repositories {
    mavenCentral()
}
