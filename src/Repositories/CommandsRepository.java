package Repositories;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class CommandsRepository {
    public String pwd(File directory) throws IOException {
        return directory.getCanonicalPath();
    }
}
