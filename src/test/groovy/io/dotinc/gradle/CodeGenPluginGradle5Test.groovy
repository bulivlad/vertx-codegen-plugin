package io.dotinc.gradle

import org.gradle.testkit.runner.GradleRunner
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TemporaryFolder

import static org.junit.Assert.assertTrue


/**
 * @author vladclaudiubulimac on 2019-04-27.
 */
class CodeGenPluginGradle5Test {
    @Rule public final TemporaryFolder testProjectDir = new TemporaryFolder()
    private File buildFile

    @Before
    void setup() {
        buildFile = testProjectDir.newFile("build.gradle")
    }

    @Test
    void noConfigSupplied() {
        String buildFileContent = "buildscript {\n" +
                "   repositories {\n" +
                "       mavenCentral()\n" +
                "       mavenLocal()\n" +
                "   }\n" +
                "   dependencies {\n" +
                "       classpath \"io.dotinc:vertx-codegen-plugin:0+\"\n" +
                "   }\n" +
                "}\n" +
                "apply plugin:'io.dotinc.vertx-codegen-plugin'\n" +
                "apply plugin: 'groovy'\n" +
                "apply plugin: 'java'\n" +
                "repositories {\n" +
                "   mavenCentral()\n" +
                "}"

        TestUtils.writeFile(buildFile, buildFileContent)

        def runner = GradleRunner.create()
                .withGradleVersion("5.0")
                .withProjectDir(testProjectDir.getRoot())
                .withArguments("clean")
                .build()

        assertTrue(runner.output.contains('adding following dependencies to the project :'))
        assertTrue(runner.output.contains("compileOnly ('io.vertx:vertx-core:3.8.4')"))
        assertTrue(runner.output.contains("compileOnly ('io.vertx:vertx-service-proxy:3.8.4')"))
        assertTrue(runner.output.contains("annotationProcessor ('io.vertx:vertx-service-proxy:3.8.4:processor')"))
        assertTrue(runner.output.contains("compileOnly ('io.vertx:vertx-codegen:3.8.4')"))
        assertTrue(runner.output.contains("annotationProcessor ('io.vertx:vertx-codegen:3.8.4:processor')"))
        assertTrue(runner.output.contains('BUILD SUCCESSFUL'))
    }

    @Test
    void configSupplied() {
        String buildFileContent = "buildscript {\n" +
                "   repositories {\n" +
                "       mavenCentral()\n" +
                "       mavenLocal()\n" +
                "   }\n" +
                "   dependencies {\n" +
                "       classpath \"io.dotinc:vertx-codegen-plugin:0+\"\n" +
                "   }\n" +
                "}\n" +
                "apply plugin:'io.dotinc.vertx-codegen-plugin'\n" +
                "apply plugin: 'groovy'\n" +
                "apply plugin: 'java'\n" +
                "repositories {\n" +
                "   mavenCentral()\n" +
                "}\n" +
                "codeGen{\n" +
                "    vertxVersion = '3.6.3'\n" +
                "    generatedDirs = \"src/main/generated\"\n" +
                "    generationPath = \"proxy\"\n" +
                "}"

        TestUtils.writeFile(buildFile, buildFileContent)

        def runner = GradleRunner.create()
                .withGradleVersion("5.0")
                .withProjectDir(testProjectDir.getRoot())
                .withArguments("clean")
                .build()

        assertTrue(runner.output.contains('adding following dependencies to the project :'))
        assertTrue(runner.output.contains("compileOnly ('io.vertx:vertx-core:3.6.3')"))
        assertTrue(runner.output.contains("compileOnly ('io.vertx:vertx-service-proxy:3.6.3')"))
        assertTrue(runner.output.contains("annotationProcessor ('io.vertx:vertx-service-proxy:3.6.3:processor')"))
        assertTrue(runner.output.contains("compileOnly ('io.vertx:vertx-codegen:3.6.3')"))
        assertTrue(runner.output.contains("annotationProcessor ('io.vertx:vertx-codegen:3.6.3:processor')"))
        assertTrue(runner.output.contains('BUILD SUCCESSFUL'))
    }

}
