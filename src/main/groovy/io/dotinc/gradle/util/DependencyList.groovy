package io.dotinc.gradle.util

/**
 * @author vladclaudiubulimac on 2019-04-27.
 */
class DependencyList {

    private static Map<String, List<String>> dependencies = new HashMap<String, List<String>>()

    private static populateMap(){
        List<String> gradle5Dependencies = new ArrayList<>()
        gradle5Dependencies.add('compileOnly:io.vertx:vertx-core:${version}')
        gradle5Dependencies.add('compileOnly:io.vertx:vertx-service-proxy:${version}')
        gradle5Dependencies.add('annotationProcessor:io.vertx:vertx-service-proxy:${version}:processor')
        gradle5Dependencies.add('compileOnly:io.vertx:vertx-codegen:${version}')
        gradle5Dependencies.add('annotationProcessor:io.vertx:vertx-codegen:${version}:processor')

        List<String> gradleDependencies = new ArrayList<>()
        gradleDependencies.add('compile:io.vertx:vertx-core:${version}')
        gradleDependencies.add('compile:io.vertx:vertx-service-proxy:${version}:processor')
        gradleDependencies.add('compile:io.vertx:vertx-codegen:${version}:processor')

        dependencies.put("gradle6", gradle5Dependencies)
        dependencies.put("gradle5", gradle5Dependencies)
        dependencies.put("gradle", gradleDependencies)
    }

    static List<String> getDependencies(String version) {
        if(dependencies.isEmpty()){
            populateMap()
            return dependencies.get(version)
        }
        return dependencies.get(version)
    }

}
