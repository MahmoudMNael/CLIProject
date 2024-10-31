package Tests;

import Repositories.CommandsRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import static org.junit.jupiter.api.Assertions.*;

class CommandsRepositoryTest {
    private CommandsRepository _commandRepository = new CommandsRepository();

    @Test
    @DisplayName("pwd succeeds when calling the function")
    void pwdSuccess() throws IOException {
        assertInstanceOf(String.class, _commandRepository.pwd(new File(".")));
    }

    // Test will not succeed twice in a row.
    // You should delete the created directory or run the rmdir test before running it again
    // Or run the whole test class bundle
    @Test
    @DisplayName("mkdir succeeds when given valid inputs")
    void mkdirSuccess() throws Exception {
        assertDoesNotThrow(() -> _commandRepository.mkdir(new ArrayList<>(Arrays.asList("./testdirectory"))));
    }

    @Test
    @DisplayName("mkdir fails when not given any input")
    void mkdirFailureEmptyInput() throws Exception {
        Exception exception = assertThrows(Exception.class, () -> _commandRepository.mkdir(new ArrayList<>()));
        assertEquals("mkdir: Missing operand!", exception.getMessage());
    }

    @Test
    @DisplayName("mkdir fails when given an already existing directory")
    void mkdirFailureExistingDirectory() throws Exception {
        Exception exception = assertThrows(Exception.class, () -> _commandRepository.mkdir(new ArrayList<>(Arrays.asList("src"))));
        assertEquals("mkdir: Directory already exists!", exception.getMessage());
    }

    @Test
    @DisplayName("mkdir fails when given a non valid path")
    void mkdirFailureNoDirectory() throws Exception {
        Exception exception = assertThrows(Exception.class, () -> _commandRepository.mkdir(new ArrayList<>(Arrays.asList("blahblah/testdir"))));
        assertEquals("mkdir: No such file or directory!", exception.getMessage());
    }

    @Test
    @DisplayName("ls succeeds when given no flags")
    void lsSuccessNoFlags() throws Exception {
        assertInstanceOf(String.class, _commandRepository.ls(new File("."), new ArrayList<>()));
    }

    @Test
    @DisplayName("ls succeeds when given -a flag")
    void lsSuccessAFlag() throws Exception {
        String list = _commandRepository.ls(new File("."), new ArrayList<>(Arrays.asList("-a")));
        assertInstanceOf(String.class, list);
        assertTrue(list.contains("./"));
    }

    @Test
    @DisplayName("ls succeeds when given -r flag")
    void lsSuccessRFlag() throws Exception {
        assertInstanceOf(String.class, _commandRepository.ls(new File("."), new ArrayList<>(Arrays.asList("-r"))));
    }

    @Test
    @DisplayName("ls succeeds when given -a -r flags")
    void lsSuccessARFlags() throws Exception {
        String list = _commandRepository.ls(new File("."), new ArrayList<>(Arrays.asList("-a", "-r")));
        assertInstanceOf(String.class, list);
        assertTrue(list.contains("./"));
    }
}