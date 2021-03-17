
plugins {
    val kotlinVersion = "1.4.30"
    java
    kotlin("jvm") version kotlinVersion
    kotlin("plugin.serialization") version kotlinVersion
    kotlin("kapt") version "1.4.0"
}

group = "org.yakumobot"
version = "1.0.0"

repositories {
    mavenLocal()
    jcenter()
    mavenCentral()
}

dependencies {
    implementation("org.junit.jupiter:junit-jupiter:5.4.2")
    compileOnly(kotlin("stdlib-jdk8"))

    val core = "1.3.0"
    val console = "1.0-RC-dev-28"
    val jdbc_sqlite = "3.32.3"
    val ktorm = "3.2.0"
    compileOnly("net.mamoe:mirai-console:$console")
    compileOnly("net.mamoe:mirai-core:$core")
    implementation("org.xerial", "sqlite-jdbc", jdbc_sqlite)
    implementation("com.squareup.okhttp3", "okhttp", "3.1.0")
    implementation("com.beust:klaxon:5.0.1")
    //implementation("org.ktorm", "ktorm-core", ktorm)
    val autoService = "1.0-rc7"
    kapt("com.google.auto.service", "auto-service", autoService)
    compileOnly("com.google.auto.service", "auto-service-annotations", autoService)

    testImplementation("net.mamoe:mirai-console:$console")
    testImplementation("net.mamoe:mirai-core:$core")
    //testImplementation("net.mamoe:mirai-console-pure:$console")
    testImplementation(kotlin("stdlib-jdk8"))

}
kotlin.target.compilations.all{
    kotlinOptions.freeCompilerArgs += "-Xjvm-default=enable"
    kotlinOptions.jvmTarget = "1.8"
}
kotlin.sourceSets.all {
    languageSettings.useExperimentalAnnotation("kotlin.RequiresOptIn")
}