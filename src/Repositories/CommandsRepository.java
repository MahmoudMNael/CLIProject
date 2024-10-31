package Repositories;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class CommandsRepository {
    public String pwd(File directory) throws IOException {
        return directory.getCanonicalPath();
    }

    public void mkdir(File workingDirectory, ArrayList<String> inputs) throws Exception {
        if (inputs.isEmpty()) {
            throw new Exception("mkdir: Missing operand!");
        }
        for (String input : inputs) {
            File file = new File(workingDirectory.getCanonicalPath() + "/" + input);
            if (file.exists()) {
                throw new Exception("mkdir: Directory already exists!");
            }
            if (!file.mkdir()) {
                throw new Exception("mkdir: No such file or directory!");
            }
        }
    }

    public String ls(File workingDirectory, ArrayList<String> flags) throws Exception {
        ArrayList<String> list = new ArrayList<>(Arrays.asList(workingDirectory.list()));
        if (flags.contains("-a")) {
            list.addFirst("..");
            list.addFirst(".");
        } else {
            list.removeIf(val -> (val.charAt(0) == '.'));
        }
        if (flags.contains("-r")) {
            list = new ArrayList(list.reversed());
        }
        ArrayList<String> mappedList = new ArrayList<>();
        for (String path : list) {
            mappedList.add(path + "/");
        }

        return String.join("\n", mappedList);
    }
}
