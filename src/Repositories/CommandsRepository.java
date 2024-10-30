package Repositories;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class CommandsRepository {
    public String pwd(File directory) throws IOException {
        return directory.getCanonicalPath();
    }

    public void rm (ArrayList<String> inputs) throws Exception {
        File file = new File(inputs.getFirst());
        if (file.exists() && file.isFile()) {
            file.delete();
        }
        else {
            throw new Exception("File can not be deleted.");
        }
    }
}
