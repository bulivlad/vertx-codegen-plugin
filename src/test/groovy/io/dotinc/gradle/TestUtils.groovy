package io.dotinc.gradle

/**
 * @author vladclaudiubulimac on 2019-04-25.
 */
class TestUtils {

    static void writeFile(File destination, String content) throws IOException {
        BufferedWriter output = null
        try {
            output = new BufferedWriter(new FileWriter(destination))
            output.write(content)
        } finally {
            if (output != null) {
                output.close()
            }
        }
    }

}
