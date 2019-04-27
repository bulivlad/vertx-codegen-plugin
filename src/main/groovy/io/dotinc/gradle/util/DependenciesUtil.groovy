package io.dotinc.gradle.util

import io.dotinc.gradle.model.Dependency

import java.util.stream.Collectors

/**
 * @author vladclaudiubulimac on 2019-04-27.
 */
class DependenciesUtil {

    static List<Dependency> buildDependenciesList(String gradleVersion) {
        def dependencies = DependencyList.getDependencies(gradleVersion)
        List<Dependency> list = dependencies.collect {
            it ->
                String[] s = it.split(':')
                if(s.length == 4){
                    return new Dependency(s[0], s[1], s[2], s[3])
                }
                return new Dependency(s[0], s[1], s[2], s[3], s[4])
        } as List<Dependency>

        return list
    }

}
