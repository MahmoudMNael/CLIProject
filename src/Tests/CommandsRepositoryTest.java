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
    @DisplayName("rmdir fails when trying to remove dir with subfolders")
    void rmdirFailureDirWithSubFolders() throws Exception {
        _commandRepository = new CommandsRepository();
        Exception exception = assertThrows(Exception.class, () -> _commandRepository.rmdir(new ArrayList<>(Arrays.asList("FullFolder"))));
        assertEquals("This directory has children so it can not be deleted", exception.getMessage());
    }

    @Test
    @DisplayName("rmdir fails when trying to remove file")
    void rmdirFailureFile() throws Exception {
        _commandRepository = new CommandsRepository();
        Exception exception = assertThrows(Exception.class, () -> _commandRepository.rmdir(new ArrayList<>(Arrays.asList("README.md"))));
        assertEquals("It is not directory to be removed", exception.getMessage());
    }

}