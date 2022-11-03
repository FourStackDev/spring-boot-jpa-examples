package util;

import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;

public class TestUtility {

    public static String getFileContent(String filePath) {
        String content = "";
        try {
            File file = ResourceUtils.getFile("classpath:" + filePath);
            content = new String(Files.readAllBytes(file.toPath()));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return content;
    }
}
