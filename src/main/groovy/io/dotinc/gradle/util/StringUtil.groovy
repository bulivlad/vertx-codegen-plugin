package io.dotinc.gradle.util

/**
 * @author vladclaudiubulimac on 2019-04-25.
 */
final class StringUtil {

    static boolean isEmpty(String input) {
        return input == null || input.empty || input.allWhitespace
    }

}
