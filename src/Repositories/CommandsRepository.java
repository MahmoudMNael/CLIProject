package Repositories;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
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

    public void mv(ArrayList<String> inputs) throws Exception {
        if (inputs.size() >= 2) {
            String source = inputs.getFirst();
            String target = inputs.getLast();
            File firstFile = new File(source);
            File secondFile = new File(target);
            if (firstFile.exists()){
                Files.move(firstFile.toPath(), secondFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
            }
            else {
                throw new Exception("File or Target path is not found");
            }
        }
        else {
            throw new Exception("Input is not valid, Please enter the file name and the path you want to move it to");
        }
    }
}
