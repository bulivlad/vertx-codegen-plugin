package io.dotinc.gradle.model

import io.dotinc.gradle.util.StringUtil

/**
 * @author vladclaudiubulimac on 2019-04-25.
 */
class Dependency {

    private String configuration
    private String group
    private String name
    private String version
    private String specialization

    Dependency(String configuration, String group, String name, String version) {
        this.configuration = configuration
        this.group = group
        this.name = name
        this.version = version
    }

    Dependency(String configuration, String group, String name, String version, String specialization) {
        this.configuration = configuration
        this.group = group
        this.name = name
        this.version = version
        this.specialization = specialization
    }

    String getGroup() {
        return group
    }

    Dependency setGroup(String group) {
        this.group = group
        return this
    }

    String getName() {
        return name
    }

    Dependency setName(String name) {
        this.name = name
        return this
    }

    String getConfiguration() {
        return configuration
    }

    Dependency setConfiguration(String configuration) {
        this.configuration = configuration
        return this
    }

    String getVersion() {
        return version
    }

    Dependency setVersion(String version) {
        this.version = version
        return this
    }

    String getSpecialization() {
        return specialization
    }

    Dependency setSpecialization(String specialization) {
        this.specialization = specialization
        return this
    }

    String toDependencyString() {
        if(specialization) {
            return group + ":" + name + ":" + version + ":" + specialization
        }

        return group + ":" + name + ":" + version
    }

    String toPrettyString() {
        return configuration + " ('" + toDependencyString() + "')"
    }
}
