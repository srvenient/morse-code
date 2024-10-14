pluginManagement {
  includeBuild("build-logic")
}

rootProject.name = "morse-code"

sequenceOf("cli", "server").forEach {
  include("morse-code-$it")
  project(":morse-code-$it").projectDir = file(it)
}
