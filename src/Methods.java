import java.io.*;
import java.nio.file.*;

public class Methods {
    // touch method should have multiple parameters
    public static void touch(String source, String[] filename) {
        Path SCpath = Paths.get(source);
        for (String file : filename) {
            try {
                Path path = SCpath.resolve(file);
                Files.createFile(path);

            } catch (IOException e) {
                System.err.println("Cannot create a file: " + file);
            }
        }
    }
}

