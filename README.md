# Vert.x Codegen Gradle Plugin for Java projects

A Gradle plugin to _facilitate_ the codegen usage for Vert.x Java projects

[![CircleCI](https://circleci.com/gh/bulivlad/vertx-codegen-plugin.svg?style=svg)](https://circleci.com/gh/bulivlad/vertx-codegen-plugin)
[![LICENSE](https://img.shields.io/github/license/bulivlad/vertx-codegen-plugin.svg)](https://github.com/bulivlad/vertx-codegen-plugin/blob/master/LICENSE)
[![GradlePluginPortal](https://img.shields.io/maven-metadata/v/https/plugins.gradle.org/m2/io/dotinc/vertx-codegen-plugin/io.dotinc.vertx-codegen-plugin.gradle.plugin/maven-metadata.xml.svg?label=gradlePluginPortal)](https://plugins.gradle.org/plugin/io.dotinc.vertx-codegen-plugin)

### Why is this plugin helpful
This [plugin](https://plugins.gradle.org/plugin/io.dotinc.vertx-codegen-plugin) focus on configuring your gradle project to be ready for Vert.x [Service-Proxy](https://github.com/vert-x3/vertx-service-proxy) usage. It applies the configuration needed for [CodeGen](https://github.com/vert-x3/vertx-codegen) API

### What the plugin does behind the scene
* Check if the [java](https://docs.gradle.org/current/userguide/java_plugin.html) plugin was applied and abort the execution otherwise
* Apply the following gradle dependencies with appropriate configuration with regards to the gradle version.
	* `io.vertx:vertx-core`
	* `io.vertx:vertx-service-proxy`
	* `io.vertx:vertx-codegen`
* Adds a new gradle task `cleanGenerated` which will clean the generated classes. The default `clean` task depends on `cleanGenerated`
* Adds the compiler attributes needed

| NOTE: | The plugin will only be executed if the `java` plugin was already applied. It will not forcefully apply any `plugin` behind the scenes. |
| ------------- | :------------- |

### Configuration example
```groovy
plugins {
	id 'java'
	id 'io.dotinc.vertx-codegen-plugin' version 'x.y.z' // (1)
}
repositories {
    mavenCentral()
}
codeGen {
	vertxVersion = 'a.b.c' // (2)
	generatedDirs = "src/main/generated"
	generationPath = "proxy"
}
```

(1) Replace `x.y.z` with one of the plugin versions available on the [Gradle Plugin Portal](https://plugins.gradle.org/plugin/io.dotinc.vertx-codegen-plugin)

(2) Replace `a.b.c` with one of the vert.x library versions available on [Maven Central Repository](https://mvnrepository.com/artifact/io.vertx/vertx-core)

### Configuration
| Options | Description | Default value |
| ------------- | :------------- | :-----|
| vertxVersion | The vert.x library version | 3.8.4 |
| generatedDirs | The path to the generated java classes. The path is relative to the project root | "src/main/generated" |
| generationPath | The specific path to the generated java class | "proxy" |

| |  |
| --- | --- |
| NOTE:  | Full path to generated proxy classes will be `${generatedDirs}/${generationPath}` |
| | The `vertxVersion` config will be overridden with `io.vertx:vertx-core` version if it was already included in `build.gradle` file |

The default values can also be found in [src/main/groovy/io/dotinc/gradle/CodeGenPluginExtension.groovy](https://github.com/bulivlad/vertx-codegen-plugin/blob/master/src/main/groovy/io/dotinc/gradle/CodeGenPluginExtension.groovy)

### Contributing

* Pull requests are welcome.
* PRs should target the `master` branch.

### Author

Vlad Bulimac (buli.vlad@gmail.com)