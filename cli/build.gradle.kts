plugins {
  id("morse-code.common-conventions")
  id("com.github.johnrengelman.shadow") version "8.1.1"
}

tasks {
  shadowJar {
    archiveBaseName.set(rootProject.name + "-cli")
    archiveClassifier.set("")

    manifest {
      attributes["Main-Class"] = "co.edu.unimonserrate.Bootstrap"
    }
  }
}
