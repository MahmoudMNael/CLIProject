package Repositories;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Objects;
import java.util.Arrays;


public class CommandsRepository {
    public String pwd(File directory) throws IOException {
        return directory.getCanonicalPath();
    }

    public static void touch(File directory, ArrayList<String> inputs) throws IOException {
        for (String input : inputs) {
            File testIfAbs = new File(input);
            File file;
            if (testIfAbs.isAbsolute()) {
                file = new File(input);
            } else {
                file = new File(directory.getCanonicalPath() + "/" + input);
            }
            file.createNewFile();
            file.setLastModified(System.currentTimeMillis());
        }
    }

    public static String cat(File directory, ArrayList<String> inputs) throws IOException , Exception {
        boolean isAppend = false;
        StringBuilder output = new StringBuilder();
        // first we should check if there is ">" or ">>" in the inputs
        // if there is, we should check if the file exists or not
        // if > : write the content of the file to the other file
        // if >> : append the content of the file to the other file
        // after either > or >>, we should check if the file exists or not
        if (inputs.contains(">") || inputs.contains(">>")) {
            int index = inputs.indexOf(">");
            if (index == -1) {
                index = inputs.indexOf(">>");
                isAppend = true;
            }

            File file1 = new File(directory.getCanonicalPath() + "/" + inputs.get(index + 1));
            file1.createNewFile();
            if (index == 0 && inputs.size() == 2) {
                // case 2: cat > file1 : take the input from the user and write it to the file
                BufferedWriter writer = new BufferedWriter(new java.io.FileWriter(file1 , isAppend));
                Scanner scanner = new Scanner(System.in);
                String input = scanner.nextLine();
                writer.write(input);
                writer.close();
                return "";


            }
            else if(index == 0 && inputs.size() > 2) {
                // case 2: cat > file1 file2 file3 ... : write the content of file2, file3, ... to file1
                // append the content of file2, file3, ... and write it with replacement to file1
                for (int i = 2; i < inputs.size(); i++) {
                    // concatenate the content of the files and replace the content of file1 with the concatenated content
                    File file2 = new File(directory.getCanonicalPath() + "/" + inputs.get(i));
                    //read the content of the file
                    if (file2.exists()) {
                        output.append(java.nio.file.Files.readString(file2.toPath()));

                    } else {
                        output.append("cat: ").append(file2.getName()).append(": No such file or directory");
                        throw new Exception(output.toString());
                    }
                        BufferedWriter writer = new BufferedWriter(new java.io.FileWriter(file1, isAppend));
                        writer.write(output.toString());
                        writer.close();
                        output.setLength(0);
                        return "";

                }


            }else{

                // the ">" or ">>" is in the middle of the inputs
                // case 2: cat file1 > file2 : write the content of file1 to file2
                // case 2: cat file1 >> file2 : append the content of file1 to file2
                // get the content of the file(s) and write it to the other file except the file after either ">" or ">>"
                for (int i = 0; i < index; i++) {
                    File file = new File(directory.getCanonicalPath() + "/" + inputs.get(i));
                    if (file.exists()) {
                        output.append(java.nio.file.Files.readString(file.toPath()));
                    } else {
                        output.append("cat: ").append(file.getName()).append(": No such file or directory\n");
                        throw new Exception(output.toString());
                    }
                }
                for (int i = index + 2; i < inputs.size(); i++) {
                    File file = new File(directory.getCanonicalPath() + "/" + inputs.get(i));
                    if (file.exists()) {
                        output.append(java.nio.file.Files.readString(file.toPath()));
                    } else {
                        output.append("cat: ").append(file.getName()).append(": No such file or directory\n");
                        throw new Exception(output.toString());
                    }
                }
                BufferedWriter writer = new BufferedWriter(new java.io.FileWriter(file1, isAppend));
                writer.write(output.toString());
                writer.close();
                return "";



        }


    }
        //case 3: cat file1 file2 file3 ...
        for (String input : inputs) {
            File file = new File(directory.getCanonicalPath() + "/" + input);
            if (file.exists()) {
                output.append(java.nio.file.Files.readString(file.toPath()));
            } else {
                output.append("cat: ").append(input).append(": No such file or directory\n");
                throw new Exception(output.toString());
            }
        }

        return output.toString();

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
            throw new Exception("rm: File can not be deleted.");
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
        throw new Exception("cd: No such file or directory!");
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
                throw new Exception("mv: No such file or directory!");
            }
        }
        else {
            throw new Exception("mv: Missing operand");
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
            throw new Exception("rmdir: Not a directory!");
        } else if (!removedFile.exists()) {
            throw new Exception("rmdir: No such directory!");
        }
        // To make sure that it doesnot return null
        if (Objects.requireNonNull(removedFile.list()).length > 0){
            throw new Exception("rmdir: Directory has children!");
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

    public String getHelpMessage() {
        String helpMessage = "pwd: It gives you the current directory path you are on \n" +
                "cd: Makes you change the directory to another one you want \n" +
                "ls: List all files and directories in the current directory you are in \n" +
                "ls -a: List all files and directories hidden or not in the current directory \n" +
                "ls -r: List all files and directories in reversed way in the current directory \n" +
                "mkdir: Create a new directory \n" +
                "touch: Create a new file \n" +
                "rmdir: Remove an existing directory \n" +
                "rm: Remove an existing file \n" +
                "cat: Displays content of file \n" +
                "cat >: Replace content of file \n" +
                "cat >>: Append content of file \n";
        return helpMessage;
    }
}
