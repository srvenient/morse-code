plugins {
  `kotlin-dsl`
}

dependencies {
  compileOnly(files(libs::class.java.protectionDomain.codeSource.location))
}

java {
  sourceCompatibility = JavaVersion.VERSION_21
  targetCompatibility = JavaVersion.VERSION_21
}

repositories {
  gradlePluginPortal()
}

kotlin {
  target {
    compilations.configureEach {
      kotlinOptions {
        jvmTarget = "21"
      }
    }
  }
}
