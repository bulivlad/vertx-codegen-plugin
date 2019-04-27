package io.dotinc.gradle

import org.gradle.testkit.runner.GradleRunner
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TemporaryFolder

import static org.junit.Assert.assertTrue

/**
 * @author vladclaudiubulimac on 2019-04-25.
 */

class CodeGenPluginTest {
    @Rule public final TemporaryFolder testProjectDir = new TemporaryFolder()
    private File buildFile

    @Before
    void setup() {
        buildFile = testProjectDir.newFile("build.gradle")
    }

    @Test
    void smokeTest() {
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
                .withProjectDir(testProjectDir.getRoot())
                .withArguments("tasks")
                .build()

        assertTrue(runner.output.contains('jar'))
        assertTrue(runner.output.contains('BUILD SUCCESSFUL'))
    }

}
