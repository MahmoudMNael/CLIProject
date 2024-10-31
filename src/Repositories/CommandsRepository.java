package Repositories;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class CommandsRepository {
    public String pwd(File directory) throws IOException {
        return directory.getCanonicalPath();
    }

    public void mkdir(ArrayList<String> inputs) throws Exception {
        if (inputs.isEmpty()) {
            throw new Exception("mkdir: Missing operand!");
        }
        for (String input : inputs) {
            File file = new File(input);
            if (file.exists()) {
                throw new Exception("mkdir: Directory already exists!");
            }
            if (!file.mkdir()) {
                throw new Exception("mkdir: No such file or directory!");
            }
        }
    }
}
