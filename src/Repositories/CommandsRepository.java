package Repositories;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class CommandsRepository {
    public String pwd(File directory) throws IOException {
        return directory.getCanonicalPath();
    }

    public String cd (File workingDirectory, ArrayList<String> inputs) throws Exception {
        String directoryName = inputs.getFirst();
        String current_directory = workingDirectory.toString();
        // Here to make the ./ return back
        if (directoryName.equals("./")) {
            for (int i = current_directory.length() - 1; i >= 0; i-- ) {
                if (current_directory.charAt(i) == '\\') {
                    // We make the current_directory the same without the last folder
                    directoryName = current_directory.substring(0, i);
                    break;
                }
            }
        }
        else {
            // We add the directory to the _workingdirectory
            directoryName = workingDirectory.toString() + "/" + directoryName;
        }
        // We made a file to see if it exists and isDirectory or not
        File directory = new File(directoryName);
        if (directory.exists() && directory.isDirectory()) {
            return directoryName;
        }
        throw new Exception("Not Valid Path");
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
