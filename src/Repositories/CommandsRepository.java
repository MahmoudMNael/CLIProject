package Repositories;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;


public class CommandsRepository {
    public String pwd(File directory) throws IOException {
        return directory.getCanonicalPath();
    }

    public static void touch(File directory, ArrayList<String> inputs) throws IOException {
        for (String input : inputs) {
            File file = new File(directory.getCanonicalPath() + "/" + input);
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
}
