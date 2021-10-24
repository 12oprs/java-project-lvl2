package hexlet.code;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.nio.file.Files;

class AppTest {

    private static final String RESOURCE_DIRECTORY = "src/test/resources";
    private static final String FILE1 = "file1.json";
    private static final String FILE2 = "file2.json";
    private static final String FILE3 = "file1.yml";
    private static final String FILE4 = "file2.yml";
    private static final String FILE5 = "resultDiff";
    private static final String FORMAT = "stylish";

    private static String filePath1;
    private static String filePath2;
    private static String filePath3;
    private static String filePath4;
    private static String filePath5;
    private static String expected;

    @BeforeAll
    static void preparing() throws Exception {
        filePath1 = Paths.get(RESOURCE_DIRECTORY, FILE1).toFile().getAbsolutePath();
        filePath2 = Paths.get(RESOURCE_DIRECTORY, FILE2).toFile().getAbsolutePath();
        filePath3 = Paths.get(RESOURCE_DIRECTORY, FILE3).toFile().getAbsolutePath();
        filePath4 = Paths.get(RESOURCE_DIRECTORY, FILE4).toFile().getAbsolutePath();
        filePath5 = Paths.get(RESOURCE_DIRECTORY, FILE5).toFile().getAbsolutePath();
        String[] temp = Files.lines(Path.of(filePath5))
                .toArray(String[]::new);
        StringBuilder sb = new StringBuilder();
        for (String s : temp) {
            sb.append(s).append("\n");
        }
        expected = sb.toString();
    }

    @Test
    @DisplayName("Run app with json testfiles")
    void jsonTest() throws Exception {
        Differ testDf = new Differ(filePath1, filePath2, FORMAT);
        assertEquals(expected, testDf.generate());
    }

    @Test
    @DisplayName("Run app with yaml testfiles")
    void yamlTest() throws Exception {
        Differ testDf = new Differ(filePath3, filePath4, FORMAT);
        assertEquals(expected, testDf.generate());
    }
}
