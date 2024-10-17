plugins {
  id("morse-code.common-conventions")
  id("com.github.johnrengelman.shadow") version "8.1.1"
}

dependencies {
  implementation(project(":morse-code-common"))
}

tasks {
  shadowJar {
    archiveBaseName.set(rootProject.name + "-server")
    archiveClassifier.set("")

    manifest {
      attributes["Main-Class"] = "co.edu.unimonserrate.Bootstrap"
    }
  }
}
