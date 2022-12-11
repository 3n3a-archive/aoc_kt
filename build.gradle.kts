plugins {
    kotlin("jvm") version "1.7.22"
    application
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("com.ionspin.kotlin:bignum:0.3.7")
}

tasks {
    sourceSets {
        main {
            java.srcDirs("src")
        }
    }

    wrapper {
        gradleVersion = "7.6"
    }
}

application {
    mainClass.set("MainKt")
}
