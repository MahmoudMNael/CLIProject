package Tests;

import Repositories.CommandsRepository;
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
    @DisplayName("cd subfolder one time")
    void cd() throws Exception{
        _commandRepository = new CommandsRepository();
        ArrayList<String> array = new ArrayList<String>();
        array.add("src/Tests");
        File dir = new File("D:\\Programming\\CLIProject");
        System.out.println(_commandRepository.cd(dir,array));
        assertEquals("D:\\Programming\\CLIProject/src/Tests", _commandRepository.cd(dir,array));
    }

    @Test
    @DisplayName("cd spaced folder")
    void cdSpaced() throws Exception{
        _commandRepository = new CommandsRepository();
        ArrayList<String> array = new ArrayList<String>();
        array.add("Space Test");
        File dir = new File("D:\\Programming\\CLIProject");
        System.out.println(_commandRepository.cd(dir,array));
        assertEquals("D:\\Programming\\CLIProject/Space Test", _commandRepository.cd(dir,array));
    }

    @Test
    @DisplayName("cd not found")
    void cdException() throws Exception{
        _commandRepository = new CommandsRepository();
        ArrayList<String> array = new ArrayList<String>();
        array.add("abcd");
        File dir = new File("D:\\Programming\\CLIProject");
        boolean thrown = false;
        try {
            _commandRepository.cd(dir,array);
        } catch (Exception e) {
            thrown = true;
        }
        assertTrue(thrown);
    }
}