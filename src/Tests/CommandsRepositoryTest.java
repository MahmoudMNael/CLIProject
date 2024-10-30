package Tests;

import Repositories.CommandsRepository;
import org.junit.Assert;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class CommandsRepositoryTest {
    private CommandsRepository _commandRepository;

    @Test
    @DisplayName("it succeeds when calling the function")
    void pwd() throws IOException {
        _commandRepository = new CommandsRepository();
        assertInstanceOf(String.class, _commandRepository.pwd(new File(".")));
    }

    @Test
    void rm() throws Exception {
        _commandRepository = new CommandsRepository();
        File created_file = new File("RemovedFileTest");
        created_file.createNewFile();
        ArrayList<String> array = new ArrayList<String>();
        array.add("RemovedFileTest");
        _commandRepository.rm(array);
        boolean checkIfExists = false;
        if (created_file.exists()){
            checkIfExists = true;
        }
        Assert.assertFalse(checkIfExists);
    }
}