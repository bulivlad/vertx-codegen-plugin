# Vert.x Codegen Gradle Plugin for Java projects

A Gradle plugin to _facilitate_ the codegen usage for Vert.x Java projects

[![CircleCI](https://circleci.com/gh/bulivlad/vertx-codegen-plugin.svg?style=svg)](https://circleci.com/gh/bulivlad/vertx-codegen-plugin)
[![LICENSE](https://img.shields.io/github/license/bulivlad/vertx-codegen-plugin.svg)](https://img.shields.io/github/license/bulivlad/vertx-codegen-plugin.svg)
[![LICENSE](https://img.shields.io/maven-metadata/v/https/plugins.gradle.org/m2/io/dotinc/vertx-codegen-plugin/io.dotinc.vertx-codegen-plugin.gradle.plugin/maven-metadata.xml.svg?label=gradlePluginPortal)](https://img.shields.io/maven-metadata/v/https/plugins.gradle.org/m2/io/dotinc/vertx-codegen-plugin/io.dotinc.vertx-codegen-plugin.gradle.plugin/maven-metadata.xml.svg?label=gradlePluginPortal)

### Why is this plugin helpful
This [plugin](https://plugins.gradle.org/plugin/io.dotinc.vertx-codegen-plugin) focus on configuring your gradle project to be ready for Vert.x [Service-Proxy](https://github.com/vert-x3/vertx-service-proxy) usage. It applies the configuration needed for [CodeGen](https://github.com/vert-x3/vertx-codegen) API

#### What the plugin does behind the scene
* Check if the [java](https://docs.gradle.org/current/userguide/java_plugin.html) plugin was applied and abort the execution otherwise
* Apply the following gradle dependencies with appropiate configuration with regatds to the gradle version
	* `io.vertx:vertx-core`
	* `io.vertx:vertx-service-proxy`
	* `io.vertx:vertx-codegen`
* Adds a new gradle task `cleanGenerated` which will clean the generated classes. The default `clean` task depends on `cleanGenerated`
* Adds the compiler attributes needed

| NOTE: | The plugin will only be executed if the `java` plugin was already applied. It will not forcefully apply any `plugin` behind the scenes. |
| --- | :--- |

