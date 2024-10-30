package Repositories;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class CommandsRepository {
    public String pwd(File directory) throws IOException {
        return directory.getCanonicalPath();
    }

    public String cd (File _workingDirectory, ArrayList<String> inputs) throws Exception {
        String directory_name = inputs.getFirst();
        String current_directory = _workingDirectory.toString();
        // Here to make the ./ return back
        if (directory_name.equals("./")) {
            for (int i = current_directory.length() - 1; i >= 0; i-- ) {
                if (current_directory.charAt(i) == '\\') {
                    // We make the current_directory the same without the last folder
                    directory_name = current_directory.substring(0, i);
                    break;
                }
            }
        }
        else {
            // We add the directory to the _workingdirectory
            directory_name = _workingDirectory.toString() + "/" + directory_name;
        }
        // We made a file to see if it exists and isDirectory or not
        File directory = new File(directory_name);
        if (directory.exists() && directory.isDirectory()) {
            return directory_name;
        }
        throw new Exception("Not Valid Path");
    }
}