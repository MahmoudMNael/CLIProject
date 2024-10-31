package Repositories;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

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

    public void rmdir(ArrayList<String> inputs) throws Exception {
        File removed_file = new File(inputs.getFirst());
        if (removed_file.isFile()) {
            throw new Exception("It is not directory to be removed");
        } else if (!removed_file.exists()) {
            throw new Exception("Directory is not found to be deleted");
        }
        // To make sure that it doesnot return null
        if (Objects.requireNonNull(removed_file.list()).length > 0){
            throw new Exception("This directory has children so it can not be deleted");
        }
        else {
            removed_file.delete();
        }
    }
}
