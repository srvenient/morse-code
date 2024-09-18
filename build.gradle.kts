plugins {
  id("java")
}

repositories {
  mavenCentral()
}

dependencies {
  compileOnly("org.jetbrains:annotations:24.1.0")

  // Test dependencies
  testImplementation(platform("org.junit:junit-bom:5.10.0"))
  testImplementation("org.junit.jupiter:junit-jupiter")
}

java {
  toolchain {
    languageVersion.set(JavaLanguageVersion.of(21))
  }
}

tasks.test {
  useJUnitPlatform()
}
