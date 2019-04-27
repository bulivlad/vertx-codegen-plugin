package io.dotinc.gradle

import io.dotinc.gradle.model.Dependency
import io.dotinc.gradle.util.DependenciesUtil
import io.dotinc.gradle.util.StringUtil
import org.gradle.api.Action
import org.gradle.api.GradleException
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.DependencyResolutionListener
import org.gradle.api.artifacts.ResolvableDependencies
import org.gradle.api.plugins.JavaPlugin
import org.gradle.util.GradleVersion

/**
 * @author vladclaudiubulimac on 2019-04-24.
 */
class CodeGenPlugin implements Plugin<Project> {

    def vertxVersion
    def gradleVersion = GradleVersion.current().version
    def gradleMajor = gradleVersion.split('\\.')[0] as Integer

    @Override
    void apply(Project project) {
        def extension = project.extensions.create('codeGen', CodeGenPluginExtension)
        List<Dependency> dependencies = DependenciesUtil.buildDependenciesList(gradleMajor > 4 ? "gradle" + gradleMajor :"gradle")

        project.afterEvaluate {

            def isJavaPlugin = project.plugins.hasPlugin(JavaPlugin.class)
            if(!isJavaPlugin) {
                throw new GradleException("Script to be used only for java projects. java plugin needs to be applied");
            }
            project.plugins.withType(JavaPlugin.class, new Action<JavaPlugin>() {
                void execute(JavaPlugin javaPlugin) {
                    vertxVersion = getDependencyVersion(project, "vertx-core")

                    println "adding following dependencies to the project :"
                    dependencies.each { dep ->
                        dep.setVersion(vertxVersion)
                        println dep.toPrettyString()
                        addDependency(project, dep)
                    }

//                    Dependency vertxCore = new Dependency('compileOnly', 'io.vertx', 'vertx-core', "${vertxVersion}")
//                    addDependency(project, vertxCore)
//
//                    Dependency vertxServiceProxy = new Dependency('compileOnly', 'io.vertx', 'vertx-service-proxy', "${vertxVersion}")
//                    addDependency(project, vertxServiceProxy)
//                    vertxServiceProxy.setConfiguration('annotationProcessor').setSpecialization('processor')
//                    addDependency(project, vertxServiceProxy)
//
//                    Dependency vertxCodeGen = new Dependency('compileOnly', 'io.vertx', 'vertx-codegen', "${vertxVersion}")
//                    addDependency(project, vertxCodeGen)
//                    vertxCodeGen.setConfiguration('annotationProcessor').setSpecialization('processor')
//                    addDependency(project, vertxCodeGen)

                    project.sourceSets {
                        main {
                            java {
                                srcDir getGeneratedPath(extension)
                            }
                        }
                    }

                    def cleanGeneratedTask = project.task("cleanGenerated").doLast {
                        project.files("${extension.generatedDirs}").each { f -> f.deleteDir() }
                    }

                    project.getTasks().getByName("clean").dependsOn(cleanGeneratedTask)

                    project.getTasks().getByName("compileJava").configure {
                        String fullGeneratedPath = getCompilerGenerationPath(extension, project)
                        it.options.annotationProcessorGeneratedSourcesDirectory = project.file(fullGeneratedPath)
                    }
                }
            })
        }

    }

    private void addDependency(Project project, Dependency dependency) {
        if (!dependencyExists(project, dependency.configuration, dependency.name)) {
            addDependencyToConfig(project, dependency.configuration, dependency.toDependencyString())
        }
    }

    private void addDependencyToConfig(Project project, String config, String dependency) {
        def compileOnly = project.getConfigurations()?.getByName(config)?.getDependencies()
        project.gradle.addListener(new DependencyResolutionListener() {
            @Override
            void beforeResolve(ResolvableDependencies resolvableDependencies) {
                compileOnly?.add(project.getDependencies()?.create(dependency))
                project.gradle.removeListener(this)
            }

            @Override
            void afterResolve(ResolvableDependencies resolvableDependencies) {
            }
        })
    }

    private boolean dependencyExists(Project project, String configName, String dependency) {
        project.configurations?.getByName(configName)?.dependencies?.any {it -> it.name.contains(dependency) }
    }

    private String getDependencyVersion(Project project, String dependency) {
        def vertxConfiguredVersion = project.extensions.getByName("codeGen").vertxVersion
        if(!StringUtil.isEmpty(vertxConfiguredVersion)) {
            return vertxConfiguredVersion
        }

        def maybeVersion = project.configurations.collect { it -> it.dependencies }
                .find { it -> it.name.contains(dependency) }
        if(maybeVersion == null){
            return vertxVersion
        }
        return StringUtil.isEmpty(maybeVersion.first().version) ? vertxVersion : maybeVersion.first().version
    }


    private String getGeneratedPath(CodeGenPluginExtension extension) {
        if(extension.generatedDirs.endsWith('/')){
            return extension.generatedDirs + extension.generationPath
        }
        return extension.generatedDirs + '/' + extension.generationPath
    }

    private String getCompilerGenerationPath(CodeGenPluginExtension extension, Project project) {
        if (!extension.generatedDirs.startsWith('/')) {
            return "${project.getProjectDir()}/" + getGeneratedPath(extension)
        } else {
            return getGeneratedPath(extension)
        }
    }

}