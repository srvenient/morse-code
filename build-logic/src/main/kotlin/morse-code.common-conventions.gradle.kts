import org.gradle.kotlin.dsl.java
import java.util.*

plugins {
  java
}

val libs = extensions.getByType(org.gradle.accessors.dm.LibrariesForLibs::class)

java {
  withJavadocJar()

  toolchain {
    languageVersion.set(JavaLanguageVersion.of(21))
  }
}

repositories {
  mavenCentral()
}

dependencies {
  compileOnly(libs.annotations)

  // Test dependencies
  testImplementation(platform("org.junit:junit-bom:5.10.0"))
  testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.test {
  useJUnitPlatform()
}

tasks {
  jar {
    manifest {
      attributes(
        "Specification-Version" to project.version,
        "Specification-Vendor" to "emptyte-team",
        "Implementation-Build-Date" to Date()
      )
    }
  }

  javadoc {
    options.encoding = Charsets.UTF_8.name()
  }

  compileJava {
    options.encoding = Charsets.UTF_8.name()
    options.compilerArgs.add("-parameters")
  }
}
