package Repositories;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Arrays;


public class CommandsRepository {
    public String pwd(File directory) throws IOException {
        return directory.getCanonicalPath();
    }


    public void rm (File workingDirectory, ArrayList<String> inputs) throws Exception {
        File testIfAbs = new File(inputs.getFirst());
        File file;
        if (testIfAbs.isAbsolute()) {
            file = new File(inputs.getFirst());
        } else {
            file = new File(workingDirectory.getCanonicalPath() + "/" + inputs.getFirst());
        }
        if (file.exists() && file.isFile()) {
            file.delete();
        }
        else {
            throw new Exception("File can not be deleted.");
        }
    }

    public String cd (File workingDirectory, ArrayList<String> inputs) throws Exception {
        String directoryName = inputs.getFirst();
        String current_directory = workingDirectory.toString();
        File testIfAbs = new File(directoryName);
        // Here to make the ./ return back
        if (directoryName.equals("./")) {
            for (int i = current_directory.length() - 1; i >= 0; i-- ) {
                if (current_directory.charAt(i) == '\\') {
                    // We make the current_directory the same without the last folder
                    directoryName = current_directory.substring(0, i);
                    break;
                }
            }
        } else if(!testIfAbs.isAbsolute()){
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
            File testIfAbs = new File(input);
            File file;
            if (testIfAbs.isAbsolute()) {
                file = new File(input);
            } else {
                file = new File(workingDirectory.getCanonicalPath() + "/" + input);
            }
            if (file.exists()) {
                throw new Exception("mkdir: Directory already exists!");
            }
            if (!file.mkdir()) {
                throw new Exception("mkdir: No such file or directory!");
            }
        }
    }


    public void mv(File workingDirectory, ArrayList<String> inputs) throws Exception {
        if (inputs.size() == 2) {
            String source = inputs.getFirst();
            String target = inputs.getLast();
            File testFirstFileIfAbs = new File(source);
            File firstFile;
            if (testFirstFileIfAbs.isAbsolute()) {
                firstFile = new File(source);
            } else {
                firstFile = new File(workingDirectory.getCanonicalPath() + "/" + source);
            }
            File testSecondFileIfAbs = new File(target);
            File secondFile;
            if (testSecondFileIfAbs.isAbsolute()) {
                secondFile = new File(target);
            } else {
                secondFile = new File(workingDirectory.getCanonicalPath() + "/" + target);
            }
            if (firstFile.exists()){
                try {
                    Files.move(firstFile.toPath(), secondFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
                } catch (Exception e) {
                    throw new Exception("mv: Invalid target path");
                }
            }
            else {
                throw new Exception("File is not found");
            }
        }
        else {
            throw new Exception("Input is not valid, Please enter the file name and the path you want to move it to");
        }
    }
  
    public void rmdir(File workingDirectory, ArrayList<String> inputs) throws Exception {
        File removedFile;
        File testIfAbs = new File(inputs.getFirst());
        if (testIfAbs.isAbsolute()) {
            removedFile = new File(inputs.getFirst());
        } else {
            removedFile = new File(workingDirectory.getCanonicalPath() + "/" + inputs.getFirst());
        }
        if (removedFile.isFile()) {
            throw new Exception("It is not directory to be removed");
        } else if (!removedFile.exists()) {
            throw new Exception("Directory is not found to be deleted");
        }
        // To make sure that it doesnot return null
        if (Objects.requireNonNull(removedFile.list()).length > 0){
            throw new Exception("This directory has children so it can not be deleted");
        } else {
            removedFile.delete();
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
