package Tests;

import Repositories.CommandsRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import static org.junit.jupiter.api.Assertions.*;

class CommandsRepositoryTest {
    private CommandsRepository _commandRepository;

    @Test
    @DisplayName("pwd succeeds when calling the function")
    void pwdSuccess() throws IOException {
        _commandRepository = new CommandsRepository();
        assertInstanceOf(String.class, _commandRepository.pwd(new File(".")));
    }

    // Test will not succeed twice in a row.
    // You should delete the created directory or run the rmdir test before running it again
    // Or run the whole test class bundle
    @Test
    @DisplayName("mkdir succeeds when given valid inputs")
    void mkdirSuccess() throws Exception {
        _commandRepository = new CommandsRepository();
        assertDoesNotThrow(() -> _commandRepository.mkdir(new ArrayList<>(Arrays.asList("./testdirectory"))));
    }

    @Test
    @DisplayName("mkdir fails when not given any input")
    void mkdirFailureEmptyInput() throws Exception {
        _commandRepository = new CommandsRepository();
        Exception exception = assertThrows(Exception.class, () -> _commandRepository.mkdir(new ArrayList<>()));
        assertEquals("mkdir: Missing operand!", exception.getMessage());
    }

    @Test
    @DisplayName("mkdir fails when given an already existing directory")
    void mkdirFailureExistingDirectory() throws Exception {
        _commandRepository = new CommandsRepository();
        Exception exception = assertThrows(Exception.class, () -> _commandRepository.mkdir(new ArrayList<>(Arrays.asList("src"))));
        assertEquals("mkdir: Directory already exists!", exception.getMessage());
    }

    @Test
    @DisplayName("mkdir fails when given a non valid path")
    void mkdirFailureNoDirectory() throws Exception {
        _commandRepository = new CommandsRepository();
        Exception exception = assertThrows(Exception.class, () -> _commandRepository.mkdir(new ArrayList<>(Arrays.asList("blahblah/testdir"))));
        assertEquals("mkdir: No such file or directory!", exception.getMessage());
    }

    @Test
    @DisplayName("mv fails when given file is not found")
    void mvFailureNotFound() throws Exception {
        _commandRepository = new CommandsRepository();
        Exception exception = assertThrows(Exception.class, () -> _commandRepository.mv(new ArrayList<>(Arrays.asList("MovedFiless","src/MovedFile"))));
        assertEquals("File or Target path is not found", exception.getMessage());
    }

    @Test
    @DisplayName("mv fails when the given inputs is not enough")
    void mvFailureInputInvalid() throws Exception {
        _commandRepository = new CommandsRepository();
        Exception exception = assertThrows(Exception.class, () -> _commandRepository.mv(new ArrayList<>(Arrays.asList("MovedFile"))));
        assertEquals("Input is not valid, Please enter the file name and the path you want to move it to", exception.getMessage());
    }

}